package com.airohit.agriculture.module.system.controller.admin.sms;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.system.convert.sms.SmsChannelConvert;
import com.airohit.agriculture.module.system.dal.dataobject.sms.SmsChannelDO;
import com.airohit.agriculture.module.system.entity.admin.sms.vo.channel.*;
import com.airohit.agriculture.module.system.service.sms.SmsChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Api(tags = "管理后台 - 短信渠道")
@RestController
@RequestMapping("system/sms-channel")
public class SmsChannelController {

    @Resource
    private SmsChannelService smsChannelService;

    @PostMapping("/create")
    @ApiOperation("创建短信渠道")
    @PreAuthorize("@ss.hasPermission('system:sms-channel:create')")
    public CommonResult<Long> createSmsChannel(@Valid @RequestBody SmsChannelCreateReqVO createReqVO) {
        return success(smsChannelService.createSmsChannel(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新短信渠道")
    @PreAuthorize("@ss.hasPermission('system:sms-channel:update')")
    public CommonResult<Boolean> updateSmsChannel(@Valid @RequestBody SmsChannelUpdateReqVO updateReqVO) {
        smsChannelService.updateSmsChannel(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除短信渠道")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:sms-channel:delete')")
    public CommonResult<Boolean> deleteSmsChannel(@RequestParam("id") Long id) {
        smsChannelService.deleteSmsChannel(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得短信渠道")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:sms-channel:query')")
    public CommonResult<SmsChannelRespVO> getSmsChannel(@RequestParam("id") Long id) {
        SmsChannelDO smsChannel = smsChannelService.getSmsChannel(id);
        return success(SmsChannelConvert.INSTANCE.convert(smsChannel));
    }

    @GetMapping("/page")
    @ApiOperation("获得短信渠道分页")
    @PreAuthorize("@ss.hasPermission('system:sms-channel:query')")
    public CommonResult<PageResult<SmsChannelRespVO>> getSmsChannelPage(@Valid SmsChannelPageReqVO pageVO) {
        PageResult<SmsChannelDO> pageResult = smsChannelService.getSmsChannelPage(pageVO);
        return success(SmsChannelConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获得短信渠道精简列表", notes = "包含被禁用的短信渠道")
    public CommonResult<List<SmsChannelSimpleRespVO>> getSimpleSmsChannels() {
        List<SmsChannelDO> list = smsChannelService.getSmsChannelList();
        // 排序后，返回给前端
        list.sort(Comparator.comparing(SmsChannelDO::getId));
        return success(SmsChannelConvert.INSTANCE.convertList03(list));
    }

}
