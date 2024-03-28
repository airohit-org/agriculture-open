package com.airohit.agriculture.module.content.vo.banner;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("管理后台 - 广告信息更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BannerUpdateReqVO extends BannerBaseVO {

    @ApiModelProperty(value = "编码", required = true)
    @NotNull(message = "编码不能为空")
    private Integer id;

}
