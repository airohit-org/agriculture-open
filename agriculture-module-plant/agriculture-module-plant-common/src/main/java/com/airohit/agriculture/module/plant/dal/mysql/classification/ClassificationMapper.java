package com.airohit.agriculture.module.plant.dal.mysql.classification;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.plant.dal.dataobject.classification.ClassificationDO;
import com.airohit.agriculture.module.plant.vo.classification.ClassificationExportReqVO;
import com.airohit.agriculture.module.plant.vo.classification.ClassificationPageReqVO;
import com.airohit.agriculture.module.plant.vo.classification.ClassificationRespVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 病虫害识别 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface ClassificationMapper extends BaseMapperX<ClassificationDO> {

    default PageResult<ClassificationDO> selectPage(ClassificationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ClassificationDO>()
                .likeIfPresent(ClassificationDO::getDiseasesName, reqVO.getDiseasesName())
                .eqIfPresent(ClassificationDO::getImageUrl, reqVO.getImageUrl())
                .eqIfPresent(ClassificationDO::getCropType, reqVO.getCropType())
                .eqIfPresent(ClassificationDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ClassificationDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ClassificationDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ClassificationDO::getPeasantId, reqVO.getPeasantId())
                .eqIfPresent(ClassificationDO::getFarmTenantId, reqVO.getFarmTenantId())
                .likeIfPresent(ClassificationDO::getFileName, reqVO.getFileName())
                .eqIfPresent(ClassificationDO::getLandId, reqVO.getLandId())
                .eqIfPresent(ClassificationDO::getLatitudeLongitude, reqVO.getLatitudeLongitude())
                .eqIfPresent(ClassificationDO::getMeasureId, reqVO.getMeasureId())
                .eqIfPresent(ClassificationDO::getOdds, reqVO.getOdds())
                .eqIfPresent(ClassificationDO::getCreator, reqVO.getOdds())
                .orderByDesc(ClassificationDO::getId));
    }

    default List<ClassificationDO> selectList(ClassificationExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ClassificationDO>()
                .likeIfPresent(ClassificationDO::getDiseasesName, reqVO.getDiseasesName())
                .eqIfPresent(ClassificationDO::getImageUrl, reqVO.getImageUrl())
                .eqIfPresent(ClassificationDO::getCropType, reqVO.getCropType())
                .eqIfPresent(ClassificationDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(ClassificationDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ClassificationDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ClassificationDO::getPeasantId, reqVO.getPeasantId())
                .eqIfPresent(ClassificationDO::getFarmTenantId, reqVO.getFarmTenantId())
                .likeIfPresent(ClassificationDO::getFileName, reqVO.getFileName())
                .eqIfPresent(ClassificationDO::getLandId, reqVO.getLandId())
                .eqIfPresent(ClassificationDO::getLatitudeLongitude, reqVO.getLatitudeLongitude())
                .eqIfPresent(ClassificationDO::getMeasureId, reqVO.getMeasureId())
                .eqIfPresent(ClassificationDO::getOdds, reqVO.getOdds())
                .orderByDesc(ClassificationDO::getId));
    }

    IPage<ClassificationRespVO> getClassificationRespVOPage(Page<ClassificationPageReqVO> page, @Param("pageVO") ClassificationPageReqVO pageVO);

}
