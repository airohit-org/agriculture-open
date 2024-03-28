package com.airohit.agriculture.module.device.client.obs;

import com.airohit.agriculture.module.device.vo.farmyun.Result;
import com.airohit.agriculture.module.device.vo.farmyun.userinfo.*;
import com.alibaba.fastjson.JSONObject;
import com.dtflys.forest.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IDEA
 * 山东仁科接口,全局接口
 *
 * @author :shiminghao
 * @date :2023/5/4 09:47
 */
@BaseRequest(baseURL = "http://api.farm.0531yun.cn")
public interface UserInfoClient {
    /*    *//**
     * 用户登录
     *
     * @param userLoginRequestVo
     * @return
     *//*
    @Post("/api/v2.0/entrance/user/userLogin")
    Result<UserLoginResponseVo> userLogin(@JSONBody UserLoginRequestVo userLoginRequestVo);*/

    /**
     * 用户登录
     *
     * @param userLoginRequestVo
     * @return
     */
    @Post("/api/v2.0/entrance/user/userLogin")
    JSONObject userLogin(@JSONBody UserLoginRequestVo userLoginRequestVo);

    /**
     * 获取设备报警通知记录
     *
     * @param token
     * @param alarmNoticeRecordResponseVo
     * @return
     */
    @Get("/api/v2.0/entrance/device/getAlarmNoticeRecord")
    Result<List<AlarmNoticeRecordRequestVo>> getAlarmNoticeRecord(@Header("token") String token, @Query AlarmNoticeRecordResponseVo alarmNoticeRecordResponseVo);

    /**
     * 获取登录用户信息
     *
     * @param token
     * @return
     */
    @Get("/api/v2.0/entrance/user/getUser")
    Result<UserInfoResponseVo> getUser(@Header("token") String token);

    /**
     * 获取子用户信息
     *
     * @param token
     * @param userName 当前用户子账号名称(非必填)
     * @return
     */
    @Get("/api/v2.0/entrance/user/getChildUser")
    Result<List<ChildUserInfoResponseVo>> getChildUser(@Header("token") String token, @Query("userName") String userName);

    /**
     * 获取用户区域
     *
     * @param token
     * @param groupName 区域名称查询(非必填)
     * @return
     */
    @Get("/api/v2.0/entrance/group/getsysUserGroup")
    Result<List<UserGroupResponseVo>> getsysUserGroup(@Header("token") String token, @Query("groupName") String groupName);

    /*    *//**
     * 获取用户设备
     *
     * @param token
     * @param groupId    区域id(非必填)
     * @param deviceType 设备类型（irrigation、worm、wormFlagship、soil、met、spore、camera）(非必填)
     * @return
     *//*
    @Get("/api/v2.0/entrance/device/getsysUserDevice")
    Result<List<DeviceListResponseVo>> getsysUserDevice(@Header("token") String token,
                                                        @Query("groupId") String groupId,
                                                        @Query("deviceType") String deviceType);*/

    /**
     * 获取用户设备
     *
     * @param token
     * @param groupId    区域id(非必填)
     * @param deviceType 设备类型（irrigation、worm、wormFlagship、soil、met、spore、camera）(非必填)
     * @return
     */
    @Get("/api/v2.0/entrance/device/getsysUserDevice")
    JSONObject getsysUserDevice(@Header("token") String token,
                                @Query("groupId") String groupId,
                                @Query("deviceType") String deviceType);
    /*    *//**
     * 获取设备实时数据
     *
     * @param token
     * @param deviceAddrs 设备地址（多个用英文逗号分隔，空为获取当前用户下的所有设备）(非必填)
     * @return
     *//*
    @Get("/api/v2.0/entrance/device/getRealTimeData")
    Result<List<DeviceRealTimeDataResponseVo>> getRealTimeData(@Header("token") String token,
                                                               @Query("deviceAddrs") String deviceAddrs);*/

    /**
     * 获取设备实时数据
     *
     * @param token
     * @param deviceAddrs 设备地址（多个用英文逗号分隔，空为获取当前用户下的所有设备）(非必填)
     * @return
     */
    @Get("/api/v2.0/entrance/device/getRealTimeData")
    JSONObject getRealTimeData(@Header("token") String token,
                               @Query("deviceAddrs") String deviceAddrs);

    /**
     * 批量获取摄像头设备信息
     *
     * @param token
     * @param deviceAddrs 设备地址（多个用英文逗号分隔
     * @return
     */
    @Get("/api/v2.0/entrance/device/getDeviceCamera")
    Result<List<DeviceCameraResponseVo>> getDeviceCamera(@Header("token") String token,
                                                         @Query("deviceAddrs") String deviceAddrs);

    /**
     * 区域绑定设备
     *
     * @return
     */
    @Post("/api/v2.0/entrance/device/groupBoundDevice")
    Result<?> groupBoundDevice(@Header("token") String token, @JSONBody GroupBoundDeviceRequestVo groupBoundDeviceRequestVo);


}
