package com.airohit.agriculture.module.plant.vo.records;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.airohit.agriculture.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 农事记录 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class RecordsBaseVO {

    @ApiModelProperty(value = "视频链接")
    private String videoUrl;

    @ApiModelProperty(value = "图片链接")
    private String imageUrls;

    @ApiModelProperty(value = "农事类型,类型同农事任务一样")
    private Integer type;

    @ApiModelProperty(value = "任务对应的表名", hidden = true)
    private String typeTableName;

    @ApiModelProperty(value = "任务对应的模型名称", hidden = true)
    private String typeModelName;

    @ApiModelProperty(value = "操作时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date operateTime;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "地块ID")
    private Integer landId;

    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "记录")
    private String records;
}
