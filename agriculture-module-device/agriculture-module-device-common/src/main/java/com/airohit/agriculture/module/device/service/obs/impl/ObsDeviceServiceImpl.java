package com.airohit.agriculture.module.device.service.obs.impl;

import ch.ethz.ssh2.Connection;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.airohit.agriculture.framework.common.exception.ErrorCode;
import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.framework.redis.core.RedisService;
import com.airohit.agriculture.framework.tenant.core.aop.FarmTenantIgnore;
import com.airohit.agriculture.framework.tenant.core.aop.TenantIgnore;
import com.airohit.agriculture.module.device.client.BeijingTH.BeijingTHDeviceClient;
import com.airohit.agriculture.module.device.client.obs.UserInfoClient;
import com.airohit.agriculture.module.device.convert.obs.ObsDeviceConvert;
import com.airohit.agriculture.module.device.convert.obs.ObsDeviceInfoConvert;
import com.airohit.agriculture.module.device.dal.dataobject.obs.ObsDeviceDO;
import com.airohit.agriculture.module.device.dal.dataobject.obs.ObsDeviceFirmDO;
import com.airohit.agriculture.module.device.dal.dataobject.obs.ObsDeviceInfoDO;
import com.airohit.agriculture.module.device.dal.dataobject.obs.ObsSystemFirmDO;
import com.airohit.agriculture.module.device.dal.mysql.obs.ObsDeviceFirmMapper;
import com.airohit.agriculture.module.device.dal.mysql.obs.ObsDeviceInfoMapper;
import com.airohit.agriculture.module.device.dal.mysql.obs.ObsDeviceMapper;
import com.airohit.agriculture.module.device.dal.mysql.obs.ObsSystemFirmMapper;
import com.airohit.agriculture.module.device.enums.ObsDeviceNodeTypeConstants;
import com.airohit.agriculture.module.device.enums.obs.ObsDeviceAlarmStatusEnum;
import com.airohit.agriculture.module.device.enums.obs.ObsDeviceNodeStauts2Enum;
import com.airohit.agriculture.module.device.enums.obs.ObsDeviceNodeStauts3Enum;
import com.airohit.agriculture.module.device.enums.obs.ObsDeviceNodeStautsEnum;
import com.airohit.agriculture.module.device.obs.*;
import com.airohit.agriculture.module.device.service.obs.ObsDeviceService;
import com.airohit.agriculture.module.device.utils.AliYunRevokeSecurityGroup;
import com.airohit.agriculture.module.device.utils.SshUtils;
import com.airohit.agriculture.module.device.vo.farmyun.*;
import com.airohit.agriculture.module.device.vo.farmyun.userinfo.DeviceListResponseVo;
import com.airohit.agriculture.module.device.vo.farmyun.userinfo.DeviceRealTimeDataResponseVo;
import com.airohit.agriculture.module.device.vo.farmyun.userinfo.UserLoginRequestVo;
import com.airohit.agriculture.module.device.vo.farmyun.userinfo.UserLoginResponseVo;
import com.airohit.agriculture.module.device.vo.obs.*;
import com.airohit.agriculture.module.device.vo.obs.beijingTH.*;
import com.airohit.agriculture.module.infra.api.file.FileApi;
import com.airohit.agriculture.module.land.dal.dataobject.land.LandDO;
import com.airohit.agriculture.module.land.service.land.LandService;
import com.airohit.agriculture.module.system.dal.dataobject.dict.DictDataDO;
import com.airohit.agriculture.module.system.dal.dataobject.farm.FarmDO;
import com.airohit.agriculture.module.system.service.dict.DictDataService;
import com.airohit.agriculture.module.system.service.farm.SystemFarmService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.device.constants.ObsDeviceConstants.ObsDeviceStatus.OFFLINE;
import static com.airohit.agriculture.module.device.constants.ObsDeviceConstants.ObsDeviceStatus.ONLINE;
import static com.airohit.agriculture.module.device.enums.ErrorCodeConstants.DEVICE_PORT_TOP;
import static com.airohit.agriculture.module.device.enums.ErrorCodeConstants.INFO_NOT_EXISTS;

/**
 * @Description:
 * @return:
 * @Author: hanliyao
 * @Date: 2023/7/13 14:58
 */
@Service
@Slf4j
public class ObsDeviceServiceImpl implements ObsDeviceService {

    private static final Integer DEVICE_PORT_NUMBER = 29000;
    private static final String DEVICE_PORT = "device_port";
    private static final String DEVICE_STATUS_EXPIRE = "device_status_expire:";
    private static final String DEVICE_BJTH_AUTHORIZATION = "device_bjth_authorization:";
    @Resource
    private RedisService redisService;
    @Value("${device-ssh.service-ip}")
    private String serviceIp;
    @Value("${device-ssh.service-username}")
    private String serviceUsername;
    @Value("${device-ssh.service-password}")
    private String servicePassword;
    @Resource
    private ObsDeviceMapper obsDeviceMapper;
    @Resource
    private ObsDeviceInfoMapper obsDeviceInfoMapper;
    @Resource
    @Lazy
    private ObsDeviceService obsDeviceService;
    @Resource
    private ObsSystemFirmMapper obsSystemFirmMapper;
    @Resource
    private ObsDeviceFirmMapper obsDeviceFirmMapper;
    @Resource
    private SystemFarmService systemFarmService;
    @Resource
    private LandService landService;
    @Resource
    private UserInfoClient userInfoClient;
    @Resource
    private BeijingTHDeviceClient beijingTHDeviceClient;
    @Resource
    private DictDataService dictDataService;
    @Resource
    private FileApi fileApi;

    private static void sporeNode(List<ObsDeviceNode> nodeList, JSONObject jsonObject) {
        DeviceSporeRealTimeVo deviceSporeRealTimeVo = jsonObject.toJavaObject(DeviceSporeRealTimeVo.class);
        String zbdMotorPulse = deviceSporeRealTimeVo.getZBDMotorPulse();
        addNode(nodeList, zbdMotorPulse, "载玻带单次运动距离的脉冲数");

        String motorPulse = deviceSporeRealTimeVo.getMotorPulse();
        addNode(nodeList, motorPulse, "Y轴电机脉冲范围（最小值，最大值）");

        String pulseTotal = deviceSporeRealTimeVo.getPulseTotal();
        addNode(nodeList, pulseTotal, "采集的累计脉冲数");

        String yMotorPulse = deviceSporeRealTimeVo.getYMotorPulse();
        addNode(nodeList, yMotorPulse, "Y轴电机脉冲数（0-55000）");

        String pulseCurrent = deviceSporeRealTimeVo.getPulseCurrent();
        addNode(nodeList, pulseCurrent, "采集的单次脉冲数");

        String altitude = deviceSporeRealTimeVo.getAltitude();
        addNode(nodeList, altitude, "海拔高度");

        String blowVentStatus = deviceSporeRealTimeVo.getBlowVentStatus();
        addNodeDevice(nodeList, blowVentStatus, "排气口状态");

        String samplingPortStatus = deviceSporeRealTimeVo.getSamplingPortStatus();
        addNodeDevice(nodeList, samplingPortStatus, "采样口状态");

        String admissionPortStatus = deviceSporeRealTimeVo.getAdmissionPortStatus();
        addNodeDevice(nodeList, admissionPortStatus, "吸气口状态");

        String iamphouseStatus = deviceSporeRealTimeVo.getIamphouseStatus();
        addNodeDevice(nodeList, iamphouseStatus, "光源状态");

        String zbdMotorStatus = deviceSporeRealTimeVo.getZBDMotorStatus();
        addNode2Device(nodeList, zbdMotorStatus, "载玻带电机状态");

        String yMotorStatus = deviceSporeRealTimeVo.getYMotorStatus();
        addNode2Device(nodeList, yMotorStatus, "Y轴电机状态");

        String yMotorDirection = deviceSporeRealTimeVo.getYMotorDirection();
        addNode3Device(nodeList, yMotorDirection, "Y轴电机方向");
    }

