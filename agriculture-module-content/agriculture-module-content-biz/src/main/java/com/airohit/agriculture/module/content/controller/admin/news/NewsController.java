package com.airohit.agriculture.module.content.controller.admin.news;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.excel.core.util.ExcelUtils;
import com.airohit.agriculture.framework.operatelog.core.annotations.OperateLog;
import com.airohit.agriculture.module.content.convert.news.NewsConvert;
import com.airohit.agriculture.module.content.dal.dataobject.news.NewsDO;
import com.airohit.agriculture.module.content.service.news.NewsService;
import com.airohit.agriculture.module.content.vo.news.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;
import static com.airohit.agriculture.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Api(tags = "管理后台 - 新闻信息")
@RestController
@RequestMapping("/content/news")
@Validated
public class NewsController {

    @Resource
    private NewsService newsService;

    @PostMapping("/create")
    @ApiOperation("创建新闻信息")
    @PreAuthorize("@ss.hasPermission('content:news:create')")
    public CommonResult<Integer> createNews(@Valid @RequestBody NewsCreateReqVO createReqVO) {
        return success(newsService.createNews(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新新闻信息")
    @PreAuthorize("@ss.hasPermission('content:news:update')")
    public CommonResult<Boolean> updateNews(@Valid @RequestBody NewsUpdateReqVO updateReqVO) {
        newsService.updateNews(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除新闻信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('content:news:delete')")
    public CommonResult<Boolean> deleteNews(@RequestParam("id") Integer id) {
        newsService.deleteNews(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得新闻信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('content:news:query')")
    public CommonResult<NewsRespVO> getNews(@RequestParam("id") Integer id) {
        NewsDO news = newsService.getNews(id);
        return success(NewsConvert.INSTANCE.convert(news));
    }

    @GetMapping("/list")
    @ApiOperation("获得新闻信息列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('content:news:query')")
    public CommonResult<List<NewsRespVO>> getNewsList(@RequestParam("ids") Collection<Integer> ids) {
        List<NewsDO> list = newsService.getNewsList(ids);
        return success(NewsConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得新闻信息分页")
    @PreAuthorize("@ss.hasPermission('content:news:query')")
    public CommonResult<PageResult<NewsRespVO>> getNewsPage(@Valid NewsPageReqVO pageVO) {
        PageResult<NewsDO> pageResult = newsService.getNewsPage(pageVO);
        return success(NewsConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出新闻信息 Excel")
    @PreAuthorize("@ss.hasPermission('content:news:export')")
    @OperateLog(type = EXPORT)
    public void exportNewsExcel(@Valid NewsExportReqVO exportReqVO,
                                HttpServletResponse response) throws IOException {
        List<NewsDO> list = newsService.getNewsList(exportReqVO);
        // 导出 Excel
        List<NewsExcelVO> datas = NewsConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "新闻信息.xls", "数据", NewsExcelVO.class, datas);
    }

}
