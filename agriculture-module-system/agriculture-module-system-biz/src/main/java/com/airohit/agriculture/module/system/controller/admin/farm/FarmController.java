package com.airohit.agriculture.module.system.controller.admin.farm;

import cn.hutool.json.JSONUtil;
import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.excel.core.util.ExcelUtils;
import com.airohit.agriculture.framework.operatelog.core.annotations.OperateLog;
import com.airohit.agriculture.module.system.api.slave.vo.farm.*;
import com.airohit.agriculture.module.system.convert.farm.FarmConvert;
import com.airohit.agriculture.module.system.dal.dataobject.farm.FarmDO;
import com.airohit.agriculture.module.system.entity.admin.farm.vo.FarmExcelVO;
import com.airohit.agriculture.module.system.service.farm.SystemFarmService;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;
import static com.airohit.agriculture.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;


@Api(tags = "管理后台 - 农场")
@RestController
@RequestMapping("/system/farm/")
@Validated
public class FarmController {

    @Resource
    private SystemFarmService Service;

    @PostMapping("/create")
    @ApiOperation("创建农场")
    @PreAuthorize("@ss.hasPermission('farm::create')")
    public CommonResult<Integer> create(@Valid @RequestBody FarmCreateReqVO createReqVO) {
        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新农场")
    @PreAuthorize("@ss.hasPermission('farm::update')")
    public CommonResult<Boolean> update(@Valid @RequestBody FarmUpdateReqVO updateReqVO) {
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除农场")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('farm::delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Integer id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得农场")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('farm::query')")
    public CommonResult<FarmRespVO> get(@RequestParam("id") Integer id) {
        FarmDO farmDO = Service.get(id);
        FarmRespVO convert = FarmConvert.INSTANCE.convert(farmDO);
        if (JSONUtil.isTypeJSON(convert.getCoordinate())) {
            convert.setPosVoList(JSONArray.parseArray(farmDO.getCoordinate(), PosVo.class));
        }
        return success(convert);
    }

    @GetMapping("/getFarmByTenant")
    @ApiOperation("根据租户获得农场")
    @PermitAll
    public CommonResult<FarmRespVO> get(@RequestParam("tenantId") Long tenantId) {
        FarmDO farmDO = Service.getFarmByTenant(tenantId);
        FarmRespVO convert = FarmConvert.INSTANCE.convert(farmDO);
        if (JSONUtil.isTypeJSON(convert.getCoordinate())) {
            convert.setPosVoList(JSONArray.parseArray(farmDO.getCoordinate(), PosVo.class));
        }
        return success(convert);
    }

    @GetMapping("/getFarmListByTenant")
    @ApiOperation("根据租户获得农场")
    @PermitAll
    public CommonResult<List<FarmRespVO>> getFarmListByTenant(@RequestParam("tenantId") Long tenantId) {
        List<FarmDO> farmListByTenant = Service.getFarmListByTenant(tenantId);
        List<FarmRespVO> farmRespVOList = new ArrayList<>();
        for (FarmDO farmDO : farmListByTenant) {
            FarmRespVO convert = FarmConvert.INSTANCE.convert(farmDO);
            if (JSONUtil.isTypeJSON(convert.getCoordinate())) {
                convert.setPosVoList(JSONArray.parseArray(farmDO.getCoordinate(), PosVo.class));
            }
            farmRespVOList.add(convert);
        }
        return success(farmRespVOList);
    }

    @GetMapping("/list")
    @ApiOperation("获得农场列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('farm::query')")
    public CommonResult<List<FarmRespVO>> getList(@RequestParam("ids") Collection<Integer> ids) {
        List<FarmDO> list = Service.getList(ids);
        List<FarmRespVO> farmRespVOS = FarmConvert.INSTANCE.convertList(list);
        for (FarmRespVO farmRespVO : farmRespVOS) {
            if (JSONUtil.isTypeJSON(farmRespVO.getCoordinate())) {
                farmRespVO.setPosVoList(JSONArray.parseArray(farmRespVO.getCoordinate(), PosVo.class));
            }
        }
        return success(farmRespVOS);
    }

    @GetMapping("/page")
    @ApiOperation("获得农场分页")
    @PreAuthorize("@ss.hasPermission('farm::query')")
    public CommonResult<PageResult<FarmRespVO>> getPage(@Valid FarmPageReqVO pageVO) {
        PageResult<FarmDO> pageResult = Service.getPage(pageVO);
        PageResult<FarmRespVO> farmRespVOPageResult = FarmConvert.INSTANCE.convertPage(pageResult);
        List<FarmRespVO> list = farmRespVOPageResult.getList();
        for (FarmRespVO farmRespVO : list) {
            if (JSONUtil.isTypeJSON(farmRespVO.getCoordinate())) {
                farmRespVO.setPosVoList(JSONArray.parseArray(farmRespVO.getCoordinate(), PosVo.class));
            }
        }
        farmRespVOPageResult.setList(list);
        return success(farmRespVOPageResult);
    }

    @GetMapping("/farmList")
    @ApiOperation("查找所有未关联租户的农场")
    @PermitAll
    public CommonResult<List<FarmRespVO>> farmList() {
        List<FarmDO> pageResult = Service.getFarmList();
        return success(FarmConvert.INSTANCE.convertList(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出农场 Excel")
    @PreAuthorize("@ss.hasPermission('farm::export')")
    @OperateLog(type = EXPORT)
    public void exportExcel(@Valid FarmExportReqVO exportReqVO,
                            HttpServletResponse response) throws IOException {
        List<FarmDO> list = Service.getList(exportReqVO);
        // 导出 Excel
        List<FarmExcelVO> datas = FarmConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "农场.xls", "数据", FarmExcelVO.class, datas);
    }

}
