package com.airohit.agriculture.module.system.service.earlywarning;

import com.airohit.agriculture.module.system.dal.dataobject.earlywarning.EarlyMessageDO;
import com.airohit.agriculture.module.system.entity.app.earlywarning.EarlyMessageTypeVo;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/7/5 17:11
 */
public interface EarlyMessageService {
    List<EarlyMessageDO> getEarlyMessageList(Integer warningType);

    EarlyMessageDO getEarlyMessageDO(Integer id);

    List<EarlyMessageTypeVo> getEarlyMessageTypeVo();
}
