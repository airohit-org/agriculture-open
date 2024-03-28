package com.airohit.agriculture.module.land.vo.crops;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@ApiModel("管理后台 - 地块作物 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CropsRespVO extends CropsBaseVO {

    @ApiModelProperty(value = "编号", required = true)
    private Integer id;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

    @ApiModelProperty(value = "种植作物名称")
    private String cropsName;

    @ApiModelProperty(value = "作物品种名称")
    private String cropsTypeName;

}
