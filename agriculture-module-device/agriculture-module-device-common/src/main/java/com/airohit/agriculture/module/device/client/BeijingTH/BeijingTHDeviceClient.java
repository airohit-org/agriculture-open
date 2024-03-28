package com.airohit.agriculture.module.device.client.BeijingTH;


import com.airohit.agriculture.module.device.vo.obs.beijingTH.BeijingTHVo;
import com.airohit.agriculture.module.device.vo.obs.beijingTH.UserLoginBeijingTHRequestVo;
import com.alibaba.fastjson.JSONObject;
import com.dtflys.forest.annotation.*;

/**
 * Created with IDEA
 * 新设备
 *
 * @author :hanliyao
 * @date :2023年8月21日15:30:38
 */
@BaseRequest(baseURL = "http://wns.thcreate.com:7000/api")
public interface BeijingTHDeviceClient {

    /**
     * 查询设备数据
     *
     * @return
     */
    @Get("/devices/{deviceId}/datas")
    BeijingTHVo getBeijingTHDevice(@Header("Authorization") String authorization,
                                   @Var("deviceId") String deviceId,
                                   @Query("start_at") String startAt,
                                   @Query("end_at") String endAt);

    /**
     * 用户登录
     *
     * @param userLoginRequestVo
     * @return
     */
    @Post("/user/login")
    JSONObject userLogin(@FormBody UserLoginBeijingTHRequestVo userLoginRequestVo);

    /**
     * 获取全部设备列表
     *
     * @return
     */
    @Get("/devices")
    JSONObject getBeijingTHDevices(@Header("Authorization") String authorization);

}
