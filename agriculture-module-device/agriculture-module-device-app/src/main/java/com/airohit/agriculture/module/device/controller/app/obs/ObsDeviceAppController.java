package com.airohit.agriculture.module.device.controller.app.obs;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.operatelog.core.annotations.OperateLog;
import com.airohit.agriculture.module.device.obs.ObsDevicePageDto;
import com.airohit.agriculture.module.device.service.obs.ObsDeviceService;
import com.airohit.agriculture.module.device.vo.obs.ObsDeviceInfoVo;
import com.airohit.agriculture.module.device.vo.obs.ObsDeviceVo;
import com.airohit.agriculture.module.system.api.dict.dto.DictDataRespDTO;
import com.airohit.agriculture.module.system.service.dict.DictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

/**
 * Created with IDEA
 *
 * @author :shiminghao,hanliyao
 * @date :2023/7/8 15:49
 */
@Api(tags = "建大仁科 - 设备数据")
@RestController
@RequestMapping("/device/app/obs")
@Validated
public class ObsDeviceAppController {
    @Resource
    private ObsDeviceService obsDeviceService;

    @Resource
    private DictDataService dictDataService;

    @GetMapping("/getUserClassify")
    @ApiOperation("获得设备分类")
    public CommonResult<List<DictDataRespDTO>> getUserClassify() {
        return success(dictDataService.getDeviceTypeList("device_type"));
    }

    @GetMapping("/getUserDevice")
    @ApiOperation("获得设备列表")
    @OperateLog(enable = false)
    public CommonResult<PageResult<ObsDeviceVo>> getUserDevice(@Valid ObsDevicePageDto obsDevicePageDto) {
        return success(obsDeviceService.getInfoPage(obsDevicePageDto));
    }

    @GetMapping("/getRealTimeData")
    @ApiOperation("获得设备实时数据")
    public CommonResult<ObsDeviceInfoVo> getRealTimeData(@RequestParam("deviceId")
                                                         @ApiParam("设备id")
                                                         Integer deviceId) {
        return success(obsDeviceService.getDeviceInfo(deviceId));
    }
}
