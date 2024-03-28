package com.airohit.agriculture.module.system.convert.permission;

import com.airohit.agriculture.module.system.api.permission.dto.MenuSimpleRespVO;
import com.airohit.agriculture.module.system.dal.dataobject.permission.MenuDO;
import com.airohit.agriculture.module.system.entity.admin.permission.vo.menu.MenuCreateReqVO;
import com.airohit.agriculture.module.system.entity.admin.permission.vo.menu.MenuRespVO;
import com.airohit.agriculture.module.system.entity.admin.permission.vo.menu.MenuUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MenuConvert {

    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    List<MenuRespVO> convertList(List<MenuDO> list);

    MenuDO convert(MenuCreateReqVO bean);

    MenuDO convert(MenuUpdateReqVO bean);

    MenuRespVO convert(MenuDO bean);

    List<MenuSimpleRespVO> convertList02(List<MenuDO> list);

}
