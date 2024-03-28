package com.airohit.agriculture.module.device.service.obs;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.device.obs.ObsDeviceFirmCreateDto;
import com.airohit.agriculture.module.device.obs.ObsDeviceFirmPageDto;
import com.airohit.agriculture.module.device.obs.ObsDeviceFirmUpdateDto;
import com.airohit.agriculture.module.device.vo.obs.ObsDeviceFirmVo;

import java.util.Map;

/**
 * Created with IDEA
 *
 * @author :hanliyao
 * @date :2023/5/16 13:35
 */
public interface ObsDeviceFirmService {

    CommonResult<Map<Integer, String>> getFirm();

    CommonResult<PageResult<ObsDeviceFirmVo>> getInfoPage(ObsDeviceFirmPageDto pageVO);

    CommonResult<Integer> createInfo(ObsDeviceFirmCreateDto obsSystemFirmCreateDto);

    CommonResult<Boolean> updateInfo(ObsDeviceFirmUpdateDto updateDto);

    CommonResult<Boolean> deleteInfo(Integer id);


    CommonResult<ObsDeviceFirmVo> getOne(Integer id);

    CommonResult<String> getFirmName(Integer firmId);

    void deleteByFirmId(Integer id);

    CommonResult<Boolean> init(Integer id);
}
