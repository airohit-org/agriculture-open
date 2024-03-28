package com.airohit.agriculture.module.system.controller.admin.ip;

import cn.hutool.core.lang.Assert;
import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.ip.core.Area;
import com.airohit.agriculture.framework.ip.core.utils.AreaUtils;
import com.airohit.agriculture.framework.ip.core.utils.IPUtils;
import com.airohit.agriculture.module.system.convert.ip.AreaConvert;
import com.airohit.agriculture.module.system.entity.admin.ip.vo.AreaNodeRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Api(tags = "管理后台 - 地区")
@RestController
@RequestMapping("/system/area")
@Validated
public class AreaController {

    @GetMapping("/tree")
    @ApiOperation("获得地区树")
    public CommonResult<List<AreaNodeRespVO>> getAreaTree() {
        Area area = AreaUtils.getArea(Area.ID_CHINA);
        Assert.notNull(area, "获取不到中国");
        return success(AreaConvert.INSTANCE.convertList(area.getChildren()));
    }

    @GetMapping("/get-by-ip")
    @ApiOperation("获得 IP 对应的地区名")
    @ApiImplicitParam(name = "ip", value = "IP", required = true, dataTypeClass = String.class)
    public CommonResult<String> getAreaByIp(@RequestParam("ip") String ip) {
        // 获得城市
        Area area = IPUtils.getArea(ip);
        if (area == null) {
            return success("未知");
        }
        // 格式化返回
        return success(AreaUtils.format(area.getId()));
    }

}
