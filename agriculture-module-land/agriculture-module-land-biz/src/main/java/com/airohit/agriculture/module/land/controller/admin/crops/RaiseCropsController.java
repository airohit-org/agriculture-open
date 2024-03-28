package com.airohit.agriculture.module.land.controller.admin.crops;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.land.convert.raise.RaiseCropsConvert;
import com.airohit.agriculture.module.land.dal.dataobject.raise.RaiseCropsDO;
import com.airohit.agriculture.module.land.service.raise.RaiseCropsService;
import com.airohit.agriculture.module.land.vo.raise.RaiseCropsCreateReqVO;
import com.airohit.agriculture.module.land.vo.raise.RaiseCropsPageReqVO;
import com.airohit.agriculture.module.land.vo.raise.RaiseCropsRespVO;
import com.airohit.agriculture.module.land.vo.raise.RaiseCropsUpdateReqVO;
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


@Api(tags = "管理后台 - 种植作物")
@RestController
@RequestMapping("/land/crops")
@Validated
public class RaiseCropsController {

    @Resource
    private RaiseCropsService cropsService;

    @PostMapping("/create")
    @ApiOperation("创建种植作物")
    @PreAuthorize("@ss.hasPermission('raise:crops:create')")
    public CommonResult<Integer> createCrops(@Valid @RequestBody RaiseCropsCreateReqVO createReqVO) {
        return success(cropsService.createCrops(createReqVO));
    }

    @PutMapping("/update")
    @PreAuthorize("@ss.hasPermission('raise:crops:update')")
    public CommonResult<Boolean> updateCrops(@Valid @RequestBody RaiseCropsUpdateReqVO updateReqVO) {
        cropsService.updateCrops(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除种植作物")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('raise:crops:delete')")
    public CommonResult<Boolean> deleteCrops(@RequestParam("id") Integer id) {
        cropsService.deleteCrops(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得种植作物")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('raise:crops:query')")
    public CommonResult<RaiseCropsRespVO> getCrops(@RequestParam("id") Integer id) {
        RaiseCropsDO crops = cropsService.getCrops(id);
        return success(RaiseCropsConvert.INSTANCE.convert(crops));
    }

    @GetMapping("/list")
    @ApiOperation("获得种植作物列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('raise:crops:query')")
    public CommonResult<List<RaiseCropsRespVO>> getCropsList(@RequestParam("ids") Collection<Integer> ids) {
        List<RaiseCropsDO> list = cropsService.getCropsList(ids);
        return success(RaiseCropsConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得种植作物分页")
    @PreAuthorize("@ss.hasPermission('raise:crops:query')")
    public CommonResult<PageResult<RaiseCropsRespVO>> getCropsPage(@Valid RaiseCropsPageReqVO pageVO) {
        PageResult<RaiseCropsDO> pageResult = cropsService.getCropsPage(pageVO);
        return success(RaiseCropsConvert.INSTANCE.convertPage(pageResult));
    }


}
