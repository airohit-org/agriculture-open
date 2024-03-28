package com.airohit.agriculture.module.land.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 地块信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class LandBaseVO {

    @ApiModelProperty(value = "地块名称", required = true)
    @NotNull(message = "地块名称不能为空")
    @Size(max = 20, message = "地块名称长度不能超过20个字符")
    private String landName;

    @ApiModelProperty(value = "种植作物")
    //@NotNull(message = "种植作物不能为空")
    private String crops;

    @ApiModelProperty(value = "作物品种")
    //@NotNull(message = "作物品种不能为空")
    private String cropsType;

    @ApiModelProperty(value = "种植面积", required = true)
    @NotNull(message = "种植面积不能为空")
    @Max(value = 999999999)
    private Double area;

    @ApiModelProperty(value = "联系人", required = true)
    //@NotNull(message = "联系人不能为空")
    //@Size(max = 10, message = "联系人长度不能超过10个字符")
    private String contacts;

    @ApiModelProperty(value = "联系电话", required = true)
    //@NotNull(message = "联系人不能为空")
    //@Size(max = 11,min = 11,message = "联系电话允许输入11个字符")
    private String tel;

    @ApiModelProperty(value = "地块坐标,前端可忽略此字段")
    private String landCoordinate;

    @ApiModelProperty(value = "坐标中心点", required = true)
    private String landCoordinateCenter;

    @ApiModelProperty(value = "颜色", required = true)
    private String color;

    @ApiModelProperty(value = "种植作物是否其它 0 否 1 是")
    private Integer cropsIsOther;

    @ApiModelProperty(value = "种植作物其它内容")
    private String cropsOtherContent;

    @ApiModelProperty(value = "种植品种是否其它 0 否 1 是")
    private Integer cropsTypeIsOther;

    @ApiModelProperty(value = "种植品种其它内容")
    private String cropsTypeOtherContent;

    @ApiModelProperty(value = "地块形状")
    private String landShapeImage;

    @ApiModelProperty(value = "遥感分割地块id")
    private Integer fid;

}
