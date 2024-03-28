package com.airohit.agriculture.module.device.vo.obs.beijingTH;

import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/5/4 09:56
 */
@Data
public class UserLoginBeijingTHResponseVo {


    private String token_type;
    private String expires_in;
    private String access_token;
    private String refresh_token;

}
