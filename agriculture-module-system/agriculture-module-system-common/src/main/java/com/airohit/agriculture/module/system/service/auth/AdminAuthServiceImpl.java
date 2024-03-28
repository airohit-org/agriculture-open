package com.airohit.agriculture.module.system.service.auth;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.airohit.agriculture.framework.common.enums.CommonStatusEnum;
import com.airohit.agriculture.framework.common.enums.UserTypeEnum;
import com.airohit.agriculture.framework.common.util.monitor.TracerUtils;
import com.airohit.agriculture.framework.common.util.servlet.ServletUtils;
import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.framework.tenant.core.context.TenantContextHolder;
import com.airohit.agriculture.module.system.api.logger.dto.LoginLogCreateReqDTO;
import com.airohit.agriculture.module.system.api.sms.SmsCodeApi;
import com.airohit.agriculture.module.system.api.social.dto.SocialUserBindReqDTO;
import com.airohit.agriculture.module.system.convert.auth.AuthConvert;
import com.airohit.agriculture.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import com.airohit.agriculture.module.system.dal.dataobject.social.SocialUserBindDO;
import com.airohit.agriculture.module.system.dal.dataobject.social.SocialUserDO;
import com.airohit.agriculture.module.system.dal.dataobject.user.AdminUserDO;
import com.airohit.agriculture.module.system.dal.mysql.social.SocialUserBindMapper;
import com.airohit.agriculture.module.system.dal.mysql.user.AdminUserMapper;
import com.airohit.agriculture.module.system.entity.admin.auth.vo.*;
import com.airohit.agriculture.module.system.entity.app.auth.AppAuthLoginReqVO;
import com.airohit.agriculture.module.system.entity.app.auth.AppAuthSmsLoginReqVO;
import com.airohit.agriculture.module.system.entity.app.auth.AppSocialLoginVo;
import com.airohit.agriculture.module.system.enums.logger.LoginLogTypeEnum;
import com.airohit.agriculture.module.system.enums.logger.LoginResultEnum;
import com.airohit.agriculture.module.system.enums.oauth2.OAuth2ClientConstants;
import com.airohit.agriculture.module.system.enums.sms.SmsSceneEnum;
import com.airohit.agriculture.module.system.service.farm.SystemFarmService;
import com.airohit.agriculture.module.system.service.logger.LoginLogService;
import com.airohit.agriculture.module.system.service.member.MemberService;
import com.airohit.agriculture.module.system.service.oauth2.OAuth2TokenService;
import com.airohit.agriculture.module.system.service.social.SocialUserService;
import com.airohit.agriculture.module.system.service.tenant.TenantService;
import com.airohit.agriculture.module.system.service.user.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.framework.common.util.servlet.ServletUtils.getClientIP;
import static com.airohit.agriculture.module.system.enums.ErrorCodeConstants.*;

/**
 * Auth Service 实现类
 *
 * @author shiminghao
 */
@Service
@Slf4j
public class AdminAuthServiceImpl implements AdminAuthService {

    @Resource
    private AdminUserService userService;
    @Resource
    private LoginLogService loginLogService;
    @Resource
    private OAuth2TokenService oauth2TokenService;
    @Resource
    private SocialUserService socialUserService;
    @Resource
    private MemberService memberService;
    @Resource
    private SocialUserBindMapper socialUserBindMapper;
    @Resource
    private Validator validator;

    @Resource
    private SmsCodeApi smsCodeApi;
    @Resource
    private AdminUserMapper adminUserMapper;
    @Resource
    private TenantService tenantService;
    @Resource
    private SystemFarmService systemFarmService;

    /**
     * 验证码的开关，默认为 true
     */
    @Value("${agriculture.captcha.enable:true}")
    private Boolean captchaEnable;

