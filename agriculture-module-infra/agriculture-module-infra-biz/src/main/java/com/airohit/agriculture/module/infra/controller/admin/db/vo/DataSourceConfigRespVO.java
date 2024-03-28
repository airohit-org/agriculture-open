package com.airohit.agriculture.module.infra.controller.admin.db.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@ApiModel("管理后台 - 数据源配置 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DataSourceConfigRespVO extends DataSourceConfigBaseVO {

    @ApiModelProperty(value = "主键编号", required = true, example = "1024")
    private Integer id;

    @ApiModelProperty(value = "创建时间", required = true)
    private LocalDateTime createTime;

}
