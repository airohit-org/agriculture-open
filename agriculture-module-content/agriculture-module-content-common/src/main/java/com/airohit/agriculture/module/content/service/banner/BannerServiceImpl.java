package com.airohit.agriculture.module.content.service.banner;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.content.convert.banner.BannerConvert;
import com.airohit.agriculture.module.content.dal.dataobject.banner.BannerDO;
import com.airohit.agriculture.module.content.dal.mysql.banner.BannerMapper;
import com.airohit.agriculture.module.content.vo.banner.BannerCreateReqVO;
import com.airohit.agriculture.module.content.vo.banner.BannerExportReqVO;
import com.airohit.agriculture.module.content.vo.banner.BannerPageReqVO;
import com.airohit.agriculture.module.content.vo.banner.BannerUpdateReqVO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.content.enums.ErrorCodeConstants.BANNER_NOT_EXISTS;

/**
 * 广告信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class BannerServiceImpl implements BannerService {

    @Resource
    private BannerMapper bannerMapper;

    @Override
    public Integer createBanner(BannerCreateReqVO createReqVO) {
        // 插入
        BannerDO banner = BannerConvert.INSTANCE.convert(createReqVO);
        bannerMapper.insert(banner);
        // 返回
        return banner.getId();
    }

    @Override
    public void updateBanner(BannerUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateBannerExists(updateReqVO.getId());
        // 更新
        BannerDO updateObj = BannerConvert.INSTANCE.convert(updateReqVO);
        bannerMapper.updateById(updateObj);
    }

    @Override
    public void deleteBanner(Integer id) {
        // 校验存在
        this.validateBannerExists(id);
        // 删除
        bannerMapper.deleteById(id);
    }

    private void validateBannerExists(Integer id) {
        if (bannerMapper.selectById(id) == null) {
            throw exception(BANNER_NOT_EXISTS);
        }
    }

    @Override
    public BannerDO getBanner(Integer id) {
        return bannerMapper.selectById(id);
    }

    @Override
    public List<BannerDO> getBannerList(Collection<Integer> ids) {
        return bannerMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<BannerDO> getBannerPage(BannerPageReqVO pageReqVO) {
        return bannerMapper.selectPage(pageReqVO);
    }

    @Override
    public List<BannerDO> getBannerList(BannerExportReqVO exportReqVO) {
        return bannerMapper.selectList(exportReqVO);
    }

    @Override
    public PageResult<BannerDO> getBannerAppPage(BannerPageReqVO pageVO) {
        return bannerMapper.selectAppPage(pageVO);
    }

}
