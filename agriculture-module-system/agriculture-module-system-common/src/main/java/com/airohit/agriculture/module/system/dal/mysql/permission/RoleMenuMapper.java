package com.airohit.agriculture.module.system.dal.mysql.permission;

import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.module.system.dal.dataobject.permission.RoleMenuDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Mapper
public interface RoleMenuMapper extends BaseMapperX<RoleMenuDO> {

    default List<RoleMenuDO> selectListByRoleId(Long roleId) {
        return selectList(new QueryWrapper<RoleMenuDO>().eq("role_id", roleId));
    }

    default void deleteListByRoleIdAndMenuIds(Long roleId, Collection<Long> menuIds) {
        delete(new QueryWrapper<RoleMenuDO>().eq("role_id", roleId)
                .in("menu_id", menuIds));
    }

    default void deleteListByMenuId(Long menuId) {
        delete(new QueryWrapper<RoleMenuDO>().eq("menu_id", menuId));
    }

    default void deleteListByRoleId(Long roleId) {
        delete(new QueryWrapper<RoleMenuDO>().eq("role_id", roleId));
    }

    @Select("SELECT COUNT(*) FROM system_role_menu WHERE update_time > #{maxUpdateTime}")
    Long selectCountByUpdateTimeGt(LocalDateTime maxUpdateTime);

    @Repository
    class BatchInsertMapper extends ServiceImpl<RoleMenuMapper, RoleMenuDO> {
    }

}