    @Override
    public AdminUserDO authenticate(String username, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        // 校验账号是否存在
        AdminUserDO user = userService.getUserByUsername(username);
//        AdminUserDO user = adminUserMapper.selectOne(AdminUserDO::getUsername,username);
        if (user == null) {
            createLoginLog(null, username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        if (!userService.isPasswordMatch(password, user.getPassword())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验是否禁用
        if (ObjectUtil.notEqual(user.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        return user;
    }

    @Override
    @TenantIgnore
    public AdminUserDO authenticateByLogin(String username, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        // 校验账号是否存在
//        AdminUserDO user = userService.getUserByUsername(username);
        AdminUserDO user = adminUserMapper.selectOne(AdminUserDO::getUsername, username);
        if (user == null || user.getTenantId() == 1L) {
            createLoginLog(null, username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        if (!userService.isPasswordMatch(password, user.getPassword())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验是否禁用
        if (ObjectUtil.notEqual(user.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        return user;
    }

    @Override
    @TenantIgnore
    public AuthLoginRespVO login(AuthLoginReqVO reqVO) {
        // 判断验证码是否正确
//        verifyCaptcha(reqVO);
        RSA rsa = new RSA(PRIVATE_KAY, PUBLIC_KSY);
        //解密密码
        String password = new String(
                rsa.decrypt(Base64.getDecoder().decode(reqVO.getPassword().getBytes(StandardCharsets.UTF_8))
                        , KeyType.PrivateKey), StandardCharsets.UTF_8);
        reqVO.setPassword(password);
        // 使用账号密码，进行登录
        AdminUserDO user = authenticateByLogin(reqVO.getUsername(), reqVO.getPassword());
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        if (user.getDeleted()) {
            throw exception(USER_NOT_EXISTS);
        }
        // 如果 socialType 非空，说明需要绑定社交用户
        if (reqVO.getSocialType() != null) {
            socialUserService.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
                    reqVO.getSocialType(), reqVO.getSocialCode(), reqVO.getSocialState()));
        }
        AuthLoginRespVO tokenAfterLoginSuccess = createTokenAfterLoginSuccess(user.getId(), reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME, user.getTenantId());
        tokenAfterLoginSuccess.setUserIP(ServletUtils.getClientIP());
        tokenAfterLoginSuccess.setFarmList(systemFarmService.getFarmListByTenant(user.getTenantId()));
        // 创建 Token 令牌，记录登录日志
        return tokenAfterLoginSuccess;
    }

    @Override
    public AuthLoginRespVO appLogin(AppAuthLoginReqVO reqVO) {
        RSA rsa = new RSA(PRIVATE_KAY, PUBLIC_KSY);
        //解密密码
        String password = new String(
                rsa.decrypt(Base64.getDecoder().decode(reqVO.getPassword().getBytes(StandardCharsets.UTF_8))
                        , KeyType.PrivateKey), StandardCharsets.UTF_8);
        reqVO.setPassword(password);
        // 使用账号密码，进行登录
        AdminUserDO user = authenticateByLogin(reqVO.getUsername(), reqVO.getPassword());
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        if (user.getDeleted()) {
            throw exception(USER_NOT_EXISTS);
        }
        tenantService.validTenant(user.getTenantId());

        // 如果 socialType 非空，说明需要绑定社交用户
        if (reqVO.getSocialType() != null) {
            socialUserService.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
                    reqVO.getSocialType(), reqVO.getSocialCode(), reqVO.getSocialState()));
        }
        AuthLoginRespVO tokenAfterLoginSuccess = createTokenAfterLoginSuccessApp(user.getId(), reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME, user.getTenantId());
        tokenAfterLoginSuccess.setUserIP(ServletUtils.getClientIP());
        tokenAfterLoginSuccess.setFarmList(systemFarmService.getFarmListByTenant(user.getTenantId()));
        // 创建 Token 令牌，记录登录日志
        return tokenAfterLoginSuccess;
    }

    @Override
    public Long sendSmsCode(AuthSmsSendReqVO reqVO) {
        // 登录场景，验证是否存在
        AdminUserDO userByMobile = userService.getUserByMobile(reqVO.getMobile());
        if (userByMobile == null) {
            throw exception(AUTH_MOBILE_NOT_EXISTS);
        }
        // 发送验证码
        smsCodeApi.sendSmsCode(AuthConvert.INSTANCE.convert(reqVO).setCreateIp(getClientIP()));
        return userByMobile.getId();
    }

    @Override
    public void sendSmsCodeApp(AuthSmsSendReqVO reqVO) {
        // 登录场景，验证是否存在
        // 发送验证码
        smsCodeApi.sendSmsCode(AuthConvert.INSTANCE.convert(reqVO).setCreateIp(getClientIP()));
    }

    @Override
    public AuthLoginRespVO smsLogin(AuthSmsLoginReqVO reqVO) {
        // 校验验证码 TODO 暂时关闭 正式环境打开
//        if (!"999999".equals(reqVO.getCode())){
//            throw ServiceExceptionUtil.exception(SMS_CODE_NOT_FOUND);
//        }
        smsCodeApi.useSmsCode(AuthConvert.INSTANCE.convert(reqVO, SmsSceneEnum.ADMIN_MEMBER_LOGIN.getScene(), getClientIP()));

        // 获得用户信息
        AdminUserDO user = userService.getUserByMobile(reqVO.getMobile());
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        if (user.getDeleted()) {
            throw exception(USER_NOT_EXISTS);
        }
        tenantService.validTenant(user.getTenantId());
        if (reqVO.getSocialType() != null) {
            socialUserService.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
                    reqVO.getSocialType(), reqVO.getSocialCode(), reqVO.getSocialState()));
        }
        AuthLoginRespVO tokenAfterLoginSuccess = createTokenAfterLoginSuccessApp(user.getId(), reqVO.getMobile(), LoginLogTypeEnum.LOGIN_MOBILE, user.getTenantId());
        tokenAfterLoginSuccess.setFarmList(systemFarmService.getFarmListByTenant(user.getTenantId()));
        // 创建 Token 令牌，记录登录日志
        return tokenAfterLoginSuccess;
    }

    @Override
    public AuthLoginRespVO smsLoginApp(AppAuthSmsLoginReqVO reqVO) {
        // 校验验证码
        smsCodeApi.useSmsCode(AuthConvert.INSTANCE.convert(BeanUtil.copyProperties(reqVO, AuthSmsLoginReqVO.class), SmsSceneEnum.MEMBER_LOGIN.getScene(), getClientIP()));

        // 获得用户信息
        AdminUserDO user = userService.getUserByMobile(reqVO.getMobile());
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        if (user.getDeleted()) {
            throw exception(USER_NOT_EXISTS);
        }
        if (ObjectUtil.notEqual(user.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        tenantService.validTenant(user.getTenantId());
        if (reqVO.getSocialType() != null) {
            socialUserService.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
                    reqVO.getSocialType(), reqVO.getSocialCode(), reqVO.getSocialState()));
        }
        // 创建 Token 令牌，记录登录日志
        AuthLoginRespVO tokenAfterLoginSuccessApp = createTokenAfterLoginSuccessApp(user.getId(), reqVO.getMobile(), LoginLogTypeEnum.LOGIN_MOBILE, user.getTenantId());
        tokenAfterLoginSuccessApp.setFarmList(systemFarmService.getFarmListByTenant(user.getTenantId()));
        return tokenAfterLoginSuccessApp;
    }

//    @VisibleForTesting
//    void verifyCaptcha(AuthLoginReqVO reqVO) {
//        // 如果验证码关闭，则不进行校验
//        if (!captchaEnable) {
//            return;
//        }
//        // 校验验证码
//        ValidationUtils.validate(validator, reqVO, AuthLoginReqVO.CodeEnableGroup.class);
//        CaptchaVO captchaVO = new CaptchaVO();
//        captchaVO.setCaptchaVerification(reqVO.getCaptchaVerification());
//        ResponseModel response = captchaService.verification(captchaVO);
//        // 验证不通过
//        if (!response.isSuccess()) {
//            // 创建登录失败日志（验证码不正确)
//            createLoginLog(null, reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME, LoginResultEnum.CAPTCHA_CODE_ERROR);
//            throw exception(AUTH_LOGIN_CAPTCHA_CODE_ERROR, response.getRepMsg());
//        }
//    }

    private void createLoginLog(Long userId, String username,
                                LoginLogTypeEnum logTypeEnum, LoginResultEnum loginResult) {
        // 插入登录日志
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logTypeEnum.getType());
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(getUserType().getValue());
        reqDTO.setUsername(username);
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(loginResult.getResult());
        loginLogService.createLoginLog(reqDTO);
        // 更新最后登录时间
        if (userId != null && Objects.equals(LoginResultEnum.SUCCESS.getResult(), loginResult.getResult())) {
            userService.updateUserLogin(userId, ServletUtils.getClientIP());
        }
    }

    @Override
    public AuthLoginRespVO socialLogin(AuthSocialLoginReqVO reqVO) {
        // 使用 code 授权码，进行登录。然后，获得到绑定的用户编号
        Long userId = socialUserService.getBindUserId(UserTypeEnum.ADMIN.getValue(), reqVO.getType(),
                reqVO.getCode(), reqVO.getState());
        if (userId == null) {
            throw exception(AUTH_THIRD_LOGIN_NOT_BIND);
        }

        // 获得用户
        AdminUserDO user = userService.getUser(userId);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        if (user.getDeleted()) {
            throw exception(USER_NOT_EXISTS);
        }
        AuthLoginRespVO tokenAfterLoginSuccess = createTokenAfterLoginSuccess(user.getId(), user.getUsername(), LoginLogTypeEnum.LOGIN_SOCIAL, user.getTenantId());
        tokenAfterLoginSuccess.setFarmList(systemFarmService.getFarmListByTenant(user.getTenantId()));
        // 创建 Token 令牌，记录登录日志
        return tokenAfterLoginSuccess;
    }

    @Override
    public AuthLoginRespVO socialLoginApp(AuthSocialLoginReqVO reqVO) {
        // 使用 code 授权码，进行登录。然后，获得到绑定的用户编号
        Long userId = socialUserService.getBindUserId(UserTypeEnum.ADMIN.getValue(), reqVO.getType(),
                reqVO.getCode(), reqVO.getState());
        if (userId == null) {
            throw exception(AUTH_THIRD_LOGIN_NOT_BIND);
        }

        // 获得用户
        AdminUserDO user = userService.getUser(userId);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        if (user.getDeleted()) {
            throw exception(USER_NOT_EXISTS);
        }
        if (ObjectUtil.notEqual(user.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        tenantService.validTenant(user.getTenantId());
        AuthLoginRespVO tokenAfterLoginSuccess = createTokenAfterLoginSuccessApp(user.getId(), user.getUsername(), LoginLogTypeEnum.LOGIN_SOCIAL, user.getTenantId());
        tokenAfterLoginSuccess.setFarmList(systemFarmService.getFarmListByTenant(user.getTenantId()));
        // 创建 Token 令牌，记录登录日志
        return tokenAfterLoginSuccess;
    }

    @Override
    public AppSocialLoginVo weChatSocialLoginApp(AuthSocialLoginReqVO reqVO) {
        AppSocialLoginVo appSocialLoginVo = new AppSocialLoginVo();
        // 使用 code 授权码，进行登录。然后，获得到绑定的用户编号
        SocialUserDO socialUserDO = socialUserService.authSocialUser(reqVO.getType(), reqVO.getCode(), reqVO.getState());
        SocialUserBindDO socialUserBindDO = socialUserBindMapper.selectByUserTypeAndSocialUserId(UserTypeEnum.ADMIN.getValue(),
                socialUserDO.getId());
        if (Objects.isNull(socialUserBindDO)) {
            appSocialLoginVo.setIsAuth(Boolean.FALSE);
            appSocialLoginVo.setSocialUserDO(socialUserDO);
            return appSocialLoginVo;
        }

        // 获得用户
        AdminUserDO user = userService.getUser(socialUserBindDO.getUserId());
        if (user == null) {
//            throw exception(USER_NOT_EXISTS);
            appSocialLoginVo.setIsAuth(Boolean.FALSE);
            appSocialLoginVo.setSocialUserDO(socialUserDO);
            return appSocialLoginVo;
        }
        if (user.getDeleted()) {
//            throw exception(USER_NOT_EXISTS);
            appSocialLoginVo.setIsAuth(Boolean.FALSE);
            appSocialLoginVo.setSocialUserDO(socialUserDO);
            return appSocialLoginVo;
        }
        if (ObjectUtil.notEqual(user.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        tenantService.validTenant(user.getTenantId());
        // 创建 Token 令牌，记录登录日志
        AuthLoginRespVO tokenAfterLoginSuccessApp = createTokenAfterLoginSuccessApp(user.getId(), user.getUsername(), LoginLogTypeEnum.LOGIN_SOCIAL, user.getTenantId());
        appSocialLoginVo.setIsAuth(Boolean.TRUE);
        appSocialLoginVo.setAuthLoginRespVO(tokenAfterLoginSuccessApp);
        tokenAfterLoginSuccessApp.setFarmList(systemFarmService.getFarmListByTenant(user.getTenantId()));
        return appSocialLoginVo;
    }

    @Override
    public AuthLoginRespVO refreshToken(String refreshToken) {
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.refreshAccessToken(refreshToken, OAuth2ClientConstants.CLIENT_ID_DEFAULT);
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }

    private AuthLoginRespVO createTokenAfterLoginSuccessApp(Long userId, String username, LoginLogTypeEnum logType, Long tenantId) {
        // 插入登陆日志
        TenantContextHolder.setTenantId(tenantId);
        createLoginLog(userId, username, logType, LoginResultEnum.SUCCESS);
        // 创建访问令牌
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.createAccessToken(userId, getUserType().getValue(),
                OAuth2ClientConstants.CLIENT_ID_DEFAULT, null);
        // 构建返回结果
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }

    private AuthLoginRespVO createTokenAfterLoginSuccess(Long userId, String username, LoginLogTypeEnum logType, Long tenantId) {
        // 插入登陆日志
        TenantContextHolder.setTenantId(tenantId);
        createLoginLog(userId, username, logType, LoginResultEnum.SUCCESS);
        // 创建访问令牌
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.createAccessToken(userId, getUserType().getValue(),
                OAuth2ClientConstants.CLIENT_ID_DEFAULT, null);
        // 构建返回结果
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }

    @Override
    public void logout(String token, Integer logType) {
        // 删除访问令牌
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.removeAccessToken(token);
        if (accessTokenDO == null) {
            return;
        }
        // 删除成功，则记录登出日志
        createLogoutLog(accessTokenDO.getUserId(), accessTokenDO.getUserType(), logType);
    }

    private void createLogoutLog(Long userId, Integer userType, Integer logType) {
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logType);
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(userType);
        if (ObjectUtil.equal(getUserType().getValue(), userType)) {
            reqDTO.setUsername(getUsername(userId));
        } else {
            reqDTO.setUsername(memberService.getMemberUserMobile(userId));
        }
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(LoginResultEnum.SUCCESS.getResult());
        loginLogService.createLoginLog(reqDTO);
    }

    private String getUsername(Long userId) {
        if (userId == null) {
            return null;
        }
        AdminUserDO user = userService.getUser(userId);
        return user != null ? user.getUsername() : null;
    }

    private UserTypeEnum getUserType() {
        return UserTypeEnum.ADMIN;
    }

}
