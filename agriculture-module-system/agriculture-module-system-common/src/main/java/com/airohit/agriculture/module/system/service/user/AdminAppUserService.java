package com.airohit.agriculture.module.system.service.user;

import com.airohit.agriculture.module.system.entity.admin.auth.vo.AuthLoginRespVO;
import com.airohit.agriculture.module.system.entity.app.user.AppCreateUserRespVo;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/6 16:45
 */

public interface AdminAppUserService {

    /**
     * app端创建用户
     *
     * @return
     */
    AuthLoginRespVO createUserByApp(AppCreateUserRespVo appCreateUserRespVo);

    AuthLoginRespVO bindAppUser(AppCreateUserRespVo appCreateUserRespVo);
}
