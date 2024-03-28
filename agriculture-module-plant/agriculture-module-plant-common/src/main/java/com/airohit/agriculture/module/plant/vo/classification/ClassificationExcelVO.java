package com.airohit.agriculture.module.plant.vo.classification;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 病虫害识别 Excel VO
 *
 * @author 管理员
 */
@Data
public class ClassificationExcelVO {

    @ExcelProperty("id")
    private Integer id;

    @ExcelProperty("病虫害名称")
    private String diseasesName;

    @ExcelProperty("图片链接")
    private String imageUrl;

    @ExcelProperty("作物类型,0水稻")
    private String cropType;

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

    @ExcelProperty("文件名")
    private String fileName;

    @ExcelProperty("地块ID")
    private Integer landId;

    @ExcelProperty("经纬度")
    private String latitudeLongitude;

    @ExcelProperty("措施ID")
    private Integer measureId;

    @ExcelProperty("几率")
    private String odds;

}
