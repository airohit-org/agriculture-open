package com.airohit.agriculture.module.plant.vo.plantypedata;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 种植计划类型创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PlanTypeDataCreateReqVO extends PlanTypeDataBaseVO {

}
