package com.airohit.agriculture.module.system.convert.user;

import com.airohit.agriculture.module.system.api.user.dto.AdminUserRespDTO;
import com.airohit.agriculture.module.system.dal.dataobject.dept.DeptDO;
import com.airohit.agriculture.module.system.dal.dataobject.dept.PostDO;
import com.airohit.agriculture.module.system.dal.dataobject.permission.RoleDO;
import com.airohit.agriculture.module.system.dal.dataobject.social.SocialUserDO;
import com.airohit.agriculture.module.system.dal.dataobject.user.AdminUserDO;
import com.airohit.agriculture.module.system.entity.admin.user.vo.profile.UserProfileRespVO;
import com.airohit.agriculture.module.system.entity.admin.user.vo.profile.UserProfileUpdatePasswordReqVO;
import com.airohit.agriculture.module.system.entity.admin.user.vo.profile.UserProfileUpdateReqVO;
import com.airohit.agriculture.module.system.entity.admin.user.vo.user.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserPageItemRespVO convert(AdminUserDO bean);

    UserPageItemRespVO.Dept convert(DeptDO bean);

    AdminUserDO convert(UserCreateReqVO bean);

    AdminUserDO convert(UserUpdateReqVO bean);

    UserExcelVO convert02(AdminUserDO bean);

    AdminUserDO convert(UserImportExcelVO bean);

    UserProfileRespVO convert03(AdminUserDO bean);

    List<UserProfileRespVO.Role> convertList(List<RoleDO> list);

    UserProfileRespVO.Dept convert02(DeptDO bean);

    AdminUserDO convert(UserProfileUpdateReqVO bean);

    AdminUserDO convert(UserProfileUpdatePasswordReqVO bean);

    List<UserProfileRespVO.Post> convertList02(List<PostDO> list);

    List<UserProfileRespVO.SocialUser> convertList03(List<SocialUserDO> list);

    List<UserSimpleRespVO> convertList04(List<AdminUserDO> list);

    AdminUserRespDTO convert4(AdminUserDO bean);

    List<AdminUserRespDTO> convertList4(List<AdminUserDO> users);

}
