package com.airohit.agriculture.module.land.vo;

import com.airohit.agriculture.module.land.vo.crops.CropsCreateReqVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel("管理后台 - 地块信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LandRespVO extends LandBaseVO {

    @ApiModelProperty(value = "编号", required = true)
    private Integer id;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("经纬度列表")
    private List<PosVo> posVoList;


    @ApiModelProperty(value = "种植作物名称", required = true)
    private String cropsName;

    @ApiModelProperty(value = "作物品种名称", required = true)
    private String cropsTypeName;

    @ApiModelProperty(value = "作物及品种", required = true)
    private List<CropsCreateReqVO> cropsCreateReqVOList;

    @ApiModelProperty(value = "种植计划编号")
    private Integer planId;

    @ApiModelProperty(value = "种植计划名称")
    private String planName;
}
