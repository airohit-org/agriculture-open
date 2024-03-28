package com.airohit.agriculture.module.statistics.vo.farm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/19 10:26
 */
@Data
@ApiModel("农场信息概览统计")
public class FarmStatisticVo {
    @ApiModelProperty("种植面积")
    private String plantArea;
    @ApiModelProperty("地块数量")
    private Integer landCount;
    @ApiModelProperty("设备数量")
    private Integer deviceCount = 1;
    @ApiModelProperty("员工数量")
    private Integer memberCount;
    @ApiModelProperty("作物数量")
    private Integer Crops;
}
