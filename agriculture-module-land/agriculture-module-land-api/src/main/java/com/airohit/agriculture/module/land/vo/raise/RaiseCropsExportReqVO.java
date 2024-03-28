package com.airohit.agriculture.module.land.vo.raise;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "管理后台 - 种植作物 Excel 导出 Request VO", description = "参数和 RaiseCropsPageReqVO 是一致的")
@Data
public class RaiseCropsExportReqVO {

    @ApiModelProperty(value = "作物名称")
    private String cropsName;

}
