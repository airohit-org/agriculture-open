package com.airohit.agriculture.module.system.dal.mysql.earlywarning;

import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.module.system.dal.dataobject.earlywarning.EarlyMessageDO;
import com.airohit.agriculture.module.system.entity.app.earlywarning.EarlyMessageTypeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/7/5 17:10
 */
@Mapper
public interface EarlyMessageMapper extends BaseMapperX<EarlyMessageDO> {
    List<EarlyMessageTypeVo> getEarlyMessageTypeVo();
}
