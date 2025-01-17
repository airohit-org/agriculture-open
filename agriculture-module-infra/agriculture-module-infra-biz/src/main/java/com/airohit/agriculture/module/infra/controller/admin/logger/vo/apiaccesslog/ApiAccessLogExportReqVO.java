package com.airohit.agriculture.module.infra.controller.admin.logger.vo.apiaccesslog;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.airohit.agriculture.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "管理后台 - API 访问日志 Excel 导出 Request VO", description = "参数和 ApiAccessLogPageReqVO 是一致的")
@Data
public class ApiAccessLogExportReqVO {

    @ApiModelProperty(value = "用户编号", example = "666")
    private Long userId;

    @ApiModelProperty(value = "用户类型", example = "2")
    private Integer userType;

    @ApiModelProperty(value = "应用名", example = "dashboard")
    private String applicationName;

    @ApiModelProperty(value = "请求地址", example = "/xxx/yyy", notes = "模糊匹配")
    private String requestUrl;

    @ApiModelProperty(value = "开始时间", example = "[2022-07-01 00:00:00, 2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] beginTime;

    @ApiModelProperty(value = "执行时长", example = "100", notes = "大于等于，单位：毫秒")
    private Integer duration;

    @ApiModelProperty(value = "结果码", example = "0")
    private Integer resultCode;

}
