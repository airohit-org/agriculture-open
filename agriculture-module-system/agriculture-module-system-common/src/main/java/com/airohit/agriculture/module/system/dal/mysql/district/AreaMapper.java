package com.airohit.agriculture.module.system.dal.mysql.district;

import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.module.system.dal.dataobject.district.Area;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/3/14 14:21
 */
@Mapper
public interface AreaMapper extends BaseMapperX<Area> {
    default Area selectByArea(String area) {
        LambdaQueryWrapper<Area> eq = new LambdaQueryWrapper<Area>().eq(Area::getName, area);
        return selectOne(new LambdaQueryWrapper<Area>().eq(Area::getName, area));
    }

}
