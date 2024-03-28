package com.airohit.agriculture.module.device.controller.admin.obs;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.device.obs.ObsDeviceFirmCreateDto;
import com.airohit.agriculture.module.device.obs.ObsDeviceFirmPageDto;
import com.airohit.agriculture.module.device.obs.ObsDeviceFirmUpdateDto;
import com.airohit.agriculture.module.device.service.obs.ObsDeviceFirmService;
import com.airohit.agriculture.module.device.vo.obs.ObsDeviceFirmVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

/**
 * Created with IDEA
 *
 * @author :hanliyao
 * @date :2023/7/8 15:49
 */
@Api(tags = "管理后台 - 厂商信息")
@RestController
@RequestMapping("/device/firm")
@Validated
public class ObsDeviceFirmBizController {
    @Resource
    private ObsDeviceFirmService obsDeviceFirmService;


    @GetMapping("/getFirm")
    @ApiOperation("获得集团端厂商表")
    public CommonResult<Map<Integer, String>> getFirm() {
        return obsDeviceFirmService.getFirm();
    }

    @GetMapping("/page")
    @ApiOperation("获得设备厂商信息分页")
    public CommonResult<PageResult<ObsDeviceFirmVo>> getInfoPage(@Valid ObsDeviceFirmPageDto obsSystemFirmPageDto) {
        return obsDeviceFirmService.getInfoPage(obsSystemFirmPageDto);
    }

    @GetMapping("/get")
    @ApiOperation("获得单个厂商信息")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataTypeClass = Integer.class)
    public CommonResult<ObsDeviceFirmVo> getOne(@RequestParam("id") Integer id) {
        return obsDeviceFirmService.getOne(id);
    }

    @PostMapping("/create")
    @ApiOperation("新增厂商信息")
    public CommonResult<Integer> createInfo(@RequestBody ObsDeviceFirmCreateDto obsDeviceFirmCreateDto) {
        return obsDeviceFirmService.createInfo(obsDeviceFirmCreateDto);
    }

    @PutMapping("/update")
    @ApiOperation("更新厂商信息")
    public CommonResult<Boolean> updateInfo(@RequestBody ObsDeviceFirmUpdateDto obsSystemFirmUpdateDto) {
        return obsDeviceFirmService.updateInfo(obsSystemFirmUpdateDto);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除厂商")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataTypeClass = Integer.class)
    public CommonResult<Boolean> deleteInfo(@RequestParam("id") Integer id) {
        return obsDeviceFirmService.deleteInfo(id);
    }

    @GetMapping("/init")
    @ApiOperation("获取设备")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataTypeClass = Integer.class)
    public CommonResult<Boolean> init(@RequestParam("id") Integer id) {
        return obsDeviceFirmService.init(id);
    }
}
