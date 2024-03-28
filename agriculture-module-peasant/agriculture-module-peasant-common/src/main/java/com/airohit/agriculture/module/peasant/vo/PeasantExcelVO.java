package com.airohit.agriculture.module.peasant.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 农户 Excel VO
 *
 * @author lrj
 */
@Data
public class PeasantExcelVO {

    @ExcelProperty("id")
    private Integer id;

    @ExcelProperty("用户名")
    private String userName;

    @ExcelProperty("登录密码")
    private String passWord;

    @ExcelProperty("用户角色")
    private String userType;

    @ExcelProperty("真实姓名")
    private String name;

    @ExcelProperty("省")
    private String province;

    @ExcelProperty("城市")
    private String city;

    @ExcelProperty("区县")
    private String area;

    @ExcelProperty("乡镇")
    private String street;

    @ExcelProperty("村委会")
    private String village;

    @ExcelProperty("手机号")
    private String phone;

    @ExcelProperty("状态（0正常 1关闭）")
    private Integer status;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("备注")
    private String remark;

}
