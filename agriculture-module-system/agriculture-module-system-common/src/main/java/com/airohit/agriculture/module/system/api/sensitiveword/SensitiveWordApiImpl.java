package com.airohit.agriculture.module.system.api.sensitiveword;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.service.sensitiveword.SensitiveWordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Service
public class SensitiveWordApiImpl implements SensitiveWordApi {

    @Resource
    private SensitiveWordService sensitiveWordService;

    @Override
    public CommonResult<List<String>> validateText(String text, List<String> tags) {
        return success(sensitiveWordService.validateText(text, tags));
    }

    @Override
    public CommonResult<Boolean> isTextValid(String text, List<String> tags) {
        return success(sensitiveWordService.isTextValid(text, tags));
    }

}
