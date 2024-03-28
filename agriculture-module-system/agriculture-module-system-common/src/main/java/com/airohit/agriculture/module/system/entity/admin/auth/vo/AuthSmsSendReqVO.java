package com.airohit.agriculture.module.system.entity.admin.auth.vo;

import com.airohit.agriculture.framework.common.validation.InEnum;
import com.airohit.agriculture.framework.common.validation.Mobile;
import com.airohit.agriculture.module.system.enums.sms.SmsSceneEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel("管理后台 - 发送手机验证码 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthSmsSendReqVO {

    @ApiModelProperty(value = "手机号", required = true, example = "agricultureyuanma")
    @NotEmpty(message = "手机号不能为空")
    @Mobile
    private String mobile;

    @ApiModelProperty(value = "短信场景,1手机号登录,3忘记密码", required = true, example = "1")
    @NotNull(message = "发送场景不能为空")
    @InEnum(SmsSceneEnum.class)
    private Integer scene;

}
