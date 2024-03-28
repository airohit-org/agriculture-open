package com.airohit.agriculture.module.content.convert.news;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.content.dal.dataobject.news.NewsDO;
import com.airohit.agriculture.module.content.vo.news.NewsCreateReqVO;
import com.airohit.agriculture.module.content.vo.news.NewsExcelVO;
import com.airohit.agriculture.module.content.vo.news.NewsRespVO;
import com.airohit.agriculture.module.content.vo.news.NewsUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 新闻信息 Convert
 *
 * @author 管理员
 */
@Mapper
public interface NewsConvert {

    NewsConvert INSTANCE = Mappers.getMapper(NewsConvert.class);

    NewsDO convert(NewsCreateReqVO bean);

    NewsDO convert(NewsUpdateReqVO bean);

    NewsRespVO convert(NewsDO bean);

    List<NewsRespVO> convertList(List<NewsDO> list);

    PageResult<NewsRespVO> convertPage(PageResult<NewsDO> page);

    List<NewsExcelVO> convertList02(List<NewsDO> list);

}
