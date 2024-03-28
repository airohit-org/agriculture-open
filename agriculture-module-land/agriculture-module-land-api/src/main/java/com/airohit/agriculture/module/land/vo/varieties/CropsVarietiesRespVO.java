package com.airohit.agriculture.module.land.vo.varieties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 品种管理 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CropsVarietiesRespVO extends CropsVarietiesBaseVO {

    /**
     * 作物名称
     */
    @ApiModelProperty("作物名称")
    private String cropsName;
}
