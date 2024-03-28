package com.airohit.agriculture.module.system.entity.admin.dept.vo.dept;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 部门创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeptCreateReqVO extends DeptBaseVO {
}
