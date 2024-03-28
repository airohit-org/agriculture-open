package com.airohit.agriculture.module.device.controller.admin.info;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.operatelog.core.annotations.OperateLog;
import com.airohit.agriculture.framework.tenant.core.context.FarmTenantContextHolder;
import com.airohit.agriculture.module.device.convert.info.DeviceInfoConvert;
import com.airohit.agriculture.module.device.dal.dataobject.info.DeviceInfoDO;
import com.airohit.agriculture.module.device.dal.dataobject.soil.SoilDeviceDataDO;
import com.airohit.agriculture.module.device.dal.dataobject.weather.DeviceDataDO;
import com.airohit.agriculture.module.device.obs.ObsDeviceListDto;
import com.airohit.agriculture.module.device.service.info.DeviceInfoService;
import com.airohit.agriculture.module.device.service.land.DeviceLandService;
import com.airohit.agriculture.module.device.service.obs.ObsDeviceService;
import com.airohit.agriculture.module.device.service.soil.SoilDeviceDataService;
import com.airohit.agriculture.module.device.service.weather.DeviceDataService;
import com.airohit.agriculture.module.device.vo.info.DeviceInfoPageReqVO;
import com.airohit.agriculture.module.device.vo.info.DeviceInfoRespVO;
import com.airohit.agriculture.module.device.vo.land.DeviceLandListVo;
import com.airohit.agriculture.module.device.vo.obs.DeviceGroupVo;
import com.airohit.agriculture.module.device.vo.obs.ObsDeviceInfoVo;
import com.airohit.agriculture.module.device.vo.obs.ObsDeviceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Api(tags = "管理后台 - 设备基本信息")
@RestController
@RequestMapping("/device/info")
@Validated
public class DeviceInfoController {

    @Resource
    private DeviceInfoService infoService;
    @Resource
    private SoilDeviceDataService service;
    @Resource
    private DeviceDataService deviceDataService;
    @Resource
    private DeviceLandService deviceLandService;

    @Resource
    private ObsDeviceService obsDeviceService;

    @GetMapping("/get")
    @ApiOperation("获得设备基本信息")
    @ApiImplicitParam(name = "id", value = "id", required = true, example = "1024", dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('device:info:query')")
    public CommonResult<DeviceInfoRespVO> getInfo(@RequestParam("id") Integer id) {
        DeviceInfoDO info = infoService.getInfo(id);
        return success(DeviceInfoConvert.INSTANCE.convert(info));
    }

    @GetMapping("/list")
    @ApiOperation("获得设备基本信息列表")
    @ApiImplicitParam(name = "ids", value = "id列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('device:info:query')")
    public CommonResult<List<DeviceInfoRespVO>> getInfoList(@RequestParam("ids") Collection<Integer> ids) {
        List<DeviceInfoDO> list = infoService.getInfoList(ids);
        return success(DeviceInfoConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得设备基本信息分页")
    @PreAuthorize("@ss.hasPermission('device:info:query')")
    public CommonResult<PageResult<DeviceInfoRespVO>> getInfoPage(@Valid DeviceInfoPageReqVO pageVO) {
        PageResult<DeviceInfoDO> pageResult = infoService.getInfoPage(pageVO);
        return success(DeviceInfoConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/getSoilDeviceDataDONew")
    @ApiOperation("查找土壤设备数据")
    @PermitAll
    public CommonResult<SoilDeviceDataDO> getSoilDeviceDataDONew() {
        return success(service.getSoilDeviceDataDONew());
    }

    @GetMapping("/getWeatherDeviceDataDONew")
    @ApiOperation("查找天气设备数据")
    @PermitAll
    public CommonResult<DeviceDataDO> getWeatherDeviceDataDONew() {
        return success(deviceDataService.getDeviceDataDONew());
    }

    @GetMapping("/getDeviceGroupVoList")
    @ApiOperation("查找设备分组统计数据")
    @PermitAll
    public CommonResult<List<DeviceGroupVo>> getDeviceGroupVoList() {
        return success(obsDeviceService.getDeviceGroupVoList(Integer.valueOf(FarmTenantContextHolder.getFarmTenantId().toString())));
    }

    @GetMapping("/getDeviceLandListVoList")
    @ApiOperation("查找地块关联设备数据")
    @PermitAll
    public CommonResult<List<DeviceLandListVo>> getDeviceLandListVoList(@RequestParam(value = "deviceId", required = false)
                                                                        @ApiParam("设备Id筛选,传52为气象站设备")
                                                                        Integer deviceId) {
        return success(deviceLandService.getDeviceLandListVoList(deviceId));
    }

    @GetMapping("/getUserDeviceList")
    @ApiOperation("获得设备列表(新)")
    @OperateLog(enable = false)
    public CommonResult<List<ObsDeviceVo>> getUserDeviceList(@Valid ObsDeviceListDto obsDeviceDto) {
        List<ObsDeviceVo> obsDeviceVoList = obsDeviceService.list(obsDeviceDto);
        return success(obsDeviceVoList);
    }

    @GetMapping("/getRealTimeData")
    @ApiOperation("获得设备实时数据")
    public CommonResult<ObsDeviceInfoVo> getRealTimeData(@RequestParam("id")
                                                         @ApiParam("设备id")
                                                         Integer id) {
        return success(obsDeviceService.getDeviceInfo(id));
    }
}
