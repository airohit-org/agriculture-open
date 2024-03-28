package com.airohit.agriculture.module.device.service.land;

import com.airohit.agriculture.framework.datapermission.core.annotation.DataPermission;
import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.framework.tenant.core.context.TenantContextHolder;
import com.airohit.agriculture.module.device.dal.mysql.deviceland.DeviceLandMapper;
import com.airohit.agriculture.module.device.vo.land.DeviceLandListVo;
import com.airohit.agriculture.module.device.vo.obs.DeviceGroupVo;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 地块下的设备 Service 实现类
 *
 * @author shiminghao
 */
@Service
@Validated
public class DeviceLandServiceImpl implements DeviceLandService {

    @Resource
    private DeviceLandMapper deviceLandMapper;


    @Override
    @TenantIgnore
    @DataPermission(enable = false)
    public List<DeviceGroupVo> getDeviceGroupVoList() {
        return deviceLandMapper.getDeviceGroupVoList(TenantContextHolder.getTenantId());
    }

    @Override
    @TenantIgnore
    @DataPermission(enable = false)
    public List<DeviceLandListVo> getDeviceLandListVoList(Integer deviceId) {
        return deviceLandMapper.getDeviceLandListVoList(TenantContextHolder.getTenantId(), deviceId);
    }
}
