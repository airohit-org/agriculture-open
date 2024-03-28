package com.airohit.agriculture.module.infra.dal.mysql.db;

import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.module.infra.dal.dataobject.db.DataSourceConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源配置 Mapper
 *
 * @author shiminghao
 */
@Mapper
public interface DataSourceConfigMapper extends BaseMapperX<DataSourceConfigDO> {
}
