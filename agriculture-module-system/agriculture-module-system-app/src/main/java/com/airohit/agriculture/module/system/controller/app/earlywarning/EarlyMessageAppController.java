package com.airohit.agriculture.module.system.controller.app.earlywarning;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.dal.dataobject.earlywarning.EarlyMessageDO;
import com.airohit.agriculture.module.system.entity.app.earlywarning.EarlyMessageTypeVo;
import com.airohit.agriculture.module.system.service.earlywarning.EarlyMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/7/5 17:30
 */
@Api(tags = "农场app - 看预警")
@RestController
@RequestMapping("/system/app/earlyMessage/")
@Validated
public class EarlyMessageAppController {
    @Resource
    private EarlyMessageService earlyMessageService;

    @GetMapping("/getEarlyMessageTypeVo")
    @ApiOperation("看预警统计")
    @PermitAll
    public CommonResult<List<EarlyMessageTypeVo>> getEarlyMessageTypeVo() {
        return success(earlyMessageService.getEarlyMessageTypeVo());
    }

    @GetMapping("/getEarlyMessageDO")
    @ApiOperation("看预警详情")
    @PermitAll
    public CommonResult<EarlyMessageDO> getEarlyMessageDO(@ApiParam("id") @RequestParam("id") Integer id) {
        return success(earlyMessageService.getEarlyMessageDO(id));
    }

    @GetMapping("/getEarlyMessageList")
    @ApiOperation("看预警列表")
    @PermitAll
    public CommonResult<List<EarlyMessageDO>> getEarlyMessageList(@ApiParam("类型") @RequestParam(value = "warningType", required = false) Integer warningType) {
        return success(earlyMessageService.getEarlyMessageList(warningType));
    }
}
