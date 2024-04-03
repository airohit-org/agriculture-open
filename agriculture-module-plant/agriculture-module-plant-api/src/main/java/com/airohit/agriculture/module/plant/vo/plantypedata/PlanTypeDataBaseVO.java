package com.airohit.agriculture.module.plant.vo.plantypedata;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 种植计划类型 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class PlanTypeDataBaseVO {

    @ApiModelProperty(value = "排序", required = true)
    private Integer sort;

    @ApiModelProperty(value = "父编号 0 上级", required = true)
    private Integer parentId;

    @ApiModelProperty(value = "阶段名称", required = true)
    @NotNull(message = "阶段名称不能为空")
    private String stageName;

    @ApiModelProperty(value = "阶段编码", required = true)
    private String stageCode;

    @ApiModelProperty(value = "计划编号", required = true)
    @NotNull(message = "计划编号不能为空")
    private Integer plantingPlanId;

    @ApiModelProperty(value = "周期（天）", required = true)
    private Integer period;

    @ApiModelProperty(value = "周期名称 （3月低-6月初）", required = false)
    private String periodName;

    @ApiModelProperty(value = "计划日期", required = true)
    private Date plantingPlanDate;


    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "租户ID", hidden = true)
    private Long tenantId;

    @ApiModelProperty(value = "农场ID", hidden = true)
    private Long farmTenantId;

    /**
     * 数据code
     */
    @ApiModelProperty(value = "数据code", hidden = true)
    private String dataCode;

}
