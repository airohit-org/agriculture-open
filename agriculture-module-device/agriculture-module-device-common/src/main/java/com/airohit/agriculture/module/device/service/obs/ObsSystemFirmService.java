package com.airohit.agriculture.module.device.service.obs;


import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.device.vo.obs.ObsSystemFirmVo;
import com.airohit.agriculture.module.device.vo.obs.systemfirm.ObsSystemFirmCreateVo;
import com.airohit.agriculture.module.device.vo.obs.systemfirm.ObsSystemFirmPageVo;
import com.airohit.agriculture.module.device.vo.obs.systemfirm.ObsSystemFirmUpdateVo;

/**
 * @Author: hanliyao
 * @Date: 2023-7-19 09:22:12
 */
public interface ObsSystemFirmService {
    PageResult<ObsSystemFirmVo> getInfoPage(ObsSystemFirmPageVo obsSystemFirmPageVo);

    ObsSystemFirmVo getOne(Integer id);

    Integer createInfo(ObsSystemFirmCreateVo obsSystemFirmCreateVo);

    void updateInfo(ObsSystemFirmUpdateVo obsSystemFirmUpdateVo);

    void deleteInfo(Integer id);
}
