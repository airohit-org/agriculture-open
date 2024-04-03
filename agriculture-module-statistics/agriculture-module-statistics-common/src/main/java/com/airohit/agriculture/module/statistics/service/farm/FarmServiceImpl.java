package com.airohit.agriculture.module.statistics.service.farm;


import com.airohit.agriculture.framework.tenant.core.aop.FarmTenantIgnore;
import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.framework.tenant.core.context.FarmTenantContextHolder;
import com.airohit.agriculture.framework.tenant.core.context.TenantContextHolder;

import com.airohit.agriculture.module.statistics.dal.mysql.farm.FarmStatisticMapper;
import com.airohit.agriculture.module.statistics.dal.mysql.farmInfo.*;
import com.airohit.agriculture.module.statistics.vo.farm.FarmStatisticVo;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/4/19 10:38
 */
@Service
public class FarmServiceImpl implements FarmService {
    @Resource
    private FarmStatisticMapper farmStatisticMapper;
    @Resource
    @Lazy // 注入自己，所以延迟加载
    private FarmServiceImpl self;

    @Resource
    private DeviceCountMapper deviceCountMapper;

    @Resource
    private LandCountMapper landCountMapper;

    @Resource
    private PeasantCountMapper peasantCountMapper;

    @Resource
    private RaiseCropCountMapper raiseCropCountMapper;

    @Resource
    private TaskInfoCountMapper taskInfoCountMapper;


    @Override
    public FarmStatisticVo getFarmStatisticVo() {
        Long tenantId = TenantContextHolder.getTenantId();
        FarmStatisticVo farmStatisticVo = new FarmStatisticVo();
        farmStatisticVo.setCrops(farmStatisticMapper.getCropsCount());
        farmStatisticVo.setLandCount(farmStatisticMapper.getLandCount());
        farmStatisticVo.setMemberCount(farmStatisticMapper.getMemberCount(tenantId));
        farmStatisticVo.setPlantArea(self.getFarmPlantArea(tenantId));
//        Integer deviceCount = obsDeviceApi.getAllDeviceGroupVoList(Integer.valueOf(FarmTenantContextHolder.getFarmTenantId().toString()))
//                .getCheckedData()
//                .stream()
//                .mapToInt(DeviceGroupVo::getCount)
//                .sum();
        farmStatisticVo.setDeviceCount(0);
        return farmStatisticVo;
    }

    @TenantIgnore
    @Override
    public String getFarmPlantArea(Long tenantId) {
        Long farmTenantId = FarmTenantContextHolder.getFarmTenantId();
        return farmStatisticMapper.getFarmPlantArea(tenantId, farmTenantId);
    }



    @Override
    @TenantIgnore
    public List<Long> getFarmInfos() {

        List<Long> list = new ArrayList<>();

        Long landCount = landCountMapper.selectCount();
        Long raiseCropCount = raiseCropCountMapper.selectCount();
        Long deviceCount = getDeviceCount();
        Long taskInfoCount = getTaskCount();
        Long peasantCount = peasantCountMapper.selectCount();

        list.add(landCount);
        list.add(raiseCropCount);
        list.add(deviceCount);
        list.add(taskInfoCount);
        list.add(peasantCount);

        return list;

    }

    @TenantIgnore
    @FarmTenantIgnore
    public Long getDeviceCount() {
        Integer farmId = Math.toIntExact(FarmTenantContextHolder.getFarmTenantId());
        return deviceCountMapper.selectCountByFarmId(farmId);
    }


    public Long getTaskCount(){
        return taskInfoCountMapper.getTaskInfoCount();
    }




}
