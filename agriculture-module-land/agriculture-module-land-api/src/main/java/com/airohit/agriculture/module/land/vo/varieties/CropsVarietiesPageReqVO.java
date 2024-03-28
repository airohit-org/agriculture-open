package com.airohit.agriculture.module.land.vo.varieties;

import com.airohit.agriculture.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 品种管理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CropsVarietiesPageReqVO extends PageParam {

    @ApiModelProperty(value = "作物品种名称")
    private String cropsVarietiesName;

    @ApiModelProperty(value = "作物ID")
    private Integer raiseCropsId;

}
