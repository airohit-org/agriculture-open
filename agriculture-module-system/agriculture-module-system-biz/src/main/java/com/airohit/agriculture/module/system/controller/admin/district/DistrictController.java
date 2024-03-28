package com.airohit.agriculture.module.system.controller.admin.district;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.dal.dataobject.district.Area;
import com.airohit.agriculture.module.system.dal.dataobject.district.City;
import com.airohit.agriculture.module.system.dal.dataobject.district.Province;
import com.airohit.agriculture.module.system.service.district.DistrictService;
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
 * @date :2023/3/14 14:51
 */
@Api(tags = "管理后台 - 行政区域")
@RestController
@RequestMapping("/system/district/")
@Validated
public class DistrictController {
    @Resource
    private DistrictService districtService;

    @GetMapping("/getProvinceList")
    @PermitAll
    @ApiOperation("获取省份列表")
    public CommonResult<List<Province>> getProvinceList() {
        return success(districtService.getProvinceList());
    }

    @GetMapping("/getCityByProvince")
    @PermitAll
    @ApiOperation("根据省份编码获取城市")
    public CommonResult<List<City>> getCityByProvince(@RequestParam("provinceCode") @ApiParam("省份编码") String provinceCode) {
        return success(districtService.getCityByProvince(provinceCode));
    }

    @GetMapping("/getAreaByCity")
    @PermitAll
    @ApiOperation("根据城市编码获取城市")
    public CommonResult<List<Area>> getAreaByCity(@RequestParam("cityCode") @ApiParam("城市编码") String cityCode) {
        return success(districtService.getAreaByCity(cityCode));
    }
}
