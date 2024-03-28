package com.airohit.agriculture.module.system.entity.app.auth;

import com.airohit.agriculture.module.system.dal.dataobject.social.SocialUserDO;
import com.airohit.agriculture.module.system.entity.admin.auth.vo.AuthLoginRespVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/7/4 08:50
 */
@Data
@ApiModel("社交登录")
public class AppSocialLoginVo {
    @ApiModelProperty("是否授权通过")
    private Boolean isAuth;
    @ApiModelProperty("社交用户信息")
    private SocialUserDO socialUserDO;
    @ApiModelProperty("授权信息")
    private AuthLoginRespVO authLoginRespVO;
}
