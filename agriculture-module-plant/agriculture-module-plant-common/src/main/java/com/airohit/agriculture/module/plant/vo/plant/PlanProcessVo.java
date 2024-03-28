package com.airohit.agriculture.module.plant.vo.plant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/7/7 09:27
 */
@Data
@ApiModel("种植环节")
public class PlanProcessVo {
    /**
     * 二层
     */
    @ApiModelProperty("种植工序")
    List<PlanProcedureVo> planProcedureVoList;
    /**
     * 编号
     */
    @ApiModelProperty("id")
    private Integer id;
    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;
    /**
     * 种植工序数量
     */
    @ApiModelProperty("种植工序数量")
    private Integer count;
}
