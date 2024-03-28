package com.airohit.agriculture.module.device.controller.admin.obs;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.operatelog.core.annotations.OperateLog;
import com.airohit.agriculture.module.device.obs.ObsDeviceClaimDto;
import com.airohit.agriculture.module.device.obs.ObsDeviceInfoPageDto;
import com.airohit.agriculture.module.device.obs.ObsDevicePageDto;
import com.airohit.agriculture.module.device.obs.ObsDeviceUpdateDto;
import com.airohit.agriculture.module.device.service.obs.ObsDeviceFirmService;
import com.airohit.agriculture.module.device.service.obs.ObsDeviceService;
import com.airohit.agriculture.module.device.vo.obs.ObsDeviceBeijingHTInfoVo;
import com.airohit.agriculture.module.device.vo.obs.ObsDeviceInfoVo;
import com.airohit.agriculture.module.device.vo.obs.ObsDeviceIpVo;
import com.airohit.agriculture.module.device.vo.obs.ObsDeviceVo;
import com.airohit.agriculture.module.system.api.dict.dto.DictDataRespDTO;
import com.airohit.agriculture.module.system.service.dict.DictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

/**
 * Created with IDEA
 *
 * @author :hanliyao
 * @date :2023/7/8 15:49
 */
@Api(tags = "管理后台 - 设备数据")
@RestController
@RequestMapping("/device/obs")
@Validated
public class ObsDeviceBizController {
    @Resource
    private ObsDeviceService obsDeviceService;

    @Resource
    private ObsDeviceFirmService obsDeviceFirmService;

    @Resource
    private DictDataService dictDataService;

    @GetMapping("/getUserDevice")
    @ApiOperation("获得设备列表")
    @OperateLog(enable = false)
    public CommonResult<PageResult<ObsDeviceVo>> getUserDevice(@Valid ObsDevicePageDto obsDevicePageDto) {
        return success(obsDeviceService.getInfoPage(obsDevicePageDto));
    }

    @GetMapping("/getOne")
    @ApiOperation("查看设备详情")
    @OperateLog(enable = false)
    public CommonResult<ObsDeviceVo> getOne(@RequestParam("id") Integer id) {
        return success(obsDeviceService.getOne(id));
    }

    @GetMapping("/getRealTimeData")
    @ApiOperation("获得设备实时数据")
    @OperateLog(enable = false)
    public CommonResult<PageResult<ObsDeviceInfoVo>> getRealTimeData(@Valid ObsDeviceInfoPageDto obsDeviceInfoPageDto) {
        return success(obsDeviceService.getRealTimeDataPage(obsDeviceInfoPageDto));
    }

    @GetMapping("/getFirm")
    @ApiOperation("获得设备厂商")
    public CommonResult<Map<Integer, String>> getFirm(@RequestParam("firmId") Integer farmId) {
        return success(obsDeviceService.getFirm(farmId));
    }

    @PostMapping("/claimDevice")
    @ApiOperation("新增设备")
    public CommonResult<ObsDeviceVo> claimDevice(@RequestBody ObsDeviceClaimDto obsDeviceClaimDto) {
        CommonResult<String> firmName = obsDeviceFirmService.getFirmName(obsDeviceClaimDto.getFirmId());
        if (firmName.getCode() == 0) {
            if (firmName.getData().equals("聚英科技"))
                obsDeviceService.createJuYingService(obsDeviceClaimDto);
        }
        return success(obsDeviceService.claimDevice(obsDeviceClaimDto));
    }

    @GetMapping("/getIpAndPort")
    @ApiOperation("自动获取ip端口模板")
    public CommonResult<ObsDeviceIpVo> getIpAndPort(@RequestParam("firmId") Integer firmId) {
        ObsDeviceIpVo ipAndPort = new ObsDeviceIpVo();
        CommonResult<String> firmName = obsDeviceFirmService.getFirmName(firmId);
        if (firmName.getCode() == 0)
            if (firmName.getData().equals("聚英科技")) {
                ipAndPort = obsDeviceService.getIpAndPort();
            }
        return success(ipAndPort);
    }

    @PostMapping("/updateDevice")
    @ApiOperation("修改设备")
    public CommonResult<Boolean> updateDevice(@RequestBody ObsDeviceUpdateDto obsDeviceUpdateDto) {
        return success(obsDeviceService.updateDevice(obsDeviceUpdateDto));
    }

    @GetMapping("/deleteDevice")
    @ApiOperation("删除设备")
    @ApiImplicitParam(name = "id", value = "设备id", required = true, dataTypeClass = Integer.class)
    public CommonResult<Boolean> deleteDevice(@RequestParam("id") Integer id) {
        ObsDeviceVo one = obsDeviceService.getOne(id);
        CommonResult<String> firmName = obsDeviceFirmService.getFirmName(one.getFirmId());
        if (firmName.getCode() == 0) {
            if (firmName.getData().equals("聚英科技"))
                obsDeviceService.juYingKillPort(one.getDeviceServicePort());
        }
        return success(obsDeviceService.deleteDevice(id));
    }

    @GetMapping("/deviceType")
    @ApiOperation("设备列表")
    public CommonResult<List<DictDataRespDTO>> deviceType() {
        return success(dictDataService.getDeviceTypeList("device_type"));
    }

    @GetMapping("/queryBeijingTHDeviceInfoByDeviceId")
    @ApiOperation("查询北京天航设备数据详情")
    public CommonResult<ObsDeviceBeijingHTInfoVo> queryBeijingTHDeviceInfoByDeviceId(@RequestParam("deviceId") Integer deviceId) {
        return success(obsDeviceService.queryBeijingTHDeviceInfoByDeviceId(deviceId));
    }

    @GetMapping("/queryBeijingTHDeviceInfo")
    @ApiOperation("根据起始日期和结束日期查询北京天航设备数据")
    public CommonResult<List<ObsDeviceInfoVo>> queryBeijingTHDeviceInfo(@RequestParam("deviceId") String deviceId, @RequestParam("startAt") String startAt, @RequestParam("endtAt") String endtAt) {
        return success(obsDeviceService.queryBeijingTHDeviceInfo(deviceId, startAt, endtAt));
    }

    @GetMapping("/getRealTimeDataImage")
    @ApiOperation("获得设备图片实时数据")
    @OperateLog(enable = false)
    public CommonResult<PageResult<ObsDeviceInfoVo>> getRealTimeDataImage(@Valid ObsDeviceInfoPageDto obsDeviceInfoPageDto) {
        return success(obsDeviceService.getRealTimeDataPageImage(obsDeviceInfoPageDto));
    }

}
