package com.airohit.agriculture.module.plant.vo.plan;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 种植计划 Excel VO
 *
 * @author 管理员
 */
@Data
public class PlanExcelVO {

    @ExcelProperty("编号")
    private Integer id;

    @ExcelProperty("地块编号")
    private Integer landId;

    @ExcelProperty("计划名称")
    private String planName;

    @ExcelProperty("种植作物")
    private String crops;

    @ExcelProperty("作物品种")
    private String cropsType;

    @ExcelProperty("开始日期")
    private Date startTime;

    @ExcelProperty("结束日期")
    private Date endTime;

    @ExcelProperty("1 管理员 2 用户")
    private Integer type;

    @ExcelProperty("状态（0正常 1关闭）")
    private Integer status;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("是否已绑定地块 1 是 0 否")
    private Integer isBindLand;

    @ExcelProperty("计划周期")
    private Integer planningCycle;

}
