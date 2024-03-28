package com.airohit.agriculture.module.system.api.sensitiveword;

import com.airohit.agriculture.framework.common.pojo.CommonResult;

import java.util.List;


// TODO shiminghao：fallbackFacx`tory =
public interface SensitiveWordApi {

    CommonResult<List<String>> validateText(String text, List<String> tags);

    CommonResult<Boolean> isTextValid(String text, List<String> tags);

}
