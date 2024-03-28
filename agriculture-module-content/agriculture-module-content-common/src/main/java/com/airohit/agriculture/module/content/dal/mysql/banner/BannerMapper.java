package com.airohit.agriculture.module.content.dal.mysql.banner;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.content.dal.dataobject.banner.BannerDO;
import com.airohit.agriculture.module.content.vo.banner.BannerExportReqVO;
import com.airohit.agriculture.module.content.vo.banner.BannerPageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * 广告信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface BannerMapper extends BaseMapperX<BannerDO> {

    default PageResult<BannerDO> selectPage(BannerPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BannerDO>()
                .betweenIfPresent(BannerDO::getCreateTime, reqVO.getCreateTime())
                .gt(BannerDO::getEndTime, new Date())
                .orderByDesc(BannerDO::getSort)
                .orderByDesc(BannerDO::getCreateTime)
        );
    }

    default List<BannerDO> selectList(BannerExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<BannerDO>()
                .betweenIfPresent(BannerDO::getCreateTime, reqVO.getCreateTime())
                .gt(BannerDO::getEndTime, new Date())
                .orderByDesc(BannerDO::getSort)
                .orderByDesc(BannerDO::getCreateTime)
        );
    }

    default PageResult<BannerDO> selectAppPage(BannerPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BannerDO>()
                .betweenIfPresent(BannerDO::getCreateTime, reqVO.getCreateTime())
                .eq(BannerDO::getStatus, 0)
                .eq(BannerDO::getDeleted, 0)
                .gt(BannerDO::getEndTime, new Date())
                .orderByDesc(BannerDO::getSort)
                .orderByDesc(BannerDO::getCreateTime)
        );
    }
}
