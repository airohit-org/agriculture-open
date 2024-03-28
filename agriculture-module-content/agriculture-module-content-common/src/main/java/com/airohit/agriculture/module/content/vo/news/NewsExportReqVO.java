package com.airohit.agriculture.module.content.vo.news;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.airohit.agriculture.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "管理后台 - 新闻信息 Excel 导出 Request VO", description = "参数和 NewsPageReqVO 是一致的")
@Data
public class NewsExportReqVO {

    @ApiModelProperty(value = "新闻标题")
    private String title;

    @ApiModelProperty(value = "发布时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] publishTime;

}
