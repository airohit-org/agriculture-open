package com.airohit.agriculture.module.system.dal.mysql.farm;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.system.api.slave.vo.farm.FarmExportReqVO;
import com.airohit.agriculture.module.system.api.slave.vo.farm.FarmPageReqVO;
import com.airohit.agriculture.module.system.dal.dataobject.farm.FarmDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 农场 Mapper
 *
 * @author shiminghao
 */
@Mapper
public interface SystemFarmMapper extends BaseMapperX<FarmDO> {

    default PageResult<FarmDO> selectPage(FarmPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FarmDO>()
                .likeIfPresent(FarmDO::getFarmName, reqVO.getFarmName())
                .eqIfPresent(FarmDO::getProvince, reqVO.getProvince())
                .eqIfPresent(FarmDO::getCity, reqVO.getCity())
                .eqIfPresent(FarmDO::getArea, reqVO.getArea())
                .betweenIfPresent(FarmDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FarmDO::getId));
    }

    default List<FarmDO> selectList(FarmExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FarmDO>()
                .likeIfPresent(FarmDO::getFarmName, reqVO.getFarmName())
                .eqIfPresent(FarmDO::getProvince, reqVO.getProvince())
                .eqIfPresent(FarmDO::getCity, reqVO.getCity())
                .eqIfPresent(FarmDO::getArea, reqVO.getArea())
                .betweenIfPresent(FarmDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FarmDO::getId));
    }


    Long getTenantByFarm(@Param("farmId") int farmId);

    default List<FarmDO> likeName(String farmName) {
        return selectList(new LambdaQueryWrapperX<FarmDO>().likeIfPresent(FarmDO::getFarmName, farmName));
    }
}
