package com.airohit.agriculture.module.plant.vo.plan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 种植计划 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class PlanBaseVO {

    @ApiModelProperty(value = "地块编号")
    private Integer landId;

    @ApiModelProperty(value = "计划名称")
    private String planName;

    @ApiModelProperty(value = "种植作物")
    private String crops;

    @ApiModelProperty(value = "作物品种")
    private String cropsType;

    @ApiModelProperty(value = "开始日期")
    private Date startTime;

    @ApiModelProperty(value = "结束日期")
    private Date endTime;

    @ApiModelProperty(value = "1 管理员 2 用户")
    private Integer type;

    @ApiModelProperty(value = "状态（0已发布 1未发布）")
    private Integer status;

    @ApiModelProperty(value = "是否已绑定地块 1 是 0 否")
    private Integer isBindLand;

    @ApiModelProperty(value = "计划周期")
    private Integer planningCycle;

    @ApiModelProperty(value = "是否是模版 1 是 0 否")
    private Integer isTemplate;

    @ApiModelProperty(value = "租户ID", hidden = true)
    private Long tenantId;

    @ApiModelProperty(value = "农场ID", hidden = true)
    private Long farmTenantId;

    /**
     * 数据code
     */
    @ApiModelProperty(value = "数据code", hidden = true)
    private String dataCode;

}
