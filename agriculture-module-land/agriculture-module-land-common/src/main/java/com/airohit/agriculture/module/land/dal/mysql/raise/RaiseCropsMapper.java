package com.airohit.agriculture.module.land.dal.mysql.raise;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.land.dal.dataobject.raise.RaiseCropsDO;
import com.airohit.agriculture.module.land.vo.raise.RaiseCropsExportReqVO;
import com.airohit.agriculture.module.land.vo.raise.RaiseCropsPageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 种植作物 Mapper
 *
 * @author shiminghao
 */
@Mapper
public interface RaiseCropsMapper extends BaseMapperX<RaiseCropsDO> {

    default PageResult<RaiseCropsDO> selectPage(RaiseCropsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RaiseCropsDO>()
                .likeIfPresent(RaiseCropsDO::getCropsName, reqVO.getCropsName())
                .orderByDesc(RaiseCropsDO::getId));
    }

    default List<RaiseCropsDO> selectList(RaiseCropsExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RaiseCropsDO>()
                .likeIfPresent(RaiseCropsDO::getCropsName, reqVO.getCropsName())
                .orderByDesc(RaiseCropsDO::getId));
    }

}
