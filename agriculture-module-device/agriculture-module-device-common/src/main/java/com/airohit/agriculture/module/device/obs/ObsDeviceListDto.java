package com.airohit.agriculture.module.device.obs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.airohit.agriculture.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("观测站 - 设备基本信息分页 Request VO")
@Data
@ToString(callSuper = true)
public class ObsDeviceListDto {

    /**
     * 关键词
     */
    @ApiModelProperty("关键词")
    private String keyWord;
    /**
     * 设备名称
     */
    @ApiModelProperty("设备分类(worm-虫情设备普通版,wormFlagship-虫情设备旗舰版,spore-孢子设备,met-气象设备,irrigation-智慧环控3.0设备,irrigation2-v智慧环控2.0设备)")
    private String deviceType;
    /**
     * 设备状态
     */
    @ApiModelProperty("设备状态(online,offline)")
    private String status;

    /**
     * 关键词
     */
    @ApiModelProperty("设备名称")
    private String deviceName;

    @ApiModelProperty("农场id")
    @NotNull(message = "农场编号不能为空")
    private Integer farmId;

    @ApiModelProperty("厂商名称")
    private String firmName;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;
}
