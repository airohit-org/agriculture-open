package com.airohit.agriculture.module.land.service.raise;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.framework.tenant.core.util.TenantUtils;
import com.airohit.agriculture.module.land.convert.raise.RaiseCropsConvert;
import com.airohit.agriculture.module.land.dal.dataobject.raise.RaiseCropsDO;
import com.airohit.agriculture.module.land.dal.mysql.raise.RaiseCropsMapper;
import com.airohit.agriculture.module.land.vo.raise.RaiseCropsCreateReqVO;
import com.airohit.agriculture.module.land.vo.raise.RaiseCropsExportReqVO;
import com.airohit.agriculture.module.land.vo.raise.RaiseCropsPageReqVO;
import com.airohit.agriculture.module.land.vo.raise.RaiseCropsUpdateReqVO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;


/**
 * 种植作物 Service 实现类
 *
 * @author shiminghao
 */
@Service
@Validated
public class RaiseCropsServiceImpl implements RaiseCropsService {

    @Resource
    private RaiseCropsMapper raiseCropsMapper;

    @Override
    public Integer createCrops(RaiseCropsCreateReqVO createReqVO) {
        // 插入
        RaiseCropsDO crops = RaiseCropsConvert.INSTANCE.convert(createReqVO);
        raiseCropsMapper.insert(crops);
        return crops.getId();
    }

    @Override
    public void createCropsByMaster(RaiseCropsCreateReqVO createReqVO) {
        TenantUtils.execute(createReqVO.getTenantId(), () -> {
            ThreadUtil.sleep(30);
            TenantUtils.executeFarm(createReqVO.getFarmTenantId(), () -> {
                RaiseCropsDO raiseCropsDO = raiseCropsMapper.selectOne(RaiseCropsDO::getDataCode, createReqVO.getDataCode());
                if (Objects.isNull(raiseCropsDO)) {
                    raiseCropsMapper.insert(RaiseCropsConvert.INSTANCE.convert(createReqVO));
                } else {
                    Integer id = raiseCropsDO.getId();
                    BeanUtil.copyProperties(createReqVO, raiseCropsDO);
                    raiseCropsDO.setId(id);
                    raiseCropsMapper.updateById(raiseCropsDO);
                }
            });

        });
    }

    @Override
    public void updateCrops(RaiseCropsUpdateReqVO updateReqVO) {
        // 更新
        RaiseCropsDO updateObj = RaiseCropsConvert.INSTANCE.convert(updateReqVO);
        raiseCropsMapper.updateById(updateObj);
    }

    @Override
    public RaiseCropsDO getCrops(String dataCode, Long tenantId, Long farmTenantId) {
        AtomicReference<RaiseCropsDO> raiseCropsDO = new AtomicReference<>(new RaiseCropsDO());
        TenantUtils.execute(tenantId, () ->
                TenantUtils.executeFarm(farmTenantId, () ->
                        raiseCropsDO.set(raiseCropsMapper.selectOne(new LambdaQueryWrapperX<RaiseCropsDO>()
                                .eqIfPresent(RaiseCropsDO::getDataCode, dataCode)))));
        return raiseCropsDO.get();
    }

    @Override
    public void deleteCrops(Integer id) {
        // 删除
        raiseCropsMapper.deleteById(id);
    }


    @Override
    public RaiseCropsDO getCrops(Integer id) {
        return raiseCropsMapper.selectById(id);
    }

    @Override
    public List<RaiseCropsDO> getCropsList(Collection<Integer> ids) {
        return raiseCropsMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<RaiseCropsDO> getCropsPage(RaiseCropsPageReqVO pageReqVO) {
        return raiseCropsMapper.selectPage(pageReqVO);
    }

    @Override
    public List<RaiseCropsDO> getCropsList(RaiseCropsExportReqVO exportReqVO) {
        return raiseCropsMapper.selectList(exportReqVO);
    }

}
