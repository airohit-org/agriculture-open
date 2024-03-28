package com.airohit.agriculture.module.device.service.land;

import com.airohit.agriculture.module.device.vo.land.DeviceLandListVo;
import com.airohit.agriculture.module.device.vo.obs.DeviceGroupVo;

import java.util.List;

/**
 * 地块下的设备 Service 接口
 *
 * @author shiminghao
 */
public interface DeviceLandService {


    /**
     * 查找设备分组统计数据
     *
     * @return
     */
    List<DeviceGroupVo> getDeviceGroupVoList();

    /**
     * 查找地块关联设备数据
     *
     * @return
     */
    List<DeviceLandListVo> getDeviceLandListVoList(Integer deviceId);

}
