package com.airohit.agriculture.module.plant.vo.plantypedata;

import com.airohit.agriculture.module.plant.dal.dataobject.taskInfo.TaskInfoDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@ApiModel("管理后台 - 种植计划类型 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PlanTypeDataRespVO extends PlanTypeDataBaseVO {

    List<TaskInfoDO> taskInfoList;
    @ApiModelProperty(value = "子任务阶段集合", required = true)
    List<PlanTypeDataRespVO> planTypeDataChildList;
    @ApiModelProperty(value = "编号", required = true)
    private Integer id;
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

}
