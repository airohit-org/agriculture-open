package com.airohit.agriculture.module.plant.vo.planTemplate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.airohit.agriculture.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "管理后台 - 计划模版 Excel 导出 Request VO", description = "参数和 PlanTemplatePageReqVO 是一致的")
@Data
public class PlanTemplateExportReqVO {

    @ApiModelProperty(value = "计划名称")
    private String planName;

    @ApiModelProperty(value = "种植作物")
    private String crops;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

}
