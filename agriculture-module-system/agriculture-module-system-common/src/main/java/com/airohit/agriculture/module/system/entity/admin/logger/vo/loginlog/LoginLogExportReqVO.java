package com.airohit.agriculture.module.system.entity.admin.logger.vo.loginlog;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.airohit.agriculture.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 登录日志分页列表 Request VO")
@Data
public class LoginLogExportReqVO {

    @ApiModelProperty(value = "用户 IP", example = "127.0.0.1", notes = "模拟匹配")
    private String userIp;

    @ApiModelProperty(value = "用户账号", example = "农业", notes = "模拟匹配")
    private String username;

    @ApiModelProperty(value = "操作状态", example = "true")
    private Boolean status;

    @ApiModelProperty(value = "登录时间", example = "[2022-07-01 00:00:00,2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
