package com.airohit.agriculture.module.peasant.controller.admin.peasant;

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
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;
import static com.airohit.agriculture.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;


@Api(tags = "管理后台 - 农户")
@RestController
@RequestMapping("/peasant/")
@Validated
public class PeasantController {

    @Resource
    private PeasantService Service;

    @PostMapping("/create")
    @ApiOperation("创建农户")
    @PreAuthorize("@ss.hasPermission('peasant::create')")
    public CommonResult<Integer> create(@Valid @RequestBody PeasantCreateReqVO createReqVO) {
        return success(Service.create(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新农户")
    @PreAuthorize("@ss.hasPermission('peasant::update')")
    public CommonResult<Boolean> update(@Valid @RequestBody PeasantUpdateReqVO updateReqVO) {
        Service.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除农户")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('peasant::delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Integer id) {
        Service.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得农户")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('peasant::query')")
    public CommonResult<PeasantRespVO> get(@RequestParam("id") Integer id) {
        PeasantDO peasantDO = Service.get(id);
        return success(PeasantConvert.INSTANCE.convert(peasantDO));
    }

    @GetMapping("/list")
    @ApiOperation("获得农户列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('peasant::query')")
    public CommonResult<List<PeasantRespVO>> getList(@RequestParam("ids") Collection<Integer> ids) {
        List<PeasantDO> list = Service.getList(ids);
        return success(PeasantConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得农户分页")
    @PreAuthorize("@ss.hasPermission('peasant::query')")
    public CommonResult<PageResult<PeasantRespVO>> getPage(@Valid PeasantPageReqVO pageVO) {
        PageResult<PeasantDO> pageResult = Service.getPage(pageVO);
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

    @GetMapping("/get-import-template")
    @ApiOperation("获得导入用户模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<Excel> list = Arrays.asList(
                //Excel.builder().name("名字").phone("手机号").province("省").city("市").area("区").build(),

                Excel.builder().name("王莽").phone("15601691301").province("黑龙江省")
                        .city("哈尔滨市").area("道里区").build(),

                Excel.builder().name("霍去病").phone("15601691300").province("天津市")
                        .city("市辖区").area("南开区").build()
        );

        // 输出
        ExcelUtils.write(response, "用户导入模板.xls", "用户列表", Excel.class, list);
    }

    @PostMapping("/import")
    @ApiOperation("导入信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "Excel 文件", required = true, dataTypeClass = MultipartFile.class),
            @ApiImplicitParam(name = "updateSupport", value = "是否支持更新，默认为 false", example = "true", dataTypeClass = Boolean.class)
    })
    @PreAuthorize("@ss.hasPermission('peasant::import')")
    public CommonResult<ExcelImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                       @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<Excel> list = ExcelUtils.read(file, Excel.class);
        return success(Service.importExcel(list, updateSupport));
    }

    @GetMapping("/allList")
    @ApiOperation("获得所有农户")
    public CommonResult<List<PeasantRespVO>> getAllList() {
        List<PeasantDO> list = Service.getAllList();
        return success(PeasantConvert.INSTANCE.convertList(list));
    }


}
