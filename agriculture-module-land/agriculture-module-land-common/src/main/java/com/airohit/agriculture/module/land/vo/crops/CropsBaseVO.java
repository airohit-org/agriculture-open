package com.airohit.agriculture.module.land.vo.crops;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 地块作物 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CropsBaseVO {

    @ApiModelProperty(value = "地块编号", required = true)
    @NotNull(message = "地块编号不能为空")
    private Integer landId;

    @ApiModelProperty(value = "种植作物")
    private String crops;

    @ApiModelProperty(value = "作物品种")
    private String cropsType;

    @ApiModelProperty(value = "状态（0正常 1关闭）", required = true)
    @NotNull(message = "状态（0正常 1关闭）不能为空")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "种植作物是否其他 0 否 1 是")
    private Integer cropsIsOther;

    @ApiModelProperty(value = "种植作物其他内容")
    private String cropsOtherContent;

    @ApiModelProperty(value = "种植品种是否其他 0 否 1 是")
    private Integer cropsTypeIsOther;

    @ApiModelProperty(value = "种植品种其他内容")
    private String cropsTypeOtherContent;

}
