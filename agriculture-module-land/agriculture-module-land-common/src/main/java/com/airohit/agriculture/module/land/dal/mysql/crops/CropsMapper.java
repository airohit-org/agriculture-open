package com.airohit.agriculture.module.land.dal.mysql.crops;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.land.dal.dataobject.crops.CropsDO;
import com.airohit.agriculture.module.land.vo.crops.CropsExportReqVO;
import com.airohit.agriculture.module.land.vo.crops.CropsPageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 地块作物 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface CropsMapper extends BaseMapperX<CropsDO> {

    default PageResult<CropsDO> selectPage(CropsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CropsDO>()
                .eqIfPresent(CropsDO::getLandId, reqVO.getLandId())
                .eqIfPresent(CropsDO::getCrops, reqVO.getCrops())
                .eqIfPresent(CropsDO::getCropsType, reqVO.getCropsType())
                .eqIfPresent(CropsDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(CropsDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(CropsDO::getRemark, reqVO.getRemark())
                .eqIfPresent(CropsDO::getCropsIsOther, reqVO.getCropsIsOther())
                .eqIfPresent(CropsDO::getCropsOtherContent, reqVO.getCropsOtherContent())
                .eqIfPresent(CropsDO::getCropsTypeIsOther, reqVO.getCropsTypeIsOther())
                .eqIfPresent(CropsDO::getCropsTypeOtherContent, reqVO.getCropsTypeOtherContent())
                .orderByDesc(CropsDO::getId));
    }

    default List<CropsDO> selectList(CropsExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<CropsDO>()
                .eqIfPresent(CropsDO::getLandId, reqVO.getLandId())
                .eqIfPresent(CropsDO::getCrops, reqVO.getCrops())
                .eqIfPresent(CropsDO::getCropsType, reqVO.getCropsType())
                .eqIfPresent(CropsDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(CropsDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(CropsDO::getRemark, reqVO.getRemark())
                .eqIfPresent(CropsDO::getCropsIsOther, reqVO.getCropsIsOther())
                .eqIfPresent(CropsDO::getCropsOtherContent, reqVO.getCropsOtherContent())
                .eqIfPresent(CropsDO::getCropsTypeIsOther, reqVO.getCropsTypeIsOther())
                .eqIfPresent(CropsDO::getCropsTypeOtherContent, reqVO.getCropsTypeOtherContent())
                .orderByAsc(CropsDO::getId));
    }

}
