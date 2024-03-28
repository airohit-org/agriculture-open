package com.airohit.agriculture.module.land.service.varieties;

import cn.hutool.core.bean.BeanUtil;
import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.framework.tenant.core.util.TenantUtils;
import com.airohit.agriculture.module.land.convert.varieties.CropsVarietiesConvert;
import com.airohit.agriculture.module.land.dal.dataobject.varieties.CropsVarietiesDO;
import com.airohit.agriculture.module.land.dal.mysql.varieties.CropsVarietiesMapper;
import com.airohit.agriculture.module.land.vo.varieties.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 品种管理 Service 实现类
 *
 * @author shiminghao
 */
@Service
@Validated
public class CropsVarietiesServiceImpl implements CropsVarietiesService {

    @Resource
    private CropsVarietiesMapper varietiesMapper;

    @Override
    public Integer createVarieties(CropsVarietiesCreateReqVO createReqVO) {
        // 插入
        CropsVarietiesDO varieties = CropsVarietiesConvert.INSTANCE.convert(createReqVO);
        varietiesMapper.insert(varieties);
        // 返回
        return varieties.getId();
    }

    @Override
    public void createVarietiesByMaster(CropsVarietiesCreateReqVO createReqVO) {
        TenantUtils.execute(createReqVO.getTenantId(), () -> {
            TenantUtils.executeFarm(createReqVO.getFarmTenantId(), () -> {
                CropsVarietiesDO cropsVarietiesDO = varietiesMapper.selectOne(CropsVarietiesDO::getDataCode, createReqVO.getDataCode());
                if (Objects.isNull(cropsVarietiesDO)) {
                    varietiesMapper.insert(CropsVarietiesConvert.INSTANCE.convert(createReqVO));
                } else {
                    Integer id = cropsVarietiesDO.getId();
                    BeanUtil.copyProperties(createReqVO, cropsVarietiesDO);
                    cropsVarietiesDO.setId(id);
                    varietiesMapper.updateById(cropsVarietiesDO);
                }
            });
        });
    }

    @Override
    public CropsVarietiesDO getVarieties(String dataCode, Long tenantId) {
        AtomicReference<CropsVarietiesDO> cropsVarietiesDOAtomicReference = new AtomicReference<>(new CropsVarietiesDO());
        TenantUtils.execute(tenantId, () -> cropsVarietiesDOAtomicReference.set(varietiesMapper.selectOne(new LambdaQueryWrapperX<CropsVarietiesDO>()
                .eqIfPresent(CropsVarietiesDO::getDataCode, dataCode))));
        return cropsVarietiesDOAtomicReference.get();
    }

    @Override
    public void updateVarieties(CropsVarietiesUpdateReqVO updateReqVO) {
        // 校验存在
        // 更新
        CropsVarietiesDO updateObj = CropsVarietiesConvert.INSTANCE.convert(updateReqVO);
        varietiesMapper.updateById(updateObj);
    }

    @Override
    public CropsVarietiesDO getVarieties(Integer id) {
        return varietiesMapper.selectById(id);
    }

    @Override
    public List<CropsVarietiesDO> getVarietiesList(Collection<Integer> ids) {
        return varietiesMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<CropsVarietiesRespVO> getVarietiesPage(CropsVarietiesPageReqVO pageReqVO) {
        IPage<CropsVarietiesRespVO> cropsVarietiesRespVOPage = varietiesMapper.getCropsVarietiesRespVOPage(new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize()), pageReqVO);
        return new PageResult<>(cropsVarietiesRespVOPage.getRecords(), cropsVarietiesRespVOPage.getTotal());
    }

    @Override
    public List<CropsVarietiesDO> getVarietiesList(CropsVarietiesExportReqVO exportReqVO) {
        return varietiesMapper.selectList(exportReqVO);
    }

}
