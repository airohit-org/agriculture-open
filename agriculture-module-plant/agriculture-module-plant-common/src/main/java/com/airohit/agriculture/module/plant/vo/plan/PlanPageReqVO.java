package com.airohit.agriculture.module.plant.vo.plan;

import com.airohit.agriculture.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.airohit.agriculture.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 种植计划分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PlanPageReqVO extends PageParam {

    @ApiModelProperty(value = "计划名称")
    private String planName;

    @ApiModelProperty(value = "种植作物")
    private String crops;

    @ApiModelProperty(value = "作物品种")
    private String cropsType;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

    @ApiModelProperty(value = "是否是模版 1 是 0 否")
    private Integer isTemplate;

    @ApiModelProperty(value = "用户类型 1 管理员 2 用户 ")
    private Integer type;

}
