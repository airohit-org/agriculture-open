package com.airohit.agriculture.module.infra.dal.dataobject.logger;

import com.airohit.agriculture.framework.common.enums.UserTypeEnum;
import com.airohit.agriculture.framework.mybatis.core.dataobject.BaseDO;
import com.airohit.agriculture.module.infra.enums.logger.ApiErrorLogProcessStatusEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * API 异常数据
 *
 * @author shiminghao
 */
@TableName("infra_api_error_log")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@KeySequence(value = "infra_api_error_log_seq")
public class ApiErrorLogDO extends BaseDO {

    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 链路追踪编号
     * <p>
     * 一般来说，通过链路追踪编号，可以将访问日志，错误日志，链路追踪日志，logger 打印日志等，结合在一起，从而进行排错。
     */
    private String traceId;
    /**
     * 用户类型
     * <p>
     * 枚举 {@link UserTypeEnum}
     */
    private Integer userType;
    /**
     * 应用名
     * <p>
     * 目前读取 spring.application.name
     */
    private String applicationName;

    // ========== 请求相关字段 ==========

    /**
     * 请求方法名
     */
    private String requestMethod;
    /**
     * 访问地址
     */
    private String requestUrl;
    /**
     * 请求参数
     * <p>
     * query: Query String
     * body: Quest Body
     */
    private String requestParams;
    /**
     * 用户 IP
     */
    private String userIp;
    /**
     * 浏览器 UA
     */
    private String userAgent;

    // ========== 异常相关字段 ==========

    /**
     * 异常发生时间
     */
    private LocalDateTime exceptionTime;
    /**
     * 异常名
     * <p>
     * {@link Throwable#getClass()} 的类全名
     */
    private String exceptionName;
    /**
     * 异常导致的消息
     * <p>
     * {@link cn.hutool.core.exceptions.ExceptionUtil#getMessage(Throwable)}
     */
    private String exceptionMessage;
    /**
     * 异常导致的根消息
     * <p>
     * {@link cn.hutool.core.exceptions.ExceptionUtil#getRootCauseMessage(Throwable)}
     */
    private String exceptionRootCauseMessage;
    /**
     * 异常的栈轨迹
     * <p>
     * {@link org.apache.commons.lang3.exception.ExceptionUtils#getStackTrace(Throwable)}
     */
    private String exceptionStackTrace;
    /**
     * 异常发生的类全名
     * <p>
     * {@link StackTraceElement#getClassName()}
     */
    private String exceptionClassName;
    /**
     * 异常发生的类文件
     * <p>
     * {@link StackTraceElement#getFileName()}
     */
    private String exceptionFileName;
    /**
     * 异常发生的方法名
     * <p>
     * {@link StackTraceElement#getMethodName()}
     */
    private String exceptionMethodName;
    /**
     * 异常发生的方法所在行
     * <p>
     * {@link StackTraceElement#getLineNumber()}
     */
    private Integer exceptionLineNumber;

    // ========== 处理相关字段 ==========

    /**
     * 处理状态
     * <p>
     * 枚举 {@link ApiErrorLogProcessStatusEnum}
     */
    private Integer processStatus;
    /**
     * 处理时间
     */
    private LocalDateTime processTime;
    /**
     * 处理用户编号
     * <p>
     * 关联 com.airohit.agriculture.adminserver.modules.system.dal.dataobject.user.SysUserDO.SysUserDO#getId()
     */
    private Long processUserId;

}
