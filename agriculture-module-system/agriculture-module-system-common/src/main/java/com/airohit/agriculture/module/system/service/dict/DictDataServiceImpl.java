package com.airohit.agriculture.module.system.service.dict;

import cn.hutool.core.collection.CollUtil;
import com.airohit.agriculture.framework.common.enums.CommonStatusEnum;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.common.util.collection.CollectionUtils;
import com.airohit.agriculture.module.system.api.dict.dto.DictDataRespDTO;
import com.airohit.agriculture.module.system.convert.dict.DictDataConvert;
import com.airohit.agriculture.module.system.dal.dataobject.dict.DictDataDO;
import com.airohit.agriculture.module.system.dal.dataobject.dict.DictTypeDO;
import com.airohit.agriculture.module.system.dal.mysql.dict.DictDataMapper;
import com.airohit.agriculture.module.system.entity.admin.dict.vo.data.DictDataCreateReqVO;
import com.airohit.agriculture.module.system.entity.admin.dict.vo.data.DictDataExportReqVO;
import com.airohit.agriculture.module.system.entity.admin.dict.vo.data.DictDataPageReqVO;
import com.airohit.agriculture.module.system.entity.admin.dict.vo.data.DictDataUpdateReqVO;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.system.enums.ErrorCodeConstants.*;

/**
 * 字典数据 Service 实现类
 *
 * @author shiminghao
 */
@Service
@Slf4j
public class DictDataServiceImpl implements DictDataService {

    /**
     * 排序 dictType > sort
     */
    private static final Comparator<DictDataDO> COMPARATOR_TYPE_AND_SORT = Comparator
            .comparing(DictDataDO::getDictType)
            .thenComparingInt(DictDataDO::getSort);

    @Resource
    private DictTypeService dictTypeService;

    @Resource
    private DictDataMapper dictDataMapper;

    @Override
    public List<DictDataDO> getDictDatas() {
        List<DictDataDO> list = dictDataMapper.selectList();
        list.sort(COMPARATOR_TYPE_AND_SORT);
        return list;
    }

    @Override
    public PageResult<DictDataDO> getDictDataPage(DictDataPageReqVO reqVO) {
        return dictDataMapper.selectPage(reqVO);
    }

    @Override
    public List<DictDataDO> getDictDatas(DictDataExportReqVO reqVO) {
        List<DictDataDO> list = dictDataMapper.selectList(reqVO);
        list.sort(COMPARATOR_TYPE_AND_SORT);
        return list;
    }

    @Override
    public DictDataDO getDictData(Long id) {
        return dictDataMapper.selectById(id);
    }

    @Override
    public Long createDictData(DictDataCreateReqVO reqVO) {
        // 校验正确性
        checkCreateOrUpdate(null, reqVO.getValue(), reqVO.getDictType());

        // 插入字典类型
        DictDataDO dictData = DictDataConvert.INSTANCE.convert(reqVO);
        dictDataMapper.insert(dictData);
        return dictData.getId();
    }

    @Override
    public void updateDictData(DictDataUpdateReqVO reqVO) {
        // 校验正确性
        checkCreateOrUpdate(reqVO.getId(), reqVO.getValue(), reqVO.getDictType());

        // 更新字典类型
        DictDataDO updateObj = DictDataConvert.INSTANCE.convert(reqVO);
        dictDataMapper.updateById(updateObj);
    }

    @Override
    public void deleteDictData(Long id) {
        // 校验是否存在
        checkDictDataExists(id);

        // 删除字典数据
        dictDataMapper.deleteById(id);
    }

    @Override
    public long countByDictType(String dictType) {
        return dictDataMapper.selectCountByDictType(dictType);
    }


    private void checkCreateOrUpdate(Long id, String value, String dictType) {
        // 校验自己存在
        checkDictDataExists(id);
        // 校验字典类型有效
        checkDictTypeValid(dictType);
        // 校验字典数据的值的唯一性
        checkDictDataValueUnique(id, dictType, value);
    }

    @VisibleForTesting
    public void checkDictDataValueUnique(Long id, String dictType, String value) {
        DictDataDO dictData = dictDataMapper.selectByDictTypeAndValue(dictType, value);
        if (dictData == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典数据
        if (id == null) {
            throw exception(DICT_DATA_VALUE_DUPLICATE);
        }
        if (!dictData.getId().equals(id)) {
            throw exception(DICT_DATA_VALUE_DUPLICATE);
        }
    }

    @VisibleForTesting
    public void checkDictDataExists(Long id) {
        if (id == null) {
            return;
        }
        DictDataDO dictData = dictDataMapper.selectById(id);
        if (dictData == null) {
            throw exception(DICT_DATA_NOT_EXISTS);
        }
    }

    @VisibleForTesting
    public void checkDictTypeValid(String type) {
        DictTypeDO dictType = dictTypeService.getDictType(type);
        if (dictType == null) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
        if (!CommonStatusEnum.ENABLE.getStatus().equals(dictType.getStatus())) {
            throw exception(DICT_TYPE_NOT_ENABLE);
        }
    }

    @Override
    public void validDictDatas(String dictType, Collection<String> values) {
        if (CollUtil.isEmpty(values)) {
            return;
        }
        Map<String, DictDataDO> dictDataMap = CollectionUtils.convertMap(
                dictDataMapper.selectByDictTypeAndValues(dictType, values), DictDataDO::getValue);
        // 校验
        values.forEach(value -> {
            DictDataDO dictData = dictDataMap.get(value);
            if (dictData == null) {
                throw exception(DICT_DATA_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(dictData.getStatus())) {
                throw exception(DICT_DATA_NOT_ENABLE, dictData.getLabel());
            }
        });
    }

    @Override
    public DictDataDO getDictData(String dictType, String value) {
        return dictDataMapper.selectByDictTypeAndValue(dictType, value);
    }

    @Override
    public DictDataDO parseDictData(String dictType, String label) {
        return dictDataMapper.selectByDictTypeAndLabel(dictType, label);
    }

    @Override
    public List<DictDataRespDTO> getDeviceTypeList(String dictType) {
        DictDataExportReqVO reqVO = new DictDataExportReqVO();
        reqVO.setDictType(dictType);
        List<DictDataDO> list = getDictDatas(reqVO);
        return DictDataConvert.INSTANCE.convertList03(list);
    }

}
