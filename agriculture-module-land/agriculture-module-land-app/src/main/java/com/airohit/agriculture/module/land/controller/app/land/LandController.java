package com.airohit.agriculture.module.land.controller.app.land;

import cn.hutool.json.JSONUtil;
import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.excel.core.util.ExcelUtils;
import com.airohit.agriculture.framework.operatelog.core.annotations.OperateLog;
import com.airohit.agriculture.module.land.convert.land.LandConvert;
import com.airohit.agriculture.module.land.dal.dataobject.crops.CropsDO;
import com.airohit.agriculture.module.land.dal.dataobject.land.LandDO;
import com.airohit.agriculture.module.land.service.crops.CropsService;
import com.airohit.agriculture.module.land.service.land.LandService;
import com.airohit.agriculture.module.land.vo.*;
import com.airohit.agriculture.module.land.vo.crops.*;
import com.airohit.agriculture.module.plant.api.plan.PlanApi;
import com.airohit.agriculture.module.plant.api.plan.dto.PlanRespDTO;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;
import static com.airohit.agriculture.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Api(tags = "app - 地块信息")
@RestController
@RequestMapping("/land/")
@Validated
@Slf4j
public class LandController {

    @Resource
    private LandService Service;

    @Resource
    private CropsService cropsService;

    @Resource
    private PlanApi planApi;

    // ok
    @PostMapping("/create")
    @ApiOperation("创建地块信息")
    public CommonResult<Integer> create(@Valid @RequestBody LandCreateReqVO createReqVO) {
        return success(Service.create(createReqVO));
    }

    // ok
    @PutMapping("/update")
    @ApiOperation("更新地块信息")
    public CommonResult<Boolean> update(@Valid @RequestBody LandUpdateReqVO updateReqVO) {
        Service.update(updateReqVO);
        return success(true);
    }

    @PutMapping("/updateOutline")
    @ApiOperation("更新地块轮廓")
    public CommonResult<Boolean> updateOutline(@Valid @RequestBody LandUpdateOutlineReqVO updateReqVO) {
        Service.updateOutline(updateReqVO);
        return success(true);
    }

