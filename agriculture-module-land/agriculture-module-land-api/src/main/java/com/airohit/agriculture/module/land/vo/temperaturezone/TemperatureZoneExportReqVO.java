package com.airohit.agriculture.module.land.vo.temperaturezone;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "管理后台 - 积温带管理 Excel 导出 Request VO", description = "参数和 TemperatureZonePageReqVO 是一致的")
@Data
public class TemperatureZoneExportReqVO {

    @ApiModelProperty(value = "积温带名称")
    private String name;

}
