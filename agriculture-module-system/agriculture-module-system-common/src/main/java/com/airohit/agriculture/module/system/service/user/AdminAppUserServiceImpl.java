package com.airohit.agriculture.module.system.service.user;

import cn.hutool.core.util.ObjectUtil;
import com.airohit.agriculture.framework.common.enums.CommonStatusEnum;
import com.airohit.agriculture.framework.common.enums.UserTypeEnum;
import com.airohit.agriculture.framework.common.util.monitor.TracerUtils;
import com.airohit.agriculture.framework.common.util.servlet.ServletUtils;
import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.framework.tenant.core.context.TenantContextHolder;
import com.airohit.agriculture.framework.tenant.core.util.TenantUtils;
import com.airohit.agriculture.module.infra.api.file.FileApi;
import com.airohit.agriculture.module.system.api.logger.dto.LoginLogCreateReqDTO;
import com.airohit.agriculture.module.system.api.social.dto.SocialUserBindReqDTO;
import com.airohit.agriculture.module.system.convert.auth.AuthConvert;
import com.airohit.agriculture.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import com.airohit.agriculture.module.system.dal.dataobject.permission.RoleDO;
import com.airohit.agriculture.module.system.dal.dataobject.social.SocialUserBindDO;
import com.airohit.agriculture.module.system.dal.dataobject.social.SocialUserDO;
import com.airohit.agriculture.module.system.dal.dataobject.user.AdminUserDO;
import com.airohit.agriculture.module.system.dal.mysql.dept.UserPostMapper;
import com.airohit.agriculture.module.system.dal.mysql.farm.SystemFarmMapper;
import com.airohit.agriculture.module.system.dal.mysql.social.SocialUserBindMapper;
import com.airohit.agriculture.module.system.dal.mysql.user.AdminUserMapper;
import com.airohit.agriculture.module.system.entity.admin.auth.vo.AuthLoginRespVO;
import com.airohit.agriculture.module.system.entity.app.user.AppCreateUserRespVo;
import com.airohit.agriculture.module.system.enums.logger.LoginLogTypeEnum;
import com.airohit.agriculture.module.system.enums.logger.LoginResultEnum;
import com.airohit.agriculture.module.system.enums.oauth2.OAuth2ClientConstants;
import com.airohit.agriculture.module.system.enums.permission.RoleCodeEnum;
import com.airohit.agriculture.module.system.service.dept.DeptService;
import com.airohit.agriculture.module.system.service.dept.PostService;
import com.airohit.agriculture.module.system.service.farm.SystemFarmService;
import com.airohit.agriculture.module.system.service.logger.LoginLogService;
import com.airohit.agriculture.module.system.service.oauth2.OAuth2TokenService;
import com.airohit.agriculture.module.system.service.permission.PermissionService;
import com.airohit.agriculture.module.system.service.permission.RoleService;
import com.airohit.agriculture.module.system.service.sms.SmsCodeService;
import com.airohit.agriculture.module.system.service.social.SocialUserService;
import com.airohit.agriculture.module.system.service.tenant.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.system.enums.ErrorCodeConstants.AUTH_LOGIN_USER_DISABLED;
import static java.util.Collections.singleton;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/6 16:45
 */
@Service("adminAppUserService")
@Slf4j
public class AdminAppUserServiceImpl implements AdminAppUserService {

    @Resource
    private AdminUserMapper userMapper;
    @Value("${sys.user.init-password:agricultureyuanma}")
    private String userInitPassword;


    @Resource
    private DeptService deptService;
    @Resource
    private PostService postService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    @Lazy // 延迟，避免循环依赖报错
    private TenantService tenantService;

    @Resource
    private UserPostMapper userPostMapper;
    @Resource
    private SystemFarmMapper systemFarmMapper;

    @Resource
    private FileApi fileApi;
    @Resource
    private LoginLogService loginLogService;

    @Resource
    private OAuth2TokenService oauth2TokenService;
    @Resource
    @Lazy // 循环依赖（自己依赖自己），避免报错
    private AdminUserServiceImpl self;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private SocialUserService socialUserService;
    @Resource
    private SmsCodeService service;
    @Resource
    private AdminUserService userService;
    @Resource
    private SocialUserBindMapper socialUserBindMapper;
    @Resource
    private SystemFarmService systemFarmService;

