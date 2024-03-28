package com.airohit.agriculture.module.statistics.vo.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.airohit.agriculture.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/19 09:15
 */
@ApiModel("预警信息统计")
@Data
public class MessageStatisticVo {
    @ApiModelProperty("预警类型")
    private String type;
    @ApiModelProperty("预警消息")
    private String area;
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty("时间")
    private LocalDateTime time;
}
