package com.airohit.agriculture.module.device.obs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@ApiModel("观测站 - 设备基本信息分页 Request VO")
@Data
@ToString(callSuper = true)
public class ObsDeviceDto {

    /**
     * 关键词
     */
    @ApiModelProperty("关键词")
    private String keyWord;
    /**
     * 设备名称
     */
    @ApiModelProperty("设备分类(worm-虫情设备普通版,wormFlagship-虫情设备旗舰版,spore-孢子设备,met-气象设备,irrigation-智慧环控3.0设备,irrigation2-v智慧环控2.0设备)")
    @NotEmpty(message = "设备分类不能为空")
    private String deviceType;
    /**
     * 设备状态
     */
    @ApiModelProperty("设备状态(online,offline)")
    private String status;
}
