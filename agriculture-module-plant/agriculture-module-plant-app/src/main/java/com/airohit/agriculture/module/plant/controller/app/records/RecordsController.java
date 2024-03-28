package com.airohit.agriculture.module.plant.controller.app.records;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.plant.convert.records.RecordsConvert;
import com.airohit.agriculture.module.plant.dal.dataobject.records.RecordsDO;
import com.airohit.agriculture.module.plant.service.records.RecordsService;
import com.airohit.agriculture.module.plant.vo.records.RecordsCreateReqVO;
import com.airohit.agriculture.module.plant.vo.records.RecordsPageReqVO;
import com.airohit.agriculture.module.plant.vo.records.RecordsRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;


@Api(tags = "农场APP  - 农事记录")
@RestController
@RequestMapping("/plant/app/records")
@Validated
public class RecordsController {

    @Resource
    private RecordsService recordsService;

    @PostMapping("/create")
    @ApiOperation("创建农事记录")
    public CommonResult<Integer> createRecords(@Valid @RequestBody RecordsCreateReqVO createReqVO) {
        return success(recordsService.createRecords(createReqVO));
    }

    @GetMapping("/get")
    @ApiOperation("获得农事记录")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    public CommonResult<RecordsRespVO> getRecords(@RequestParam("id") Integer id) {
        return success(recordsService.getRecordsById(id));
    }

    @GetMapping("/page")
    @ApiOperation("获得农事记录分页")
    public CommonResult<PageResult<RecordsRespVO>> getRecordsPage(@Valid RecordsPageReqVO pageVO) {
        PageResult<RecordsDO> pageResult = recordsService.getRecordsPage(pageVO);
        return success(RecordsConvert.INSTANCE.convertPage(pageResult));
    }


}
