package com.airohit.agriculture.module.device.vo.obs.systemfirm;

import com.airohit.agriculture.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel("设备中心 - 厂商管理信息分页 Request VO")
@Data
@ToString(callSuper = true)
public class ObsSystemFirmPageVo extends PageParam {

    @ApiModelProperty("厂商名称")
    private String firmName;
}
