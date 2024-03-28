package com.airohit.agriculture.module.system.dal.mysql.district;

import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.module.system.dal.dataobject.district.Province;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/3/14 14:21
 */
@Mapper
public interface ProvinceMapper extends BaseMapperX<Province> {
    default Province selectByProvince(String province) {
        return selectOne(new LambdaQueryWrapper<Province>().eq(Province::getName, province));
    }
}
