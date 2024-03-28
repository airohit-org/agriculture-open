package com.airohit.agriculture.module.plant.vo.plantypedata;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 种植计划类型 Excel VO
 *
 * @author 管理员
 */
@Data
public class PlanTypeDataExcelVO {

    @ExcelProperty("编号")
    private Integer id;

    @ExcelProperty("排序")
    private Integer sort;

    @ExcelProperty("阶段名称")
    private String stageName;

    @ExcelProperty("阶段编码")
    private String stageCode;

    @ExcelProperty("计划编号")
    private Integer plantingPlanId;

    @ExcelProperty("周期（天）")
    private Integer period;

    @ExcelProperty("计划日期")
    private Date plantingPlanDate;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("创建时间")
    private Date createTime;

}
