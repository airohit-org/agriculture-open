package com.airohit.agriculture.module.system.entity.admin.socail.vo;

import com.airohit.agriculture.framework.common.validation.InEnum;
import com.airohit.agriculture.module.system.enums.social.SocialTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel("管理后台 - 社交绑定 Request VO，使用 code 授权码")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialUserBindReqVO {

    @ApiModelProperty(value = "社交平台的类型", required = true, example = "10", notes = "参见 UserSocialTypeEnum 枚举值")
    @InEnum(SocialTypeEnum.class)
    @NotNull(message = "社交平台的类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "授权码", required = true, example = "1024")
    @NotEmpty(message = "授权码不能为空")
    private String code;

    @ApiModelProperty(value = "state", required = true, example = "9b2ffbc1-7425-4155-9894-9d5c08541d62")
    @NotEmpty(message = "state 不能为空")
    private String state;

}
