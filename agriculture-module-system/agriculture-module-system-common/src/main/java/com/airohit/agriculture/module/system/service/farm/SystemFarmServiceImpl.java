package com.airohit.agriculture.module.system.service.farm;

import cn.hutool.core.collection.CollUtil;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.framework.tenant.core.context.FarmTenantContextHolder;
import com.airohit.agriculture.module.system.api.slave.vo.farm.FarmCreateReqVO;
import com.airohit.agriculture.module.system.api.slave.vo.farm.FarmExportReqVO;
import com.airohit.agriculture.module.system.api.slave.vo.farm.FarmPageReqVO;
import com.airohit.agriculture.module.system.api.slave.vo.farm.FarmUpdateReqVO;
import com.airohit.agriculture.module.system.convert.farm.FarmConvert;
import com.airohit.agriculture.module.system.dal.dataobject.farm.FarmDO;
import com.airohit.agriculture.module.system.dal.dataobject.farm.FarmTenantDO;
import com.airohit.agriculture.module.system.dal.dataobject.tenant.TenantDO;
import com.airohit.agriculture.module.system.dal.mysql.farm.SystemFarmMapper;
import com.airohit.agriculture.module.system.dal.mysql.farm.FarmTenantMapper;
import com.airohit.agriculture.module.system.dal.mysql.tenant.TenantMapper;
import com.airohit.agriculture.module.system.service.tenant.TenantService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.system.enums.ErrorCodeConstants.*;

/**
 * 农场 Service 实现类
 *
 * @author shiminghao
 */
@Service
@Validated
public class SystemFarmServiceImpl implements SystemFarmService {

    @Resource
    private SystemFarmMapper Mapper;
    @Resource
    private TenantService tenantService;
    @Resource
    private TenantMapper tenantMapper;
    @Resource
    private FarmTenantMapper farmTenantMapper;

    @Override
    @TenantIgnore
    public Integer create(FarmCreateReqVO createReqVO) {
        if (Objects.isNull(createReqVO.getTenantId())) {
            throw exception(TENANT_IS_NULL);
        }
        // 插入
//        if (farmTenantMapper.selectCount(new LambdaQueryWrapperX<FarmTenantDO>()
//                .eq(FarmTenantDO::getTenantId, createReqVO.getTenantId())
//                .eq(FarmTenantDO::getDeleted, 0)) > 0) {
//            throw exception(TENANT_ALREADY_USE);
//        }
        FarmDO farmDO = FarmConvert.INSTANCE.convert(createReqVO);
        farmDO.setCoordinate(JSONObject.toJSONString(createReqVO.getPosVoList()));
        Mapper.insert(farmDO);
        FarmTenantDO farmTenantDO = new FarmTenantDO();
        farmTenantDO.setTenantId(createReqVO.getTenantId());
        farmTenantDO.setFarmId(farmDO.getId());
        farmTenantMapper.insert(farmTenantDO);
        // 返回
        return farmDO.getId();
    }

    @Override
    @TenantIgnore
    public void update(FarmUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateExists(updateReqVO.getId());
        // 更新
        FarmDO updateObj = FarmConvert.INSTANCE.convert(updateReqVO);
        updateObj.setCoordinate(JSONObject.toJSONString(updateReqVO.getPosVoList()));
        Mapper.updateById(updateObj);
    }

    @Override
    @TenantIgnore
    public void delete(Integer id) {
        // 校验存在
        this.validateExists(id);
        if (tenantMapper.selectOne(TenantDO::getFarmId, id) != null) {
            throw exception(FARM_ALREADY_USE);
        }
        // 删除
        Mapper.deleteById(id);
        farmTenantMapper.delete(new LambdaQueryWrapperX<FarmTenantDO>().eq(FarmTenantDO::getFarmId, id));
    }

    private void validateExists(Integer id) {
        if (Mapper.selectById(id) == null) {
            throw exception(FARM_NOT_EXISTS);
        }

    }

    @Override
    @TenantIgnore
    public FarmDO get(Integer id) {
        return Mapper.selectById(id);
    }

    @Override
    @TenantIgnore
    public FarmDO getFarmByTenant(Long tenantId) {
        FarmTenantDO tenant = farmTenantMapper.selectOne(new LambdaQueryWrapperX<FarmTenantDO>()
                .eq(FarmTenantDO::getTenantId, tenantId)
                .eqIfPresent(FarmTenantDO::getFarmId, FarmTenantContextHolder.getFarmTenantId())
                .eq(FarmTenantDO::getDeleted, 0));
        if (Objects.isNull(tenant)) {
            throw exception(TENANT_NOT_USE_FARM);
        }
        return Mapper.selectById(tenant.getFarmId());
    }

    @Override
    @TenantIgnore
    public List<FarmDO> getFarmListByTenant(Long tenantId) {
        List<Integer> collect = farmTenantMapper.selectList(new LambdaQueryWrapperX<FarmTenantDO>()
                .eq(FarmTenantDO::getTenantId, tenantId)
                .eq(FarmTenantDO::getDeleted, 0)).stream().map(FarmTenantDO::getFarmId).collect(Collectors.toList());


        return Mapper.selectList(new LambdaQueryWrapperX<FarmDO>().in(FarmDO::getId, collect));
    }

    @Override
    public List<FarmDO> getList(Collection<Integer> ids) {
        return Mapper.selectBatchIds(ids);
    }

    @Override
    @TenantIgnore
    public PageResult<FarmDO> getPage(FarmPageReqVO pageReqVO) {
        return Mapper.selectPage(pageReqVO);
    }

    @Override
    public List<FarmDO> getList(FarmExportReqVO exportReqVO) {
        return Mapper.selectList(exportReqVO);
    }

    @Override
    @TenantIgnore
    public List<FarmDO> getFarmList() {
        List<Integer> allFarmId = tenantService.getAllFarmId();
        if (CollUtil.isEmpty(allFarmId)) {
            return Mapper.selectList();
        } else {
            return Mapper.selectList(new LambdaQueryWrapperX<FarmDO>()
                    .notIn(FarmDO::getId, allFarmId));
        }

    }

    @Override
    @TenantIgnore
    public List<FarmDO> getAllFarmList() {
        List<Long> tenantIdList = tenantMapper.selectList(new LambdaQueryWrapperX<TenantDO>()
                .eq(TenantDO::getDeleted, 0)).stream().map(TenantDO::getId).collect(Collectors.toList());
        if (CollUtil.isEmpty(tenantIdList)) {
            return new ArrayList<>();
        }
        List<Integer> collect = farmTenantMapper.selectList(new LambdaQueryWrapperX<FarmTenantDO>()
                        .eq(FarmTenantDO::getDeleted, 0)
                        .inIfPresent(FarmTenantDO::getTenantId, tenantIdList))
                .stream().map(FarmTenantDO::getFarmId).collect(Collectors.toList());
        return Mapper.selectList(new LambdaQueryWrapperX<FarmDO>()
                .in(FarmDO::getId, collect)
                .eq(FarmDO::getDeleted, 0));
    }

    @Override
    public List<FarmDO> likeName(String farmName) {
        return Mapper.likeName(farmName);
    }

}
