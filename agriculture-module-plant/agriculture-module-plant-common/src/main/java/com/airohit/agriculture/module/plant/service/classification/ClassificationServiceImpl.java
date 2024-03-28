package com.airohit.agriculture.module.plant.service.classification;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.infra.api.file.FileApi;
import com.airohit.agriculture.module.plant.convert.classification.ClassificationConvert;
import com.airohit.agriculture.module.plant.dal.dataobject.classification.ClassificationDO;
import com.airohit.agriculture.module.plant.dal.dataobject.prevention.PreventionDO;
import com.airohit.agriculture.module.plant.dal.mysql.classification.ClassificationMapper;
import com.airohit.agriculture.module.plant.service.prevention.PreventionService;
import com.airohit.agriculture.module.plant.vo.classification.*;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.plant.enums.ErrorCodeConstants.CLASSIFICATION_NOT_EXISTS;

/**
 * 病虫害识别 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
@Slf4j
public class ClassificationServiceImpl implements ClassificationService {

    @Resource
    private ClassificationMapper classificationMapper;
    @Resource
    private FileApi fileApi;
    @Resource
    private PreventionService preventionService;

    @Override
    public Integer createClassification(ClassificationCreateReqVO createReqVO) {
        // 插入
        ClassificationDO classification = ClassificationConvert.INSTANCE.convert(createReqVO);
        classificationMapper.insert(classification);
        // 返回
        return classification.getId();
    }

    @Override
    public Integer createClassificationCreateVO(ClassificationCreateVO classificationCreateVO) {
        ClassificationDO classificationDO = BeanUtil.copyProperties(classificationCreateVO, ClassificationDO.class);
        classificationDO.setFileName(classificationCreateVO.getFile().getOriginalFilename());
        String fileUrl = "/" + System.currentTimeMillis() + FileNameUtil.getSuffix(classificationCreateVO.getFile().getOriginalFilename());
        File tempFile = new File(fileUrl);
        try {
            tempFile = FileUtil.writeFromStream(classificationCreateVO.getFile().getInputStream(), tempFile);
            String file = fileApi.createFile(FileUtil.readBytes(tempFile));
            classificationDO.setImageUrl(file);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        CommonResult<ClassificationResultVo> upload;
        List<OddsVo> result;
        try {
            if ("0".equals(classificationCreateVO.getCropType())) {
                File file = new File(fileUrl);
                HttpRequest post = HttpUtil.createPost("http://172.25.229.29:1122/upload");
                post.form("file", file);
                HttpResponse execute = post.execute();
                if (execute.isOk()) {
                    TypeReference<CommonResult<ClassificationResultVo>> typeRef = new TypeReference<>() {
                    };
                    upload = JSONObject.parseObject(execute.body(), typeRef);
                    result = upload.getData().getResult()
                            .stream()
                            .filter(s -> s.getOdds().doubleValue() > new BigDecimal("45").doubleValue())
                            .sorted(Comparator.comparing(OddsVo::getOdds).reversed())
                            .collect(Collectors.toList());
                } else {
                    upload = CommonResult.error(500, "识别失败");
                    result = new ArrayList<>();
                    log.error("识别失败");
                }

            } else {
                File file = new File(fileUrl);
                HttpRequest post = HttpUtil.createPost("http://172.25.229.29:1122/uploadPlantVillage");
                post.form("file", file);
                HttpResponse execute = post.execute();
                if (execute.isOk()) {
                    TypeReference<CommonResult<ClassificationResultVo>> typeRef = new TypeReference<>() {
                    };
                    upload = JSONObject.parseObject(execute.body(), typeRef);
                    result = upload.getData().getResult()
                            .stream()
                            .sorted(Comparator.comparing(OddsVo::getOdds).reversed())
                            .collect(Collectors.toList());
                } else {
                    upload = CommonResult.error(500, "识别失败");
                    result = new ArrayList<>();
                    log.error("识别失败");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (upload.isSuccess()) {
            log.info("识别结果--------->" + JSONObject.toJSONString(upload.getData()));
            OddsVo oddsVo = result.stream().findFirst().orElse(null);
            if (Objects.nonNull(oddsVo) && !oddsVo.getName().startsWith("健康")) {
                classificationDO.setDiseasesName(oddsVo.getName());
                classificationDO.setOdds(oddsVo.getOdds().toString());
                PreventionDO preventionDO = preventionService.getPrevention(oddsVo.getName());
                classificationDO.setMeasureId(Optional.ofNullable(preventionDO).orElse(new PreventionDO()).getId());
            } else {
                classificationDO.setDiseasesName("无");
            }
        } else {
            classificationDO.setDiseasesName("无");
        }
        classificationMapper.insert(classificationDO);
        FileUtil.del(tempFile);
        return classificationDO.getId();
    }

    @Override
    public void updateClassification(ClassificationUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateClassificationExists(updateReqVO.getId());
        // 更新
        ClassificationDO updateObj = ClassificationConvert.INSTANCE.convert(updateReqVO);
        classificationMapper.updateById(updateObj);
    }

    @Override
    public void deleteClassification(Integer id) {
        // 校验存在
        this.validateClassificationExists(id);
        // 删除
        classificationMapper.deleteById(id);
    }

    private void validateClassificationExists(Integer id) {
        if (classificationMapper.selectById(id) == null) {
            throw exception(CLASSIFICATION_NOT_EXISTS);
        }
    }

    @Override
    public ClassificationDO getClassification(Integer id) {
        return classificationMapper.selectById(id);
    }

    @Override
    public List<ClassificationDO> getClassificationList(Collection<Integer> ids) {
        return classificationMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ClassificationDO> getClassificationPage(ClassificationPageReqVO pageReqVO) {
        return classificationMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<ClassificationRespVO> getClassificationRespVOPage(ClassificationPageReqVO pageReqVO) {
        IPage<ClassificationRespVO> classificationRespVOIPage = classificationMapper.getClassificationRespVOPage(new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize()), pageReqVO);
        return new PageResult<>(classificationRespVOIPage.getRecords(), classificationRespVOIPage.getTotal());
    }

    @Override
    public List<ClassificationDO> getClassificationList(ClassificationExportReqVO exportReqVO) {
        return classificationMapper.selectList(exportReqVO);
    }

}