    // ok
    @DeleteMapping("/delete")
    @ApiOperation("删除地块信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Integer.class)
    public CommonResult<Boolean> delete(@RequestParam("id") Integer id) {
        Service.delete(id);
        Service.updatePlanByLandId(id);
        return success(true);
    }

    // ok
    @GetMapping("/get")
    @ApiOperation("获得地块信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    public CommonResult<LandRespVO> get(@RequestParam("id") Integer id) {
        LandDO landDO = Service.get(id);
        LandRespVO landRespVO = LandConvert.INSTANCE.convert(landDO);
        if (JSONUtil.isTypeJSON(landRespVO.getLandCoordinate())) {
            landRespVO.setPosVoList(JSONArray.parseArray(landDO.getLandCoordinate(), PosVo.class));
        }
        if (landRespVO.getCropsIsOther() != null && landRespVO.getCropsIsOther() == 1) {
            landRespVO.setCropsName(landRespVO.getCropsOtherContent());
            landRespVO.setCropsTypeName(landRespVO.getCropsTypeOtherContent());
        } else {
            RaiseCropsBaseVO raiseCropsRespVO = Service.queryRaiseCropsByCode(landRespVO.getCrops());
            if (raiseCropsRespVO != null) {
                landRespVO.setCropsName(raiseCropsRespVO.getCropsName());
                CropsVarietiesBaseVO cropsVarietiesBaseVO = Service.queryCropsVarietiesByCode(raiseCropsRespVO.getId(), landRespVO.getCropsType());
                if (cropsVarietiesBaseVO != null) {
                    landRespVO.setCropsTypeName(cropsVarietiesBaseVO.getCropsVarietiesName());
                }
            }
        }

        queryLandRespVO(landRespVO);

        CommonResult<PlanRespDTO> plan = planApi.getPlan(id);
        if (plan.isSuccess()) {
            PlanRespDTO planRespDTO = plan.getData();
            if (planRespDTO != null) {
                landRespVO.setPlanId(planRespDTO.getPlanId());
                landRespVO.setPlanName(planRespDTO.getPlanName());
            }
        }

        return success(landRespVO);
    }

    private LandRespVO queryLandRespVO(LandRespVO landRespVO) {
        CropsExportReqVO cropsExportReqVO = new CropsExportReqVO();
        cropsExportReqVO.setLandId(landRespVO.getId());
        List<CropsDO> cropsList = cropsService.getCropsList(cropsExportReqVO);

        List<CropsCreateReqVO> cropsCreateReqVOList = new ArrayList<>();

        for (CropsDO cropsDO : cropsList) {
            CropsCreateReqVO cropsCreateReqVO = new CropsCreateReqVO();
            if (cropsDO.getCropsIsOther() != null && cropsDO.getCropsIsOther() == 1) {
                cropsCreateReqVO.setCropsName(cropsDO.getCropsOtherContent());
                cropsCreateReqVO.setCropsTypeName(cropsDO.getCropsTypeOtherContent());
            } else {
                RaiseCropsBaseVO raiseCropsRespVO = Service.queryRaiseCropsByCode(cropsDO.getCrops());
                if (raiseCropsRespVO != null) {
                    cropsCreateReqVO.setCropsName(raiseCropsRespVO.getCropsName());
                    CropsVarietiesBaseVO cropsVarietiesBaseVO = Service.queryCropsVarietiesByCode(raiseCropsRespVO.getId(), cropsDO.getCropsType());
                    if (cropsVarietiesBaseVO != null) {
                        cropsCreateReqVO.setCropsTypeName(cropsVarietiesBaseVO.getCropsVarietiesName());
                    }
                }
            }
            cropsCreateReqVO.setCrops(cropsDO.getCrops());
            cropsCreateReqVO.setCropsType(cropsDO.getCropsType());
            cropsCreateReqVO.setCropsIsOther(cropsDO.getCropsIsOther());
            cropsCreateReqVO.setCropsTypeIsOther(cropsDO.getCropsTypeIsOther());
            cropsCreateReqVO.setCropsOtherContent(cropsDO.getCropsOtherContent());
            cropsCreateReqVO.setCropsTypeOtherContent(cropsDO.getCropsTypeOtherContent());
            cropsCreateReqVOList.add(cropsCreateReqVO);

        }
        landRespVO.setCropsCreateReqVOList(cropsCreateReqVOList);
        return landRespVO;
    }


    // ok
    @GetMapping("/list")
    @ApiOperation("获得地块信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "编号列表", required = false, example = "1024,2048", dataTypeClass = List.class),
            @ApiImplicitParam(name = "landName", value = "地块名称", required = false, example = "地块", dataTypeClass = String.class)
    })
    public CommonResult<List<LandRespVO>> getList(@RequestParam(value = "ids", required = false) Collection<Integer> ids, @RequestParam(value = "landName", required = false) String landName) {
        List<LandDO> list = Service.getList(ids, landName);

        List<LandRespVO> landRespVOS = LandConvert.INSTANCE.convertList(list);

        for (LandRespVO landRespVO : landRespVOS) {
            if (JSONUtil.isTypeJSON(landRespVO.getLandCoordinate())) {
                landRespVO.setPosVoList(JSONArray.parseArray(landRespVO.getLandCoordinate(), PosVo.class));
            }

            if (landRespVO.getCropsIsOther() != null && landRespVO.getCropsIsOther() == 1) {
                landRespVO.setCropsName(landRespVO.getCropsOtherContent());
                landRespVO.setCropsTypeName(landRespVO.getCropsTypeOtherContent());
            } else {
                RaiseCropsBaseVO raiseCropsRespVO = Service.queryRaiseCropsByCode(landRespVO.getCrops());
                if (raiseCropsRespVO != null) {
                    landRespVO.setCropsName(raiseCropsRespVO.getCropsName());
                    CropsVarietiesBaseVO cropsVarietiesBaseVO = Service.queryCropsVarietiesByCode(raiseCropsRespVO.getId(), landRespVO.getCropsType());
                    if (cropsVarietiesBaseVO != null) {
                        landRespVO.setCropsTypeName(cropsVarietiesBaseVO.getCropsVarietiesName());
                    }
                }
            }
            queryLandRespVO(landRespVO);

            CommonResult<PlanRespDTO> plan = planApi.getPlan(landRespVO.getId());
            if (plan.isSuccess()) {
                PlanRespDTO planRespDTO = plan.getData();
                if (planRespDTO != null) {
                    landRespVO.setPlanId(planRespDTO.getPlanId());
                    landRespVO.setPlanName(planRespDTO.getPlanName());
                }
            }

        }
        return success(landRespVOS);
    }


