package com.airohit.agriculture.module.plant.vo.prevention;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 防治方案创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PreventionCreateReqVO extends PreventionBaseVO {

}
