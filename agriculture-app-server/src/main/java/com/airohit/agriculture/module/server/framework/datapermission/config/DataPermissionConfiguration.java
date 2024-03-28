package com.airohit.agriculture.module.server.framework.datapermission.config;

import com.airohit.agriculture.framework.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import com.airohit.agriculture.module.system.dal.dataobject.dept.DeptDO;
import com.airohit.agriculture.module.system.dal.dataobject.user.AdminUserDO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * system 模块的数据权限 Configuration
 *
 * @author shiminghao
 */
@Configuration(proxyBeanMethods = false)
public class DataPermissionConfiguration {

    @Bean
    public DeptDataPermissionRuleCustomizer sysDeptDataPermissionRuleCustomizer() {
        return rule -> {
            // dept
            rule.addDeptColumn(AdminUserDO.class);
            rule.addDeptColumn(DeptDO.class, "id");
            // user
            rule.addUserColumn(AdminUserDO.class, "id");
        };
    }

}
