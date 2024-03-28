package com.airohit.agriculture.module.content.controller.admin.banner;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.excel.core.util.ExcelUtils;
import com.airohit.agriculture.framework.operatelog.core.annotations.OperateLog;
import com.airohit.agriculture.module.content.convert.banner.BannerConvert;
import com.airohit.agriculture.module.content.dal.dataobject.banner.BannerDO;
import com.airohit.agriculture.module.content.service.banner.BannerService;
import com.airohit.agriculture.module.content.vo.banner.*;
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

@Api(tags = "管理后台 - 广告信息")
@RestController
@RequestMapping("/content/banner")
@Validated
public class BannerController {

    @Resource
    private BannerService bannerService;

    @PostMapping("/create")
    @ApiOperation("创建广告信息")
    @PreAuthorize("@ss.hasPermission('content:banner:create')")
    public CommonResult<Integer> createBanner(@Valid @RequestBody BannerCreateReqVO createReqVO) {
        return success(bannerService.createBanner(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新广告信息")
    @PreAuthorize("@ss.hasPermission('content:banner:update')")
    public CommonResult<Boolean> updateBanner(@Valid @RequestBody BannerUpdateReqVO updateReqVO) {
        bannerService.updateBanner(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除广告信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('content:banner:delete')")
    public CommonResult<Boolean> deleteBanner(@RequestParam("id") Integer id) {
        bannerService.deleteBanner(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得广告信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('content:banner:query')")
    public CommonResult<BannerRespVO> getBanner(@RequestParam("id") Integer id) {
        BannerDO banner = bannerService.getBanner(id);
        return success(BannerConvert.INSTANCE.convert(banner));
    }

    @GetMapping("/list")
    @ApiOperation("获得广告信息列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('content:banner:query')")
    public CommonResult<List<BannerRespVO>> getBannerList(@RequestParam("ids") Collection<Integer> ids) {
        List<BannerDO> list = bannerService.getBannerList(ids);
        return success(BannerConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得广告信息分页")
    @PreAuthorize("@ss.hasPermission('content:banner:query')")
    public CommonResult<PageResult<BannerRespVO>> getBannerPage(@Valid BannerPageReqVO pageVO) {
        PageResult<BannerDO> pageResult = bannerService.getBannerPage(pageVO);
        return success(BannerConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出广告信息 Excel")
    @PreAuthorize("@ss.hasPermission('content:banner:export')")
    @OperateLog(type = EXPORT)
    public void exportBannerExcel(@Valid BannerExportReqVO exportReqVO,
                                  HttpServletResponse response) throws IOException {
        List<BannerDO> list = bannerService.getBannerList(exportReqVO);
        // 导出 Excel
        List<BannerExcelVO> datas = BannerConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "广告信息.xls", "数据", BannerExcelVO.class, datas);
    }

}
