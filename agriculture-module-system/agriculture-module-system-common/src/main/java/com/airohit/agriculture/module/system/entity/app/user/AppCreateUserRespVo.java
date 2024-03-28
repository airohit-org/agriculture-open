package com.airohit.agriculture.module.system.entity.app.user;

import com.airohit.agriculture.framework.common.validation.InEnum;
import com.airohit.agriculture.module.system.dal.dataobject.social.SocialUserDO;
import com.airohit.agriculture.module.system.enums.social.SocialTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/23 13:30
 */
@Data
@ApiModel("app创建用户")
public class AppCreateUserRespVo {
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("验证码")
    private String code;
    @ApiModelProperty("农场ID")
    private Integer farmId;

    @ApiModelProperty(value = "社交平台的类型,微信公众平台传31,此字段用来绑定社交用户,非必填", example = "10", notes = "参见 SysUserSocialTypeEnum 枚举值")
    @InEnum(SocialTypeEnum.class)
    private Integer socialType;

    @ApiModelProperty(value = "授权码,此字段用来绑定社交用户,非必填", example = "1024")
    private String socialCode;

    @ApiModelProperty(value = "state,此字段用来绑定社交用户,非必填", example = "9b2ffbc1-7425-4155-9894-9d5c08541d62")
    private String socialState;


    @ApiModelProperty("社交用户信息")
    private SocialUserDO socialUserDO;

}
