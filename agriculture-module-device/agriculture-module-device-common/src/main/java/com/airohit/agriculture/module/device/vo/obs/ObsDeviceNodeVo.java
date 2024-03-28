package com.airohit.agriculture.module.device.vo.obs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @return:
 * @Author: hanliyao
 * @Date: 2023/7/13 14:58
 */
@Data
@ApiModel("山东仁科设备节点信息")
public class ObsDeviceNodeVo {
    @ApiModelProperty("节点名称")
    private String name;
    @ApiModelProperty("节点单位")
    private String unit;
    @ApiModelProperty("节点数值")
    private String value;
    @ApiModelProperty("节点状态")
    private String nodeStatus;
}
