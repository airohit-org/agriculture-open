package com.airohit.agriculture.module.system.service.member;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Member Service 实现类
 *
 * @author shiminghao
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Value("${agriculture.info.base-package}")
    private String basePackage;

    private volatile Object memberUserApi;

    @Override
    public String getMemberUserMobile(Long id) {
        if (id == null) {
            return null;
        }
        Object user = ReflectUtil.invoke(getMemberUserApi(), "getUser", id);
        if (user == null) {
            return null;
        }
        return ReflectUtil.invoke(user, "getMobile");
    }

    private Object getMemberUserApi() {
        if (memberUserApi == null) {
            memberUserApi = SpringUtil.getBean(ClassUtil.loadClass(String.format("%s.module.member.api.user.MemberUserApi", basePackage)));
        }
        return memberUserApi;
    }

}
