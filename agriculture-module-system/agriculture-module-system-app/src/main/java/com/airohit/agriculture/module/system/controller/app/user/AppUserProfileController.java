package com.airohit.agriculture.module.system.controller.app.user;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import com.airohit.agriculture.framework.common.enums.UserTypeEnum;
import com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil;
import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.framework.datapermission.core.annotation.DataPermission;
import com.airohit.agriculture.framework.operatelog.core.annotations.OperateLog;
import com.airohit.agriculture.module.infra.api.file.FileApi;
import com.airohit.agriculture.module.system.api.slave.vo.farm.FarmRespVO;
import com.airohit.agriculture.module.system.api.slave.vo.farm.PosVo;
import com.airohit.agriculture.module.system.convert.farm.FarmConvert;
import com.airohit.agriculture.module.system.convert.user.UserConvert;
import com.airohit.agriculture.module.system.dal.dataobject.dept.DeptDO;
import com.airohit.agriculture.module.system.dal.dataobject.dept.PostDO;
import com.airohit.agriculture.module.system.dal.dataobject.farm.FarmDO;
import com.airohit.agriculture.module.system.dal.dataobject.permission.RoleDO;
import com.airohit.agriculture.module.system.dal.dataobject.social.SocialUserDO;
import com.airohit.agriculture.module.system.dal.dataobject.user.AdminUserDO;
import com.airohit.agriculture.module.system.entity.admin.user.vo.profile.UserProfileRespVO;
import com.airohit.agriculture.module.system.entity.admin.user.vo.profile.UserProfileUpdatePasswordReqVO;
import com.airohit.agriculture.module.system.entity.admin.user.vo.profile.UserProfileUpdateReqVO;
import com.airohit.agriculture.module.system.service.dept.DeptService;
import com.airohit.agriculture.module.system.service.dept.PostService;
import com.airohit.agriculture.module.system.service.farm.SystemFarmService;
import com.airohit.agriculture.module.system.service.permission.PermissionService;
import com.airohit.agriculture.module.system.service.permission.RoleService;
import com.airohit.agriculture.module.system.service.social.SocialUserService;
import com.airohit.agriculture.module.system.service.user.AdminUserService;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.List;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;
import static com.airohit.agriculture.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static com.airohit.agriculture.module.infra.enums.ErrorCodeConstants.FILE_IS_EMPTY;

@Api(tags = "农场app - 用户个人中心")
@RestController
@RequestMapping("/system/app/profile")
@Validated
@Slf4j
public class AppUserProfileController {

    @Resource
    private AdminUserService userService;
    @Resource
    private DeptService deptService;
    @Resource
    private FileApi fileService;

    @Resource
    private PostService postService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;
    @Resource
    private SocialUserService socialService;
    @Resource
    private SystemFarmService service;

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

    @PutMapping("/update-password-notLogin")
    @ApiOperation("重置密码")
    @PermitAll
    public CommonResult<Boolean> updateUserProfilePasswordNotLogin(@Valid @RequestBody UserProfileUpdatePasswordReqVO reqVO) {
        userService.updateUserPasswordNotLogin(reqVO.getUserId(), reqVO);
        return success(true);
    }

    @PostMapping("/update-avatar")
    @ApiOperation("上传用户个人头像")
    public CommonResult<String> updateUserAvatar(@RequestParam("avatarFile") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw ServiceExceptionUtil.exception(FILE_IS_EMPTY);
        }
        String avatar = userService.updateUserAvatar(getLoginUserId(), file.getInputStream());
        return success(avatar);
    }

    @GetMapping("/getFarmByTenant")
    @ApiOperation("根据租户获得农场")
    @PermitAll
    public CommonResult<FarmRespVO> get(@RequestParam("tenantId") Long tenantId) {
        FarmDO farmDO = service.getFarmByTenant(tenantId);
        FarmRespVO convert = FarmConvert.INSTANCE.convert(farmDO);
        if (JSONUtil.isTypeJSON(convert.getCoordinate())) {
            convert.setPosVoList(JSONArray.parseArray(farmDO.getCoordinate(), PosVo.class));
        }
        return success(convert);
    }


    @PostMapping("/upload")
    @ApiOperation("上传文件")
    @OperateLog(logArgs = false) // 上传文件，没有记录操作日志的必要
    public CommonResult<String> uploadFile(@RequestParam("avatarFile") MultipartFile file) throws Exception {
        return success(fileService.createFile(IoUtil.readBytes(file.getInputStream())));
    }
}