    @Override
    @TenantIgnore
    @Transactional(rollbackFor = Exception.class)
    public AuthLoginRespVO createUserByApp(AppCreateUserRespVo appCreateUserRespVo) {
        AdminUserDO user = userService.getUserByMobile(appCreateUserRespVo.getMobile());
        if (user == null || user.getDeleted()) {
            // 创建 Token 令牌，记录登录日志
            //创建用户
            user = new AdminUserDO();
            user.setUsername(appCreateUserRespVo.getMobile());
            user.setMobile(appCreateUserRespVo.getMobile());
            user.setNickname(appCreateUserRespVo.getNickname());
            user.setStatus(CommonStatusEnum.ENABLE.getStatus()); // 默认开启
            user.setTenantId(systemFarmMapper.getTenantByFarm(appCreateUserRespVo.getFarmId()));
            user.setPassword(passwordEncoder.encode(appCreateUserRespVo.getPassword())); // 加密密码
            userMapper.insert(user);
            //绑定默认角色
            RoleDO roleByCode = roleService.getRoleByCode(RoleCodeEnum.PEASANT_ROLE.getCode(), user.getTenantId());
            AdminUserDO finalUser = user;
            TenantUtils.execute(user.getTenantId(), () -> permissionService.assignUserRole(finalUser.getId(), singleton(roleByCode.getId())));
        } else {
            tenantService.validTenant(user.getTenantId());
        }
        if (ObjectUtil.notEqual(user.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        SocialUserDO socialUser = appCreateUserRespVo.getSocialUserDO();
        if (Objects.nonNull(socialUser)) {
            // 社交用户可能之前绑定过别的用户，需要进行解绑
            socialUserBindMapper.deleteByUserTypeAndSocialUserId(getUserType().getValue(), socialUser.getId());

            // 用户可能之前已经绑定过该社交类型，需要进行解绑
            socialUserBindMapper.deleteByUserTypeAndUserIdAndSocialType(getUserType().getValue(), user.getId(),
                    socialUser.getType());

            // 绑定当前登录的社交用户
            SocialUserBindDO socialUserBind = SocialUserBindDO.builder()
                    .userId(user.getId()).userType(getUserType().getValue())
                    .socialUserId(socialUser.getId()).socialType(socialUser.getType()).build();
            socialUserBindMapper.insert(socialUserBind);
        }
        // 创建 Token 令牌，记录登录日志
        AuthLoginRespVO tokenAfterLoginSuccess = createTokenAfterLoginSuccessApp(user.getId(), user.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME, user.getTenantId());
        tokenAfterLoginSuccess.setUserIP(ServletUtils.getClientIP());
        tokenAfterLoginSuccess.setFarmList(systemFarmService.getFarmListByTenant(user.getTenantId()));
        return tokenAfterLoginSuccess;
    }

    @Override
    public AuthLoginRespVO bindAppUser(AppCreateUserRespVo appCreateUserRespVo) {
        self.checkCreateOrUpdate(null, appCreateUserRespVo.getMobile(), appCreateUserRespVo.getMobile(), null,
                null, null);

        //创建用户
        AdminUserDO user = new AdminUserDO();
        user.setUsername(appCreateUserRespVo.getMobile());
        user.setMobile(appCreateUserRespVo.getMobile());
        user.setNickname(appCreateUserRespVo.getNickname());
        user.setStatus(CommonStatusEnum.ENABLE.getStatus()); // 默认开启
        user.setTenantId(systemFarmMapper.getTenantByFarm(appCreateUserRespVo.getFarmId()));
        user.setPassword(passwordEncoder.encode(appCreateUserRespVo.getPassword())); // 加密密码
        userMapper.insert(user);
        if (appCreateUserRespVo.getSocialType() != null) {
            //绑定社交用户
            socialUserService.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
                    appCreateUserRespVo.getSocialType(), appCreateUserRespVo.getSocialCode(), appCreateUserRespVo.getSocialState()));
        }
        //绑定默认角色
        RoleDO roleByCode = roleService.getRoleByCode(RoleCodeEnum.PEASANT_ROLE.getCode(), user.getTenantId());
        TenantUtils.execute(user.getTenantId(), () -> permissionService.assignUserRole(user.getId(), singleton(roleByCode.getId())));
        // 创建 Token 令牌，记录登录日志
        AuthLoginRespVO tokenAfterLoginSuccess = createTokenAfterLoginSuccessApp(user.getId(), user.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME, user.getTenantId());
        tokenAfterLoginSuccess.setFarmList(systemFarmService.getFarmListByTenant(user.getTenantId()));
        tokenAfterLoginSuccess.setUserIP(ServletUtils.getClientIP());
        return tokenAfterLoginSuccess;
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

    private UserTypeEnum getUserType() {
        return UserTypeEnum.ADMIN;
    }

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
            self.updateUserLogin(userId, ServletUtils.getClientIP());
        }
    }


}
