package com.airohit.agriculture.module.statistics.vo.message;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 预警消息 Excel VO
 *
 * @author 史铭浩
 */
@Data
public class WarningMessageExcelVO {

    @ExcelProperty("Id")
    private Integer id;

    @ExcelProperty("预警类型")
    private Integer warningType;

    @ExcelProperty("预警消息")
    private String warningMessage;

    @ExcelProperty("状态")
    private Integer status;

    @ExcelProperty("创建时间")
    private Date createTime;

}
