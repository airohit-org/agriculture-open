package com.airohit.agriculture.module.land.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "管理后台 - 地块信息 Excel 导出 Request VO", description = "参数和 LandPageReqVO 是一致的")
@Data
public class LandExportReqVO {

    @ApiModelProperty(value = "地块名称")
    private String landName;

}
