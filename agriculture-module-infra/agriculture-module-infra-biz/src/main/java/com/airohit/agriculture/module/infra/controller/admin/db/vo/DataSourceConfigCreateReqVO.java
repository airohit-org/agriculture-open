package com.airohit.agriculture.module.infra.controller.admin.db.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("管理后台 - 数据源配置创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DataSourceConfigCreateReqVO extends DataSourceConfigBaseVO {

    @ApiModelProperty(value = "密码", required = true, example = "123456")
    @NotNull(message = "密码不能为空")
    private String password;

}
