package com.airohit.agriculture.module.land.dal.mysql.temperaturezone;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.land.dal.dataobject.temperaturezone.TemperatureZoneDO;
import com.airohit.agriculture.module.land.vo.temperaturezone.TemperatureZoneExportReqVO;
import com.airohit.agriculture.module.land.vo.temperaturezone.TemperatureZonePageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 积温带管理 Mapper
 *
 * @author shiminghao
 */
@Mapper
public interface TemperatureZoneMapper extends BaseMapperX<TemperatureZoneDO> {

    default PageResult<TemperatureZoneDO> selectPage(TemperatureZonePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TemperatureZoneDO>()
                .likeIfPresent(TemperatureZoneDO::getName, reqVO.getName())
                .orderByDesc(TemperatureZoneDO::getId));
    }

    default List<TemperatureZoneDO> selectList(TemperatureZoneExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TemperatureZoneDO>()
                .likeIfPresent(TemperatureZoneDO::getName, reqVO.getName())
                .orderByDesc(TemperatureZoneDO::getId));
    }

}
