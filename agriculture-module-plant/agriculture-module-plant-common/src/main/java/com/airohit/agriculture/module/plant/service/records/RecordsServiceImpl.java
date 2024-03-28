package com.airohit.agriculture.module.plant.service.records;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.plant.convert.records.RecordsConvert;
import com.airohit.agriculture.module.plant.dal.dataobject.records.RecordsDO;
import com.airohit.agriculture.module.plant.dal.mysql.records.RecordsMapper;
import com.airohit.agriculture.module.plant.vo.records.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.plant.enums.ErrorCodeConstants.RECORDS_NOT_EXISTS;

/**
 * 农事记录 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class RecordsServiceImpl implements RecordsService {

    @Resource
    private RecordsMapper recordsMapper;

    @Override
    public Integer createRecords(RecordsCreateReqVO createReqVO) {
        // 插入
        RecordsDO records = RecordsConvert.INSTANCE.convert(createReqVO);
        recordsMapper.insert(records);
        // 返回
        return records.getId();
    }

    @Override
    public void updateRecords(RecordsUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateRecordsExists(updateReqVO.getId());
        // 更新
        RecordsDO updateObj = RecordsConvert.INSTANCE.convert(updateReqVO);
        recordsMapper.updateById(updateObj);
    }

    @Override
    public void deleteRecords(Integer id) {
        // 校验存在
        this.validateRecordsExists(id);
        // 删除
        recordsMapper.deleteById(id);
    }

    private void validateRecordsExists(Integer id) {
        if (recordsMapper.selectById(id) == null) {
            throw exception(RECORDS_NOT_EXISTS);
        }
    }

    @Override
    public RecordsDO getRecords(Integer id) {
        return recordsMapper.selectById(id);
    }

    @Override
    public RecordsRespVO getRecordsById(Integer id) {
        return recordsMapper.selectAllById(id);
    }

    @Override
    public List<RecordsDO> getRecordsList(Collection<Integer> ids) {
        return recordsMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<RecordsDO> getRecordsPage(RecordsPageReqVO pageReqVO) {
        return recordsMapper.selectPage(pageReqVO);
    }

    @Override
    public List<RecordsDO> getRecordsList(RecordsExportReqVO exportReqVO) {
        return recordsMapper.selectList(exportReqVO);
    }

}
