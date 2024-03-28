package com.airohit.agriculture.module.system.entity.admin.farm.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 农场 Excel VO
 *
 * @author shiminghao
 */
@Data
public class FarmExcelVO {

    @ExcelProperty("Id")
    private Integer id;

    @ExcelProperty("农场名称")
    private String farmName;

    @ExcelProperty("种植面积")
    private String plantArea;

    @ExcelProperty("联系人")
    private String contacts;

    @ExcelProperty("详细地址")
    private String address;

    @ExcelProperty("联系电话")
    private String tel;

    @ExcelProperty("省")
    private String province;

    @ExcelProperty("市")
    private String city;

    @ExcelProperty("区")
    private String area;

    @ExcelProperty("创建时间")
    private Date createTime;

}
