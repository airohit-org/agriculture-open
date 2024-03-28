package com.airohit.agriculture.module.system.service.earlywarning;

import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.module.system.dal.dataobject.earlywarning.EarlyMessageDO;
import com.airohit.agriculture.module.system.dal.mysql.earlywarning.EarlyMessageMapper;
import com.airohit.agriculture.module.system.entity.app.earlywarning.EarlyMessageTypeVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/7/5 17:12
 */
@Service
public class EarlyMessageServiceImpl implements EarlyMessageService {
    @Resource
    private EarlyMessageMapper earlyMessageMapper;

    @Override
    @TenantIgnore
    public List<EarlyMessageDO> getEarlyMessageList(Integer warningType) {
        return earlyMessageMapper.selectList(new LambdaQueryWrapperX<EarlyMessageDO>().eqIfPresent(EarlyMessageDO::getWarningType, warningType));
    }

    @Override
    @TenantIgnore
    public EarlyMessageDO getEarlyMessageDO(Integer id) {
        return earlyMessageMapper.selectById(id);
    }

    @Override
    @TenantIgnore
    public List<EarlyMessageTypeVo> getEarlyMessageTypeVo() {
        return earlyMessageMapper.getEarlyMessageTypeVo();
    }
}
