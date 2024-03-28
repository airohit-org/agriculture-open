package com.airohit.agriculture.module.plant.vo.plantypedata;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.airohit.agriculture.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "管理后台 - 种植计划类型 Excel 导出 Request VO", description = "参数和 PlanTypeDataPageReqVO 是一致的")
@Data
public class PlanTypeDataExportReqVO {

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "阶段名称")
    private String stageName;

    @ApiModelProperty(value = "阶段编码")
    private String stageCode;

    @ApiModelProperty(value = "计划编号")
    private Integer plantingPlanId;

    @ApiModelProperty(value = "周期（天）")
    private Integer period;

    @ApiModelProperty(value = "计划日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] plantingPlanDate;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

    @ApiModelProperty(value = "父编码")
    private Integer parentId;

}
