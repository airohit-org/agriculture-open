package com.airohit.agriculture.module.land.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/3/6 15:06
 */
@ApiModel("管理后台 - 地块信息经纬度 Request VO")
@Data
@ToString(callSuper = true)
public class PosVo {

    @ApiModelProperty("精度")
    private BigDecimal longitude;

    @ApiModelProperty("纬度")
    private BigDecimal latitude;
}
