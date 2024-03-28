package com.airohit.agriculture.module.device.vo.land;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/26 15:03
 */
@Data
@ApiModel("地块关联设备信息")
public class DeviceLandListVo {
    /**
     * id
     */
    @ApiModelProperty("id")
    private Integer id;
    /**
     * 坐标
     */
    @ApiModelProperty("设备坐标,经纬度")
    private String coordinate;
    /**
     * 设备ID
     */
    @ApiModelProperty("设备ID")
    private Integer deviceId;
    /**
     * 地块ID
     */
    @ApiModelProperty("地块ID")
    private Integer landId;
    /**
     * 设备名称
     */
    @ApiModelProperty("设备名称")
    private String deviceName;
}