    @GetMapping("/page")
    @ApiOperation("获得地块信息分页")
    public CommonResult<PageResult<LandRespVO>> getPage(@Valid LandPageReqVO pageVO) {
        PageResult<LandDO> pageResult = Service.getPage(pageVO);

        PageResult<LandRespVO> landRespVOPageResult = LandConvert.INSTANCE.convertPage(pageResult);
        List<LandRespVO> list = landRespVOPageResult.getList();


        for (LandRespVO landRespVO : list) {
            if (JSONUtil.isTypeJSON(landRespVO.getLandCoordinate())) {
                landRespVO.setPosVoList(JSONArray.parseArray(landRespVO.getLandCoordinate(), PosVo.class));
            }
            if (landRespVO.getCropsIsOther() != null && landRespVO.getCropsIsOther() == 1) {
                landRespVO.setCropsName(landRespVO.getCropsOtherContent());
                landRespVO.setCropsTypeName(landRespVO.getCropsTypeOtherContent());
            } else {
                RaiseCropsBaseVO raiseCropsRespVO = Service.queryRaiseCropsByCode(landRespVO.getCrops());
                if (raiseCropsRespVO != null) {
                    landRespVO.setCropsName(raiseCropsRespVO.getCropsName());
                    CropsVarietiesBaseVO cropsVarietiesBaseVO = Service.queryCropsVarietiesByCode(raiseCropsRespVO.getId(), landRespVO.getCropsType());
                    if (cropsVarietiesBaseVO != null) {
                        landRespVO.setCropsTypeName(cropsVarietiesBaseVO.getCropsVarietiesName());
                    }
                }
            }
            queryLandRespVO(landRespVO);
        }
        landRespVOPageResult.setList(list);
        return success(landRespVOPageResult);
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出地块信息 Excel")
    @OperateLog(type = EXPORT)
    public void exportExcel(@Valid LandExportReqVO exportReqVO,
                            HttpServletResponse response) throws IOException {
        List<LandDO> list = Service.getList(exportReqVO);
        // 导出 Excel
        List<LandExcelVO> datas = LandConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "地块信息.xls", "数据", LandExcelVO.class, datas);
    }

    /**
     * 查询地块种植作物信息
     *
     * @return
     */
    @GetMapping("/queryRaiseCrops")
    @ApiOperation("查询地块种植作物信息")
    public CommonResult<List<RaiseCropsRespVO>> queryRaiseCrops() {
        List<RaiseCropsRespVO> raiseCropsRespVOS = Service.queryRaiseCrops();

        for (RaiseCropsRespVO raiseCropsRespVO : raiseCropsRespVOS) {
            raiseCropsRespVO.setCropsVarietiesList(Service.queryCropsVarieties(raiseCropsRespVO.getId()));
        }

        return success(raiseCropsRespVOS);
    }


    /**
     * 查询地块统计
     *
     * @return
     */
    @GetMapping("/queryLandStatistics")
    @ApiOperation("查询地块种植作物信息")
    public CommonResult<LandStatisticsVO> queryLandStatistics() {
        LandStatisticsVO landStatisticsVO = Service.queryLandStatistics();
        return success(landStatisticsVO);
    }

}
