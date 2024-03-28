package com.airohit.agriculture.module.system.service.weixin;

import me.chanjar.weixin.common.bean.WxJsapiSignature;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/3/29 14:56
 */
public interface WeiXinService {

    WxJsapiSignature createJsapiSignature(String url);
}
