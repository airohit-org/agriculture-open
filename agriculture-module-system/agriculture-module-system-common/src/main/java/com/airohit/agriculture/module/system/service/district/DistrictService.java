package com.airohit.agriculture.module.system.service.district;

import com.airohit.agriculture.module.system.dal.dataobject.district.Area;
import com.airohit.agriculture.module.system.dal.dataobject.district.City;
import com.airohit.agriculture.module.system.dal.dataobject.district.Province;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/3/14 14:24
 */
public interface DistrictService {
    /**
     * 获取所有省份
     *
     * @return
     */
    List<Province> getProvinceList();

    /**
     * 根据省份查找城市
     *
     * @param provinceCode
     * @return
     */
    List<City> getCityByProvince(String provinceCode);

    /**
     * 根据城市查找区县
     *
     * @return
     */
    List<Area> getAreaByCity(String cityCode);
}
