package com.airohit.agriculture.module.land.service.layer;

import com.airohit.agriculture.framework.tenant.core.aop.FarmTenantIgnore;
import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.framework.tenant.core.context.FarmTenantContextHolder;
import com.airohit.agriculture.module.land.convert.layer.FarmLayerConvert;
import com.airohit.agriculture.module.land.dal.dataobject.layer.FarmLayerDO;
import com.airohit.agriculture.module.land.dal.mysql.layer.FarmLayerMapper;
import com.airohit.agriculture.module.land.vo.layer.FarmLayerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 农场图层 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
@Slf4j
public class FarmLayerServiceImpl implements FarmLayerService {

    @Resource
    private FarmLayerMapper farmLayerMapper;

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public List<FarmLayerVO> queryFarmLayer() {
        Long farmTenantId = FarmTenantContextHolder.getFarmTenantId();
        List<FarmLayerDO> farmLayerDOS = farmLayerMapper.selectList(FarmLayerDO::getFarmId, farmTenantId);
        List<FarmLayerVO> farmLayerVOList = FarmLayerConvert.INSTANCE.convertList(farmLayerDOS);
        return farmLayerVOList;
    }
}
