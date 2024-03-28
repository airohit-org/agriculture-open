package com.airohit.agriculture.module.land.vo.temperaturezone;

import com.airohit.agriculture.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 积温带管理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TemperatureZonePageReqVO extends PageParam {

    @ApiModelProperty(value = "积温带名称")
    private String name;

}