    private static void metOrSoilNode(List<ObsDeviceNode> nodeList, JSONObject jsonObject) {
        ObsDeviceNode obsDeviceNode = new ObsDeviceNode();
        DeviceMetRealTimeVo deviceMetRealTimeVo = jsonObject.toJavaObject(DeviceMetRealTimeVo.class);
        int nodeType = deviceMetRealTimeVo.getNodeType();
        if (nodeType == ObsDeviceNodeTypeConstants.ONE) {
            ObsDeviceNode temObsDeviceNode = new ObsDeviceNode();
            addNode(deviceMetRealTimeVo.getTemName(), deviceMetRealTimeVo.getTemUnit(), deviceMetRealTimeVo.getTemValueStr(), deviceMetRealTimeVo.getTemAlarmStatus(), temObsDeviceNode);
            nodeList.add(temObsDeviceNode);
            addNode(deviceMetRealTimeVo.getHumName(), deviceMetRealTimeVo.getHumUnit(), deviceMetRealTimeVo.getHumValueStr(), deviceMetRealTimeVo.getHumAlarmStatus(), obsDeviceNode);
        } else if (nodeType == ObsDeviceNodeTypeConstants.THREE) {
            addNode(deviceMetRealTimeVo.getHumName(), deviceMetRealTimeVo.getHumUnit(), deviceMetRealTimeVo.getHumValueStr(), deviceMetRealTimeVo.getHumAlarmStatus(), obsDeviceNode);
        } else if (nodeType == ObsDeviceNodeTypeConstants.FIVE) {
            String nodeName = deviceMetRealTimeVo.getNodeName();
            String value = deviceMetRealTimeVo.getTemValueStr();
            String temStatus = ObsDeviceAlarmStatusEnum.getByCode(deviceMetRealTimeVo.getTemAlarmStatus()).getExplain();
            obsDeviceNode.setName(nodeName);
            obsDeviceNode.setValue(value);
            obsDeviceNode.setNodeStatus(temStatus);
        } else {
            addNode(deviceMetRealTimeVo.getTemName(), deviceMetRealTimeVo.getTemUnit(), deviceMetRealTimeVo.getTemValueStr(), deviceMetRealTimeVo.getTemAlarmStatus(), obsDeviceNode);
        }
        nodeList.add(obsDeviceNode);
    }

    private static void irrigation2Node(List<ObsDeviceNode> nodeList, JSONObject jsonObject) {
        ObsDeviceNode obsDeviceNode = new ObsDeviceNode();
        DeviceIrrigationTwoRealTimeVo deviceIrrigationTwoRealTimeVo = jsonObject.toJavaObject(DeviceIrrigationTwoRealTimeVo.class);
        int nodeType = deviceIrrigationTwoRealTimeVo.getNodeType();
        if (nodeType == ObsDeviceNodeTypeConstants.ONE) {
            ObsDeviceNode temObsDeviceNode = new ObsDeviceNode();
            addNode(deviceIrrigationTwoRealTimeVo.getTemName(), deviceIrrigationTwoRealTimeVo.getTemUnit(), deviceIrrigationTwoRealTimeVo.getTemValueStr(), deviceIrrigationTwoRealTimeVo.getTemAlarmStatus(), temObsDeviceNode);
            nodeList.add(temObsDeviceNode);
            addNode(deviceIrrigationTwoRealTimeVo.getHumName(), deviceIrrigationTwoRealTimeVo.getHumUnit(), deviceIrrigationTwoRealTimeVo.getHumValueStr(), deviceIrrigationTwoRealTimeVo.getHumAlarmStatus(), obsDeviceNode);
        } else if (nodeType == ObsDeviceNodeTypeConstants.THREE) {
            addNode(deviceIrrigationTwoRealTimeVo.getHumName(), deviceIrrigationTwoRealTimeVo.getHumUnit(), deviceIrrigationTwoRealTimeVo.getHumValueStr(), deviceIrrigationTwoRealTimeVo.getHumAlarmStatus(), obsDeviceNode);
        } else if (nodeType == ObsDeviceNodeTypeConstants.FIVE) {
            String nodeName = deviceIrrigationTwoRealTimeVo.getNodeName();
            String value = deviceIrrigationTwoRealTimeVo.getTemValueStr();
            if (StrUtil.isEmpty(value)) {
                value = ObsDeviceNodeStautsEnum.getByCode(deviceIrrigationTwoRealTimeVo.getValveStatus()).getExplain();
            }
            String temStatus = ObsDeviceAlarmStatusEnum.getByCode(deviceIrrigationTwoRealTimeVo.getTemAlarmStatus()).getExplain();
            obsDeviceNode.setName(nodeName);
            obsDeviceNode.setValue(value);
            obsDeviceNode.setNodeStatus(temStatus);
        } else {
            addNode(deviceIrrigationTwoRealTimeVo.getTemName(), deviceIrrigationTwoRealTimeVo.getTemUnit(), deviceIrrigationTwoRealTimeVo.getTemValueStr(), deviceIrrigationTwoRealTimeVo.getTemAlarmStatus(), obsDeviceNode);
        }
        nodeList.add(obsDeviceNode);
    }

    private static void wormFlagshipOrWormNode(List<ObsDeviceNode> nodeList, JSONObject jsonObject) {
        DeviceWormRealTimeVo deviceWormRealTimeVo = jsonObject.toJavaObject(DeviceWormRealTimeVo.class);
        String rain = deviceWormRealTimeVo.getRain();
        if (StrUtil.isNotEmpty(rain)) {
            ObsDeviceNode obsDeviceNode = new ObsDeviceNode();
            obsDeviceNode.setName("降雨状态");
            if (StrUtil.equals("1", rain)) {
                obsDeviceNode.setValue("有雨");
            } else {
                obsDeviceNode.setValue("无雨");
            }
            nodeList.add(obsDeviceNode);
        }

        String insecticideTem = deviceWormRealTimeVo.getInsecticideTem();
        addNode(nodeList, insecticideTem, "杀虫仓温度");

        String illum = deviceWormRealTimeVo.getIllum();
        addNode(nodeList, illum, "光照度");

        String dryingTem = deviceWormRealTimeVo.getDryingTem();
        addNode(nodeList, dryingTem, "烘干仓温度");

        String wormFlap = deviceWormRealTimeVo.getWormFlap();
        addNodeDevice(nodeList, wormFlap, "杀虫挡板");

        String shake = deviceWormRealTimeVo.getShake();
        addNodeDevice(nodeList, shake, "震动装置");

        String dryingFlap = deviceWormRealTimeVo.getDryingFlap();
        addNodeDevice(nodeList, dryingFlap, "烘干挡板");

        String insecticide = deviceWormRealTimeVo.getInsecticide();
        addNodeDevice(nodeList, insecticide, "杀虫控制");

        String moveWorm = deviceWormRealTimeVo.getMoveWorm();
        addNodeDevice(nodeList, moveWorm, "移虫挡板");

        String drying = deviceWormRealTimeVo.getDrying();
        addNodeDevice(nodeList, drying, "烘干控制");

        String rainFlap = deviceWormRealTimeVo.getRainFlap();
        addNodeDevice(nodeList, rainFlap, "虫雨挡板");

        String attractWorm = deviceWormRealTimeVo.getAttractWorm();
        addNodeDevice(nodeList, attractWorm, "诱虫灯状态");

        String fillLight = deviceWormRealTimeVo.getFillLight();
        addNodeDevice(nodeList, fillLight, "补光灯");
    }

    private static void addNode(String deviceMetRealTimeVo, String deviceMetRealTimeVo1, String
            deviceMetRealTimeVo2, int deviceMetRealTimeVo3, ObsDeviceNode obsDeviceNode) {
        String humStatus = ObsDeviceAlarmStatusEnum.getByCode(deviceMetRealTimeVo3).getExplain();
        obsDeviceNode.setName(deviceMetRealTimeVo);
        obsDeviceNode.setUnit(deviceMetRealTimeVo1);
        obsDeviceNode.setValue(deviceMetRealTimeVo2);
        obsDeviceNode.setNodeStatus(humStatus);
    }

    private static void addNode(List<ObsDeviceNode> nodeList, String code, String name) {
        if (StrUtil.isNotEmpty(code)) {
            ObsDeviceNode obsDeviceNode = new ObsDeviceNode();
            obsDeviceNode.setName(name);
            obsDeviceNode.setValue(code);
            nodeList.add(obsDeviceNode);
        }
    }

    private static void addNodeDevice(List<ObsDeviceNode> nodeList, String code, String name) {
        if (StrUtil.isNotEmpty(code)) {
            ObsDeviceNode obsDeviceNode = new ObsDeviceNode();
            obsDeviceNode.setName(name);
            obsDeviceNode.setValue(ObsDeviceNodeStautsEnum.getByCode(code).getExplain());
            nodeList.add(obsDeviceNode);
        }
    }

    private static void addNode2Device(List<ObsDeviceNode> nodeList, String code, String name) {
        if (StrUtil.isNotEmpty(code)) {
            ObsDeviceNode obsDeviceNode = new ObsDeviceNode();
            obsDeviceNode.setName(name);
            obsDeviceNode.setValue(ObsDeviceNodeStauts2Enum.getByCode(code).getExplain());
            nodeList.add(obsDeviceNode);
        }
    }

