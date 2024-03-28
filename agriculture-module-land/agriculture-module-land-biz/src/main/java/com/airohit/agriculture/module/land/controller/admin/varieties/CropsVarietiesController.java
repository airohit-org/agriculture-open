package com.airohit.agriculture.module.land.controller.admin.varieties;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.land.convert.varieties.CropsVarietiesConvert;
import com.airohit.agriculture.module.land.dal.dataobject.raise.RaiseCropsDO;
import com.airohit.agriculture.module.land.dal.dataobject.varieties.CropsVarietiesDO;
import com.airohit.agriculture.module.land.service.raise.RaiseCropsService;
import com.airohit.agriculture.module.land.service.varieties.CropsVarietiesService;
import com.airohit.agriculture.module.land.vo.varieties.CropsVarietiesCreateReqVO;
import com.airohit.agriculture.module.land.vo.varieties.CropsVarietiesPageReqVO;
import com.airohit.agriculture.module.land.vo.varieties.CropsVarietiesRespVO;
import com.airohit.agriculture.module.land.vo.varieties.CropsVarietiesUpdateReqVO;
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


@Api(tags = "管理后台 - 品种管理")
@RestController
@RequestMapping("/land/varieties")
@Validated
public class CropsVarietiesController {

    @Resource
    private CropsVarietiesService varietiesService;
    @Resource
    private RaiseCropsService cropsService;

    @PostMapping("/create")
    @ApiOperation("创建品种管理")
    @PreAuthorize("@ss.hasPermission('crops:varieties:create')")
    public CommonResult<Integer> createVarieties(@Valid @RequestBody CropsVarietiesCreateReqVO createReqVO) {
        return success(varietiesService.createVarieties(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新品种管理")
    @PreAuthorize("@ss.hasPermission('crops:varieties:update')")
    public CommonResult<Boolean> updateVarieties(@Valid @RequestBody CropsVarietiesUpdateReqVO updateReqVO) {
        varietiesService.updateVarieties(updateReqVO);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得品种管理")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('crops:varieties:query')")
    public CommonResult<CropsVarietiesRespVO> getVarieties(@RequestParam("id") Integer id) {
        CropsVarietiesDO varieties = varietiesService.getVarieties(id);
        RaiseCropsDO crops = cropsService.getCrops(varieties.getRaiseCropsId());
        CropsVarietiesRespVO convert = CropsVarietiesConvert.INSTANCE.convert(varieties);
        convert.setCropsName(crops.getCropsName());
        return success(convert);
    }

    @GetMapping("/list")
    @ApiOperation("获得品种管理列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('crops:varieties:query')")
    public CommonResult<List<CropsVarietiesRespVO>> getVarietiesList(@RequestParam("ids") Collection<Integer> ids) {
        List<CropsVarietiesDO> list = varietiesService.getVarietiesList(ids);
        return success(CropsVarietiesConvert.INSTANCE.convertList(list));
    }


    @GetMapping("/page")
    @ApiOperation("获得品种管理分页")
    @PreAuthorize("@ss.hasPermission('crops:varieties:query')")
    public CommonResult<PageResult<CropsVarietiesRespVO>> getVarietiesPage(@Valid CropsVarietiesPageReqVO pageVO) {

        return success(varietiesService.getVarietiesPage(pageVO));
    }


}
