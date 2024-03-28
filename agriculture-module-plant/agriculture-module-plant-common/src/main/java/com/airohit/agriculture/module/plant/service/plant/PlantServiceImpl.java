package com.airohit.agriculture.module.plant.service.plant;

import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.module.plant.dal.dataobject.plant.PlanProcedureDO;
import com.airohit.agriculture.module.plant.dal.dataobject.plant.PlanProcessDO;
import com.airohit.agriculture.module.plant.dal.dataobject.recordsNew.RecordsNewDO;
import com.airohit.agriculture.module.plant.dal.mysql.plant.PlanProcedureMapper;
import com.airohit.agriculture.module.plant.dal.mysql.plant.PlanProcessMapper;
import com.airohit.agriculture.module.plant.dal.mysql.recordsNew.RecordsNewMapper;
import com.airohit.agriculture.module.plant.vo.plant.PlanProcedureVo;
import com.airohit.agriculture.module.plant.vo.plant.PlanProcessVo;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/7/7 09:25
 */
@Service
public class PlantServiceImpl implements PlantService {
    @Resource
    private PlanProcedureMapper planProcedureMapper;
    @Resource
    private PlanProcessMapper planProcessMapper;
    @Resource
    private RecordsNewMapper recordsNewMapper;

    @Override
    @TenantIgnore
    public List<PlanProcessVo> getPlanProcessVoList() {
        List<PlanProcessDO> planProcessDOS = planProcessMapper.selectList();
        List<PlanProcedureDO> planProcedureDOS = planProcedureMapper.selectList(new LambdaQueryWrapperX<PlanProcedureDO>()
                .inIfPresent(PlanProcedureDO::getPlantProcessId, planProcessDOS.stream().map(PlanProcessDO::getId).collect(Collectors.toList())));
        Map<Integer, List<PlanProcedureDO>> collect = planProcedureDOS.stream().collect(Collectors.groupingBy(PlanProcedureDO::getPlantProcessId));
        List<PlanProcessVo> planProcessVos = JSONArray.parseArray(JSONArray.toJSONString(planProcessDOS), PlanProcessVo.class);
        for (PlanProcessVo planProcessVo : planProcessVos) {
            List<PlanProcedureVo> planProcedureVos = JSONArray.parseArray(JSONArray.toJSONString(Optional.ofNullable(collect.get(planProcessVo.getId())).orElse(new ArrayList<>())), PlanProcedureVo.class);
            planProcessVo.setPlanProcedureVoList(planProcedureVos);
            planProcessVo.setCount(planProcedureVos.size());
        }
        return planProcessVos;
    }

    @Override
    @TenantIgnore
    public void createRecordsNew(RecordsNewDO recordsNewDO) {
        if (recordsNewMapper.selectCount(RecordsNewDO::getPlantProcedureId, recordsNewDO.getPlantProcedureId()) == 0) {
            recordsNewMapper.insert(recordsNewDO);
            PlanProcedureDO planProcedureDO = planProcedureMapper.selectById(recordsNewDO.getPlantProcedureId());
            planProcedureDO.setStatus(1);
            planProcedureMapper.updateById(planProcedureDO);
        }
    }

    @Override
    public RecordsNewDO getRecordsNewDO(Integer plantProcedureId) {
        return recordsNewMapper.selectOne(RecordsNewDO::getPlantProcedureId, plantProcedureId);
    }
}
