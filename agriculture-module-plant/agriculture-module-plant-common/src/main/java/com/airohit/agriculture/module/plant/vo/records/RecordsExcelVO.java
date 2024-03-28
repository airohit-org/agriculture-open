package com.airohit.agriculture.module.plant.vo.records;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 农事记录 Excel VO
 *
 * @author 管理员
 */
@Data
public class RecordsExcelVO {

    @ExcelProperty("id")
    private Integer id;

    @ExcelProperty("视频链接")
    private String videoUrl;

    @ExcelProperty("图片链接")
    private String imageUrls;

    @ExcelProperty("农事类型")
    private Integer type;

    @ExcelProperty("任务对应的表名")
    private String typeTableName;

    @ExcelProperty("任务对应的模型名称")
    private String typeModelName;

    @ExcelProperty("操作时间")
    private Date operateTime;

    @ExcelProperty("状态")
    private Integer status;

    @ExcelProperty("创建时间")
    private Date createTime;

    @ExcelProperty("备注")
    private String remark;

}
