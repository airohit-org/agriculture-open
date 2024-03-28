package com.airohit.agriculture.module.system.entity.app.auth;

import cn.hutool.core.util.StrUtil;
import com.airohit.agriculture.framework.common.validation.InEnum;
import com.airohit.agriculture.module.system.enums.social.SocialTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;

@ApiModel(value = "APP - 账号密码登录 Request VO", description = "如果登录并绑定社交用户，需要传递 social 开头的参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppAuthLoginReqVO {

    @ApiModelProperty(value = "账号", required = true, example = "agricultureyuanma")
    @NotEmpty(message = "登录账号不能为空")
    private String username;

    @ApiModelProperty(value = "密码", required = true, example = "buzhidao")
    @NotEmpty(message = "密码不能为空")
    private String password;

    // ========== 图片验证码相关 ==========

    @ApiModelProperty(value = "验证码", required = false,
            example = "PfcH6mgr8tpXuMWFjvW6YVaqrswIuwmWI5dsVZSg7sGpWtDCUbHuDEXl3cFB1+VvCC/rAkSwK8Fad52FSuncVg==",
            notes = "验证码开启时，需要传递")
    private String captchaVerification;

    // ========== 绑定社交登录时，需要传递如下参数 ==========

    @ApiModelProperty(value = "社交平台的类型,微信公众平台传31", required = true, example = "10", notes = "参见 SysUserSocialTypeEnum 枚举值")
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

    /**
     * 开启验证码的 Group
     */
    public interface CodeEnableGroup {
    }

}
