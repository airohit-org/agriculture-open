package com.airohit.agriculture.module.plant.controller.app.plant;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.plant.dal.dataobject.plant.RecordDateDO;
import com.airohit.agriculture.module.plant.dal.dataobject.recordsNew.RecordsNewDO;
import com.airohit.agriculture.module.plant.dal.mysql.plant.RecordDateMapper;
import com.airohit.agriculture.module.plant.service.plant.PlantService;
import com.airohit.agriculture.module.plant.vo.plant.PlanProcessVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/7/7 09:46
 */
@Api(tags = "乔府大院  - 种植环节")
@RestController
@RequestMapping("/plant/app/process")
@Validated
public class PlantAppController {

    @Resource
    private PlantService plantService;
    @Resource
    private RecordDateMapper recordDateMapper;

    @GetMapping("/getPlanProcessVoList")
    @ApiOperation("获得种植环节")
    @PermitAll
    public CommonResult<List<PlanProcessVo>> getPlanProcessVoList() {
        return success(plantService.getPlanProcessVoList());
    }

    @PostMapping("/getPlanProcessVoList")
    @ApiOperation("创建农事记录")
    @PermitAll
    public CommonResult<Boolean> createRecordsNew(@RequestBody RecordsNewDO recordsNewDO) {
        plantService.createRecordsNew(recordsNewDO);
        return success(Boolean.TRUE);
    }

    @GetMapping("/getRecordDateDO")
    @ApiOperation("获得时间")
    @PermitAll
    public CommonResult<RecordDateDO> getRecordDateDO() {
        return success(recordDateMapper.selectById(1));
    }

    @GetMapping("/getRecordsNewDO")
    @ApiOperation("获得农事记录")
    @PermitAll
    public CommonResult<RecordsNewDO> getRecordsNewDO(@RequestParam("plantProcedureId")
                                                      @ApiParam("plantProcedureId") Integer plantProcedureId) {
        return success(plantService.getRecordsNewDO(plantProcedureId));
    }
}
