package com.airohit.agriculture.module.system.api.dict;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.api.dict.dto.DictDataRespDTO;
import com.airohit.agriculture.module.system.convert.dict.DictDataConvert;
import com.airohit.agriculture.module.system.dal.dataobject.dict.DictDataDO;
import com.airohit.agriculture.module.system.entity.admin.dict.vo.data.DictDataExportReqVO;
import com.airohit.agriculture.module.system.service.dict.DictDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Service
public class DictDataApiImpl implements DictDataApi {

    @Resource
    private DictDataService dictDataService;

    @Override
    public CommonResult<Boolean> validDictDatas(String dictType, Collection<String> values) {
        dictDataService.validDictDatas(dictType, values);
        return success(true);
    }

    @Override
    public CommonResult<DictDataRespDTO> getDictData(String dictType, String value) {
        DictDataDO dictData = dictDataService.getDictData(dictType, value);
        return success(DictDataConvert.INSTANCE.convert02(dictData));
    }

    @Override
    public CommonResult<DictDataRespDTO> parseDictData(String dictType, String label) {
        DictDataDO dictData = dictDataService.parseDictData(dictType, label);
        return success(DictDataConvert.INSTANCE.convert02(dictData));
    }

    @Override
    public CommonResult<List<DictDataRespDTO>> getDictDataAll(String dictType) {
        DictDataExportReqVO reqVO = new DictDataExportReqVO();
        reqVO.setDictType(dictType);
        List<DictDataDO> list = dictDataService.getDictDatas(reqVO);
        return success(DictDataConvert.INSTANCE.convertList03(list));
    }

}
