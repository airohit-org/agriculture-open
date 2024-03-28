package com.airohit.agriculture.module.peasant.dal.mysql.peasant;

import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.module.peasant.dal.dataobject.peasant.ProvinceVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RealProvinceMapper extends BaseMapperX<ProvinceVo> {

    default ProvinceVo selectByProvince(String province) {
        return selectOne(new LambdaQueryWrapper<ProvinceVo>().eq(ProvinceVo::getName, province));
    }


}
