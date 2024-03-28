package com.airohit.agriculture.module.peasant.vo;

import com.airohit.agriculture.framework.common.pojo.PageParam;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.airohit.agriculture.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 农户分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PeasantPageReqVO extends PageParam {

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "登录密码")
    private String passWord;

    @ApiModelProperty(value = "用户角色")
    private String userType;

    @ApiModelProperty(value = "真实姓名")
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

    @ApiModelProperty(value = "状态（0正常 1关闭）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

    @ApiModelProperty(value = "备注")
    private String remark;

}
