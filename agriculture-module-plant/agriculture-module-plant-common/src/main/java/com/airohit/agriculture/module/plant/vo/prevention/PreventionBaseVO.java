package com.airohit.agriculture.module.plant.vo.prevention;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 防治方案 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class PreventionBaseVO {

    @ApiModelProperty(value = "病虫害名称")
    private String diseasesName;

    @ApiModelProperty(value = "状态", required = true)
    @NotNull(message = "状态不能为空")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "农户ID")
    private Integer peasantId;

    @ApiModelProperty(value = "农场编号", required = true)
    @NotNull(message = "农场编号不能为空")
    private Long farmTenantId;

    @ApiModelProperty(value = "防治措施")
    private String measure;

    @ApiModelProperty(value = "防治方案")
    private String preventionPlan;

}
