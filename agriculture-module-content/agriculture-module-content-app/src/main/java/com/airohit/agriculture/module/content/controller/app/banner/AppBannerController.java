package com.airohit.agriculture.module.content.controller.app.banner;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.module.content.convert.banner.BannerConvert;
import com.airohit.agriculture.module.content.dal.dataobject.banner.BannerDO;
import com.airohit.agriculture.module.content.service.banner.BannerService;
import com.airohit.agriculture.module.content.vo.banner.BannerPageReqVO;
import com.airohit.agriculture.module.content.vo.banner.BannerRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Api(tags = "APP - 广告信息")
@RestController
@RequestMapping("/content/banner")
@Validated
public class AppBannerController {

    @Resource
    private BannerService bannerService;

    @GetMapping("/page")
    @ApiOperation("获得广告信息分页")
    @TenantIgnore
    public CommonResult<PageResult<BannerRespVO>> getBannerPage(@Valid BannerPageReqVO pageVO) {
        PageResult<BannerDO> pageResult = bannerService.getBannerAppPage(pageVO);
        return success(BannerConvert.INSTANCE.convertPage(pageResult));
    }
}
