package com.airohit.agriculture.module.system.service.permission;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.airohit.agriculture.framework.common.enums.CommonStatusEnum;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.common.util.collection.CollectionUtils;
import com.airohit.agriculture.framework.tenant.core.util.TenantUtils;
import com.airohit.agriculture.module.system.convert.permission.RoleConvert;
import com.airohit.agriculture.module.system.dal.dataobject.permission.RoleDO;
import com.airohit.agriculture.module.system.dal.mysql.permission.RoleMapper;
import com.airohit.agriculture.module.system.entity.admin.permission.vo.role.RoleCreateReqVO;
import com.airohit.agriculture.module.system.entity.admin.permission.vo.role.RoleExportReqVO;
import com.airohit.agriculture.module.system.entity.admin.permission.vo.role.RolePageReqVO;
import com.airohit.agriculture.module.system.entity.admin.permission.vo.role.RoleUpdateReqVO;
import com.airohit.agriculture.module.system.enums.permission.DataScopeEnum;
import com.airohit.agriculture.module.system.enums.permission.RoleCodeEnum;
import com.airohit.agriculture.module.system.enums.permission.RoleTypeEnum;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.system.enums.ErrorCodeConstants.*;

/**
 * 角色 Service 实现类
 *
 * @author shiminghao
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    /**
     * 定时执行 {@link #schedulePeriodicRefresh()} 的周期
     * 因为已经通过 Redis Pub/Sub 机制，所以频率不需要高
     */
    private static final long SCHEDULER_PERIOD = 5 * 60 * 1000L;

    /**
     * 角色缓存
     * key：角色编号 {@link RoleDO#getId()}
     * <p>
     * 这里声明 volatile 修饰的原因是，每次刷新时，直接修改指向
     */
    @Getter
    private volatile ImmutableMap<Long, RoleDO> roleCache;
    /**
     * 缓存角色的最大更新时间，用于后续的增量轮询，判断是否有更新
     */
    @Getter
    private volatile LocalDateTime maxUpdateTime;

    @Resource
    private PermissionService permissionService;

    @Resource
    private RoleMapper roleMapper;


    @Resource
    @Lazy // 注入自己，所以延迟加载
    private RoleService self;

    /**
     * 初始化 {@link #roleCache} 缓存
     */
    @Override
    @PostConstruct
    public void initLocalCache() {
        initLocalCacheIfUpdate(null);
    }

    @Scheduled(fixedDelay = SCHEDULER_PERIOD, initialDelay = SCHEDULER_PERIOD)
    public void schedulePeriodicRefresh() {
        initLocalCacheIfUpdate(this.maxUpdateTime);
    }

    /**
     * 刷新本地缓存
     *
     * @param maxUpdateTime 最大更新时间
     *                      1. 如果 maxUpdateTime 为 null，则“强制”刷新缓存
     *                      2. 如果 maxUpdateTime 不为 null，判断自 maxUpdateTime 是否有数据发生变化，有的情况下才刷新缓存
     */
    private void initLocalCacheIfUpdate(LocalDateTime maxUpdateTime) {
        // 注意：忽略自动多租户，因为要全局初始化缓存
        TenantUtils.executeIgnore(() -> {
            // 第一步：基于 maxUpdateTime 判断缓存是否刷新。
            // 如果没有增量的数据变化，则不进行本地缓存的刷新
            if (maxUpdateTime != null
                    && roleMapper.selectCountByUpdateTimeGt(maxUpdateTime) == 0) {
                log.info("[initLocalCacheIfUpdate][数据未发生变化({})，本地缓存不刷新]", maxUpdateTime);
                return;
            }
            List<RoleDO> roleList = roleMapper.selectList();
            log.info("[initLocalCacheIfUpdate][缓存角色，数量为:{}]", roleList.size());
            // 第二步：构建缓存。
            Map<Long, RoleDO> longRoleDOMap = CollectionUtils.convertMap(roleList, RoleDO::getId);
            ImmutableMap.Builder<Long, RoleDO> builder = ImmutableMap.builder();
            builder.putAll(longRoleDOMap);
            roleCache = builder.build();
            // 第三步：设置最新的 maxUpdateTime，用于下次的增量判断。
            this.maxUpdateTime = CollectionUtils.getMaxValue(roleList, RoleDO::getUpdateTime);
        });
    }

    @Override
    @Transactional
    public Long createRole(RoleCreateReqVO reqVO, Integer type) {
        // 校验角色
        checkDuplicateRole(reqVO.getName(), reqVO.getCode(), null);
        // 插入到数据库
        RoleDO role = RoleConvert.INSTANCE.convert(reqVO);
        role.setType(ObjectUtil.defaultIfNull(type, RoleTypeEnum.CUSTOM.getType()));
        role.setStatus(CommonStatusEnum.ENABLE.getStatus());
        role.setDataScope(DataScopeEnum.ALL.getScope()); // 默认可查看所有数据。原因是，可能一些项目不需要项目权限
        roleMapper.insert(role);
        // 发送刷新消息
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                initLocalCache();
            }
        });
        // 返回
        return role.getId();
    }

    @Override
    public void updateRole(RoleUpdateReqVO reqVO) {
        // 校验是否可以更新
        checkUpdateRole(reqVO.getId());
        // 校验角色的唯一字段是否重复
        checkDuplicateRole(reqVO.getName(), reqVO.getCode(), reqVO.getId());

        // 更新到数据库
        RoleDO updateObject = RoleConvert.INSTANCE.convert(reqVO);
        roleMapper.updateById(updateObject);
        // 发送刷新消息
        initLocalCache();
    }

    @Override
    public void updateRoleStatus(Long id, Integer status) {
        // 校验是否可以更新
        checkUpdateRole(id);
        // 更新状态
        RoleDO updateObject = new RoleDO();
        updateObject.setId(id);
        updateObject.setStatus(status);
        roleMapper.updateById(updateObject);
        // 发送刷新消息
        initLocalCache();
    }

    @Override
    public void updateRoleDataScope(Long id, Integer dataScope, Set<Long> dataScopeDeptIds) {
        // 校验是否可以更新
        checkUpdateRole(id);
        // 更新数据范围
        RoleDO updateObject = new RoleDO();
        updateObject.setId(id);
        updateObject.setDataScope(dataScope);
        updateObject.setDataScopeDeptIds(dataScopeDeptIds);
        roleMapper.updateById(updateObject);
        // 发送刷新消息
        initLocalCache();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long id) {
        // 校验是否可以更新
        this.checkUpdateRole(id);
        // 标记删除
        roleMapper.deleteById(id);
        // 删除相关数据
        permissionService.processRoleDeleted(id);
        // 发送刷新消息. 注意，需要事务提交后，在进行发送刷新消息。不然 db 还未提交，结果缓存先刷新了
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

            @Override
            public void afterCommit() {
                initLocalCache();
            }

        });
    }

    @Override
    public RoleDO getRoleFromCache(Long id) {
        return roleCache.get(id);
    }

    @Override
    public List<RoleDO> getRoles(@Nullable Collection<Integer> statuses) {
        if (CollUtil.isEmpty(statuses)) {
            return roleMapper.selectList();
        }
        return roleMapper.selectListByStatus(statuses);
    }

    @Override
    public List<RoleDO> getRolesFromCache(Collection<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return roleCache.values().stream().filter(roleDO -> ids.contains(roleDO.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasAnySuperAdmin(Collection<RoleDO> roleList) {
        if (CollectionUtil.isEmpty(roleList)) {
            return false;
        }
        return roleList.stream().anyMatch(role -> RoleCodeEnum.isSuperAdmin(role.getCode()));
    }

    @Override
    public RoleDO getRole(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public RoleDO getRoleByCode(String code, Long tenantId) {
        AtomicReference<RoleDO> roleDO = new AtomicReference<>();
        TenantUtils.execute(tenantId, () -> {
            roleDO.set(roleMapper.selectOne(RoleDO::getCode, code));
        });
        return roleDO.get();
    }

    @Override
    public PageResult<RoleDO> getRolePage(RolePageReqVO reqVO) {
        return roleMapper.selectPage(reqVO);
    }

    @Override
    public List<RoleDO> getRoleList(RoleExportReqVO reqVO) {
        return roleMapper.selectList(reqVO);
    }

    /**
     * 校验角色的唯一字段是否重复
     * <p>
     * 1. 是否存在相同名字的角色
     * 2. 是否存在相同编码的角色
     *
     * @param name 角色名字
     * @param code 角色额编码
     * @param id   角色编号
     */
    @VisibleForTesting
    public void checkDuplicateRole(String name, String code, Long id) {
        // 0. 超级管理员，不允许创建
        if (RoleCodeEnum.isSuperAdmin(code)) {
            throw exception(ROLE_ADMIN_CODE_ERROR, code);
        }
        // 1. 该 name 名字被其它角色所使用
        RoleDO role = roleMapper.selectByName(name);
        if (role != null && !role.getId().equals(id)) {
            throw exception(ROLE_NAME_DUPLICATE, name);
        }
        // 2. 是否存在相同编码的角色
        if (!StringUtils.hasText(code)) {
            return;
        }
        // 该 code 编码被其它角色所使用
        role = roleMapper.selectByCode(code);
        if (role != null && !role.getId().equals(id)) {
            throw exception(ROLE_CODE_DUPLICATE, code);
        }
    }

    /**
     * 校验角色是否可以被更新
     *
     * @param id 角色编号
     */
    @VisibleForTesting
    public void checkUpdateRole(Long id) {
        RoleDO roleDO = roleMapper.selectById(id);
        if (roleDO == null) {
            throw exception(ROLE_NOT_EXISTS);
        }
        // 内置角色，不允许删除
        if (RoleTypeEnum.SYSTEM.getType().equals(roleDO.getType())) {
            throw exception(ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE);
        }
    }

    @Override
    public void validRoles(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得角色信息
        List<RoleDO> roles = roleMapper.selectBatchIds(ids);
        Map<Long, RoleDO> roleMap = CollectionUtils.convertMap(roles, RoleDO::getId);
        // 校验
        ids.forEach(id -> {
            RoleDO role = roleMap.get(id);
            if (role == null) {
                throw exception(ROLE_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(role.getStatus())) {
                throw exception(ROLE_IS_DISABLE, role.getName());
            }
        });
    }
}
