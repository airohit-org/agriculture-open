package com.airohit.agriculture.module.device.vo.info;

import com.airohit.agriculture.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 设备基本信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeviceInfoPageReqVO extends PageParam {

    /**
     * 设备名称
     */
    @ApiModelProperty("设备名称")
    private String deviceName;
    /**
     * 设备厂商
     */
    @ApiModelProperty("设备厂商")
    private String deviceCompany;

}
