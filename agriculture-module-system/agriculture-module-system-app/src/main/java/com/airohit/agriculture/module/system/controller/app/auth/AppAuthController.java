package com.airohit.agriculture.module.system.controller.app.auth;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.operatelog.core.annotations.OperateLog;
import com.airohit.agriculture.framework.security.config.SecurityProperties;
import com.airohit.agriculture.module.system.dal.dataobject.farm.FarmDO;
import com.airohit.agriculture.module.system.entity.admin.auth.vo.AuthLoginRespVO;
import com.airohit.agriculture.module.system.entity.admin.auth.vo.AuthSmsSendReqVO;
import com.airohit.agriculture.module.system.entity.admin.auth.vo.AuthSocialLoginReqVO;
import com.airohit.agriculture.module.system.entity.app.auth.AppAuthLoginReqVO;
import com.airohit.agriculture.module.system.entity.app.auth.AppAuthSmsLoginReqVO;
import com.airohit.agriculture.module.system.entity.app.auth.AppSocialLoginVo;
import com.airohit.agriculture.module.system.entity.app.auth.SmsCodeCheckReqVo;
import com.airohit.agriculture.module.system.entity.app.user.AppCreateUserRespVo;
import com.airohit.agriculture.module.system.enums.logger.LoginLogTypeEnum;
import com.airohit.agriculture.module.system.service.auth.AdminAuthService;
import com.airohit.agriculture.module.system.service.farm.SystemFarmService;
import com.airohit.agriculture.module.system.service.sms.SmsCodeService;
import com.airohit.agriculture.module.system.service.social.SocialUserService;
import com.airohit.agriculture.module.system.service.user.AdminAppUserService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;
import static com.airohit.agriculture.framework.security.core.util.SecurityFrameworkUtils.obtainAuthorization;

@Api(tags = "农场APP - 认证")
@RestController
@RequestMapping("/system/app/auth")
@Validated
@Slf4j
public class AppAuthController {

    @Resource
    private AdminAuthService authService;
    @Resource
    private SocialUserService socialUserService;

    @Resource
    private SecurityProperties securityProperties;
    @Resource
    private SystemFarmService systemFarmService;
    @Resource
    private SmsCodeService smsCodeService;

    @Resource
    private AdminAppUserService adminAppUserService;

    @PostMapping("/login")
    @PermitAll
    @ApiOperation("使用账号密码登录")
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public CommonResult<AuthLoginRespVO> login(@RequestBody @Valid AppAuthLoginReqVO reqVO) {
        return success(authService.appLogin(reqVO));
    }

    @PostMapping("/logout")
    @PermitAll
    @ApiOperation("登出系统")
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public CommonResult<Boolean> logout(HttpServletRequest request) {
        String token = obtainAuthorization(request, securityProperties.getTokenHeader());
        if (StrUtil.isNotBlank(token)) {
            authService.logout(token, LoginLogTypeEnum.LOGOUT_SELF.getType());
        }
        return success(true);
    }

    @PostMapping("/refresh-token")
    @PermitAll
    @ApiOperation("刷新令牌")
    @ApiImplicitParam(name = "refreshToken", value = "刷新令牌", required = true, dataTypeClass = String.class)
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public CommonResult<AuthLoginRespVO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return success(authService.refreshToken(refreshToken));
    }


    // ========== 短信登录相关 ==========

    @PostMapping("/sms-login")
    @PermitAll
    @ApiOperation("使用短信验证码登录")
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public CommonResult<AuthLoginRespVO> smsLogin(@RequestBody @Valid AppAuthSmsLoginReqVO reqVO) {
        return success(authService.smsLoginApp(reqVO));
    }

    @PostMapping("/send-sms-code")
    @PermitAll
    @ApiOperation(value = "发送手机验证码")
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public CommonResult<Boolean> sendLoginSmsCode(@RequestBody @Valid AuthSmsSendReqVO reqVO) {
        authService.sendSmsCodeApp(reqVO);
        return success(true);
    }
    // ========== 社交登录相关 ==========


    @PostMapping("/social-login")
    @PermitAll
    @ApiOperation(value = "社交快捷登录，使用 code 授权码", notes = "适合未登录的用户，但是社交账号已绑定用户")
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public CommonResult<AuthLoginRespVO> socialQuickLogin(@RequestBody @Valid AuthSocialLoginReqVO reqVO) {
        return success(authService.socialLoginApp(reqVO));
    }

    @PostMapping("/social-login-app")
    @PermitAll
    @ApiOperation(value = "社交快捷登录(新)，使用 code 授权码", notes = "适合未登录的用户，但是社交账号已绑定用户")
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public CommonResult<AppSocialLoginVo> weChatSocialLoginApp(@RequestBody @Valid AuthSocialLoginReqVO reqVO) {
        return success(authService.weChatSocialLoginApp(reqVO));
    }


    @GetMapping("/getAllFarmList")
    @PermitAll
    @OperateLog(enable = false)
    @ApiOperation(value = "查找所有农场")
    public CommonResult<List<FarmDO>> getAllFarmList() {
        return success(systemFarmService.getAllFarmList());
    }


    @PostMapping("/checkSmsCode")
    @PermitAll
    @ApiOperation(value = "检查验证码是否有效")
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public CommonResult<Long> checkSmsCode(@RequestBody SmsCodeCheckReqVo reqDTO) {
        return success(smsCodeService.checkSmsCodeApp(reqDTO));
    }

    @PostMapping("/createAppUser")
    @ApiOperation("注册用户")
    @PermitAll
    public CommonResult<AuthLoginRespVO> createAppUser(@Valid @RequestBody AppCreateUserRespVo reqVO) {
        AuthLoginRespVO userByApp = adminAppUserService.createUserByApp(reqVO);
        return success(userByApp);
    }

    @PostMapping("/bindAppUser")
    @ApiOperation("微信首次绑定用户")
    @PermitAll
    public CommonResult<AuthLoginRespVO> bindAppUser(@Valid @RequestBody AppCreateUserRespVo reqVO) {
        AuthLoginRespVO userByApp = adminAppUserService.bindAppUser(reqVO);
        return success(userByApp);
    }

    @GetMapping("/tianditu")
    @PermitAll
    @OperateLog(enable = false)
    @ApiOperation(value = "天地图")
    public CommonResult<JSONObject> tianditu(@ApiParam("tk") @RequestParam("tk") String tk,
                                             @ApiParam("lon") @RequestParam("lon") BigDecimal lon,
                                             @ApiParam("lat") @RequestParam("lat") BigDecimal lat,
                                             @ApiParam("浏览器User-Agent") @RequestParam(value = "us", required = false) String ua) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", "geocode");
        map.put("tk", tk);
        StringBuilder builder = StrUtil.builder();
        builder.append("{'lon':");
        builder.append(lon);
        builder.append(",'lat':");
        builder.append(lat);
        builder.append(",'ver':1}");
        map.put("postStr", builder);
        HttpRequest get = HttpUtil.createGet("https://api.tianditu.gov.cn/geocoder").form(map);
        if (StrUtil.isBlank(ua)) {
            get.removeHeader("User-Agent");
        } else {
            get.header("User-Agent", ua);
        }
        JSONObject jsonObject = JSONObject.parseObject(get.execute().body());
        return success(jsonObject);
    }
}
