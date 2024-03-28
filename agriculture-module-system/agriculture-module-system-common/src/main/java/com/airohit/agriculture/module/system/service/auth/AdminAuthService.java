package com.airohit.agriculture.module.system.service.auth;

import com.airohit.agriculture.module.system.dal.dataobject.user.AdminUserDO;
import com.airohit.agriculture.module.system.entity.admin.auth.vo.*;
import com.airohit.agriculture.module.system.entity.app.auth.AppAuthLoginReqVO;
import com.airohit.agriculture.module.system.entity.app.auth.AppAuthSmsLoginReqVO;
import com.airohit.agriculture.module.system.entity.app.auth.AppSocialLoginVo;

import javax.validation.Valid;

/**
 * 管理后台的认证 Service 接口
 * <p>
 * 提供用户的登录、登出的能力
 *
 * @author shiminghao
 */
public interface AdminAuthService {

    /**
     * 验证账号 + 密码。如果通过，则返回用户
     *
     * @param username 账号
     * @param password 密码
     * @return 用户
     */
    AdminUserDO authenticate(String username, String password);

    /**
     * 验证账号 + 密码。如果通过，则返回用户
     *
     * @param username 账号
     * @param password 密码
     * @return 用户
     */
    AdminUserDO authenticateByLogin(String username, String password);

    /**
     * 账号登录
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    AuthLoginRespVO login(@Valid AuthLoginReqVO reqVO);

    /**
     * 账号登录
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    AuthLoginRespVO appLogin(@Valid AppAuthLoginReqVO reqVO);

    /**
     * 基于 token 退出登录
     *
     * @param token   token
     * @param logType 登出类型
     */
    void logout(String token, Integer logType);

    /**
     * 短信验证码发送
     *
     * @param reqVO 发送请求
     */
    Long sendSmsCode(AuthSmsSendReqVO reqVO);

    /**
     * 短信验证码发送
     *
     * @param reqVO 发送请求
     */
    void sendSmsCodeApp(AuthSmsSendReqVO reqVO);

    /**
     * 短信登录
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    AuthLoginRespVO smsLogin(AuthSmsLoginReqVO reqVO);

    /**
     * 短信登录
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    AuthLoginRespVO smsLoginApp(AppAuthSmsLoginReqVO reqVO);

    /**
     * 社交快捷登录，使用 code 授权码
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    AuthLoginRespVO socialLogin(@Valid AuthSocialLoginReqVO reqVO);

    /**
     * 社交快捷登录，使用 code 授权码
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    AuthLoginRespVO socialLoginApp(@Valid AuthSocialLoginReqVO reqVO);

    /**
     * 社交快捷登录，使用 code 授权码
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    AppSocialLoginVo weChatSocialLoginApp(@Valid AuthSocialLoginReqVO reqVO);

    /**
     * 刷新访问令牌
     *
     * @param refreshToken 刷新令牌
     * @return 登录结果
     */
    AuthLoginRespVO refreshToken(String refreshToken);

}
