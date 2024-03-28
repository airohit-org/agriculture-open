package com.airohit.agriculture.module.plant.service.classification;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.plant.dal.dataobject.classification.ClassificationDO;
import com.airohit.agriculture.module.plant.vo.classification.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 病虫害识别 Service 接口
 *
 * @author 管理员
 */
public interface ClassificationService {

    /**
     * 创建病虫害识别
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createClassification(@Valid ClassificationCreateReqVO createReqVO);


    Integer createClassificationCreateVO(ClassificationCreateVO classificationCreateVO);

    /**
     * 更新病虫害识别
     *
     * @param updateReqVO 更新信息
     */
    void updateClassification(@Valid ClassificationUpdateReqVO updateReqVO);

    /**
     * 删除病虫害识别
     *
     * @param id 编号
     */
    void deleteClassification(Integer id);

    /**
     * 获得病虫害识别
     *
     * @param id 编号
     * @return 病虫害识别
     */
    ClassificationDO getClassification(Integer id);

    /**
     * 获得病虫害识别列表
     *
     * @param ids 编号
     * @return 病虫害识别列表
     */
    List<ClassificationDO> getClassificationList(Collection<Integer> ids);

    /**
     * 获得病虫害识别分页
     *
     * @param pageReqVO 分页查询
     * @return 病虫害识别分页
     */
    PageResult<ClassificationDO> getClassificationPage(ClassificationPageReqVO pageReqVO);

    /**
     * 获得病虫害识别分页
     *
     * @param pageReqVO 分页查询
     * @return 病虫害识别分页
     */
    PageResult<ClassificationRespVO> getClassificationRespVOPage(ClassificationPageReqVO pageReqVO);

    /**
     * 获得病虫害识别列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 病虫害识别列表
     */
    List<ClassificationDO> getClassificationList(ClassificationExportReqVO exportReqVO);

}
