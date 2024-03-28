package com.airohit.agriculture.module.plant.service.plant;

import com.airohit.agriculture.module.plant.dal.dataobject.recordsNew.RecordsNewDO;
import com.airohit.agriculture.module.plant.vo.plant.PlanProcessVo;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/7/7 09:25
 */
public interface PlantService {
    List<PlanProcessVo> getPlanProcessVoList();

    void createRecordsNew(RecordsNewDO recordsNewDO);


    RecordsNewDO getRecordsNewDO(Integer plantProcedureId);
}
