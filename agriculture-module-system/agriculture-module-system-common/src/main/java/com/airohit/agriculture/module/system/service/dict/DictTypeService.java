package com.airohit.agriculture.module.system.service.dict;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.system.dal.dataobject.dict.DictTypeDO;
import com.airohit.agriculture.module.system.entity.admin.dict.vo.type.DictTypeCreateReqVO;
import com.airohit.agriculture.module.system.entity.admin.dict.vo.type.DictTypeExportReqVO;
import com.airohit.agriculture.module.system.entity.admin.dict.vo.type.DictTypePageReqVO;
import com.airohit.agriculture.module.system.entity.admin.dict.vo.type.DictTypeUpdateReqVO;

import java.util.List;

/**
 * 字典类型 Service 接口
 *
 * @author shiminghao
 */
public interface DictTypeService {

    /**
     * 创建字典类型
     *
     * @param reqVO 字典类型信息
     * @return 字典类型编号
     */
    Long createDictType(DictTypeCreateReqVO reqVO);

    /**
     * 更新字典类型
     *
     * @param reqVO 字典类型信息
     */
    void updateDictType(DictTypeUpdateReqVO reqVO);

    /**
     * 删除字典类型
     *
     * @param id 字典类型编号
     */
    void deleteDictType(Long id);

    /**
     * 获得字典类型分页列表
     *
     * @param reqVO 分页请求
     * @return 字典类型分页列表
     */
    PageResult<DictTypeDO> getDictTypePage(DictTypePageReqVO reqVO);

    /**
     * 获得字典类型列表
     *
     * @param reqVO 列表请求
     * @return 字典类型列表
     */
    List<DictTypeDO> getDictTypeList(DictTypeExportReqVO reqVO);

    /**
     * 获得字典类型详情
     *
     * @param id 字典类型编号
     * @return 字典类型
     */
    DictTypeDO getDictType(Long id);

    /**
     * 获得字典类型详情
     *
     * @param type 字典类型
     * @return 字典类型详情
     */
    DictTypeDO getDictType(String type);

    /**
     * 获得全部字典类型列表
     *
     * @return 字典类型列表
     */
    List<DictTypeDO> getDictTypeList();

}
