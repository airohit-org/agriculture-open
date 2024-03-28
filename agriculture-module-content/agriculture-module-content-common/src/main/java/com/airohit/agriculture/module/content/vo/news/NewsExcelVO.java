package com.airohit.agriculture.module.content.vo.news;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 新闻信息 Excel VO
 *
 * @author 管理员
 */
@Data
public class NewsExcelVO {

    @ExcelProperty("编码")
    private Integer id;

    @ExcelProperty("新闻标题")
    private String title;

    @ExcelProperty("排序")
    private Integer sort;

    @ExcelProperty("是否置顶 0 否 1 是")
    private Integer isTop;

    @ExcelProperty("发布时间")
    private Date publishTime;

    @ExcelProperty("发布状态 1 未发布 2已发布 3已下架")
    private Integer status;

    @ExcelProperty("创建时间")
    private Date createTime;

}
