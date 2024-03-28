package com.airohit.agriculture.module.system.controller.admin.user;

import cn.hutool.core.collection.CollUtil;
import com.airohit.agriculture.framework.common.enums.UserTypeEnum;
import com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil;
import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.datapermission.core.annotation.DataPermission;
import com.airohit.agriculture.module.system.convert.user.UserConvert;
import com.airohit.agriculture.module.system.dal.dataobject.dept.DeptDO;
import com.airohit.agriculture.module.system.dal.dataobject.dept.PostDO;
import com.airohit.agriculture.module.system.dal.dataobject.permission.RoleDO;
import com.airohit.agriculture.module.system.dal.dataobject.social.SocialUserDO;
import com.airohit.agriculture.module.system.dal.dataobject.tenant.TenantDO;
import com.airohit.agriculture.module.system.dal.dataobject.user.AdminUserDO;
import com.airohit.agriculture.module.system.entity.admin.user.vo.profile.UserProfileRespVO;
import com.airohit.agriculture.module.system.entity.admin.user.vo.profile.UserProfileUpdatePasswordReqVO;
import com.airohit.agriculture.module.system.entity.admin.user.vo.profile.UserProfileUpdateReqVO;
import com.airohit.agriculture.module.system.service.dept.DeptService;
import com.airohit.agriculture.module.system.service.dept.PostService;
import com.airohit.agriculture.module.system.service.permission.PermissionService;
import com.airohit.agriculture.module.system.service.permission.RoleService;
import com.airohit.agriculture.module.system.service.social.SocialUserService;
import com.airohit.agriculture.module.system.service.tenant.TenantService;
import com.airohit.agriculture.module.system.service.user.AdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;
import static com.airohit.agriculture.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static com.airohit.agriculture.module.infra.enums.ErrorCodeConstants.FILE_IS_EMPTY;

@Api(tags = "管理后台 - 用户个人中心")
@RestController
@RequestMapping("/system/user/profile")
@Validated
@Slf4j
public class AdminUserProfileController {

    @Resource
    private AdminUserService userService;
    @Resource
    private DeptService deptService;
    @Resource
    private PostService postService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;
    @Resource
    private SocialUserService socialService;
    @Resource
    private TenantService tenantService;

    @GetMapping("/get")
    @ApiOperation("获得登录用户信息")
    @DataPermission(enable = false) // 关闭数据权限，避免只查看自己时，查询不到部门。
    public CommonResult<UserProfileRespVO> profile() {
        // 获得用户基本信息
        AdminUserDO user = userService.getUser(getLoginUserId());
        UserProfileRespVO resp = UserConvert.INSTANCE.convert03(user);
        // 获得用户角色
        List<RoleDO> userRoles = roleService.getRolesFromCache(permissionService.getUserRoleIdListByUserId(user.getId()));
        resp.setRoles(UserConvert.INSTANCE.convertList(userRoles));
        // 获得部门信息
        if (user.getDeptId() != null) {
            DeptDO dept = deptService.getDept(user.getDeptId());
            resp.setDept(UserConvert.INSTANCE.convert02(dept));
        }
        // 获得岗位信息
        if (CollUtil.isNotEmpty(user.getPostIds())) {
            List<PostDO> posts = postService.getPosts(user.getPostIds());
            resp.setPosts(UserConvert.INSTANCE.convertList02(posts));
        }
        // 获得社交用户信息
        List<SocialUserDO> socialUsers = socialService.getSocialUserList(user.getId(), UserTypeEnum.ADMIN.getValue());
        resp.setSocialUsers(UserConvert.INSTANCE.convertList03(socialUsers));
        TenantDO tenant = tenantService.getTenant(user.getTenantId());
        resp.setTenantContactName(tenant.getContactName());
        return success(resp);
    }

    @PutMapping("/update")
    @ApiOperation("修改用户个人信息")
    public CommonResult<Boolean> updateUserProfile(@Valid @RequestBody UserProfileUpdateReqVO reqVO) {
        userService.updateUserProfile(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-password")
    @ApiOperation("修改用户个人密码")
    public CommonResult<Boolean> updateUserProfilePassword(@Valid @RequestBody UserProfileUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-avatar")
    @ApiOperation("上传用户个人头像")
    public CommonResult<String> updateUserAvatar(@RequestParam("avatarFile") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw ServiceExceptionUtil.exception(FILE_IS_EMPTY);
        }
        String avatar = userService.updateUserAvatar(getLoginUserId(), file.getInputStream());
        return success(avatar);
    }

}
