package com.airohit.agriculture.module.content.service.news;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.content.dal.dataobject.news.NewsDO;
import com.airohit.agriculture.module.content.vo.news.NewsCreateReqVO;
import com.airohit.agriculture.module.content.vo.news.NewsExportReqVO;
import com.airohit.agriculture.module.content.vo.news.NewsPageReqVO;
import com.airohit.agriculture.module.content.vo.news.NewsUpdateReqVO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 新闻信息 Service 接口
 *
 * @author 管理员
 */
public interface NewsService {

    /**
     * 创建新闻信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createNews(@Valid NewsCreateReqVO createReqVO);

    /**
     * 更新新闻信息
     *
     * @param updateReqVO 更新信息
     */
    void updateNews(@Valid NewsUpdateReqVO updateReqVO);

    /**
     * 删除新闻信息
     *
     * @param id 编号
     */
    void deleteNews(Integer id);

    /**
     * 获得新闻信息
     *
     * @param id 编号
     * @return 新闻信息
     */
    NewsDO getNews(Integer id);

    /**
     * 获得新闻信息列表
     *
     * @param ids 编号
     * @return 新闻信息列表
     */
    List<NewsDO> getNewsList(Collection<Integer> ids);

    /**
     * 获得新闻信息分页
     *
     * @param pageReqVO 分页查询
     * @return 新闻信息分页
     */
    PageResult<NewsDO> getNewsPage(NewsPageReqVO pageReqVO);

    /**
     * 获得新闻信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 新闻信息列表
     */
    List<NewsDO> getNewsList(NewsExportReqVO exportReqVO);

    /**
     * app 新闻信息查询
     *
     * @param pageVO
     * @return
     */
    PageResult<NewsDO> getNewsAppPage(NewsPageReqVO pageVO);
}
