package com.airohit.agriculture.module.system.service.tenant;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.airohit.agriculture.framework.common.enums.CommonStatusEnum;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.common.util.collection.CollectionUtils;
import com.airohit.agriculture.framework.common.util.date.DateUtils;
import com.airohit.agriculture.framework.datapermission.core.annotation.DataPermission;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.framework.tenant.config.TenantProperties;
import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.framework.tenant.core.context.TenantContextHolder;
import com.airohit.agriculture.framework.tenant.core.util.TenantUtils;
import com.airohit.agriculture.module.system.api.slave.vo.tenant.TenantCreateReqVO;
import com.airohit.agriculture.module.system.api.slave.vo.tenant.TenantExportReqVO;
import com.airohit.agriculture.module.system.api.slave.vo.tenant.TenantPageReqVO;
import com.airohit.agriculture.module.system.api.slave.vo.tenant.TenantUpdateReqVO;
import com.airohit.agriculture.module.system.convert.tenant.TenantConvert;
import com.airohit.agriculture.module.system.dal.dataobject.farm.FarmTenantDO;
import com.airohit.agriculture.module.system.dal.dataobject.permission.MenuDO;
import com.airohit.agriculture.module.system.dal.dataobject.permission.RoleDO;
import com.airohit.agriculture.module.system.dal.dataobject.tenant.TenantDO;
import com.airohit.agriculture.module.system.dal.dataobject.tenant.TenantPackageDO;
import com.airohit.agriculture.module.system.dal.mysql.farm.FarmTenantMapper;
import com.airohit.agriculture.module.system.dal.mysql.tenant.TenantMapper;
import com.airohit.agriculture.module.system.entity.admin.permission.vo.role.RoleCreateReqVO;
import com.airohit.agriculture.module.system.enums.permission.RoleCodeEnum;
import com.airohit.agriculture.module.system.enums.permission.RoleTypeEnum;
import com.airohit.agriculture.module.system.service.permission.MenuService;
import com.airohit.agriculture.module.system.service.permission.PermissionService;
import com.airohit.agriculture.module.system.service.permission.RoleService;
import com.airohit.agriculture.module.system.service.tenant.handler.TenantInfoHandler;
import com.airohit.agriculture.module.system.service.tenant.handler.TenantMenuHandler;
import com.airohit.agriculture.module.system.service.user.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.system.enums.ErrorCodeConstants.*;
import static java.util.Collections.singleton;

/**
 * 租户 Service 实现类
 *
 * @author shiminghao
 */
@Service
@Validated
@Slf4j
public class TenantServiceImpl implements TenantService {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired(required = false) // 由于 agriculture.tenant.enable 配置项，可以关闭多租户的功能，所以这里只能不强制注入
    private TenantProperties tenantProperties;
    @Resource
    private FarmTenantMapper farmTenantMapper;

    @Resource
    private TenantMapper tenantMapper;


    @Resource
    @Lazy
    private TenantService tenantService;

    @Resource
    private TenantPackageService tenantPackageService;
    @Resource
    @Lazy // 延迟，避免循环依赖报错
    private AdminUserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;
    @Resource
    private PermissionService permissionService;

    private static boolean isSystemTenant(TenantDO tenant) {
        return Objects.equals(tenant.getPackageId(), TenantDO.PACKAGE_ID_SYSTEM);
    }

    @Override
    public List<Long> getTenantIds() {
        List<TenantDO> tenants = tenantMapper.selectList();
        return CollectionUtils.convertList(tenants, TenantDO::getId);
    }

    @Override
    public void validTenant(Long id) {
        TenantDO tenant = getTenant(id);
        if (tenant == null) {
            throw exception(TENANT_NOT_EXISTS);
        }
        if (tenant.getStatus().equals(CommonStatusEnum.DISABLE.getStatus())) {
            throw exception(TENANT_DISABLE, tenant.getName());
        }
        if (DateUtils.isExpired(tenant.getExpireTime())) {
            throw exception(TENANT_EXPIRE, tenant.getName());
        }
    }

