package com.airohit.agriculture.module.device.obs;

import com.airohit.agriculture.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel("设备中心 - 厂商管理信息分页 Request VO")
@Data
@ToString(callSuper = true)
public class ObsDeviceFirmPageDto extends PageParam {

    @ApiModelProperty("厂商名称")
    private String firmName;

    @ApiModelProperty("农场id")
    //@NotNull(message = "农场id不能为空")
    private Integer farmId;
}
