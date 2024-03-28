package com.airohit.agriculture.module.land.vo;

import com.airohit.agriculture.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 地块信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LandPageReqVO extends PageParam {

    @ApiModelProperty(value = "地块名称")
    private String landName;

}
