package com.airohit.agriculture.framework.tenant.core.security;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.airohit.agriculture.framework.common.enums.RpcConstants;
import com.airohit.agriculture.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.common.util.servlet.ServletUtils;
import com.airohit.agriculture.framework.security.core.LoginUser;
import com.airohit.agriculture.framework.security.core.util.SecurityFrameworkUtils;
import com.airohit.agriculture.framework.tenant.config.FarmTenantProperties;
import com.airohit.agriculture.framework.tenant.config.TenantProperties;
import com.airohit.agriculture.framework.tenant.core.context.FarmTenantContextHolder;
import com.airohit.agriculture.framework.tenant.core.context.TenantContextHolder;
import com.airohit.agriculture.framework.tenant.core.service.TenantFrameworkService;
import com.airohit.agriculture.framework.web.config.WebProperties;
import com.airohit.agriculture.framework.web.core.filter.ApiRequestFilter;
import com.airohit.agriculture.framework.web.core.handler.GlobalExceptionHandler;
import com.airohit.agriculture.framework.web.core.util.WebFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 多租户 Security Web 过滤器
 * 1. 如果是登陆的用户，校验是否有权限访问该租户，避免越权问题。
 * 2. 如果请求未带租户的编号，检查是否是忽略的 URL，否则也不允许访问。
 * 3. 校验租户是合法，例如说被禁用、到期
 * <p>
 * 校验用户访问的租户，是否是其所在的租户，
 *
 * @author shiminghao
 */
@Slf4j
public class TenantSecurityWebFilter extends ApiRequestFilter {

    private final TenantProperties tenantProperties;

    private final FarmTenantProperties farmTenantProperties;

    private final AntPathMatcher pathMatcher;

    private final GlobalExceptionHandler globalExceptionHandler;
    private final TenantFrameworkService tenantFrameworkService;

    public TenantSecurityWebFilter(TenantProperties tenantProperties,
                                   WebProperties webProperties,
                                   FarmTenantProperties farmTenantProperties,
                                   GlobalExceptionHandler globalExceptionHandler,
                                   TenantFrameworkService tenantFrameworkService) {
        super(webProperties);
        this.tenantProperties = tenantProperties;
        this.farmTenantProperties = farmTenantProperties;
        this.pathMatcher = new AntPathMatcher();
        this.globalExceptionHandler = globalExceptionHandler;
        this.tenantFrameworkService = tenantFrameworkService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return super.shouldNotFilter(request) &&
                !StrUtil.startWithAny(request.getRequestURI(), RpcConstants.RPC_API_PREFIX); // 因为 RPC API 也会透传租户编号
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        Long tenantId = TenantContextHolder.getTenantId();
        boolean isRpcRequest = !WebFrameworkUtils.isRpcRequest(request);
        Long farmTenantId = FarmTenantContextHolder.getFarmTenantId();
        // 1. 登陆的用户，校验是否有权限访问该租户，避免越权问题。
        LoginUser user = SecurityFrameworkUtils.getLoginUser();
        if (user != null) {
            // 如果获取不到租户编号，则尝试使用登陆用户的租户编号
            if (tenantId == null) {
                tenantId = user.getTenantId();
                TenantContextHolder.setTenantId(tenantId);
                // 如果传递了租户编号，则进行比对租户编号，避免越权问题
            } else if (!Objects.equals(user.getTenantId(), TenantContextHolder.getTenantId())
                    && !isRpcRequest) { // Cloud 特殊逻辑：如果是 RPC 请求，就不校验了。主要考虑，一些场景下，会调用 TenantUtils 去切换租户
                log.error("[doFilterInternal][租户({}) User({}/{}) 越权访问租户({}) URL({}/{})]",
                        user.getTenantId(), user.getId(), user.getUserType(),
                        TenantContextHolder.getTenantId(), request.getRequestURI(), request.getMethod());
                ServletUtils.writeJSON(response, CommonResult.error(GlobalErrorCodeConstants.FORBIDDEN.getCode(),
                        "您无权访问该租户的数据"));
                return;
            }
        }

        // 如果非允许忽略租户的 URL，则校验租户是否合法
        if (!isIgnoreUrl(request)) {
            // 2. 如果请求未带租户的编号，不允许访问。
            if (tenantId == null) {
                log.error("[doFilterInternal][URL({}/{}) 未传递租户编号]", request.getRequestURI(), request.getMethod());
                ServletUtils.writeJSON(response, CommonResult.error(GlobalErrorCodeConstants.BAD_REQUEST.getCode(),
                        "租户的请求未传递，请进行排查"));
                return;
            }

            // 3. 校验租户是合法，例如说被禁用、到期
            try {
                tenantFrameworkService.validTenant(tenantId);
            } catch (Throwable ex) {
                CommonResult<?> result = globalExceptionHandler.allExceptionHandler(request, ex);
                ServletUtils.writeJSON(response, result);
                return;
            }
        } else { // 如果是允许忽略租户的 URL，若未传递租户编号，则默认忽略租户编号，避免报错
            if (tenantId == null) {
                TenantContextHolder.setIgnore(true);
            }

        }
        if (!isFarmIgnoreUrl(request)) {
            if (farmTenantId == null) {
                log.error("[doFilterInternal][URL({}/{}) 未传递农场编号]", request.getRequestURI(), request.getMethod());
                ServletUtils.writeJSON(response, CommonResult.error(GlobalErrorCodeConstants.BAD_REQUEST.getCode(),
                        "农场的请求未传递，请进行排查"));
                return;
            }
        } else {
            if (farmTenantId == null) {
                FarmTenantContextHolder.setIgnore(true);
            }
        }

        // 继续过滤
        chain.doFilter(request, response);
    }

    private boolean isIgnoreUrl(HttpServletRequest request) {
        // 快速匹配，保证性能
        if (CollUtil.contains(tenantProperties.getIgnoreUrls(), request.getRequestURI())) {
            return true;
        }
        // 逐个 Ant 路径匹配
        for (String url : tenantProperties.getIgnoreUrls()) {
            if (pathMatcher.match(url, request.getRequestURI())) {
                return true;
            }
        }
        return false;
    }

    private boolean isFarmIgnoreUrl(HttpServletRequest request) {
        // 快速匹配，保证性能
        if (CollUtil.contains(farmTenantProperties.getIgnoreUrls(), request.getRequestURI())) {
            return true;
        }
        // 逐个 Ant 路径匹配
        for (String url : farmTenantProperties.getIgnoreUrls()) {
            if (pathMatcher.match(url, request.getRequestURI())) {
                return true;
            }
        }
        return false;
    }

}
