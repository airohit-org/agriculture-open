package com.airohit.agriculture.module.plant.vo.plant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/7/7 09:28
 */
@Data
@ApiModel("种植工序")
public class PlanProcedureVo {
    /**
     * 编号
     */
    @ApiModelProperty("编号")
    private Integer id;
    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;
    /**
     * 种植环节ID
     */
    @ApiModelProperty("种植环节ID")
    private Integer plantProcessId;
    /**
     * 是否完成
     */
    @ApiModelProperty("是否完成,1是0否")
    private Integer status;
}
