package com.airohit.agriculture.module.device.obs;

import com.airohit.agriculture.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.airohit.agriculture.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("观测站 - 设备基本信息分页 Request VO")
@Data
@ToString(callSuper = true)
public class ObsDevicePageDto extends PageParam {

    /**
     * 关键词
     */
    @ApiModelProperty("设备名称")
    private String deviceName;
    /**
     * 设备名称
     */
    @ApiModelProperty("设备类型")
    private String deviceType;
    /**
     * 设备状态
     */
    @ApiModelProperty("设备状态(online,offline)")
    private String status;

    @ApiModelProperty("农场id")
    private Integer farmId;

    @ApiModelProperty("厂商名称")
    private String firmName;

    @ApiModelProperty("农场名称")
    private String farmName;
    @ApiModelProperty(value = "创建时间", example = "[2022-07-01 00:00:00,2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;
}
