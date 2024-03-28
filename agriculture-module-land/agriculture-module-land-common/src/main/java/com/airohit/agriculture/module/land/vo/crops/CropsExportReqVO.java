package com.airohit.agriculture.module.land.vo.crops;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.airohit.agriculture.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "管理后台 - 地块作物 Excel 导出 Request VO", description = "参数和 CropsPageReqVO 是一致的")
@Data
public class CropsExportReqVO {

    @ApiModelProperty(value = "地块编号")
    private Integer landId;

    @ApiModelProperty(value = "种植作物")
    private String crops;

    @ApiModelProperty(value = "作物品种")
    private String cropsType;

    @ApiModelProperty(value = "状态（0正常 1关闭）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

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
