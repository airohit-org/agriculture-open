package com.airohit.agriculture.module.system.controller.admin.weixin;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.service.weixin.WeiXinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/3/29 15:01
 */
@Api(tags = "管理后台 - 微信相关接口")
@RestController
@RequestMapping("/system/weixin")
@Validated
@Slf4j
public class WeiXinController {
    @Resource
    private WeiXinService weiXinService;

    @GetMapping("/getJsapiSignature")
    @ApiOperation("获得微信JsapiSignature")
    @PermitAll
    public CommonResult<WxJsapiSignature> createJsapiSignature(@RequestParam("url")
                                                               @ApiParam("url")
                                                               String url) {
        return success(weiXinService.createJsapiSignature(url));
    }
}
