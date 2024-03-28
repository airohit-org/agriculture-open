package com.airohit.agriculture.module.plant.service.records;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.plant.dal.dataobject.records.RecordsDO;
import com.airohit.agriculture.module.plant.vo.records.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 农事记录 Service 接口
 *
 * @author 管理员
 */
public interface RecordsService {

    /**
     * 创建农事记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createRecords(@Valid RecordsCreateReqVO createReqVO);

    /**
     * 更新农事记录
     *
     * @param updateReqVO 更新信息
     */
    void updateRecords(@Valid RecordsUpdateReqVO updateReqVO);

    /**
     * 删除农事记录
     *
     * @param id 编号
     */
    void deleteRecords(Integer id);

    /**
     * 获得农事记录
     *
     * @param id 编号
     * @return 农事记录
     */
    RecordsDO getRecords(Integer id);

    /**
     * 获得农事记录
     *
     * @param id 编号
     * @return 农事记录
     */
    RecordsRespVO getRecordsById(Integer id);

    /**
     * 获得农事记录列表
     *
     * @param ids 编号
     * @return 农事记录列表
     */
    List<RecordsDO> getRecordsList(Collection<Integer> ids);

    /**
     * 获得农事记录分页
     *
     * @param pageReqVO 分页查询
     * @return 农事记录分页
     */
    PageResult<RecordsDO> getRecordsPage(RecordsPageReqVO pageReqVO);

    /**
     * 获得农事记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 农事记录列表
     */
    List<RecordsDO> getRecordsList(RecordsExportReqVO exportReqVO);

}
