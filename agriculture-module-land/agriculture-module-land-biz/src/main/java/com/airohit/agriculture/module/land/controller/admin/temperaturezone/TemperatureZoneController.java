package com.airohit.agriculture.module.land.controller.admin.temperaturezone;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.land.convert.temperaturezone.TemperatureZoneConvert;
import com.airohit.agriculture.module.land.dal.dataobject.temperaturezone.TemperatureZoneDO;
import com.airohit.agriculture.module.land.service.temperaturezone.TemperatureZoneService;
import com.airohit.agriculture.module.land.vo.temperaturezone.TemperatureZoneCreateReqVO;
import com.airohit.agriculture.module.land.vo.temperaturezone.TemperatureZonePageReqVO;
import com.airohit.agriculture.module.land.vo.temperaturezone.TemperatureZoneRespVO;
import com.airohit.agriculture.module.land.vo.temperaturezone.TemperatureZoneUpdateReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;


@Api(tags = "管理后台 - 积温带管理")
@RestController
@RequestMapping("/land/temperature-zone")
@Validated
public class TemperatureZoneController {

    @Resource
    private TemperatureZoneService temperatureZoneService;

    @PostMapping("/create")
    @ApiOperation("创建积温带管理")
    @PreAuthorize("@ss.hasPermission('accumulated:temperature-zone:create')")
    public CommonResult<Integer> createTemperatureZone(@Valid @RequestBody TemperatureZoneCreateReqVO createReqVO) {
        return success(temperatureZoneService.createTemperatureZone(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新积温带管理")
    @PreAuthorize("@ss.hasPermission('accumulated:temperature-zone:update')")
    public CommonResult<Boolean> updateTemperatureZone(@Valid @RequestBody TemperatureZoneUpdateReqVO updateReqVO) {
        temperatureZoneService.updateTemperatureZone(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除积温带管理")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('accumulated:temperature-zone:delete')")
    public CommonResult<Boolean> deleteTemperatureZone(@RequestParam("id") Integer id) {
        temperatureZoneService.deleteTemperatureZone(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得积温带管理")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('accumulated:temperature-zone:query')")
    public CommonResult<TemperatureZoneRespVO> getTemperatureZone(@RequestParam("id") Integer id) {
        TemperatureZoneDO temperatureZone = temperatureZoneService.getTemperatureZone(id);
        return success(TemperatureZoneConvert.INSTANCE.convert(temperatureZone));
    }

    @GetMapping("/list")
    @ApiOperation("获得积温带管理列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('accumulated:temperature-zone:query')")
    public CommonResult<List<TemperatureZoneRespVO>> getTemperatureZoneList(@RequestParam("ids") Collection<Integer> ids) {
        List<TemperatureZoneDO> list = temperatureZoneService.getTemperatureZoneList(ids);
        return success(TemperatureZoneConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得积温带管理分页")
    @PreAuthorize("@ss.hasPermission('accumulated:temperature-zone:query')")
    public CommonResult<PageResult<TemperatureZoneRespVO>> getTemperatureZonePage(@Valid TemperatureZonePageReqVO pageVO) {
        PageResult<TemperatureZoneDO> pageResult = temperatureZoneService.getTemperatureZonePage(pageVO);
        return success(TemperatureZoneConvert.INSTANCE.convertPage(pageResult));
    }


}
