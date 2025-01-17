package com.airohit.agriculture.module.plant.controller.admin.classification;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.operatelog.core.annotations.OperateLog;
import com.airohit.agriculture.module.plant.convert.classification.ClassificationConvert;
import com.airohit.agriculture.module.plant.dal.dataobject.classification.ClassificationDO;
import com.airohit.agriculture.module.plant.service.classification.ClassificationService;
import com.airohit.agriculture.module.plant.service.prevention.PreventionService;
import com.airohit.agriculture.module.plant.vo.classification.ClassificationCreateVO;
import com.airohit.agriculture.module.plant.vo.classification.ClassificationPageReqVO;
import com.airohit.agriculture.module.plant.vo.classification.ClassificationRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;


@Api(tags = "管理后台 - 病虫害识别")
@RestController
@RequestMapping("/plant/classification")
@Validated
public class ClassificationController {

    @Resource
    private ClassificationService classificationService;
    @Resource
    private PreventionService preventionService;

    @PostMapping("/create")
    @ApiOperation("创建病虫害识别")
    @PreAuthorize("@ss.hasPermission('diseases:classification:create')")
    @OperateLog(enable = false)
    public CommonResult<Integer> createClassification(ClassificationCreateVO createReqVO) {
        createReqVO.setIsPc(1);
        return success(classificationService.createClassificationCreateVO(createReqVO));
    }


    @DeleteMapping("/delete")
    @ApiOperation("删除病虫害识别")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('diseases:classification:delete')")
    public CommonResult<Boolean> deleteClassification(@RequestParam("id") Integer id) {
        classificationService.deleteClassification(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得病虫害识别")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('diseases:classification:query')")
    public CommonResult<ClassificationRespVO> getClassification(@RequestParam("id") Integer id) {
        ClassificationDO classification = classificationService.getClassification(id);
        ClassificationRespVO convert = ClassificationConvert.INSTANCE.convert(classification);
        if (Objects.nonNull(convert.getMeasureId())) {
            convert.setPreventionDO(preventionService.getPrevention(convert.getMeasureId()));
        }
        convert.setCreateTime(DateUtil.date(LocalDateTimeUtil.toEpochMilli(classification.getCreateTime())));
        return success(convert);
    }

    @GetMapping("/list")
    @ApiOperation("获得病虫害识别列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('diseases:classification:query')")
    public CommonResult<List<ClassificationRespVO>> getClassificationList(@RequestParam("ids") Collection<Integer> ids) {
        List<ClassificationDO> list = classificationService.getClassificationList(ids);
        return success(ClassificationConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得病虫害识别分页")
    @PreAuthorize("@ss.hasPermission('diseases:classification:query')")
    public CommonResult<PageResult<ClassificationRespVO>> getClassificationPage(@Valid ClassificationPageReqVO pageVO) {
        return success(classificationService.getClassificationRespVOPage(pageVO));
    }


}
