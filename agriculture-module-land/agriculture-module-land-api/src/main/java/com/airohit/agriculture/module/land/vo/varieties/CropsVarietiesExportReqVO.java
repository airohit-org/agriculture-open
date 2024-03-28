package com.airohit.agriculture.module.land.vo.varieties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "管理后台 - 品种管理 Excel 导出 Request VO", description = "参数和 CropsVarietiesPageReqVO 是一致的")
@Data
public class CropsVarietiesExportReqVO {

    @ApiModelProperty(value = "作物品种名称")
    private String cropsVarietiesName;

}
