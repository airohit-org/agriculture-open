package com.airohit.agriculture.module.peasant.service.peasant;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.peasant.dal.dataobject.peasant.PeasantDO;
import com.airohit.agriculture.module.peasant.vo.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 农户 Service 接口
 *
 * @author lrj
 */
public interface PeasantService {

    /**
     * 创建农户
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer create(@Valid PeasantCreateReqVO createReqVO);

    /**
     * 更新农户
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid PeasantUpdateReqVO updateReqVO);

    /**
     * 删除农户
     *
     * @param id 编号
     */
    void delete(Integer id);

    /**
     * 获得农户
     *
     * @param id 编号
     * @return 农户
     */
    PeasantDO get(Integer id);

    /**
     * 获得农户列表
     *
     * @param ids 编号
     * @return 农户列表
     */
    List<PeasantDO> getList(Collection<Integer> ids);

    /**
     * 获得农户分页
     *
     * @param pageReqVO 分页查询
     * @return 农户分页
     */
    PageResult<PeasantDO> getPage(PeasantPageReqVO pageReqVO);

    /**
     * 获得农户列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 农户列表
     */
    List<PeasantDO> getList(PeasantExportReqVO exportReqVO);


    /**
     * 批量导入用户
     *
     * @param importUsers     导入用户列表
     * @param isUpdateSupport 是否支持更新
     * @return 导入结果
     */
    ExcelImportRespVO importExcel(List<Excel> importUsers, boolean isUpdateSupport);

    List<PeasantDO> getAllList();


    Integer createApp(@Valid PeasantCreateReqVO createReqVO);

    void updateApp(@Valid PeasantUpdateReqVO updateReqVO);

    void deleteApp(Integer id);

    PeasantDO getApp(Integer id);

    List<PeasantDO> getListApp(Collection<Integer> ids);

    PageResult<PeasantDO> getPageApp(PeasantPageReqVO pageReqVO);


}
