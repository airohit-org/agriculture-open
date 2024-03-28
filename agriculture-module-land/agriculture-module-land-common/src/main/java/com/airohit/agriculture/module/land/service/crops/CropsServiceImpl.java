package com.airohit.agriculture.module.land.service.crops;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.land.convert.crops.CropsConvert;
import com.airohit.agriculture.module.land.dal.dataobject.crops.CropsDO;
import com.airohit.agriculture.module.land.dal.mysql.crops.CropsMapper;
import com.airohit.agriculture.module.land.vo.crops.CropsCreateReqVO;
import com.airohit.agriculture.module.land.vo.crops.CropsExportReqVO;
import com.airohit.agriculture.module.land.vo.crops.CropsPageReqVO;
import com.airohit.agriculture.module.land.vo.crops.CropsUpdateReqVO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.land.enums.ErrorCodeConstants.CROPS_NOT_EXISTS;

/**
 * 地块作物 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
public class CropsServiceImpl implements CropsService {

    @Resource
    private CropsMapper cropsMapper;

    @Override
    public Integer createCrops(CropsCreateReqVO createReqVO) {
        // 插入
        CropsDO crops = CropsConvert.INSTANCE.convert(createReqVO);
        cropsMapper.insert(crops);
        // 返回
        return crops.getId();
    }

    @Override
    public void updateCrops(CropsUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateCropsExists(updateReqVO.getId());
        // 更新
        CropsDO updateObj = CropsConvert.INSTANCE.convert(updateReqVO);
        cropsMapper.updateById(updateObj);
    }

    @Override
    public void deleteCrops(Integer id) {
        // 校验存在
        this.validateCropsExists(id);
        // 删除
        cropsMapper.deleteById(id);
    }

    private void validateCropsExists(Integer id) {
        if (cropsMapper.selectById(id) == null) {
            throw exception(CROPS_NOT_EXISTS);
        }
    }

    @Override
    public CropsDO getCrops(Integer id) {
        return cropsMapper.selectById(id);
    }

    @Override
    public List<CropsDO> getCropsList(Collection<Integer> ids) {
        return cropsMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<CropsDO> getCropsPage(CropsPageReqVO pageReqVO) {
        return cropsMapper.selectPage(pageReqVO);
    }

    @Override
    public List<CropsDO> getCropsList(CropsExportReqVO exportReqVO) {
        return cropsMapper.selectList(exportReqVO);
    }

}
