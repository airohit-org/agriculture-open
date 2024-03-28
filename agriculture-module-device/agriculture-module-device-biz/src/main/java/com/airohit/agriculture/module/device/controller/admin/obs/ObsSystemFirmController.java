package com.airohit.agriculture.module.device.controller.admin.obs;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.device.service.obs.ObsSystemFirmService;
import com.airohit.agriculture.module.device.vo.obs.ObsSystemFirmVo;
import com.airohit.agriculture.module.device.vo.obs.systemfirm.ObsSystemFirmCreateVo;
import com.airohit.agriculture.module.device.vo.obs.systemfirm.ObsSystemFirmPageVo;
import com.airohit.agriculture.module.device.vo.obs.systemfirm.ObsSystemFirmUpdateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Api(tags = "设备厂商信息")
@RestController
@RequestMapping("/device/system/firm")
@Validated
public class ObsSystemFirmController {

    @Resource
    private ObsSystemFirmService obsSystemFirmService;

    @GetMapping("/page")
    @ApiOperation("获得设备厂商信息分页")
    @PreAuthorize("@ss.hasPermission('device:firm:query')")
    public CommonResult<PageResult<ObsSystemFirmVo>> getInfoPage(@Valid ObsSystemFirmPageVo obsSystemFirmPageVo) {
        return success(obsSystemFirmService.getInfoPage(obsSystemFirmPageVo));
    }

    @GetMapping("/get")
    @ApiOperation("获得单个厂商信息")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('device:firm:get')")
    public CommonResult<ObsSystemFirmVo> getOne(@RequestParam("id") Integer id) {
        return success(obsSystemFirmService.getOne(id));
    }

    @PostMapping("/create")
    @ApiOperation("新增厂商信息")
    @PreAuthorize("@ss.hasPermission('device:firm:create')")
    public CommonResult<Integer> createInfo(@Valid @RequestBody ObsSystemFirmCreateVo obsSystemFirmCreateVo) {
        return success(obsSystemFirmService.createInfo(obsSystemFirmCreateVo));
    }

    @PutMapping("/update")
    @ApiOperation("更新厂商信息")
    @PreAuthorize("@ss.hasPermission('device:firm:update')")
    public CommonResult<Boolean> updateInfo(@Valid @RequestBody ObsSystemFirmUpdateVo obsSystemFirmUpdateVo) {
        obsSystemFirmService.updateInfo(obsSystemFirmUpdateVo);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除厂商")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('device:firm:delete')")
    public CommonResult<Boolean> deleteInfo(@RequestParam("id") Integer id) {
        obsSystemFirmService.deleteInfo(id);
        return success(true);
    }
}
