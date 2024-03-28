package com.airohit.agriculture.module.content.service.banner;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.content.dal.dataobject.banner.BannerDO;
import com.airohit.agriculture.module.content.vo.banner.BannerCreateReqVO;
import com.airohit.agriculture.module.content.vo.banner.BannerExportReqVO;
import com.airohit.agriculture.module.content.vo.banner.BannerPageReqVO;
import com.airohit.agriculture.module.content.vo.banner.BannerUpdateReqVO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 广告信息 Service 接口
 *
 * @author 管理员
 */
public interface BannerService {

    /**
     * 创建广告信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createBanner(@Valid BannerCreateReqVO createReqVO);

    /**
     * 更新广告信息
     *
     * @param updateReqVO 更新信息
     */
    void updateBanner(@Valid BannerUpdateReqVO updateReqVO);

    /**
     * 删除广告信息
     *
     * @param id 编号
     */
    void deleteBanner(Integer id);

    /**
     * 获得广告信息
     *
     * @param id 编号
     * @return 广告信息
     */
    BannerDO getBanner(Integer id);

    /**
     * 获得广告信息列表
     *
     * @param ids 编号
     * @return 广告信息列表
     */
    List<BannerDO> getBannerList(Collection<Integer> ids);

    /**
     * 获得广告信息分页
     *
     * @param pageReqVO 分页查询
     * @return 广告信息分页
     */
    PageResult<BannerDO> getBannerPage(BannerPageReqVO pageReqVO);

    /**
     * 获得广告信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 广告信息列表
     */
    List<BannerDO> getBannerList(BannerExportReqVO exportReqVO);

    /**
     * app 查询广告信息
     *
     * @param pageVO
     * @return
     */
    PageResult<BannerDO> getBannerAppPage(BannerPageReqVO pageVO);
}
