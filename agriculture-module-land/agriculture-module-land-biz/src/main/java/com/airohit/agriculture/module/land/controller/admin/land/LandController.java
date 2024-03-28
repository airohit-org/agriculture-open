package com.airohit.agriculture.module.land.controller.admin.land;

import cn.hutool.json.JSONUtil;
import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.excel.core.util.ExcelUtils;
import com.airohit.agriculture.framework.operatelog.core.annotations.OperateLog;
import com.airohit.agriculture.module.land.convert.land.LandConvert;
import com.airohit.agriculture.module.land.dal.dataobject.land.LandDO;
import com.airohit.agriculture.module.land.service.crops.CropsService;
import com.airohit.agriculture.module.land.service.land.LandService;
import com.airohit.agriculture.module.land.service.layer.FarmLayerService;
import com.airohit.agriculture.module.land.vo.*;
import com.airohit.agriculture.module.land.vo.crops.CropsCreateReqVO;
import com.airohit.agriculture.module.land.vo.crops.RaiseCropsRespVO;
import com.airohit.agriculture.module.land.vo.layer.FarmLayerVO;
import com.airohit.agriculture.module.plant.api.plan.PlanApi;
import com.airohit.agriculture.module.plant.api.plan.dto.PlanRespDTO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;
import static com.airohit.agriculture.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Api(tags = "管理后台 - 地块信息")
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

    @Resource
    private FarmLayerService farmLayerService;

    @PostMapping("/create")
    @ApiOperation("创建地块信息")
    @PreAuthorize("@ss.hasPermission('land::create')")
    public CommonResult<Integer> create(@Valid @RequestBody LandCreateReqVO createReqVO) {
        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新地块信息")
    @PreAuthorize("@ss.hasPermission('land::update')")
    public CommonResult<Boolean> update(@Valid @RequestBody LandUpdateReqVO updateReqVO) {
        Service.update(updateReqVO);
        return success(true);
    }

    @PutMapping("/updateOutline")
    @ApiOperation("更新地块轮廓")
    @PreAuthorize("@ss.hasPermission('land::update')")
    public CommonResult<Boolean> updateOutline(@Valid @RequestBody LandUpdateOutlineReqVO updateReqVO) {
        Service.updateOutline(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除地块信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('land::delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Integer id) {
        Service.delete(id);
        Service.updatePlanByLandId(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得地块信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('land::query')")
    public CommonResult<LandRespVO> get(@RequestParam("id") Integer id) {
        LandDO landDO = Service.get(id);
        LandRespVO landRespVO = LandConvert.INSTANCE.convert(landDO);
        if (JSONUtil.isTypeJSON(landRespVO.getLandCoordinate())) {
            landRespVO.setPosVoList(JSONArray.parseArray(landDO.getLandCoordinate(), PosVo.class));
        }

        landRespVO.setCropsCreateReqVOList(Service.queryLandCropsByLandId(landRespVO.getId()));

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

    /**
     * private LandRespVO queryLandRespVO(LandRespVO landRespVO){
     * CropsExportReqVO cropsExportReqVO = new CropsExportReqVO();
     * cropsExportReqVO.setLandId(landRespVO.getId());
     * List<CropsDO> cropsList = cropsService.getCropsList(cropsExportReqVO);
     * <p>
     * List<CropsCreateReqVO> cropsCreateReqVOList = new ArrayList<>();
     * <p>
     * for (CropsDO cropsDO :cropsList){
     * CropsCreateReqVO cropsCreateReqVO = new CropsCreateReqVO();
     * if (cropsDO.getCropsIsOther() !=null && cropsDO.getCropsIsOther()==1 ) {
     * cropsCreateReqVO.setCropsName(cropsDO.getCropsOtherContent());
     * cropsCreateReqVO.setCropsTypeName(cropsDO.getCropsTypeOtherContent());
     * }else {
     * RaiseCropsBaseVO raiseCropsRespVO = Service.queryRaiseCropsByCode(cropsDO.getCrops());
     * if(raiseCropsRespVO != null) {
     * cropsCreateReqVO.setCropsName(raiseCropsRespVO.getCropsName());
     * CropsVarietiesBaseVO cropsVarietiesBaseVO = Service.queryCropsVarietiesByCode(raiseCropsRespVO.getId(), cropsDO.getCropsType());
     * if(cropsVarietiesBaseVO != null) {
     * cropsCreateReqVO.setCropsTypeName(cropsVarietiesBaseVO.getCropsVarietiesName());
     * }
     * }
     * }
     * cropsCreateReqVO.setCrops(cropsDO.getCrops());
     * cropsCreateReqVO.setCropsType(cropsDO.getCropsType());
     * cropsCreateReqVO.setCropsIsOther(cropsDO.getCropsIsOther());
     * cropsCreateReqVO.setCropsTypeIsOther(cropsDO.getCropsTypeIsOther());
     * cropsCreateReqVO.setCropsOtherContent(cropsDO.getCropsOtherContent());
     * cropsCreateReqVO.setCropsTypeOtherContent(cropsDO.getCropsTypeOtherContent());
     * cropsCreateReqVOList.add(cropsCreateReqVO);
     * <p>
     * }
     * landRespVO.setCropsCreateReqVOList(cropsCreateReqVOList);
     * return  landRespVO;
     * }
     **/


    @GetMapping("/list")
    @ApiOperation("获得地块信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "编号列表", required = false, example = "1024,2048", dataTypeClass = List.class),
            @ApiImplicitParam(name = "landName", value = "地块名称", required = false, example = "地块", dataTypeClass = String.class)
    })
    @PreAuthorize("@ss.hasPermission('land::query')")
    public CommonResult<List<LandRespVO>> getList(@RequestParam(value = "ids", required = false) Collection<Integer> ids, @RequestParam(value = "landName", required = false) String landName) {

        //List<LandDO> list = Service.getList(ids, landName);
        //List<LandRespVO> landRespVOS = LandConvert.INSTANCE.convertList(list);

        long landRespVOSStartTime = System.currentTimeMillis();
        // 执行需要计时的代码
        List<LandRespVO> landRespVOS = Service.getLandList(landName);
        long landRespVOSEndTime = System.currentTimeMillis();
        long landRespVOSSlapsedTime = landRespVOSEndTime - landRespVOSStartTime;
        log.info("=========================== landRespVOS 代码执行时间：" + landRespVOSSlapsedTime + "毫秒");


        long cropsCreateReqVOListStartTime = System.currentTimeMillis();
        // 执行需要计时的代码
        List<CropsCreateReqVO> cropsCreateReqVOList = Service.queryLandCrops();
        Map<Integer, List<CropsCreateReqVO>> mapCropsRespVO = cropsCreateReqVOList.stream().collect(Collectors.groupingBy(CropsCreateReqVO::getLandId));

        long cropsCreateReqVOListEndTime = System.currentTimeMillis();
        long cropsCreateReqVOListlapsedTime = cropsCreateReqVOListEndTime - cropsCreateReqVOListStartTime;
        log.info("=========================== cropsCreateReqVOList 代码执行时间：" + cropsCreateReqVOListlapsedTime + "毫秒");


        long forStartTime = System.currentTimeMillis();
        for (LandRespVO landRespVO : landRespVOS) {
            if (JSONUtil.isTypeJSON(landRespVO.getLandCoordinate())) {
                landRespVO.setPosVoList(JSONArray.parseArray(landRespVO.getLandCoordinate(), PosVo.class));
            }
            landRespVO.setCropsCreateReqVOList(mapCropsRespVO.get(landRespVO.getId()));
        }

        long forEndTime = System.currentTimeMillis();
        long forlapsedTime = forEndTime - forStartTime;
        log.info("=========================== for 代码执行时间：" + forlapsedTime + "毫秒");

        return success(landRespVOS);
    }


    @GetMapping("/listAll")
    @ApiOperation("获得地块所有地块")
    @PreAuthorize("@ss.hasPermission('land::query')")
    public CommonResult<List<LandRespVO>> listAll() {
        List<LandDO> list = Service.getList(null, null);
        List<LandRespVO> landRespVOS = LandConvert.INSTANCE.convertList(list);
        for (LandRespVO landRespVO : landRespVOS) {
            if (JSONUtil.isTypeJSON(landRespVO.getLandCoordinate())) {
                landRespVO.setPosVoList(JSONArray.parseArray(landRespVO.getLandCoordinate(), PosVo.class));
            }
        }
        return success(landRespVOS);
    }

    @GetMapping("/page")
    @ApiOperation("获得地块信息分页")
    @PreAuthorize("@ss.hasPermission('land::query')")
    public CommonResult<PageResult<LandRespVO>> getPage(@Valid LandPageReqVO pageVO) {
        PageResult<LandDO> pageResult = Service.getPage(pageVO);

        PageResult<LandRespVO> landRespVOPageResult = LandConvert.INSTANCE.convertPage(pageResult);
        List<LandRespVO> list = landRespVOPageResult.getList();


        for (LandRespVO landRespVO : list) {
            if (JSONUtil.isTypeJSON(landRespVO.getLandCoordinate())) {
                landRespVO.setPosVoList(JSONArray.parseArray(landRespVO.getLandCoordinate(), PosVo.class));
            }

            landRespVO.setCropsCreateReqVOList(Service.queryLandCropsByLandId(landRespVO.getId()));
        }
        landRespVOPageResult.setList(list);
        return success(landRespVOPageResult);
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出地块信息 Excel")
    @PreAuthorize("@ss.hasPermission('land::export')")
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
    @PreAuthorize("@ss.hasPermission('land::query')")
    public CommonResult<List<RaiseCropsRespVO>> queryRaiseCrops() {
        List<RaiseCropsRespVO> raiseCropsRespVOS = Service.queryRaiseCrops();

        for (RaiseCropsRespVO raiseCropsRespVO : raiseCropsRespVOS) {
            raiseCropsRespVO.setCropsVarietiesList(Service.queryCropsVarieties(raiseCropsRespVO.getId()));
        }

        return success(raiseCropsRespVOS);
    }

    @SneakyThrows
    @PostMapping("/initLand")
    @ApiOperation("遥感初始化地块")
    @PreAuthorize("@ss.hasPermission('land::create')")
    public CommonResult<Integer> initLand(MultipartFile file) {
        InputStream inputStream = file.getInputStream();
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        //将输入流数据放入StringBuilder
        StringBuilder resultSB = new StringBuilder();
        String inputStr = null;
        while ((inputStr = streamReader.readLine()) != null) {
            resultSB.append(inputStr);
        }

        //将StringBuilder转换为JSONObject
        JSONObject jsonObject = JSONObject.parseObject(resultSB.toString());

        JSONArray featuresJsonArray = (JSONArray) jsonObject.get("features");

        //地块颜色
        String[] colors = {"#52F5FF", "#C8FF52", "#FF1F25", "#3400FF", "#FFE602", "#FF9922"};

        for (Object featuresObject : featuresJsonArray) {
            JSONObject featuresJSONObject = (JSONObject) featuresObject;
            JSONObject propertiesJSONObject = (JSONObject) featuresJSONObject.get("properties");
            String area = propertiesJSONObject.getString("area");
            Integer fid = propertiesJSONObject.getInteger("fid");
            String x = propertiesJSONObject.getString("centroid_x");
            String y = propertiesJSONObject.getString("centroid_y");
            String cropType = propertiesJSONObject.getString("crop_type");
            JSONObject geometryJSONObject = (JSONObject) featuresJSONObject.get("geometry");
            JSONArray coordinatesJSONArray = (JSONArray) geometryJSONObject.get("coordinates");

            String landCoordinate = y + "," + x;

            log.info("=============================== area {} fid {} landCoordinate {} geometryJSONObject {} coordinatesJSONArray {}", area, fid, landCoordinate, geometryJSONObject.toString(), coordinatesJSONArray.get(0).toString());
            LandCreateReqVO createReqVO = new LandCreateReqVO();
            createReqVO.setArea(Double.valueOf(area));
            createReqVO.setLandCoordinateCenter(landCoordinate);
            createReqVO.setLandCoordinate(coordinatesJSONArray.get(0).toString());

            int random = (int) (Math.random() * 6);
            createReqVO.setColor(colors[random]);
            createReqVO.setFid(fid);
            createReqVO.setLandName(fid.toString());
            createReqVO.setCrops(cropType);
            Service.createInitLand(createReqVO);

        }

        return success(1);
    }

    /**
     * 查询地块种植作物信息
     *
     * @return
     */
    @GetMapping("/queryFarmLayer")
    @ApiOperation("查询农场")
    @PreAuthorize("@ss.hasPermission('land::query')")
    public CommonResult<List<FarmLayerVO>> queryFarmLayer() {
        List<FarmLayerVO> farmLayerVOList = farmLayerService.queryFarmLayer();
        return success(farmLayerVOList);
    }
}
