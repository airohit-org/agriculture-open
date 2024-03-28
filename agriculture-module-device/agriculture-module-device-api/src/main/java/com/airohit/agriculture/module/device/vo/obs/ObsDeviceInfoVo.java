package com.airohit.agriculture.module.device.vo.obs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: hanliyao
 * @Date: 2023/7/13 14:58
 */
@Data
@ApiModel("设备详细信息")
public class ObsDeviceInfoVo {
    @ApiModelProperty("设备ID")
    private Integer deviceId;
    @ApiModelProperty("节点信息")
    private List<ObsDeviceNode> data;
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("当时设备状态")
    private String status;
}
