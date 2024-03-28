package com.airohit.agriculture.module.land.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel("管理后台 - 更新地块轮廓 Request VO")
@Data
@ToString(callSuper = true)
public class LandUpdateOutlineReqVO {

    @ApiModelProperty(value = "编号", required = true)
    @NotNull(message = "编号不能为空")
    private Integer id;

    @ApiModelProperty(value = "坐标中心点", required = true)
    private String landCoordinateCenter;

    @ApiModelProperty(value = "种植面积", required = true)
    @NotNull(message = "种植面积不能为空")
    @Max(value = 999999999)
    private Double area;

    @ApiModelProperty(value = "经纬度列表", required = true)
    private List<PosVo> posVoList;

}
