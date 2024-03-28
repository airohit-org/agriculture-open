package com.airohit.agriculture.module.land.service.temperaturezone;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.tenant.core.util.TenantUtils;
import com.airohit.agriculture.module.land.convert.temperaturezone.TemperatureZoneConvert;
import com.airohit.agriculture.module.land.dal.dataobject.temperaturezone.TemperatureZoneDO;
import com.airohit.agriculture.module.land.dal.mysql.temperaturezone.TemperatureZoneMapper;
import com.airohit.agriculture.module.land.vo.temperaturezone.TemperatureZoneCreateReqVO;
import com.airohit.agriculture.module.land.vo.temperaturezone.TemperatureZoneExportReqVO;
import com.airohit.agriculture.module.land.vo.temperaturezone.TemperatureZonePageReqVO;
import com.airohit.agriculture.module.land.vo.temperaturezone.TemperatureZoneUpdateReqVO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.land.enums.ErrorCodeConstants.TEMPERATURE_ZONE_NOT_EXISTS;

/**
 * 积温带管理 Service 实现类
 *
 * @author shiminghao
 */
@Service
@Validated
public class TemperatureZoneServiceImpl implements TemperatureZoneService {

    @Resource
    private TemperatureZoneMapper temperatureZoneMapper;

    @Override
    public Integer createTemperatureZone(TemperatureZoneCreateReqVO createReqVO) {
        // 插入
        TemperatureZoneDO temperatureZone = TemperatureZoneConvert.INSTANCE.convert(createReqVO);
        temperatureZoneMapper.insert(temperatureZone);
        // 返回
        return temperatureZone.getId();
    }

    @Override
    public void createTemperatureZoneByMaster(TemperatureZoneCreateReqVO createReqVO) {
        TenantUtils.execute(createReqVO.getTenantId(), () -> {
            ThreadUtil.sleep(30);
            TenantUtils.executeFarm(createReqVO.getFarmTenantId(), () -> {
                TemperatureZoneDO temperatureZoneDO = temperatureZoneMapper.selectOne(TemperatureZoneDO::getDataCode, createReqVO.getDataCode());
                if (Objects.isNull(temperatureZoneDO)) {
                    temperatureZoneMapper.insert(TemperatureZoneConvert.INSTANCE.convert(createReqVO));
                } else {
                    Integer id = temperatureZoneDO.getId();
                    BeanUtil.copyProperties(createReqVO, temperatureZoneDO);
                    temperatureZoneDO.setId(id);
                    temperatureZoneMapper.updateById(temperatureZoneDO);
                }
            });

        });
    }

    @Override
    public void updateTemperatureZone(TemperatureZoneUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateTemperatureZoneExists(updateReqVO.getId());
        // 更新
        TemperatureZoneDO updateObj = TemperatureZoneConvert.INSTANCE.convert(updateReqVO);
        temperatureZoneMapper.updateById(updateObj);
    }

    @Override
    public void deleteTemperatureZone(Integer id) {
        // 校验存在
        this.validateTemperatureZoneExists(id);
        // 删除
        temperatureZoneMapper.deleteById(id);
    }

    private void validateTemperatureZoneExists(Integer id) {
        if (temperatureZoneMapper.selectById(id) == null) {
            throw exception(TEMPERATURE_ZONE_NOT_EXISTS);
        }
    }

    @Override
    public TemperatureZoneDO getTemperatureZone(Integer id) {
        return temperatureZoneMapper.selectById(id);
    }

    @Override
    public List<TemperatureZoneDO> getTemperatureZoneList(Collection<Integer> ids) {
        return temperatureZoneMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<TemperatureZoneDO> getTemperatureZonePage(TemperatureZonePageReqVO pageReqVO) {
        return temperatureZoneMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TemperatureZoneDO> getTemperatureZoneList(TemperatureZoneExportReqVO exportReqVO) {
        return temperatureZoneMapper.selectList(exportReqVO);
    }

}
