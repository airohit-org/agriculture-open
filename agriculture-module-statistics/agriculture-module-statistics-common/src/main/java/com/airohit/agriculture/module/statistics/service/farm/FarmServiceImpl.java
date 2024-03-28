package com.airohit.agriculture.module.statistics.service.farm;

import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.framework.tenant.core.context.FarmTenantContextHolder;
import com.airohit.agriculture.framework.tenant.core.context.TenantContextHolder;
import com.airohit.agriculture.module.device.vo.obs.DeviceGroupVo;
import com.airohit.agriculture.module.statistics.dal.mysql.farm.FarmStatisticMapper;
import com.airohit.agriculture.module.statistics.vo.farm.FarmStatisticVo;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/19 10:38
 */
@Service
public class FarmServiceImpl implements FarmService {
    @Resource
    private FarmStatisticMapper farmStatisticMapper;
    @Resource
    @Lazy // 注入自己，所以延迟加载
    private FarmServiceImpl self;

    @Override
    public FarmStatisticVo getFarmStatisticVo() {
        Long tenantId = TenantContextHolder.getTenantId();
        FarmStatisticVo farmStatisticVo = new FarmStatisticVo();
        farmStatisticVo.setCrops(farmStatisticMapper.getCropsCount());
        farmStatisticVo.setLandCount(farmStatisticMapper.getLandCount());
        farmStatisticVo.setMemberCount(farmStatisticMapper.getMemberCount(tenantId));
        farmStatisticVo.setPlantArea(self.getFarmPlantArea(tenantId));
//        Integer deviceCount = obsDeviceApi.getAllDeviceGroupVoList(Integer.valueOf(FarmTenantContextHolder.getFarmTenantId().toString()))
//                .getCheckedData()
//                .stream()
//                .mapToInt(DeviceGroupVo::getCount)
//                .sum();
        farmStatisticVo.setDeviceCount(0);
        return farmStatisticVo;
    }

    @TenantIgnore
    @Override
    public String getFarmPlantArea(Long tenantId) {
        Long farmTenantId = FarmTenantContextHolder.getFarmTenantId();
        return farmStatisticMapper.getFarmPlantArea(tenantId, farmTenantId);
    }
}
