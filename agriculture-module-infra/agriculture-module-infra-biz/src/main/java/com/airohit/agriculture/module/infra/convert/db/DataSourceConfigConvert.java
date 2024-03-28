package com.airohit.agriculture.module.infra.convert.db;

import com.airohit.agriculture.module.infra.controller.admin.db.vo.DataSourceConfigCreateReqVO;
import com.airohit.agriculture.module.infra.controller.admin.db.vo.DataSourceConfigRespVO;
import com.airohit.agriculture.module.infra.controller.admin.db.vo.DataSourceConfigUpdateReqVO;
import com.airohit.agriculture.module.infra.dal.dataobject.db.DataSourceConfigDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 数据源配置 Convert
 *
 * @author shiminghao
 */
@Mapper
public interface DataSourceConfigConvert {

    DataSourceConfigConvert INSTANCE = Mappers.getMapper(DataSourceConfigConvert.class);

    DataSourceConfigDO convert(DataSourceConfigCreateReqVO bean);

    DataSourceConfigDO convert(DataSourceConfigUpdateReqVO bean);

    DataSourceConfigRespVO convert(DataSourceConfigDO bean);

    List<DataSourceConfigRespVO> convertList(List<DataSourceConfigDO> list);

}
