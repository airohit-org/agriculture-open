package com.airohit.agriculture.module.plant.vo.prevention;

import com.airohit.agriculture.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.airohit.agriculture.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 防治方案分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PreventionPageReqVO extends PageParam {

    @ApiModelProperty(value = "病虫害名称")
    private String diseasesName;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "农户ID")
    private Integer peasantId;

    @ApiModelProperty(value = "农场编号")
    private Long farmTenantId;

    @ApiModelProperty(value = "防治措施")
    private String measure;

    @ApiModelProperty(value = "防治方案")
    private String preventionPlan;

}
