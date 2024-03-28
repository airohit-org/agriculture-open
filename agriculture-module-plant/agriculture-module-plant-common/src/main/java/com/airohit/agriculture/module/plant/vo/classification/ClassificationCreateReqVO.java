package com.airohit.agriculture.module.plant.vo.classification;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 病虫害识别创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ClassificationCreateReqVO extends ClassificationBaseVO {

}
