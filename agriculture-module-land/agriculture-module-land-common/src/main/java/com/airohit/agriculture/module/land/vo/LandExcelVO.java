package com.airohit.agriculture.module.land.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 地块信息 Excel VO
 *
 * @author 管理员
 */
@Data
public class LandExcelVO {

    @ExcelProperty("编号")
    private Integer id;

    @ExcelProperty("地块名称")
    private String landName;

    @ExcelProperty("种植作物")
    private String crops;

    @ExcelProperty("作物品种")
    private String cropsType;

    @ExcelProperty("种植面积")
    private Double area;

    @ExcelProperty("联系人")
    private String contacts;

    @ExcelProperty("联系电话")
    private String tel;

    @ExcelProperty("地块坐标")
    private String landCoordinate;

    @ExcelProperty("坐标中心点")
    private String land_coordinate_center;

    @ExcelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
