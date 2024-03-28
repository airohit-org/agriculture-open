package com.airohit.agriculture.module.content.dal.mysql.news;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.content.dal.dataobject.news.NewsDO;
import com.airohit.agriculture.module.content.vo.news.NewsExportReqVO;
import com.airohit.agriculture.module.content.vo.news.NewsPageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 新闻信息 Mapper
 *
 * @author 管理员
 */
@Mapper
public interface NewsMapper extends BaseMapperX<NewsDO> {

    default PageResult<NewsDO> selectPage(NewsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<NewsDO>()
                .likeIfPresent(NewsDO::getTitle, reqVO.getTitle())
                .betweenIfPresent(NewsDO::getPublishTime, reqVO.getPublishTime())
                .orderByDesc(NewsDO::getIsTop)
                .orderByDesc(NewsDO::getSort)
                .orderByDesc(NewsDO::getUpdateTime)
        );
    }

    default List<NewsDO> selectList(NewsExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<NewsDO>()
                .likeIfPresent(NewsDO::getTitle, reqVO.getTitle())
                .betweenIfPresent(NewsDO::getPublishTime, reqVO.getPublishTime())
                .orderByDesc(NewsDO::getIsTop)
                .orderByDesc(NewsDO::getSort)
                .orderByDesc(NewsDO::getUpdateTime)
        );
    }

    default PageResult<NewsDO> getNewsAppPage(NewsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<NewsDO>()
                .eqIfPresent(NewsDO::getTitle, reqVO.getTitle())
                .eq(NewsDO::getStatus, 2)
                .eq(NewsDO::getDeleted, 0)
                .orderByDesc(NewsDO::getIsTop)
                .orderByDesc(NewsDO::getSort)
                .orderByDesc(NewsDO::getUpdateTime)
        );
    }
}
