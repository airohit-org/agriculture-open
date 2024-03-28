package com.airohit.agriculture.module.device.vo.obs;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 山东仁科设备节点
 * @Author: hanliyao
 * @Date: 2023/7/11 17:08
 */

@Data
public class ObsDeviceNode {
    @ApiModelProperty("节点名称")
    private String name;
    @ApiModelProperty("节点单位")
    private String unit;
    @ApiModelProperty("节点数值")
    private String value;
    @ApiModelProperty("节点状态")
    private String nodeStatus;
    @ApiModelProperty("节点从属")
    private String nodeSub;
}
