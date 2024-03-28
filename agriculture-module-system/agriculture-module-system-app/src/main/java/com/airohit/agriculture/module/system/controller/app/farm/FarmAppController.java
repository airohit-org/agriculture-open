package com.airohit.agriculture.module.system.controller.app.farm;

import cn.hutool.json.JSONUtil;
import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.api.slave.vo.farm.FarmRespVO;
import com.airohit.agriculture.module.system.api.slave.vo.farm.PosVo;
import com.airohit.agriculture.module.system.convert.farm.FarmConvert;
import com.airohit.agriculture.module.system.dal.dataobject.farm.FarmDO;
import com.airohit.agriculture.module.system.service.farm.SystemFarmService;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;


@Api(tags = "农场app - 农场")
@RestController
@RequestMapping("/system/app/farm/")
@Validated
public class FarmAppController {

    @Resource
    private SystemFarmService Service;


    @GetMapping("/getFarmByTenant")
    @ApiOperation("根据租户获得农场")
    @PermitAll
    public CommonResult<FarmRespVO> get(@RequestParam("tenantId") Long tenantId) {
        FarmDO farmDO = Service.getFarmByTenant(tenantId);
        FarmRespVO convert = FarmConvert.INSTANCE.convert(farmDO);
        if (JSONUtil.isTypeJSON(convert.getCoordinate())) {
            convert.setPosVoList(JSONArray.parseArray(farmDO.getCoordinate(), PosVo.class));
        }
        return success(convert);
    }

    @GetMapping("/getFarmListByTenant")
    @ApiOperation("根据租户获得农场")
    @PermitAll
    public CommonResult<List<FarmRespVO>> getFarmListByTenant(@RequestParam("tenantId") Long tenantId) {
        List<FarmDO> farmListByTenant = Service.getFarmListByTenant(tenantId);
        List<FarmRespVO> farmRespVOList = new ArrayList<>();
        for (FarmDO farmDO : farmListByTenant) {
            FarmRespVO convert = FarmConvert.INSTANCE.convert(farmDO);
            if (JSONUtil.isTypeJSON(convert.getCoordinate())) {
                convert.setPosVoList(JSONArray.parseArray(farmDO.getCoordinate(), PosVo.class));
            }
            farmRespVOList.add(convert);
        }
        return success(farmRespVOList);
    }

}
