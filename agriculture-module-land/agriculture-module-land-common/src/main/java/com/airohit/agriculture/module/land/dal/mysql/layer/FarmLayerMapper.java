package com.airohit.agriculture.module.land.dal.mysql.layer;

import com.airohit.agriculture.framework.datasource.core.enums.DataSourceEnum;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.module.land.dal.dataobject.layer.FarmLayerDO;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;

/**
 * 农场图层 Mapper
 *
 * @author zyg
 */
@Mapper
@DS(value = DataSourceEnum.SLAVE)
public interface FarmLayerMapper extends BaseMapperX<FarmLayerDO> {

}
