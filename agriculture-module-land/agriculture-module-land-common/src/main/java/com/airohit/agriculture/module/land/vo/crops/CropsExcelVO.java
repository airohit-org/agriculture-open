package com.airohit.agriculture.module.land.vo.crops;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 地块作物 Excel VO
 *
 * @author 管理员
 */
@Data
public class CropsExcelVO {

    @ExcelProperty("编号")
    private Integer id;

    @ExcelProperty("地块编号")
    private Integer landId;

    @ExcelProperty("种植作物")
    private String crops;

    @ExcelProperty("作物品种")
    private String cropsType;

    @ExcelProperty("状态（0正常 1关闭）")
    private Integer status;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("种植作物是否其他 0 否 1 是")
    private Integer cropsIsOther;

    @ExcelProperty("种植作物其他内容")
    private String cropsOtherContent;

    @ExcelProperty("种植品种是否其他 0 否 1 是")
    private Integer cropsTypeIsOther;

    @ExcelProperty("种植品种其他内容")
    private String cropsTypeOtherContent;

}
