package com.airohit.agriculture.module.system.entity.admin.dept.vo.dept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("管理后台 - 部门列表 Request VO")
@Data
public class DeptListReqVO {

    @ApiModelProperty(value = "部门名称", example = "农业", notes = "模糊匹配")
    private String name;

    @ApiModelProperty(value = "展示状态", example = "1", notes = "参见 CommonStatusEnum 枚举类")
    private Integer status;

}
