package com.airohit.agriculture.module.plant.dal.mysql.records;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.plant.dal.dataobject.records.RecordsDO;
import com.airohit.agriculture.module.plant.vo.records.RecordsExportReqVO;
import com.airohit.agriculture.module.plant.vo.records.RecordsPageReqVO;
import com.airohit.agriculture.module.plant.vo.records.RecordsRespVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import static com.airohit.agriculture.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * 农事记录 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface RecordsMapper extends BaseMapperX<RecordsDO> {

    default PageResult<RecordsDO> selectPage(RecordsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RecordsDO>()
                .eqIfPresent(RecordsDO::getVideoUrl, reqVO.getVideoUrl())
                .eqIfPresent(RecordsDO::getImageUrls, reqVO.getImageUrls())
                .eqIfPresent(RecordsDO::getType, reqVO.getType())
                .likeIfPresent(RecordsDO::getTypeTableName, reqVO.getTypeTableName())
                .likeIfPresent(RecordsDO::getTypeModelName, reqVO.getTypeModelName())
                .betweenIfPresent(RecordsDO::getOperateTime, reqVO.getOperateTime())
                .eqIfPresent(RecordsDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RecordsDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(RecordsDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RecordsDO::getCreator, getLoginUserId())
                .orderByDesc(RecordsDO::getId));
    }

    RecordsRespVO selectAllById(@Param("id") Integer id);


    default List<RecordsDO> selectList(RecordsExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RecordsDO>()
                .eqIfPresent(RecordsDO::getVideoUrl, reqVO.getVideoUrl())
                .eqIfPresent(RecordsDO::getImageUrls, reqVO.getImageUrls())
                .eqIfPresent(RecordsDO::getType, reqVO.getType())
                .likeIfPresent(RecordsDO::getTypeTableName, reqVO.getTypeTableName())
                .likeIfPresent(RecordsDO::getTypeModelName, reqVO.getTypeModelName())
                .betweenIfPresent(RecordsDO::getOperateTime, reqVO.getOperateTime())
                .eqIfPresent(RecordsDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RecordsDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(RecordsDO::getRemark, reqVO.getRemark())
                .orderByDesc(RecordsDO::getId));
    }

}
