package com.airohit.agriculture.module.system.service.weixin;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.system.enums.ErrorCodeConstants.WEIXIN_ERROR;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/3/29 14:57
 */
@Service
@Slf4j
public class WeiXinServiceImpl implements WeiXinService {

    @Resource
    private WxMpService wxMpService;

    @Override
    public WxJsapiSignature createJsapiSignature(String url) {
        try {
            return wxMpService.createJsapiSignature(url);
        } catch (WxErrorException e) {
            log.error("获取信息失败--------->", e);
            throw exception(WEIXIN_ERROR);
        }
    }
}
