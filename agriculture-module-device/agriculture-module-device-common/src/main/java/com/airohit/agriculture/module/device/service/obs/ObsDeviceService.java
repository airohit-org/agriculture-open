package com.airohit.agriculture.module.device.service.obs;


import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.device.dal.dataobject.obs.ObsDeviceDO;
import com.airohit.agriculture.module.device.dal.dataobject.obs.ObsDeviceFirmDO;
import com.airohit.agriculture.module.device.obs.*;
import com.airohit.agriculture.module.device.vo.obs.*;

import java.util.List;
import java.util.Map;

/**
 * &#064;Author:  hanliyao
 * &#064;Date:  2023/7/13 13:56
 */
public interface ObsDeviceService {

    void createJuYingService(ObsDeviceClaimDto obsDeviceClaimDto);

    void juYingKillPort(Integer port);

    ObsDeviceIpVo getIpAndPort();

    /**
     * 以下为调用集团端的代码
     */
    PageResult<ObsDeviceVo> getInfoPage(ObsDevicePageDto obsDevicePageDto);

    PageResult<ObsDeviceInfoVo> getRealTimeDataPage(ObsDeviceInfoPageDto obsDeviceInfoPageDto);

    Map<String, String> getUserClassify();

    Map<Integer, String> getFirm(Integer farmId);

    ObsDeviceVo claimDevice(ObsDeviceClaimDto obsDeviceClaimDto);

    Boolean updateDevice(ObsDeviceUpdateDto obsDeviceUpdateDto);

    Boolean deleteDevice(Integer id);

    ObsDeviceVo getOne(Integer id);


    void deleteByFarmFirmId(Integer id);

    ObsDeviceInfoVo getDeviceInfo(Integer id);

    PageResult<ObsDeviceInfoVo> getRealTimeDataPageImage(ObsDeviceInfoPageDto obsDeviceInfoPageDto);

    List<ObsDeviceInfoVo> queryBeijingTHDeviceInfo(String deviceId, String startAt, String endtAt);

    ObsDeviceBeijingHTInfoVo queryBeijingTHDeviceInfoByDeviceId(Integer deviceId);

    List<ObsDeviceVo> list(ObsDeviceListDto obsDeviceDto);

    List<DeviceGroupVo> getDeviceGroupVoList(Integer integer);

    void deleteByFirmId(Integer id);

    void influxObsDeviceSave(ObsDeviceFirmDO obsDeviceFirmDO);

    void beijingTHDeviceInfoSave(ObsDeviceDO obsDeviceDO);

    void beijingTHDeviceImageSave(ObsDeviceDO obsDeviceDO);

    void getObsDeviceTask();

    void beijingTHDeviceInfo();

    void beijingTHDeviceImage();

    PageResult<ObsDeviceInfoVo> getInfo(ObsDeviceInfoPageDto obsDeviceInfoPageDto);

    void init(ObsDeviceFirmDO obsDeviceFirmDO);

    PageResult<ObsDeviceInfoVo> getInfoImage(ObsDeviceInfoPageDto obsDeviceInfoPageDto);

    void initBJTH(ObsDeviceFirmDO obsDeviceFirmDO);

    void bjthSave(ObsDeviceFirmDO obsDeviceFirmDO);
}
