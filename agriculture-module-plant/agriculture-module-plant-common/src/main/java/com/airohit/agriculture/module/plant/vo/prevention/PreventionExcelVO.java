package com.airohit.agriculture.module.plant.vo.prevention;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 防治方案 Excel VO
 *
 * @author 管理员
 */
@Data
public class PreventionExcelVO {

    @ExcelProperty("id")
    private Integer id;

    @ExcelProperty("病虫害名称")
    private String diseasesName;

    @ExcelProperty("状态")
    private Integer status;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("农户ID")
    private Integer peasantId;

    @ExcelProperty("农场编号")
    private Long farmTenantId;

    @ExcelProperty("防治措施")
    private String measure;

    @ExcelProperty("防治方案")
    private String preventionPlan;

}
