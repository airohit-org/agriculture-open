package com.airohit.agriculture.module.plant.vo.plan;

import lombok.Data;

@Data
public class PlanBindLandVO {
    private Integer plantingPlanId;
    private Integer landId;
    private String planName;
    private String landName;
}
