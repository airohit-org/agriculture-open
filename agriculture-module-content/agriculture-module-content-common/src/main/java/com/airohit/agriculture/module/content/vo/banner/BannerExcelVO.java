package com.airohit.agriculture.module.content.vo.banner;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 广告信息 Excel VO
 *
 * @author 管理员
 */
@Data
public class BannerExcelVO {

    @ExcelProperty("编码")
    private Integer id;

    @ExcelProperty("连接")
    private String link;

    @ExcelProperty("排序")
    private Integer sort;

    @ExcelProperty("地址")
    private String url;

    @ExcelProperty("截至日期")
    private Date endTime;

    @ExcelProperty("app地址")
    private String appUrl;

    @ExcelProperty("状态（0正常 1关闭）")
    private Integer status;

    @ExcelProperty("创建时间")
    private Date createTime;

}