    private static void addNode3Device(List<ObsDeviceNode> nodeList, String code, String name) {
        if (StrUtil.isNotEmpty(code)) {
            ObsDeviceNode obsDeviceNode = new ObsDeviceNode();
            obsDeviceNode.setName(name);
            obsDeviceNode.setValue(ObsDeviceNodeStauts3Enum.getByCode(code).getExplain());
            nodeList.add(obsDeviceNode);
        }
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public void createJuYingService(ObsDeviceClaimDto obsDeviceClaimDto) {
        Connection connection = SshUtils.login(serviceIp, serviceUsername, servicePassword);
        SshUtils.execmd(connection, "sh /home/modbus/jydz/modbus_tcp_port/shell_test.sh " + obsDeviceClaimDto.getDeviceServicePort());
    }

    private void setPort(Integer port, ObsDeviceIpVo obsDeviceIpVo) {
        Connection connection = SshUtils.login(serviceIp, serviceUsername, servicePassword);
        String lsofExecmd = SshUtils.execmd(connection, "lsof -i:" + port);
        if (StringUtil.isNotBlank(lsofExecmd)) {
            port = redisService.getCacheObject(DEVICE_PORT);

            if (port > 30000) {
                throw exception(DEVICE_PORT_TOP);
            }

            Integer newPort = port + 1;
            redisService.setCacheObject(DEVICE_PORT, newPort);

            setPort(port, obsDeviceIpVo);
        } else {
            obsDeviceIpVo.setDeviceServiceIp(serviceIp);
            obsDeviceIpVo.setDeviceServicePort(port);
            String template = "请编辑短信：*JY#11#1#deviceServiceIp#deviceServicePort#10000#300#0#31#\n" +
                    "            发送到设备sim卡中，用于激活设备";
            obsDeviceIpVo.setDeviceServiceTemplate(template);
        }
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public void juYingKillPort(Integer port) {
        Connection connection = SshUtils.login(serviceIp, serviceUsername, servicePassword);
        String lsofExecmd = SshUtils.execmd(connection, "lsof -i:" + port);
        if (StringUtil.isNotBlank(lsofExecmd)) {
            //调用服务器shell脚本
            SshUtils.execmd(connection, "sh /home/modbus/jydz/modbus_tcp_port/shell_kill_port.sh " + port);

            //调用阿里云安全组api
            AliYunRevokeSecurityGroup.revokeSecurityGroup(String.valueOf(port));
        }
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public ObsDeviceIpVo getIpAndPort() {
        ObsDeviceIpVo obsDeviceIpVo = new ObsDeviceIpVo();
        Integer port;
        if (!redisService.hasKey(DEVICE_PORT)) {
            redisService.setCacheObject(DEVICE_PORT, DEVICE_PORT_NUMBER);
        }

        port = redisService.getCacheObject(DEVICE_PORT);

        Integer newPort = port + 1;
        redisService.setCacheObject(DEVICE_PORT, newPort);
        setPort(port, obsDeviceIpVo);

        return obsDeviceIpVo;
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public PageResult<ObsDeviceVo> getInfoPage(ObsDevicePageDto obsDevicePageDto) {
        List<Integer> farmIdList = new ArrayList<>();
        PageResult<ObsDeviceVo> pageResult = new PageResult<>();
        if (ObjectUtil.isNotEmpty(obsDevicePageDto.getFarmId())) {
            FarmDO farmDO = systemFarmService.get(obsDevicePageDto.getFarmId());
            if (ObjectUtil.isEmpty(farmDO)) {
                return pageResult;
            }
            farmIdList.add(farmDO.getId());
        }
        List<Integer> firmIdList = new ArrayList<>();
        if (StrUtil.isNotEmpty(obsDevicePageDto.getFirmName())) {
            List<ObsSystemFirmDO> obsSystemFirmDOS = obsSystemFirmMapper.selectList(new LambdaQueryWrapperX<ObsSystemFirmDO>()
                    .likeIfPresent(ObsSystemFirmDO::getFirmName, obsDevicePageDto.getFirmName()));
            if (CollUtil.isNotEmpty(obsSystemFirmDOS)) {
                firmIdList = CollUtil.getFieldValues(obsSystemFirmDOS, "id", Integer.class);
            } else {
                return pageResult;
            }
        }
        LambdaQueryWrapperX<ObsDeviceDO> wrapperX = new LambdaQueryWrapperX<>();
        String deviceType = obsDevicePageDto.getDeviceType();
        if (StrUtil.isNotEmpty(deviceType)) {
            List<String> split = StrUtil.split(deviceType, ',');
            wrapperX.and(paper -> {
                for (int i = 0; i < split.size(); i++) {
                    if (i == 0) {
                        paper.eq(ObsDeviceDO::getDeviceType, split.get(i));
                    } else {
                        paper.or().eq(ObsDeviceDO::getDeviceType, split.get(i));
                    }
                }
            });
        }
        PageResult<ObsDeviceDO> obsDeviceDOPageResult = obsDeviceMapper.selectPage(obsDevicePageDto, wrapperX
                .eqIfPresent(ObsDeviceDO::getStatus, obsDevicePageDto.getStatus())
                .likeIfPresent(ObsDeviceDO::getDeviceName, obsDevicePageDto.getDeviceName())
                .inIfPresent(ObsDeviceDO::getFarmId, farmIdList)
                .inIfPresent(ObsDeviceDO::getFirmId, firmIdList)
                .betweenIfPresent(BaseDO::getCreateTime, obsDevicePageDto.getCreateTime()).orderByDesc(BaseDO::getCreateTime));

        if (CollUtil.isNotEmpty(obsDeviceDOPageResult.getList())) {
            List<ObsDeviceVo> voList = new ArrayList<>();
            obsDeviceDOPageResult.getList().forEach(obsDeviceDO -> {
                ObsDeviceVo obsDeviceVo = new ObsDeviceVo();
                BeanUtil.copyProperties(obsDeviceDO, obsDeviceVo);

                if (ObjectUtil.isNotEmpty(obsDeviceDO.getFarmId())) {
                    FarmDO farmDO = systemFarmService.get(obsDeviceDO.getFarmId());
                    if (ObjectUtil.isNotEmpty(farmDO)) {
                        obsDeviceVo.setFarmName(farmDO.getFarmName());
                    }
                }
                ObsSystemFirmDO obsSystemFirmDO = obsSystemFirmMapper.selectById(obsDeviceDO.getFirmId());
                if (ObjectUtil.isNotEmpty(obsSystemFirmDO)) {
                    obsDeviceVo.setDeviceFirm(obsSystemFirmDO.getFirmName());
                }

                if (ObjectUtil.isNotEmpty(obsDeviceVo.getLandId())) {
                    LandDO result = landService.get(obsDeviceVo.getLandId());
                    if (StrUtil.isNotEmpty(result.getLandName()))
                        obsDeviceVo.setLandName(result.getLandName());
                }

                Boolean hasKey = redisService.hasKey(DEVICE_STATUS_EXPIRE + obsDeviceVo.getDeviceAddr());
                if (hasKey && obsDeviceVo.getStatus().equals(OFFLINE)) {
                    obsDeviceVo.setStatus(ONLINE);
                    obsDeviceDO.setStatus(ONLINE);
                    obsDeviceMapper.updateById(obsDeviceDO);
                } else if (!hasKey && obsDeviceVo.getStatus().equals(ONLINE)) {
                    obsDeviceVo.setStatus(OFFLINE);
                    obsDeviceDO.setStatus(OFFLINE);
                    obsDeviceMapper.updateById(obsDeviceDO);
                }

                voList.add(obsDeviceVo);
            });
            pageResult.setList(voList);
            pageResult.setTotal(obsDeviceDOPageResult.getTotal());
        }
        return pageResult;
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public PageResult<ObsDeviceInfoVo> getRealTimeDataPage(ObsDeviceInfoPageDto obsDeviceInfoPageDto) {
        PageResult<ObsDeviceInfoDO> obsDeviceInfoDOPageResult = obsDeviceInfoMapper.selectPage(obsDeviceInfoPageDto, new LambdaQueryWrapperX<ObsDeviceInfoDO>().eq(ObsDeviceInfoDO::getDeviceId, obsDeviceInfoPageDto.getDeviceId())
                .eq(ObsDeviceInfoDO::getType, "data")
                .orderByDesc(ObsDeviceInfoDO::getCreateTime));
        PageResult<ObsDeviceInfoVo> result = ObsDeviceInfoConvert.INSTANCE.convertPage(obsDeviceInfoDOPageResult);
        if (CollUtil.isNotEmpty(result.getList()))
            result.getList().forEach(obsDeviceInfoVo -> {
                if (CollUtil.isNotEmpty(obsDeviceInfoVo.getData())) {
                    obsDeviceInfoVo.setStatus("online");
                } else {
                    obsDeviceInfoVo.setStatus("offline");
                }
            });
        return result;
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public ObsDeviceVo getOne(Integer id) {
        ObsDeviceVo obsDeviceVo = new ObsDeviceVo();

        ObsDeviceDO obsDeviceDO = obsDeviceMapper.selectById(id);
        if (ObjectUtil.isEmpty(obsDeviceDO)) {
            throw exception(INFO_NOT_EXISTS);
        }
        BeanUtil.copyProperties(obsDeviceDO, obsDeviceVo);
        if (ObjectUtil.isNotEmpty(obsDeviceDO.getFarmId())) {
            FarmDO farmDO = systemFarmService.get(obsDeviceDO.getFarmId());
            if (ObjectUtil.isNotEmpty(farmDO)) {
                obsDeviceVo.setFarmName(farmDO.getFarmName());
            }
        }

        ObsSystemFirmDO obsSystemFirmDO = obsSystemFirmMapper.selectById(obsDeviceDO.getFirmId());
        if (ObjectUtil.isNotEmpty(obsSystemFirmDO)) {
            obsDeviceVo.setDeviceFirm(obsSystemFirmDO.getFirmName());
        }
        return obsDeviceVo;
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public void deleteByFarmFirmId(Integer id) {
        obsDeviceMapper.delete(new LambdaQueryWrapperX<ObsDeviceDO>().eq(ObsDeviceDO::getFarmFirmId, id));
    }

    private void setName(ObsDeviceVo obsDeviceVo) {
        ObsSystemFirmDO obsSystemFirmDO = obsSystemFirmMapper.selectById(obsDeviceVo.getFirmId());
        if (ObjectUtil.isNotEmpty(obsSystemFirmDO)) {
            obsDeviceVo.setDeviceFirm(obsSystemFirmDO.getFirmName());
        }

        if (ObjectUtil.isNotEmpty(obsDeviceVo.getLandId())) {
            LandDO result = landService.get(obsDeviceVo.getLandId());
            if (StrUtil.isNotEmpty(result.getLandName()))
                obsDeviceVo.setLandName(result.getLandName());
        }

        Boolean hasKey = redisService.hasKey(DEVICE_STATUS_EXPIRE + obsDeviceVo.getDeviceAddr());
        ObsDeviceDO obsDeviceDO = new ObsDeviceDO();
        if (hasKey && obsDeviceVo.getStatus().equals(OFFLINE)) {
            obsDeviceVo.setStatus(ONLINE);
            BeanUtil.copyProperties(obsDeviceVo, obsDeviceDO);
            obsDeviceMapper.updateById(obsDeviceDO);
        } else if (!hasKey && obsDeviceVo.getStatus().equals(ONLINE)) {
            obsDeviceVo.setStatus(OFFLINE);
            BeanUtil.copyProperties(obsDeviceVo, obsDeviceDO);
            obsDeviceMapper.updateById(obsDeviceDO);
        }
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public Map<Integer, String> getFirm(Integer farmId) {
        Map<Integer, String> firmMap = new HashMap<>();
        List<ObsDeviceFirmDO> firmDOS = obsDeviceFirmMapper.selectList(new LambdaQueryWrapperX<ObsDeviceFirmDO>().eq(ObsDeviceFirmDO::getFarmId, farmId));
        if (CollUtil.isEmpty(firmDOS)) {
            return firmMap;
        }
        List<Integer> firmIdList = CollUtil.getFieldValues(firmDOS, "firmId", Integer.class);
        List<ObsSystemFirmDO> obsSystemFirmDOS = obsSystemFirmMapper.selectBatchIds(firmIdList);
        obsSystemFirmDOS.forEach(obsSystemFirmDO -> {
            firmMap.put(obsSystemFirmDO.getId(), obsSystemFirmDO.getFirmName());
        });
        return firmMap;
    }

    @Override
    @TenantIgnore
    public ObsDeviceVo claimDevice(ObsDeviceClaimDto obsDeviceClaimDto) {
        ObsDeviceDO obsDeviceDO = new ObsDeviceDO();
        BeanUtil.copyProperties(obsDeviceClaimDto, obsDeviceDO);
        obsDeviceDO.setStatus("offline");
        ObsDeviceFirmDO obsDeviceFirmDO = obsDeviceFirmMapper.selectOne(new LambdaQueryWrapperX<ObsDeviceFirmDO>()
                .eq(ObsDeviceFirmDO::getFirmId, obsDeviceClaimDto.getFirmId())
                .eq(ObsDeviceFirmDO::getFarmId, obsDeviceClaimDto.getFarmId()));
        if (ObjectUtil.isNotEmpty(obsDeviceFirmDO)) {
            obsDeviceDO.setFarmFirmId(obsDeviceFirmDO.getId());
        }
        obsDeviceMapper.insert(obsDeviceDO);
        return ObsDeviceConvert.INSTANCE.convert(obsDeviceDO);
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public Boolean updateDevice(ObsDeviceUpdateDto obsDeviceUpdateDto) {
        ObsDeviceDO obsDeviceDO = obsDeviceMapper.selectById(obsDeviceUpdateDto.getId());
        if (ObjectUtil.isEmpty(obsDeviceDO)) {
            throw exception(INFO_NOT_EXISTS);
        }
        obsDeviceDO.setLandId(obsDeviceUpdateDto.getLandId());
        obsDeviceDO.setDevicelng(obsDeviceUpdateDto.getDevicelng());
        obsDeviceDO.setDevicelat(obsDeviceUpdateDto.getDevicelat());
        obsDeviceDO.setRemark(obsDeviceUpdateDto.getRemark());
        obsDeviceDO.setDeviceName(obsDeviceUpdateDto.getDeviceName());
        obsDeviceDO.setDeviceType(obsDeviceUpdateDto.getDeviceType());
        obsDeviceMapper.updateById(obsDeviceDO);
        return true;
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public Boolean deleteDevice(Integer id) {
        obsDeviceMapper.deleteById(id);
        return true;
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public ObsDeviceInfoVo getDeviceInfo(Integer id) {
        ObsDeviceInfoVo obsDeviceInfoVo = new ObsDeviceInfoVo();
        ObsDeviceInfoDO obsDeviceInfoDO = obsDeviceInfoMapper.selectOne(new LambdaQueryWrapper<ObsDeviceInfoDO>()
                .eq(ObsDeviceInfoDO::getDeviceId, id).eq(ObsDeviceInfoDO::getType, "data").orderByDesc(ObsDeviceInfoDO::getCreateTime)
                .last("limit 1"));
        if (ObjectUtil.isEmpty(obsDeviceInfoDO)) {
            return obsDeviceInfoVo;
        }
        BeanUtil.copyProperties(obsDeviceInfoDO, obsDeviceInfoVo);
        return obsDeviceInfoVo;
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public PageResult<ObsDeviceInfoVo> getRealTimeDataPageImage(ObsDeviceInfoPageDto obsDeviceInfoPageDto) {
        PageResult<ObsDeviceInfoDO> obsDeviceInfoDOPageResult = obsDeviceInfoMapper.selectPage(obsDeviceInfoPageDto, new LambdaQueryWrapperX<ObsDeviceInfoDO>().eq(ObsDeviceInfoDO::getDeviceId, obsDeviceInfoPageDto.getDeviceId())
                .eq(ObsDeviceInfoDO::getType, "image")
                .orderByDesc(ObsDeviceInfoDO::getCreateTime));
        PageResult<ObsDeviceInfoVo> result = ObsDeviceInfoConvert.INSTANCE.convertPage(obsDeviceInfoDOPageResult);
        if (CollUtil.isNotEmpty(result.getList()))
            result.getList().forEach(obsDeviceInfoVo -> {
                if (CollUtil.isNotEmpty(obsDeviceInfoVo.getData())) {
                    obsDeviceInfoVo.setStatus("online");
                } else {
                    obsDeviceInfoVo.setStatus("offline");
                }
            });
        return result;
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public List<ObsDeviceInfoVo> queryBeijingTHDeviceInfo(String deviceId, String startAt, String endtAt) {
        List<ObsDeviceInfoDO> obsDeviceInfoDOS = obsDeviceInfoMapper.selectList(new LambdaQueryWrapperX<ObsDeviceInfoDO>()
                .between(ObsDeviceInfoDO::getCreateTime, startAt, endtAt)
                .eq(ObsDeviceInfoDO::getDeviceId, deviceId)
                .eq(ObsDeviceInfoDO::getType, "data")
        );
        return ObsDeviceInfoConvert.INSTANCE.convertList(obsDeviceInfoDOS);
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public ObsDeviceBeijingHTInfoVo queryBeijingTHDeviceInfoByDeviceId(Integer deviceId) {

        ObsDeviceBeijingHTInfoVo obsDeviceBeijingHTInfoVo = new ObsDeviceBeijingHTInfoVo();

        ObsDeviceInfoDO data = obsDeviceInfoMapper.selectOne(new LambdaQueryWrapperX<ObsDeviceInfoDO>()
                .eq(ObsDeviceInfoDO::getDeviceId, deviceId)
                .eq(ObsDeviceInfoDO::getType, "data")
                .orderByDesc(ObsDeviceInfoDO::getCreateTime)
                .last("limit 1"));
        ObsDeviceInfoVo obsDeviceInfoVoData = new ObsDeviceInfoVo();
        BeanUtil.copyProperties(data, obsDeviceInfoVoData);

        ObsDeviceInfoDO dataImage = obsDeviceInfoMapper.selectOne(new LambdaQueryWrapperX<ObsDeviceInfoDO>()
                .eq(ObsDeviceInfoDO::getDeviceId, deviceId)
                .eq(ObsDeviceInfoDO::getType, "image")
                .orderByDesc(ObsDeviceInfoDO::getCreateTime)
                .last("limit 1"));

        ObsDeviceInfoVo obsDeviceInfoVoImage = new ObsDeviceInfoVo();
        BeanUtil.copyProperties(dataImage, obsDeviceInfoVoImage);

        obsDeviceBeijingHTInfoVo.setObsDeviceInfoVoData(obsDeviceInfoVoData);
        obsDeviceBeijingHTInfoVo.setObsDeviceInfoVoImage(obsDeviceInfoVoImage);

        return obsDeviceBeijingHTInfoVo;
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public List<ObsDeviceVo> list(ObsDeviceListDto obsDeviceDto) {
        List<Integer> firmIdList = null;
        List<ObsDeviceVo> obsDeviceVos = new ArrayList<>();
        if (StrUtil.isNotEmpty(obsDeviceDto.getFirmName())) {
            List<ObsSystemFirmDO> obsSystemFirmDOS = obsSystemFirmMapper.selectList(new LambdaQueryWrapperX<ObsSystemFirmDO>()
                    .like(ObsSystemFirmDO::getFirmName, obsDeviceDto.getFirmName()));
            if (CollUtil.isNotEmpty(obsSystemFirmDOS)) {
                firmIdList = CollUtil.getFieldValues(obsSystemFirmDOS, "id", Integer.class);
            } else {
                return obsDeviceVos;
            }
        }
        LambdaQueryWrapperX<ObsDeviceDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(ObsDeviceDO::getFarmId, obsDeviceDto.getFarmId())
                .eqIfPresent(ObsDeviceDO::getDeviceType, obsDeviceDto.getDeviceType())
                .eqIfPresent(ObsDeviceDO::getStatus, obsDeviceDto.getStatus())
                .inIfPresent(ObsDeviceDO::getFirmId, firmIdList)
                .likeIfPresent(ObsDeviceDO::getDeviceName, obsDeviceDto.getDeviceName())
                .betweenIfPresent(BaseDO::getCreateTime, obsDeviceDto.getCreateTime())
                .orderByDesc(BaseDO::getCreateTime);
        obsDeviceVos = ObsDeviceConvert.INSTANCE.convertList(obsDeviceMapper.selectList(wrapperX));
        if (CollUtil.isNotEmpty(obsDeviceVos)) {
            obsDeviceVos.forEach(this::setName);
        }
        return obsDeviceVos;
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public List<DeviceGroupVo> getDeviceGroupVoList(Integer farmId) {
        return obsDeviceMapper.getDeviceGroupVoList(farmId);
    }

    @Override
    public void deleteByFirmId(Integer firmId) {
        obsDeviceMapper.delete(new LambdaQueryWrapperX<ObsDeviceDO>().eq(ObsDeviceDO::getFirmId, firmId));
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public void influxObsDeviceSave(ObsDeviceFirmDO obsDeviceFirmDO) {
        //登录验证
        UserLoginResponseVo userLoginResponseVo = this.getUserLoginResponseVo(obsDeviceFirmDO);
        if (ObjectUtil.isNotEmpty(userLoginResponseVo)) {
            //获取设备基本信息
            JSONObject listResult = userInfoClient.getsysUserDevice(userLoginResponseVo.getToken(), null, null);
            Result resultObject = JSONObject.toJavaObject(listResult, Result.class);
            if (resultObject.getCode() != 1000) {
                return;
            }
            Object resultData = resultObject.getData();
            List<DeviceListResponseVo> responseDataList = JSON.parseArray(JSONObject.toJSONString(resultData), DeviceListResponseVo.class);
            Instant instantTimeNow = Instant.now();
            long time = instantTimeNow.getEpochSecond();
            String requestTime = StrUtil.toString(instantTimeNow.plus(8, ChronoUnit.HOURS));

            for (DeviceListResponseVo deviceListResponseVo : responseDataList) {
                Map<String, Object> map = BeanUtil.beanToMap(deviceListResponseVo);
                String deviceAddr = deviceListResponseVo.getDeviceAddr();

                //继续处理数据
                ObsDeviceDO obsDeviceDO = obsDeviceMapper.selectOne(new LambdaQueryWrapper<ObsDeviceDO>().eq(ObsDeviceDO::getDeviceAddr, deviceListResponseVo.getDeviceAddr())
                        .eq(ObsDeviceDO::getFirmId, obsDeviceFirmDO.getFirmId()).eq(ObsDeviceDO::getFarmId, obsDeviceFirmDO.getFarmId()));
                if (ObjectUtil.isNotEmpty(obsDeviceDO)) {
                    BeanUtil.copyProperties(deviceListResponseVo, obsDeviceDO, "id", "devicelng", "devicelat", "deviceName", "deviceType");
                    obsDeviceDO.setUpdateTime(null);
                } else {
                    obsDeviceDO = new ObsDeviceDO();
                    BeanUtil.copyProperties(deviceListResponseVo, obsDeviceDO, "devicelng", "devicelat");
                }

                ObsSystemFirmDO obsSystemFirmDO = obsSystemFirmMapper.selectOne(new LambdaQueryWrapperX<ObsSystemFirmDO>()
                        .eq(ObsSystemFirmDO::getFirmName, "建大仁科"));
                obsDeviceDO.setFirmId(obsSystemFirmDO.getId());
                obsDeviceDO.setFarmId(obsDeviceFirmDO.getFarmId());
                obsDeviceDO.setStatus(OFFLINE);
                obsDeviceDO.setFarmFirmId(obsDeviceFirmDO.getId());
                if (ObjectUtil.isEmpty(obsDeviceDO.getId())) {
                    obsDeviceMapper.insert(obsDeviceDO);
                } else {
                    obsDeviceMapper.updateById(obsDeviceDO);
                }

                JSONObject realTimeData = userInfoClient.getRealTimeData(userLoginResponseVo.getToken(), deviceAddr);
                Result realTimeResult = JSONObject.toJavaObject(realTimeData, Result.class);
                if (realTimeResult.getCode() != 1000) {
                    break;
                }
                Object realTimeDataData = realTimeResult.getData();
                List<DeviceRealTimeDataResponseVo> realTimeDataList = JSON.parseArray(JSONObject.toJSONString(realTimeDataData), DeviceRealTimeDataResponseVo.class);

                //处理数据
                for (DeviceRealTimeDataResponseVo r : realTimeDataList) {
                    List<JSONObject> data = r.getData();
                    //处理设备个节点详细信息
                    ObsDeviceInfoDO obsDeviceInfoDO = new ObsDeviceInfoDO();
                    obsDeviceInfoDO.setDeviceId(obsDeviceDO.getId());
                    List<ObsDeviceNode> nodeList = new ArrayList<>();
                    //取得每个节点的详细信息
                    ObsDeviceDO finalObsDeviceDO = obsDeviceDO;
                    data.forEach(jsonObject -> {
                        switch (finalObsDeviceDO.getDeviceType()) {
                            case "irrigation2":
                                irrigation2Node(nodeList, jsonObject);
                                break;
                            case "irrigation":
                                DeviceIrrigationThreeRealTimeVo deviceIrrigationThreeRealTimeVo = jsonObject.toJavaObject(DeviceIrrigationThreeRealTimeVo.class);
                                //addNode();
                                String factorName = deviceIrrigationThreeRealTimeVo.getFactorName();
                                String unit = deviceIrrigationThreeRealTimeVo.getUnit();
                                String factorStatus = deviceIrrigationThreeRealTimeVo.getFactorStatus();
                                break;
                            case "wormFlagship":
                            case "worm":
                                wormFlagshipOrWormNode(nodeList, jsonObject);
                                break;
                            case "met":
                            case "soil":
                                metOrSoilNode(nodeList, jsonObject);
                                break;
                            case "spore":
                                sporeNode(nodeList, jsonObject);
                                break;
                            default:
                        }
                    });
                    obsDeviceInfoDO.setData(nodeList);
                    obsDeviceInfoDO.setType("data");
                    obsDeviceInfoDO.setCreateTime(LocalDateTime.now());
                    obsDeviceInfoDO.setUpdateTime(LocalDateTime.now());
                    obsDeviceInfoMapper.insert(obsDeviceInfoDO);
                    redisService.setCacheObject(DEVICE_STATUS_EXPIRE + obsDeviceDO.getDeviceAddr(), obsDeviceDO.getDeviceAddr(), 6L, TimeUnit.MINUTES);
                }
            }
        }
    }

    public UserLoginResponseVo getUserLoginResponseVo(ObsDeviceFirmDO obsDeviceFirmDO) {
        UserLoginResponseVo userLoginResponseVo = null;
        if (ObjectUtil.isNotEmpty(obsDeviceFirmDO)) {
            UserLoginRequestVo userLoginRequestVo = new UserLoginRequestVo();
            userLoginRequestVo.setLoginName(obsDeviceFirmDO.getLoginName());
            userLoginRequestVo.setLoginPwd(obsDeviceFirmDO.getLoginPwd());
/*            userLoginRequestVo.setLoginName("cq230329gydx");
            userLoginRequestVo.setLoginPwd("cq230329gydx");*/
            String redisKey = "DeviceUserLoginResponseVo:" + obsDeviceFirmDO.getId() + ":" + obsDeviceFirmDO.getLoginName();
            if (redisService.hasKey(redisKey)) {
                userLoginResponseVo = redisService.getCacheObject(redisKey);
                //userLoginResponseVo = JSONObject.parseObject(json, UserLoginResponseVo.class);
            } else {
                JSONObject userLoginResponseVoResult = userInfoClient.userLogin(userLoginRequestVo);
                Result javaObject = JSONObject.toJavaObject(userLoginResponseVoResult, Result.class);
                if (javaObject.getCode() != 1000) {
                    throw exception(new ErrorCode(500, javaObject.getMessage()));
                }
                Object data = javaObject.getData();
                userLoginResponseVo = JSON.toJavaObject(JSONObject.parseObject(data.toString()), UserLoginResponseVo.class);
                redisService.setCacheObject(redisKey, userLoginResponseVo);
                long time = userLoginResponseVo.getExpDate() - (NumberUtil.div(new BigDecimal(System.currentTimeMillis()), new BigDecimal("1000")).longValue());
                redisService.expire(redisKey, time, TimeUnit.SECONDS);
            }
        }
        return userLoginResponseVo;
    }

    /**
     * 定时获取当前登录用户设备信息,每5分钟执行一次
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    @TenantIgnore
    @Override
    @FarmTenantIgnore
    public void getObsDeviceTask() {
        ObsSystemFirmDO obsSystemFirmDO = obsSystemFirmMapper.selectOne(new LambdaQueryWrapperX<ObsSystemFirmDO>()
                .eq(ObsSystemFirmDO::getFirmName, "建大仁科"));
        List<ObsDeviceFirmDO> obsDeviceFirmDOS = obsDeviceFirmMapper.selectList(new LambdaQueryWrapperX<ObsDeviceFirmDO>()
                .eq(ObsDeviceFirmDO::getFirmId, obsSystemFirmDO.getId()));
        obsDeviceFirmDOS.forEach(obsDeviceFirmDO -> {
            obsDeviceService.influxObsDeviceSave(obsDeviceFirmDO);
        });
/*        try {
            boolean lock = redisLock.tryLock(OBS_DEVICE_INFO_LOCK, TimeUnit.MINUTES, 0L, 4L);
            if (lock) {
                log.info("开始执行观测站设备信息获取定时任务");

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisLock.unlock(OBS_DEVICE_INFO_LOCK);
        }*/
    }

    /**
     * 北京天航设备数据 32分钟一次
     */
    @Scheduled(cron = "0 0/32 * * * ?")
    //@Scheduled(cron = "0 0/1 * * * ?")
    @TenantIgnore
    @FarmTenantIgnore
    @Override
    public void beijingTHDeviceInfo() {
        ObsSystemFirmDO obsSystemFirmDO = obsSystemFirmMapper.selectOne(new LambdaQueryWrapperX<ObsSystemFirmDO>()
                .eq(ObsSystemFirmDO::getFirmName, "北京天航华创科技股份有限公司"));
        List<ObsDeviceDO> obsDeviceDOS = obsDeviceMapper.selectList(new LambdaQueryWrapperX<ObsDeviceDO>().eq(ObsDeviceDO::getFirmId, obsSystemFirmDO.getId()));
        obsDeviceDOS.forEach(obsDeviceDO -> {
            log.info("开始获取北京天航数据:" + obsDeviceDO.getDeviceAddr());
            obsDeviceService.beijingTHDeviceInfoSave(obsDeviceDO);
        });
    }

    @TenantIgnore
    @FarmTenantIgnore
    @Override
    public void beijingTHDeviceInfoSave(ObsDeviceDO obsDeviceDO) {

        ObsDeviceFirmDO obsDeviceFirmDO = obsDeviceFirmMapper.selectById(obsDeviceDO.getFarmFirmId());

        if (ObjectUtil.isNotEmpty(obsDeviceFirmDO)) {

            UserLoginBeijingTHResponseVo userLoginBeijingTHResponseVo = null;
            if (redisService.hasKey(DEVICE_BJTH_AUTHORIZATION)) {
                userLoginBeijingTHResponseVo = redisService.getCacheObject(DEVICE_BJTH_AUTHORIZATION + obsDeviceDO.getFarmId());
            } else {
                UserLoginBeijingTHRequestVo userLoginBeijingTHRequestVo = new UserLoginBeijingTHRequestVo();
                userLoginBeijingTHRequestVo.setClient_id("1");
                userLoginBeijingTHRequestVo.setPassword(obsDeviceFirmDO.getLoginPwd());
                userLoginBeijingTHRequestVo.setGrant_type("password");
                userLoginBeijingTHRequestVo.setUsername(obsDeviceFirmDO.getLoginName());
                userLoginBeijingTHRequestVo.setClient_secret("MpuK1xYUTPq7dDEyJydY1i1TojS4aW1c1iPGDZJg");
                JSONObject jsonObject = beijingTHDeviceClient.userLogin(userLoginBeijingTHRequestVo);
                userLoginBeijingTHResponseVo = JSON.toJavaObject(jsonObject, UserLoginBeijingTHResponseVo.class);

                redisService.setCacheObject(DEVICE_BJTH_AUTHORIZATION + obsDeviceDO.getFarmId(), userLoginBeijingTHResponseVo);
                redisService.expire(DEVICE_BJTH_AUTHORIZATION + obsDeviceDO.getFarmId(), 10l, TimeUnit.HOURS);
            }


            String accessToken = "Bearer " + userLoginBeijingTHResponseVo.getAccess_token();

            log.info("===============================beijingTHDeviceInfoSave accessToken {}", accessToken);

            BeijingTHVo beijingTHDevice = beijingTHDeviceClient.getBeijingTHDevice(accessToken, obsDeviceDO.getDeviceAddr(), DateUtil.today(), DateUtil.formatDate(DateUtil.tomorrow()));
            if (ObjectUtil.isNotEmpty(beijingTHDevice)) {
                List<BeijingTHInfoVo> data = beijingTHDevice.getData();
                for (int i = data.size() - 1; i >= 0; i--) {
                    BeijingTHInfoVo beijingTHInfoVo = data.get(i);
                    if (beijingTHInfoVo.getType().equals("data")) {
                        //判断时间是否一致，一致表示为更新数据，不做存储
                        ObsDeviceInfoDO obsDeviceInfoDO = obsDeviceInfoMapper.selectOne(new LambdaQueryWrapperX<ObsDeviceInfoDO>().eq(ObsDeviceInfoDO::getDeviceId, obsDeviceDO.getId())
                                .eq(ObsDeviceInfoDO::getType, "data").last("limit 1"));
                        if (ObjectUtil.isNotEmpty(obsDeviceInfoDO)) {
                            LocalDateTime createTime = obsDeviceInfoDO.getCreateTime();
                            String ts = beijingTHInfoVo.getTs();
                            String dateTime = DateUtil.formatLocalDateTime(createTime);
                            if (dateTime.equals(ts)) {
                                break;
                            }
                        }
                        obsDeviceInfoDO = new ObsDeviceInfoDO();
                        obsDeviceInfoDO.setDeviceId(obsDeviceDO.getId());
                        Map<String, Map<String, Object>> bean = JSONUtil.toBean(beijingTHInfoVo.getData(), Map.class);
                        List<ObsDeviceNode> obsDeviceNodes = new ArrayList<>();
                        for (String key : bean.keySet()) {
                            Map<String, Object> objectMap = bean.get(key);
                            ObsDeviceNode obsDeviceNode = new ObsDeviceNode();
                            obsDeviceNode.setValue(StrUtil.toString(objectMap.get("value")));
                            //获取映射
                            DictDataDO result = dictDataService.parseDictData("bjthhcsj_type", key);
                            if (ObjectUtil.isNotEmpty(result)) {
                                String value = result.getValue();
                                List<String> split = StrUtil.split(value, ',');
                                obsDeviceNode.setName(split.get(0));
                                if (split.size() > 1) {
                                    obsDeviceNode.setUnit(split.get(1));
                                }
                                obsDeviceNode.setNodeSub(result.getRemark());
                            }
                            obsDeviceNodes.add(obsDeviceNode);
                        }
                        obsDeviceInfoDO.setData(obsDeviceNodes);
                        obsDeviceInfoDO.setType("data");
                        obsDeviceInfoDO.setCreateTime(LocalDateTime.now());
                        obsDeviceInfoDO.setUpdateTime(LocalDateTime.now());
                        obsDeviceInfoMapper.insert(obsDeviceInfoDO);
                        redisService.setCacheObject(DEVICE_STATUS_EXPIRE + obsDeviceDO.getDeviceAddr(), obsDeviceDO.getDeviceAddr(), 33L, TimeUnit.MINUTES);
                        break;
                    }
                }
            }
        }
    }

    @Scheduled(cron = "0 0/30 0/2 * * ?")
    //@Scheduled(cron = "0 0/5 * * * ?")
    @TenantIgnore
    @FarmTenantIgnore
    @Override
    public void beijingTHDeviceImage() {
        ObsSystemFirmDO obsSystemFirmDO = obsSystemFirmMapper.selectOne(new LambdaQueryWrapperX<ObsSystemFirmDO>()
                .eq(ObsSystemFirmDO::getFirmName, "北京天航华创科技股份有限公司"));
        List<ObsDeviceDO> obsDeviceDOS = obsDeviceMapper.selectList(new LambdaQueryWrapperX<ObsDeviceDO>().eq(ObsDeviceDO::getFirmId, obsSystemFirmDO.getId()));
        obsDeviceDOS.forEach(obsDeviceDO -> {
            log.info("开始获取北京天航图片:" + obsDeviceDO.getDeviceAddr());
            obsDeviceService.beijingTHDeviceImageSave(obsDeviceDO);
        });
    }

    @TenantIgnore
    @FarmTenantIgnore
    @Override
    public void beijingTHDeviceImageSave(ObsDeviceDO obsDeviceDO) {

        ObsDeviceFirmDO obsDeviceFirmDO = obsDeviceFirmMapper.selectById(obsDeviceDO.getFarmFirmId());

        if (ObjectUtil.isNotEmpty(obsDeviceFirmDO)) {

            UserLoginBeijingTHResponseVo userLoginBeijingTHResponseVo = null;
            if (redisService.hasKey(DEVICE_BJTH_AUTHORIZATION + obsDeviceDO.getFarmId())) {
                userLoginBeijingTHResponseVo = redisService.getCacheObject(DEVICE_BJTH_AUTHORIZATION + obsDeviceDO.getFarmId());
            } else {

                UserLoginBeijingTHRequestVo userLoginBeijingTHRequestVo = new UserLoginBeijingTHRequestVo();
                userLoginBeijingTHRequestVo.setClient_id("1");
                userLoginBeijingTHRequestVo.setPassword(obsDeviceFirmDO.getLoginPwd());
                userLoginBeijingTHRequestVo.setGrant_type("password");
                userLoginBeijingTHRequestVo.setUsername(obsDeviceFirmDO.getLoginName());
                userLoginBeijingTHRequestVo.setClient_secret("MpuK1xYUTPq7dDEyJydY1i1TojS4aW1c1iPGDZJg");
                JSONObject jsonObject = beijingTHDeviceClient.userLogin(userLoginBeijingTHRequestVo);
                userLoginBeijingTHResponseVo = JSON.toJavaObject(jsonObject, UserLoginBeijingTHResponseVo.class);

                redisService.setCacheObject(DEVICE_BJTH_AUTHORIZATION + obsDeviceDO.getFarmId(), userLoginBeijingTHResponseVo);
                redisService.expire(DEVICE_BJTH_AUTHORIZATION + obsDeviceDO.getFarmId(), 10l, TimeUnit.HOURS);
            }


            String accessToken = "Bearer " + (String) userLoginBeijingTHResponseVo.getAccess_token();

            log.info("===============================beijingTHDeviceImageSave accessToken {}", accessToken);

            BeijingTHVo beijingTHDevice = beijingTHDeviceClient.getBeijingTHDevice(accessToken, obsDeviceDO.getDeviceAddr(), DateUtil.today(), DateUtil.formatDate(DateUtil.tomorrow()));
            if (ObjectUtil.isNotEmpty(beijingTHDevice)) {
                List<BeijingTHInfoVo> data = beijingTHDevice.getData();
                if (CollUtil.isNotEmpty(data)) {
                    List<BeijingTHInfoVo> imageData = new ArrayList<>();
                    boolean isImage = false;
                    for (int i = data.size() - 1; i >= 0; i--) {
                        BeijingTHInfoVo beijingTHInfoVo = data.get(i);
                        if (!isImage && beijingTHInfoVo.getType().equals("image")) {
                            isImage = true;
                        } else if (isImage && beijingTHInfoVo.getType().equals("data")) {
                            break;
                        } else if (!isImage && beijingTHInfoVo.getType().equals("data")) {
                            continue;
                        }

                        //判断时间是否一致，一致表示为更新数据，不做存储
                        ObsDeviceInfoDO obsDeviceInfoDO = obsDeviceInfoMapper.selectOne(new LambdaQueryWrapperX<ObsDeviceInfoDO>().eq(ObsDeviceInfoDO::getDeviceId, obsDeviceDO.getId())
                                .eq(ObsDeviceInfoDO::getType, "image").last("limit 1"));
                        if (ObjectUtil.isNotEmpty(obsDeviceInfoDO))
                            if (DateUtil.formatLocalDateTime(obsDeviceInfoDO.getCreateTime()).equals(beijingTHInfoVo.getTs())) {
                                break;
                            }
                        imageData.add(beijingTHInfoVo);
                    }

                    ObsDeviceInfoDO obsDeviceInfoDO = new ObsDeviceInfoDO();
                    obsDeviceInfoDO.setDeviceId(obsDeviceDO.getId());
                    List<ObsDeviceNode> obsDeviceNodes = new ArrayList<>();
                    if (CollUtil.isNotEmpty(imageData)) {
                        for (BeijingTHInfoVo thInfoVo : imageData) {
                            Map<String, Map<String, Object>> bean = JSONUtil.toBean(thInfoVo.getData(), Map.class);
                            for (String key : bean.keySet()) {
                                Map<String, Object> objectMap = bean.get(key);
                                ObsDeviceNode obsDeviceNode = new ObsDeviceNode();
                                String oosUrl = "https://iot-datas.oss-cn-beijing.aliyuncs.com" + objectMap.get("value");
                                String file = "";

                                try {
                                    //把地址转换成URL对象
                                    URL url = new URL(oosUrl);
                                    //创建http链接
                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                    //设置超时间为3秒
                                    conn.setConnectTimeout(3 * 1000);
                                    //防止屏蔽程序抓取而返回403错误
                                    conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
                                    //得到输入流
                                    InputStream inputStream = conn.getInputStream();
                                    byte[] image = IoUtil.readBytes(inputStream);
                                    CommonResult<String> imageResult = fileApi.beijingTHImage(image);
                                    if (imageResult.getCode() == 0)
                                        file = imageResult.getData();
                                } catch (IOException e) {
                                    log.error("上传图片出错" + e);
                                }

                                obsDeviceNode.setValue(file);
                                //获取映射
                                DictDataDO result = dictDataService.parseDictData("bjthhcsj_type", key);
                                if (ObjectUtil.isNotEmpty(result)) {
                                    String value = result.getValue();
                                    List<String> split = StrUtil.split(value, ',');
                                    obsDeviceNode.setName(split.get(0));
                                    if (split.size() > 1) {
                                        obsDeviceNode.setUnit(split.get(1));
                                    }
                                    obsDeviceNode.setNodeSub(result.getRemark());
                                }
                                obsDeviceNodes.add(obsDeviceNode);
                            }
                        }
                        obsDeviceInfoDO.setData(obsDeviceNodes);
                        obsDeviceInfoDO.setType("image");
                        obsDeviceInfoDO.setCreateTime(LocalDateTime.now());
                        obsDeviceInfoDO.setUpdateTime(LocalDateTime.now());
                        obsDeviceInfoMapper.insert(obsDeviceInfoDO);
                    }
                }
            }
        }
    }

    @Override
    public Map<String, String> getUserClassify() {
        Map<String, String> deviceNameMap = new HashMap<>();
/*        List<ObsDeviceDO> obsDeviceDOList = obsDeviceMapper.selectList(new LambdaQueryWrapper<ObsDeviceDO>().groupBy(ObsDeviceDO::getDeviceName)
                .orderBy(false, true, BaseDO::getCreateTime));
        if (CollUtil.isEmpty(obsDeviceDOList)) {
            return deviceNameMap;
        }
        deviceNameMap = CollUtil.getFieldValues(obsDeviceDOList, "deviceName", String.class);*/
        deviceNameMap.put("worm", "虫情设备普通版");
        deviceNameMap.put("wormFlagship", "虫情设备旗舰版");
        deviceNameMap.put("spore", "孢子设备");
        deviceNameMap.put("met", "气象设备");
        deviceNameMap.put("irrigation", "智慧环控3.0设备");
        deviceNameMap.put("irrigation2", "智慧环控2.0设备");
        deviceNameMap.put("soil", "墒情设备");
        deviceNameMap.put("camera", "摄像头");
        return deviceNameMap;
    }


    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public PageResult<ObsDeviceInfoVo> getInfo(ObsDeviceInfoPageDto obsDeviceInfoPageDto) {
        PageResult<ObsDeviceInfoDO> obsDeviceInfoDOPageResult = obsDeviceInfoMapper.selectPage(obsDeviceInfoPageDto,
                new LambdaQueryWrapperX<ObsDeviceInfoDO>().eq(ObsDeviceInfoDO::getDeviceId, obsDeviceInfoPageDto.getDeviceId()).eq(ObsDeviceInfoDO::getType, "data")
                        .orderByDesc(ObsDeviceInfoDO::getCreateTime));
        return ObsDeviceInfoConvert.INSTANCE.convertPage(obsDeviceInfoDOPageResult);
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public void init(ObsDeviceFirmDO obsDeviceFirmDO) {
        ObsSystemFirmDO obsSystemFirmDO = obsSystemFirmMapper.selectById(obsDeviceFirmDO.getFirmId());
        if (obsSystemFirmDO.getFirmName().equals("建大仁科")) {
            influxObsDeviceSave(obsDeviceFirmDO);
        } else if (obsSystemFirmDO.getFirmName().equals("北京天航华创科技股份有限公司")) {
            initBJTH(obsDeviceFirmDO);
        }
    }


    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public PageResult<ObsDeviceInfoVo> getInfoImage(ObsDeviceInfoPageDto obsDeviceInfoPageDto) {
        PageResult<ObsDeviceInfoDO> obsDeviceInfoDOPageResult = obsDeviceInfoMapper.selectPage(obsDeviceInfoPageDto,
                new LambdaQueryWrapperX<ObsDeviceInfoDO>().eq(ObsDeviceInfoDO::getDeviceId, obsDeviceInfoPageDto.getDeviceId()).eq(ObsDeviceInfoDO::getType, "image")
                        .orderByDesc(ObsDeviceInfoDO::getCreateTime));
        return ObsDeviceInfoConvert.INSTANCE.convertPage(obsDeviceInfoDOPageResult);
    }

    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public void initBJTH(ObsDeviceFirmDO obsDeviceFirmDO) {
        UserLoginBeijingTHResponseVo userLoginBeijingTHResponseVo = null;
        if (redisService.hasKey(DEVICE_BJTH_AUTHORIZATION + obsDeviceFirmDO.getFarmId())) {
            userLoginBeijingTHResponseVo = redisService.getCacheObject(DEVICE_BJTH_AUTHORIZATION + obsDeviceFirmDO.getFarmId());
        } else {

            UserLoginBeijingTHRequestVo userLoginBeijingTHRequestVo = new UserLoginBeijingTHRequestVo();
            userLoginBeijingTHRequestVo.setClient_id("1");
            userLoginBeijingTHRequestVo.setPassword(obsDeviceFirmDO.getLoginPwd());
            userLoginBeijingTHRequestVo.setGrant_type("password");
            userLoginBeijingTHRequestVo.setUsername(obsDeviceFirmDO.getLoginName());
            userLoginBeijingTHRequestVo.setClient_secret("MpuK1xYUTPq7dDEyJydY1i1TojS4aW1c1iPGDZJg");
            JSONObject jsonObject = beijingTHDeviceClient.userLogin(userLoginBeijingTHRequestVo);
            userLoginBeijingTHResponseVo = JSON.toJavaObject(jsonObject, UserLoginBeijingTHResponseVo.class);

            redisService.setCacheObject(DEVICE_BJTH_AUTHORIZATION + obsDeviceFirmDO.getFarmId(), userLoginBeijingTHResponseVo);
            redisService.expire(DEVICE_BJTH_AUTHORIZATION + obsDeviceFirmDO.getFarmId(), 10l, TimeUnit.HOURS);
        }

        String accessToken = userLoginBeijingTHResponseVo.getToken_type() + " " + userLoginBeijingTHResponseVo.getAccess_token();

        JSONObject beijingTHDevices = beijingTHDeviceClient.getBeijingTHDevices(accessToken);

        if (beijingTHDevices != null) {
            JSONArray data = (JSONArray) beijingTHDevices.get("data");
            for (Object object : data) {
                DeviceListBJTHResponseVo deviceListBJTHResponseVo = JSON.toJavaObject((JSON) object, DeviceListBJTHResponseVo.class);
                log.info("==================================== obsDeviceFirmDO {} initBJTH {}", obsDeviceFirmDO, deviceListBJTHResponseVo);

                ObsDeviceDO obsDeviceDO = obsDeviceMapper.selectOne(new LambdaQueryWrapper<ObsDeviceDO>().eq(ObsDeviceDO::getDeviceAddr,
                                deviceListBJTHResponseVo.getId() + "@hotuns")
                        .eq(ObsDeviceDO::getFirmId, obsDeviceFirmDO.getFirmId()).eq(ObsDeviceDO::getFarmId, obsDeviceFirmDO.getFarmId()));

                if (ObjectUtil.isNotEmpty(obsDeviceDO)) {
                    obsDeviceDO.setDeviceName(deviceListBJTHResponseVo.getName());
                    obsDeviceDO.setDeviceIccId(deviceListBJTHResponseVo.getIccid());
                    obsDeviceDO.setUpdateTime(null);

                } else {
                    obsDeviceDO = new ObsDeviceDO();
                    obsDeviceDO.setDeviceName(deviceListBJTHResponseVo.getName());
                    obsDeviceDO.setDeviceIccId(deviceListBJTHResponseVo.getIccid());
                    obsDeviceDO.setDeviceAddr(deviceListBJTHResponseVo.getId() + "@hotuns");
                    obsDeviceDO.setDeviceType("met");
                }

                ObsSystemFirmDO obsSystemFirmDO = obsSystemFirmMapper.selectOne(new LambdaQueryWrapperX<ObsSystemFirmDO>()
                        .eq(ObsSystemFirmDO::getFirmName, "北京天航华创科技股份有限公司"));
                obsDeviceDO.setFirmId(obsSystemFirmDO.getId());
                obsDeviceDO.setFarmId(obsDeviceFirmDO.getFarmId());
                obsDeviceDO.setStatus(OFFLINE);
                obsDeviceDO.setFarmFirmId(obsDeviceFirmDO.getId());


                if (ObjectUtil.isEmpty(obsDeviceDO.getId())) {
                    obsDeviceMapper.insert(obsDeviceDO);
                } else {
                    obsDeviceMapper.updateById(obsDeviceDO);
                }

                redisService.setCacheObject(DEVICE_STATUS_EXPIRE + deviceListBJTHResponseVo.getId() + "@hotuns",
                        deviceListBJTHResponseVo.getId() + "@hotuns", 1L, TimeUnit.HOURS);

            }


        }
        //bjthSave(obsDeviceFirmDO);
    }

    @Async
    @Override
    @TenantIgnore
    @FarmTenantIgnore
    public void bjthSave(ObsDeviceFirmDO obsDeviceFirmDO) {
        List<ObsDeviceDO> obsDeviceDOS = obsDeviceMapper.selectList(new LambdaQueryWrapperX<ObsDeviceDO>().eq(ObsDeviceDO::getFarmFirmId, obsDeviceFirmDO.getId()));
        obsDeviceDOS.forEach(obsDeviceDO -> {
            log.info("开始获取北京天航数据:" + obsDeviceDO.getDeviceAddr());
            obsDeviceService.beijingTHDeviceInfoSave(obsDeviceDO);

            log.info("开始获取北京天航图片:" + obsDeviceDO.getDeviceAddr());
            obsDeviceService.beijingTHDeviceImageSave(obsDeviceDO);
        });
    }

}
