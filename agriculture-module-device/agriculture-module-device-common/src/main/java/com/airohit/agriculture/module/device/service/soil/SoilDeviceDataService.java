package com.airohit.agriculture.module.device.service.soil;

import com.airohit.agriculture.module.device.dal.dataobject.soil.SoilDeviceDataDO;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/24 14:27
 */
public interface SoilDeviceDataService {

    void createSoilDeviceData();

    SoilDeviceDataDO getSoilDeviceDataDONew();
}
