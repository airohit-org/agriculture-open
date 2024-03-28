package com.airohit.agriculture.module.system.controller.app.message;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.system.entity.app.message.WarningMessagePageReqVO;
import com.airohit.agriculture.module.system.entity.app.message.WarningMessageRespVO;
import com.airohit.agriculture.module.system.service.message.SystemWarningMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/6/9 08:46
 */
@Api(tags = "农场app - 消息")
@RestController
@RequestMapping("/system/app/message")
@Validated
public class AppMessageController {
    @Resource
    private SystemWarningMessageService systemWarningMessageService;

    @GetMapping("/page")
    @ApiOperation("获得消息分页")
    public CommonResult<PageResult<WarningMessageRespVO>> getPage(@Valid WarningMessagePageReqVO pageVO) {
//        PageResult<WarningMessageRespVO> pageResult = systemWarningMessageService.getMessagePage(pageVO);
        return success(new PageResult<>());
    }


    @GetMapping("/get")
    @ApiOperation("获得消息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    public CommonResult<WarningMessageRespVO> get(@RequestParam("id") Integer id) {
        return success(systemWarningMessageService.getMessage(id));
    }

    @GetMapping("/getMessageCount")
    @ApiOperation("获得消息数量")
    @ApiImplicitParam(name = "messageStatus", value = "是否已读,1是0否", required = true, example = "1024", dataTypeClass = Integer.class)
    public CommonResult<Integer> getMessageCount(@RequestParam("messageStatus") Integer messageStatus) {
        return success(systemWarningMessageService.getMessageCount(messageStatus));
    }

    @PostMapping("/readAllMessage")
    @ApiOperation("获得消息")
    public CommonResult<Boolean> readAllMessage() {
        systemWarningMessageService.readAllMessage();
        return success(true);
    }
}
