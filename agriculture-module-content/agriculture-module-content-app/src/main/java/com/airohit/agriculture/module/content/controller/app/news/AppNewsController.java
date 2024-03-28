package com.airohit.agriculture.module.content.controller.app.news;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.module.content.convert.news.NewsConvert;
import com.airohit.agriculture.module.content.dal.dataobject.news.NewsDO;
import com.airohit.agriculture.module.content.service.news.NewsService;
import com.airohit.agriculture.module.content.vo.news.NewsPageReqVO;
import com.airohit.agriculture.module.content.vo.news.NewsRespVO;
import com.airohit.agriculture.module.content.vo.news.NewsUpdateReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Api(tags = "APP - 新闻信息")
@RestController
@RequestMapping("/content/news")
@Validated
public class AppNewsController {

    @Resource
    private NewsService newsService;

    @GetMapping("/page")
    @ApiOperation("获得新闻信息分页")
    @TenantIgnore
    public CommonResult<PageResult<NewsRespVO>> getNewsPage(@Valid NewsPageReqVO pageVO) {
        PageResult<NewsDO> pageResult = newsService.getNewsAppPage(pageVO);
        return success(NewsConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/queryNewsInfo")
    @ApiOperation("新闻详情")
    @TenantIgnore
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    public CommonResult<NewsRespVO> queryNewsInfo(@RequestParam("id") Integer id) {
        NewsDO news = newsService.getNews(id);

        NewsUpdateReqVO newsUpdateReqVO = new NewsUpdateReqVO();
        BeanUtils.copyProperties(news, newsUpdateReqVO);

        //阅读数量 + 1
        newsUpdateReqVO.setReadNum(newsUpdateReqVO.getReadNum() + 1);
        newsService.updateNews(newsUpdateReqVO);

        return success(NewsConvert.INSTANCE.convert(news));
    }


}
