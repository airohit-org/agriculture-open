package com.airohit.agriculture.module.statistics.dal.mysql.farm;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/19 10:27
 */
@Mapper
public interface FarmStatisticMapper {
    Integer getLandCount();

    Integer getMemberCount(@Param("tenantId") Long tenantId);

    Integer getCropsCount();

    String getFarmPlantArea(@Param("tenantId") Long tenantId, @Param("farmTenantId") Long farmTenantId);
}
