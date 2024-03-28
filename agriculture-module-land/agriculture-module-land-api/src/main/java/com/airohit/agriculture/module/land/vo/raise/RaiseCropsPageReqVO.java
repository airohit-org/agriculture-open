package com.airohit.agriculture.module.land.vo.raise;

import com.airohit.agriculture.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 种植作物分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RaiseCropsPageReqVO extends PageParam {

    @ApiModelProperty(value = "作物名称")
    private String cropsName;

}
