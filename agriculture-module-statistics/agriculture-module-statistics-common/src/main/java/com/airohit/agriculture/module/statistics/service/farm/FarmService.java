package com.airohit.agriculture.module.statistics.service.farm;

import com.airohit.agriculture.module.statistics.vo.farm.FarmStatisticVo;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/19 10:38
 */
public interface FarmService {

    FarmStatisticVo getFarmStatisticVo();

    String getFarmPlantArea(Long tenantId);

    List<Long> getFarmInfos();
}
