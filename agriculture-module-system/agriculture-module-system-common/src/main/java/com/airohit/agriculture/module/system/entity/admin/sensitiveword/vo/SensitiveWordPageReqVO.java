package com.airohit.agriculture.module.system.entity.admin.sensitiveword.vo;

import com.airohit.agriculture.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.airohit.agriculture.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 敏感词分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SensitiveWordPageReqVO extends PageParam {

    @ApiModelProperty(value = "敏感词", example = "敏感词")
    private String name;

    @ApiModelProperty(value = "标签", example = "短信,评论")
    private String tag;

    @ApiModelProperty(value = "状态", example = "1", notes = "参见 CommonStatusEnum 枚举类")
    private Integer status;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime[] createTime;

}