    @Override
    public List<Integer> getAllFarmId() {
        return tenantMapper.selectList().stream().map(TenantDO::getFarmId).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataPermission(enable = false) // 租户创建，不需要数据权限，避免出现被数据权限拦截
    public Long createTenant(TenantCreateReqVO createReqVO) {
        // 校验套餐被禁用
        TenantPackageDO tenantPackage = tenantPackageService.validTenantPackage(createReqVO.getPackageId());

        // 创建租户
        TenantDO tenant = TenantConvert.INSTANCE.convert(createReqVO);
        tenantMapper.insert(tenant);

        TenantUtils.execute(tenant.getId(), () -> {
            // 创建角色
            Long roleId = createRole(tenantPackage);
            // 创建用户，并分配角色
            Long userId = createUser(roleId, createReqVO);
            // 修改租户的管理员
            tenantMapper.updateById(new TenantDO().setId(tenant.getId()).setContactUserId(userId));
        });
        return tenant.getId();
    }

    private Long createUser(Long roleId, TenantCreateReqVO createReqVO) {
        // 创建用户
        Long userId = userService.createUser(TenantConvert.INSTANCE.convert02(createReqVO));
        // 分配角色
        permissionService.assignUserRole(userId, singleton(roleId));
        return userId;
    }

    private Long createRole(TenantPackageDO tenantPackage) {
        // 创建租户管理员角色
        RoleCreateReqVO reqVO = new RoleCreateReqVO();
        reqVO.setName(RoleCodeEnum.TENANT_ADMIN.getName()).setCode(RoleCodeEnum.TENANT_ADMIN.getCode())
                .setSort(0).setRemark("系统自动生成");
        Long roleId = roleService.createRole(reqVO, RoleTypeEnum.SYSTEM.getType());
        RoleCreateReqVO peasant = new RoleCreateReqVO();
        peasant.setName(RoleCodeEnum.PEASANT_ROLE.getName()).setCode(RoleCodeEnum.PEASANT_ROLE.getCode())
                .setSort(1).setRemark("系统自动生成");
        roleService.createRole(peasant, RoleTypeEnum.SYSTEM.getType());
        //创建
        // 分配权限
        permissionService.assignRoleMenu(roleId, tenantPackage.getMenuIds());
        return roleId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTenant(TenantUpdateReqVO updateReqVO) {
        // 校验存在
        TenantDO tenant = checkUpdateTenant(updateReqVO.getId());
        // 校验套餐被禁用
        TenantPackageDO tenantPackage = tenantPackageService.validTenantPackage(updateReqVO.getPackageId());

        // 更新租户
        TenantDO updateObj = TenantConvert.INSTANCE.convert(updateReqVO);
        tenantMapper.updateById(updateObj);
        // 如果套餐发生变化，则修改其角色的权限
        if (ObjectUtil.notEqual(tenant.getPackageId(), updateReqVO.getPackageId())) {
            updateTenantRoleMenu(tenant.getId(), tenantPackage.getMenuIds());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTenantRoleMenu(Long tenantId, Set<Long> menuIds) {
        TenantUtils.execute(tenantId, () -> {
            // 获得所有角色
            List<RoleDO> roles = roleService.getRoles(null);
            roles.forEach(role -> Assert.isTrue(tenantId.equals(role.getTenantId()), "角色({}/{}) 租户不匹配",
                    role.getId(), role.getTenantId(), tenantId)); // 兜底校验
            // 重新分配每个角色的权限
            roles.forEach(role -> {
                // 如果是租户管理员，重新分配其权限为租户套餐的权限
                if (Objects.equals(role.getCode(), RoleCodeEnum.TENANT_ADMIN.getCode())) {
                    permissionService.assignRoleMenu(role.getId(), menuIds);
                    log.info("[updateTenantRoleMenu][租户管理员({}/{}) 的权限修改为({})]", role.getId(), role.getTenantId(), menuIds);
                    return;
                }
                // 如果是其他角色，则去掉超过套餐的权限
                Set<Long> roleMenuIds = permissionService.getRoleMenuIds(role.getId());
                roleMenuIds = CollUtil.intersectionDistinct(roleMenuIds, menuIds);
                permissionService.assignRoleMenu(role.getId(), roleMenuIds);
                log.info("[updateTenantRoleMenu][角色({}/{}) 的权限修改为({})]", role.getId(), role.getTenantId(), roleMenuIds);
            });
        });
    }

    @Override
    public void deleteTenant(Long id) {
        // 校验存在
        checkUpdateTenant(id);
        // 删除
        tenantMapper.deleteById(id);
    }

    private TenantDO checkUpdateTenant(Long id) {
        TenantDO tenant = tenantMapper.selectById(id);
        if (tenant == null) {
            throw exception(TENANT_NOT_EXISTS);
        }
        // 内置租户，不允许删除
        if (isSystemTenant(tenant)) {
            throw exception(TENANT_CAN_NOT_UPDATE_SYSTEM);
        }
        return tenant;
    }

    @Override
    public TenantDO getTenant(Long id) {
        return tenantMapper.selectById(id);
    }

    @Override
    public PageResult<TenantDO> getTenantPage(TenantPageReqVO pageReqVO) {
        return tenantMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TenantDO> getTenantList(TenantExportReqVO exportReqVO) {
        return tenantMapper.selectList(exportReqVO);
    }

    @Override
    public List<TenantDO> getNotUseTenantList() {
        return tenantMapper.selectList(new LambdaQueryWrapperX<TenantDO>()
                .not(i -> i.eq(TenantDO::getId, 1)));
    }

    @Override
    @TenantIgnore
    public List<Long> getFarmTenant() {
        return farmTenantMapper.selectList(FarmTenantDO::getDeleted, 0)
                .stream().map(FarmTenantDO::getTenantId).distinct().collect(Collectors.toList());
    }

    @Override
    public TenantDO getTenantByName(String name) {
        return tenantMapper.selectByName(name);
    }

    @Override
    public Long getTenantCountByPackageId(Long packageId) {
        return tenantMapper.selectCountByPackageId(packageId);
    }

    @Override
    public List<TenantDO> getTenantListByPackageId(Long packageId) {
        return tenantMapper.selectListByPackageId(packageId);
    }

    @Override
    public void handleTenantInfo(TenantInfoHandler handler) {
        // 如果禁用，则不执行逻辑
        if (isTenantDisable()) {
            return;
        }
        // 获得租户
        TenantDO tenant = getTenant(TenantContextHolder.getRequiredTenantId());
        // 执行处理器
        handler.handle(tenant);
    }

    @Override
    public void handleTenantMenu(TenantMenuHandler handler) {
        // 如果禁用，则不执行逻辑
        if (isTenantDisable()) {
            return;
        }
        // 获得租户，然后获得菜单
        TenantDO tenant = getTenant(TenantContextHolder.getRequiredTenantId());
        Set<Long> menuIds;
        if (isSystemTenant(tenant)) { // 系统租户，菜单是全量的
            menuIds = CollectionUtils.convertSet(menuService.getMenus(), MenuDO::getId);
        } else {
            menuIds = tenantPackageService.getTenantPackage(tenant.getPackageId()).getMenuIds();
        }
        // 执行处理器
        handler.handle(menuIds);
    }

    private boolean isTenantDisable() {
        return tenantProperties == null || Boolean.FALSE.equals(tenantProperties.getEnable());
    }

}
