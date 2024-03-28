package com.airohit.agriculture.module.device.vo.device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/23 09:36
 */
@ApiModel("绿农设备数据")
public class DeviceDataVo {
    @ApiModelProperty("地表温度")
    private String w0;
    @ApiModelProperty("地表湿度")
    private String s0;
    @ApiModelProperty("风速 m/s")
    private String fs0;
    @ApiModelProperty("风向")
    private String fx0;
    @ApiModelProperty("雨量")
    private String yl0;
    @ApiModelProperty("空气温度")
    private String aw0;
    @ApiModelProperty("空气湿度")
    private String as0;
    @ApiModelProperty("日照lux")
    private String rz0;
    @ApiModelProperty("二氧化碳")
    private String co2;
}
