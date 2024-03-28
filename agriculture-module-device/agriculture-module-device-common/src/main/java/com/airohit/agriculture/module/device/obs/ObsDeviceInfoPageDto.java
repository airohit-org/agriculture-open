package com.airohit.agriculture.module.device.obs;

import com.airohit.agriculture.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;


@ApiModel("观测站 - 设备数据信息分页 Request VO")
@Data
@ToString(callSuper = true)
public class ObsDeviceInfoPageDto extends PageParam {

    @ApiModelProperty("设备id")
    @NotNull(message = "设备id不能为空")
    private Integer deviceId;
}
