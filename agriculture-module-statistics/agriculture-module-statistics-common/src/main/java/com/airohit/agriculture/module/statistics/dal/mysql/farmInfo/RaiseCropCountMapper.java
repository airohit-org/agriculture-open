package com.airohit.agriculture.module.statistics.dal.mysql.farmInfo;

import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.module.statistics.dal.dataobject.farmInfo.LandDO;
import com.airohit.agriculture.module.statistics.dal.dataobject.farmInfo.RaiseCropsDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RaiseCropCountMapper extends BaseMapperX<RaiseCropsDO> {
}
