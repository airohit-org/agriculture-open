package com.airohit.agriculture.module.land.dal.mysql.varieties;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.land.dal.dataobject.varieties.CropsVarietiesDO;
import com.airohit.agriculture.module.land.vo.varieties.CropsVarietiesExportReqVO;
import com.airohit.agriculture.module.land.vo.varieties.CropsVarietiesPageReqVO;
import com.airohit.agriculture.module.land.vo.varieties.CropsVarietiesRespVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 品种管理 Mapper
 *
 * @author shiminghao
 */
@Mapper
public interface CropsVarietiesMapper extends BaseMapperX<CropsVarietiesDO> {

    default PageResult<CropsVarietiesDO> selectPage(CropsVarietiesPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CropsVarietiesDO>()
                .likeIfPresent(CropsVarietiesDO::getCropsVarietiesName, reqVO.getCropsVarietiesName())
                .orderByDesc(CropsVarietiesDO::getId));
    }

    default List<CropsVarietiesDO> selectList(CropsVarietiesExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<CropsVarietiesDO>()
                .likeIfPresent(CropsVarietiesDO::getCropsVarietiesName, reqVO.getCropsVarietiesName())
                .orderByDesc(CropsVarietiesDO::getId));
    }

    Page<CropsVarietiesRespVO> getCropsVarietiesRespVOPage(IPage<CropsVarietiesPageReqVO> page, @Param("cropsVarietiesPageReqVO") CropsVarietiesPageReqVO cropsVarietiesPageReqVO);
}
