package com.airohit.agriculture.module.peasant.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 农户 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class PeasantBaseVO {

    @ApiModelProperty(value = "用户名 （登录账号）")
    private String userName;

    @ApiModelProperty(value = "登录密码")
    private String passWord;

    @ApiModelProperty(value = "用户角色")
    private String userType;

    @ApiModelProperty(value = "真实姓名", required = true)
    @NotNull(message = "真实姓名不能为空")
    private String name;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "区县")
    private String area;

    @ApiModelProperty(value = "乡镇")
    private String street;

    @ApiModelProperty(value = "村委会")
    private String village;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "状态（0正常 1关闭）", required = true)
    @NotNull(message = "状态（0正常 1关闭）不能为空")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

}
