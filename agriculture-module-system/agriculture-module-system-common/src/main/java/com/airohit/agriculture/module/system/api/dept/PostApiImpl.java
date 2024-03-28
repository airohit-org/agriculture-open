package com.airohit.agriculture.module.system.api.dept;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.system.service.dept.PostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Service
public class PostApiImpl implements PostApi {

    @Resource
    private PostService postService;

    @Override
    public CommonResult<Boolean> validPosts(Collection<Long> ids) {
        postService.validPosts(ids);
        return success(true);
    }

}
