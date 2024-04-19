package com.airohit.agriculture.module.system.service.district;

import cn.hutool.core.collection.CollUtil;
import com.airohit.agriculture.framework.redis.core.RedisService;
import com.airohit.agriculture.framework.tenant.core.aop.FarmTenantIgnore;
import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.module.system.dal.dataobject.district.Area;
import com.airohit.agriculture.module.system.dal.dataobject.district.City;
import com.airohit.agriculture.module.system.dal.dataobject.district.Province;
import com.airohit.agriculture.module.system.dal.mysql.district.AreaMapper;
import com.airohit.agriculture.module.system.dal.mysql.district.CityMapper;
import com.airohit.agriculture.module.system.dal.mysql.district.ProvinceMapper;
import com.airohit.agriculture.module.system.enums.district.DistrictRedisEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/3/14 14:31
 */
@Service
@Validated
@Slf4j
public class DistrictServiceImpl implements DistrictService {
    @Resource
    private ProvinceMapper provinceMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private CityMapper cityMapper;
    @Resource
    private AreaMapper areaMapper;

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public List<Province> getProvinceList() {
        List<Province> provinces = redisService.getCacheList(DistrictRedisEnum.PROVINCE.getName());
        if (CollUtil.isEmpty(provinces)) {
            provinces = provinceMapper.selectList();
            redisService.setCacheList(DistrictRedisEnum.PROVINCE.getName(), provinces);
        }
        return provinces;
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public List<City> getCityByProvince(String provinceCode) {
        List<City> cities = redisService.getCacheMapValue(DistrictRedisEnum.CITY.getName(), provinceCode);
        if (CollUtil.isEmpty(cities)) {
            Map<String, List<City>> cityMap = cityMapper.selectList().
                    stream().collect(Collectors.groupingBy(City::getProvinceCode));
            cities = cityMap.get(provinceCode);
            redisService.setCacheMap(DistrictRedisEnum.CITY.getName(), cityMap);
        }
        return cities;
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public List<Area> getAreaByCity(String cityCode) {
        List<Area> areas = redisService.getCacheMapValue(DistrictRedisEnum.AREA.getName(), cityCode);
        if (CollUtil.isEmpty(areas)) {
            Map<String, List<Area>> areaMap = areaMapper.selectList().
                    stream().collect(Collectors.groupingBy(Area::getCityCode));
            areas = areaMap.get(cityCode);
            redisService.setCacheMap(DistrictRedisEnum.AREA.getName(), areaMap);
        }
        return areas;
    }
}
