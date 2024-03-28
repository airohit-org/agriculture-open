package com.airohit.agriculture.framework.tenant.core.db;

import cn.hutool.core.collection.CollUtil;
import com.airohit.agriculture.framework.tenant.config.TenantProperties;
import com.airohit.agriculture.framework.tenant.core.context.TenantContextHolder;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 基于 MyBatis Plus 多租户的功能，实现 DB 层面的多租户的功能
 *
 * @author shiminghao
 */
public class TenantDatabaseInterceptor implements TenantLineHandler {

    private final Set<String> ignoreTables = new HashSet<>();

    private final Set<String> ignoreSystemTenantTables = new HashSet<>();

    public TenantDatabaseInterceptor(TenantProperties properties) {
        // 不同 DB 下，大小写的习惯不同，所以需要都添加进去
        properties.getIgnoreTables().forEach(table -> {
            ignoreTables.add(table.toLowerCase());
            ignoreTables.add(table.toUpperCase());
        });
        properties.getIgnoreSystemTenantTables().forEach(table -> {
            ignoreSystemTenantTables.add(table.toLowerCase());
            ignoreSystemTenantTables.add(table.toUpperCase());
        });
        // 在 OracleKeyGenerator 中，生成主键时，会查询这个表，查询这个表后，会自动拼接 TENANT_ID 导致报错
        ignoreTables.add("DUAL");
    }

    @Override
    public Expression getTenantId() {
        return new LongValue(TenantContextHolder.getRequiredTenantId());
    }

    @Override
    public boolean ignoreTable(String tableName) {
        Long tenantId = TenantContextHolder.getTenantId();
        boolean ignoreTableFlag = TenantContextHolder.isIgnore() // 情况一，全局忽略多租户
                || CollUtil.contains(ignoreTables, tableName);
        if (Objects.nonNull(tenantId) && //租户不为null
                1L == tenantId //当前租户为系统租户
                && CollUtil.contains(ignoreSystemTenantTables, tableName)) { //当前表在忽略系统租户的表里面
            ignoreTableFlag = true;//设置为忽略系统租户
        }
        return ignoreTableFlag;
    }

}
