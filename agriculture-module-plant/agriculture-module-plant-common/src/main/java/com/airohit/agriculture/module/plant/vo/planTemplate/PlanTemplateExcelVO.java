package com.airohit.agriculture.module.plant.vo.planTemplate;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 计划模版 Excel VO
 *
 * @author 管理员
 */
@Data
public class PlanTemplateExcelVO {

    @ExcelProperty("编号")
    private Integer id;

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
    private Boolean type;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("计划周期")
    private Integer planningCycle;

}
