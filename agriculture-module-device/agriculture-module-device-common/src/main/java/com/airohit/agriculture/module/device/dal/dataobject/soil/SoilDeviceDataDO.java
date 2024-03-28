package com.airohit.agriculture.module.device.dal.dataobject.soil;

import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/24 14:18
 */
@TableName("soil_device_data")
@KeySequence("soil_device_data_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("土壤设备数据")
public class SoilDeviceDataDO extends BaseDO {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("土壤表层温度,单位:℃")
    private Double w0;
    @ApiModelProperty("土壤表层湿度,单位:%")
    private Double s0;
    @ApiModelProperty("土壤温度第一层,单位:℃")
    private Double w1;
    @ApiModelProperty("土壤湿度第一层,单位:%")
    private Double s1;
    @ApiModelProperty("土壤温度第二层,单位:℃")
    private Double w2;
    @ApiModelProperty("土壤湿度第二层,单位:%")
    private Double s2;
    @ApiModelProperty("土壤温度第三层,单位:℃")
    private Double w3;
    @ApiModelProperty("土壤湿度第三层,单位:%")
    private Double s3;
    @ApiModelProperty("土壤PH值,单位:pH")
    private Double ph;
    @ApiModelProperty("土壤EC值,单位:us/cm")
    private Double ec;
    @ApiModelProperty("土壤氮含量,单位:mg/kg")
    private Double dl;
    @ApiModelProperty("土壤磷含量,单位:mg/kg")
    private Double ll;
    @ApiModelProperty("土壤钾含量,单位:mg/kg")
    private Double jl;
    @ApiModelProperty("倾角,单位:°")
    private Double angleRepose;
}
