package com.airohit.agriculture.module.peasant.controller.app.peasant;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.excel.core.util.ExcelUtils;
import com.airohit.agriculture.framework.operatelog.core.annotations.OperateLog;
import com.airohit.agriculture.module.peasant.convert.peasant.PeasantConvert;
import com.airohit.agriculture.module.peasant.dal.dataobject.peasant.PeasantDO;
import com.airohit.agriculture.module.peasant.service.peasant.PeasantService;
import com.airohit.agriculture.module.peasant.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;
import static com.airohit.agriculture.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;


@Api(tags = "管理后台 - 农户")
@RestController
@RequestMapping("/app/peasant/")
@Validated
public class AppPeasantController {

    @Resource
    private PeasantService Service;

    @PostMapping("/create")
    @ApiOperation("创建农户")
    @PreAuthorize("@ss.hasPermission('peasant::create')")
    public CommonResult<Integer> createApp(@Valid @RequestBody PeasantCreateReqVO createReqVO) {
        return success(Service.createApp(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新农户")
    @PreAuthorize("@ss.hasPermission('peasant::update')")
    public CommonResult<Boolean> updateApp(@Valid @RequestBody PeasantUpdateReqVO updateReqVO) {
        Service.updateApp(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除农户")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('peasant::delete')")
    public CommonResult<Boolean> deleteApp(@RequestParam("id") Integer id) {
        Service.deleteApp(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得农户")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('peasant::query')")
    public CommonResult<PeasantRespVO> getApp(@RequestParam("id") Integer id) {
        PeasantDO peasantDO = Service.getApp(id);
        return success(PeasantConvert.INSTANCE.convert(peasantDO));
    }

    @GetMapping("/list")
    @ApiOperation("获得农户列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('peasant::query')")
    public CommonResult<List<PeasantRespVO>> getListApp(@RequestParam("ids") Collection<Integer> ids) {
        List<PeasantDO> list = Service.getListApp(ids);
        return success(PeasantConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得农户分页")
    @PreAuthorize("@ss.hasPermission('peasant::query')")
    public CommonResult<PageResult<PeasantRespVO>> getPageApp(@Valid PeasantPageReqVO pageVO) {
        PageResult<PeasantDO> pageResult = Service.getPageApp(pageVO);
        return success(PeasantConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出农户 Excel")
    @PreAuthorize("@ss.hasPermission('peasant::export')")
    @OperateLog(type = EXPORT)
    public void exportExcel(@Valid PeasantExportReqVO exportReqVO,
                            HttpServletResponse response) throws IOException {
        List<PeasantDO> list = Service.getList(exportReqVO);
        // 导出 Excel
        List<PeasantExcelVO> datas = PeasantConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "农户.xls", "数据", PeasantExcelVO.class, datas);
    }

}
