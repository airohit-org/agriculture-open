package com.airohit.agriculture.module.system.api.dict;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.api.dict.dto.DictDataRespDTO;

import java.util.Collection;
import java.util.List;


public interface DictDataApi {

    CommonResult<Boolean> validDictDatas(String dictType, Collection<String> values);

    CommonResult<DictDataRespDTO> getDictData(String dictType, String value);

    CommonResult<DictDataRespDTO> parseDictData(String dictType, String label);


    CommonResult<List<DictDataRespDTO>> getDictDataAll(String dictType);

}
