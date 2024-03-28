package com.airohit.agriculture.module.land.dal.mysql.land;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.land.dal.dataobject.land.LandDO;
import com.airohit.agriculture.module.land.vo.LandExportReqVO;
import com.airohit.agriculture.module.land.vo.LandPageReqVO;
import com.airohit.agriculture.module.land.vo.LandRespVO;
import com.airohit.agriculture.module.land.vo.LandStatisticsVO;
import com.airohit.agriculture.module.land.vo.crops.CropsCreateReqVO;
import com.airohit.agriculture.module.land.vo.crops.CropsVarietiesBaseVO;
import com.airohit.agriculture.module.land.vo.crops.RaiseCropsBaseVO;
import com.airohit.agriculture.module.land.vo.crops.RaiseCropsRespVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 地块信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface LandMapper extends BaseMapperX<LandDO> {

    default PageResult<LandDO> selectPage(LandPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<LandDO>()
                .likeIfPresent(LandDO::getLandName, reqVO.getLandName())
                .orderByDesc(LandDO::getId));
    }

    default List<LandDO> selectList(LandExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<LandDO>()
                .likeIfPresent(LandDO::getLandName, reqVO.getLandName())
                .orderByDesc(LandDO::getId));
    }

    List<RaiseCropsRespVO> queryRaiseCrops();

    List<CropsVarietiesBaseVO> queryCropsVarieties(@Param("raiseCropsRespId") Integer id);

    RaiseCropsBaseVO queryRaiseCropsByCode(@Param("code") String crops);

    CropsVarietiesBaseVO queryCropsVarietiesByCode(@Param("raiseCropsRespId") Integer raiseCropsRespId, @Param("code") String cropsType);

    @Update("update planting_plan set land_id = null WHERE land_id = #{id}")
    void updatePlanByLandId(@Param("id") Integer id);

    LandStatisticsVO queryLandStatistics();

    List<CropsCreateReqVO> queryLandCropsByLandId(Integer id);

    List<LandRespVO> getLandList(String landName);

    List<CropsCreateReqVO> queryLandCrops();
}
