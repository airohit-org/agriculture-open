package com.airohit.agriculture.module.system.controller.admin.notice;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.system.convert.notice.NoticeConvert;
import com.airohit.agriculture.module.system.entity.admin.notice.vo.NoticeCreateReqVO;
import com.airohit.agriculture.module.system.entity.admin.notice.vo.NoticePageReqVO;
import com.airohit.agriculture.module.system.entity.admin.notice.vo.NoticeRespVO;
import com.airohit.agriculture.module.system.entity.admin.notice.vo.NoticeUpdateReqVO;
import com.airohit.agriculture.module.system.service.notice.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Api(tags = "管理后台 - 通知公告")
@RestController
@RequestMapping("/system/notice")
@Validated
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @PostMapping("/create")
    @ApiOperation("创建通知公告")
    @PreAuthorize("@ss.hasPermission('system:notice:create')")
    public CommonResult<Long> createNotice(@Valid @RequestBody NoticeCreateReqVO reqVO) {
        Long noticeId = noticeService.createNotice(reqVO);
        return success(noticeId);
    }

    @PutMapping("/update")
    @ApiOperation("修改通知公告")
    @PreAuthorize("@ss.hasPermission('system:notice:update')")
    public CommonResult<Boolean> updateNotice(@Valid @RequestBody NoticeUpdateReqVO reqVO) {
        noticeService.updateNotice(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除通知公告")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:notice:delete')")
    public CommonResult<Boolean> deleteNotice(@RequestParam("id") Long id) {
        noticeService.deleteNotice(id);
        return success(true);
    }

    @GetMapping("/page")
    @ApiOperation("获取通知公告列表")
    @PreAuthorize("@ss.hasPermission('system:notice:query')")
    public CommonResult<PageResult<NoticeRespVO>> pageNotices(@Validated NoticePageReqVO reqVO) {
        return success(NoticeConvert.INSTANCE.convertPage(noticeService.pageNotices(reqVO)));
    }

    @GetMapping("/get")
    @ApiOperation("获得通知公告")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:notice:query')")
    public CommonResult<NoticeRespVO> getNotice(@RequestParam("id") Long id) {
        return success(NoticeConvert.INSTANCE.convert(noticeService.getNotice(id)));
    }

}
