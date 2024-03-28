package com.airohit.agriculture.module.land.vo.varieties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 品种管理更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CropsVarietiesUpdateReqVO extends CropsVarietiesBaseVO {


    @ApiModelProperty(value = "种植作物编号")
    private Integer raiseCropsId;

    @ApiModelProperty(value = "选育机构")
    private String breedingAgency;

    @ApiModelProperty(value = "品种来源")
    private String varietiesSource;

    @ApiModelProperty(value = "特征特性")
    private String feature;

    @ApiModelProperty(value = "产量表现")
    private String production;

    @ApiModelProperty(value = "栽培技术")
    private String cultivationTechnique;

}
