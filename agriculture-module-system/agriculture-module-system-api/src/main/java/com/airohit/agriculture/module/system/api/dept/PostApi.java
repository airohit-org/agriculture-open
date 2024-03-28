package com.airohit.agriculture.module.system.api.dept;

import com.airohit.agriculture.framework.common.pojo.CommonResult;

import java.util.Collection;

public interface PostApi {

    CommonResult<Boolean> validPosts(Collection<Long> ids);

}
