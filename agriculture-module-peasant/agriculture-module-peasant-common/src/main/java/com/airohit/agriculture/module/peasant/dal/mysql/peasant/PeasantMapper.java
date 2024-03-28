package com.airohit.agriculture.module.peasant.dal.mysql.peasant;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.mapper.BaseMapperX;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.peasant.dal.dataobject.peasant.PeasantDO;
import com.airohit.agriculture.module.peasant.vo.PeasantExportReqVO;
import com.airohit.agriculture.module.peasant.vo.PeasantPageReqVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 农户 Mapper
 *
 * @author lrj
 */
@Mapper
public interface PeasantMapper extends BaseMapperX<PeasantDO> {

    default PageResult<PeasantDO> selectPage(PeasantPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PeasantDO>()
                .likeIfPresent(PeasantDO::getUserName, reqVO.getUserName())
                .eqIfPresent(PeasantDO::getPassWord, reqVO.getPassWord())
                .eqIfPresent(PeasantDO::getUserType, reqVO.getUserType())
                .likeIfPresent(PeasantDO::getName, reqVO.getName())
                .eqIfPresent(PeasantDO::getProvince, reqVO.getProvince())
                .eqIfPresent(PeasantDO::getCity, reqVO.getCity())
                .eqIfPresent(PeasantDO::getArea, reqVO.getArea())
                .eqIfPresent(PeasantDO::getStreet, reqVO.getStreet())
                .eqIfPresent(PeasantDO::getVillage, reqVO.getVillage())
                .eqIfPresent(PeasantDO::getPhone, reqVO.getPhone())
                .eqIfPresent(PeasantDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PeasantDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(PeasantDO::getRemark, reqVO.getRemark())
                .orderByDesc(PeasantDO::getId));
    }

    default List<PeasantDO> selectList(PeasantExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PeasantDO>()
                .likeIfPresent(PeasantDO::getUserName, reqVO.getUserName())
                .eqIfPresent(PeasantDO::getPassWord, reqVO.getPassWord())
                .eqIfPresent(PeasantDO::getUserType, reqVO.getUserType())
                .likeIfPresent(PeasantDO::getName, reqVO.getName())
                .eqIfPresent(PeasantDO::getProvince, reqVO.getProvince())
                .eqIfPresent(PeasantDO::getCity, reqVO.getCity())
                .eqIfPresent(PeasantDO::getArea, reqVO.getArea())
                .eqIfPresent(PeasantDO::getStreet, reqVO.getStreet())
                .eqIfPresent(PeasantDO::getVillage, reqVO.getVillage())
                .eqIfPresent(PeasantDO::getPhone, reqVO.getPhone())
                .eqIfPresent(PeasantDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(PeasantDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(PeasantDO::getRemark, reqVO.getRemark())
                .orderByDesc(PeasantDO::getId));
    }

    default List<PeasantDO> selectListAll(PeasantExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PeasantDO>()
                .likeIfPresent(PeasantDO::getUserName, reqVO.getUserName())
                .eqIfPresent(PeasantDO::getName, reqVO.getName())
                .eqIfPresent(PeasantDO::getPhone, reqVO.getPhone())
                .betweenIfPresent(PeasantDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PeasantDO::getId));
    }

    default PeasantDO selectByPhone(String phone) {
        return selectOne(new LambdaQueryWrapper<PeasantDO>().eq(PeasantDO::getPhone, phone));
    }


}
