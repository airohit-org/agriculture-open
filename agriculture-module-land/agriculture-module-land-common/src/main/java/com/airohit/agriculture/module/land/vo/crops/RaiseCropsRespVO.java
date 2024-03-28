package com.airohit.agriculture.module.land.vo.crops;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@ApiModel("管理后台 - 地块种植作物信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RaiseCropsRespVO extends RaiseCropsBaseVO {
    List<CropsVarietiesBaseVO> cropsVarietiesList;
}
