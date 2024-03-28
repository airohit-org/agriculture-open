package com.airohit.agriculture.module.system.entity.app.auth;

import cn.hutool.core.util.StrUtil;
import com.airohit.agriculture.framework.common.validation.InEnum;
import com.airohit.agriculture.framework.common.validation.Mobile;
import com.airohit.agriculture.module.system.enums.social.SocialTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;

@ApiModel("app - 短信验证码的登录 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppAuthSmsLoginReqVO {

    @ApiModelProperty(value = "手机号", required = true, example = "agricultureyuanma")
    @NotEmpty(message = "手机号不能为空")
    @Mobile
    private String mobile;

    @ApiModelProperty(value = "短信验证码", required = true, example = "1024")
    @NotEmpty(message = "验证码不能为空")
    private String code;

    @ApiModelProperty(value = "社交平台的类型", required = true, example = "10", notes = "参见 SysUserSocialTypeEnum 枚举值")
    @InEnum(SocialTypeEnum.class)
    private Integer socialType;

    @ApiModelProperty(value = "授权码", required = true, example = "1024")
    private String socialCode;

    @ApiModelProperty(value = "state", required = true, example = "9b2ffbc1-7425-4155-9894-9d5c08541d62")
    private String socialState;


    @AssertTrue(message = "授权码不能为空")
    public boolean isSocialCodeValid() {
        return socialType == null || StrUtil.isNotEmpty(socialCode);
    }

    @AssertTrue(message = "授权 state 不能为空")
    public boolean isSocialState() {
        return socialType == null || StrUtil.isNotEmpty(socialState);
    }

}
