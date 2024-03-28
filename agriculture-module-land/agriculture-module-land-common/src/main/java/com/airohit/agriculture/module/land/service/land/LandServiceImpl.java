package com.airohit.agriculture.module.land.service.land;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.airohit.agriculture.module.land.convert.crops.CropsConvert;
import com.airohit.agriculture.module.land.convert.land.LandConvert;
import com.airohit.agriculture.module.land.dal.dataobject.crops.CropsDO;
import com.airohit.agriculture.module.land.dal.dataobject.land.LandDO;
import com.airohit.agriculture.module.land.dal.mysql.crops.CropsMapper;
import com.airohit.agriculture.module.land.dal.mysql.land.LandMapper;
import com.airohit.agriculture.module.land.vo.*;
import com.airohit.agriculture.module.land.vo.crops.CropsCreateReqVO;
import com.airohit.agriculture.module.land.vo.crops.CropsVarietiesBaseVO;
import com.airohit.agriculture.module.land.vo.crops.RaiseCropsBaseVO;
import com.airohit.agriculture.module.land.vo.crops.RaiseCropsRespVO;
import com.airohit.agriculture.module.plant.api.plan.PlanApi;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static com.airohit.agriculture.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.airohit.agriculture.module.land.enums.ErrorCodeConstants.LAND_NOT_EXISTS;

/**
 * 地块信息 Service 实现类
 *
 * @author 管理员
 */
@Service
@Validated
@Slf4j
public class LandServiceImpl implements LandService {

    @Resource
    private LandMapper Mapper;

    @Resource
    private CropsMapper cropsMapper;

    @Resource
    private PlanApi planApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer create(LandCreateReqVO createReqVO) {
        // 插入
        LandDO landDO = LandConvert.INSTANCE.convert(createReqVO);
        landDO.setLandCoordinate(JSONObject.toJSONString(createReqVO.getPosVoList()));
        Mapper.insert(landDO);

        List<CropsCreateReqVO> cropsCreateReqVOList = createReqVO.getCropsCreateReqVOList();
        if (cropsCreateReqVOList != null) {
            for (CropsCreateReqVO cropsCreateReqVO : cropsCreateReqVOList) {
                CropsDO cropsDO = CropsConvert.INSTANCE.convert(cropsCreateReqVO);
                cropsDO.setLandId(landDO.getId());
                cropsMapper.insert(cropsDO);
            }
        }

        if (createReqVO.getPlanId() != -1) {
            log.info("创建地块 地块绑定计划 地块编号:{}   计划编号:{}", landDO.getId(), createReqVO.getPlanId());
            //地块绑定计划
            planApi.landBindPlan(createReqVO.getPlanId(), landDO.getId());
        }

        // 返回
        return landDO.getId();
    }

    @Override
    public void update(LandUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateExists(updateReqVO.getId());
        // 更新
        LandDO updateObj = LandConvert.INSTANCE.convert(updateReqVO);
        updateObj.setLandCoordinate(JSONObject.toJSONString(updateReqVO.getPosVoList()));

        //先删除地块关联作物
        List<CropsCreateReqVO> cropsCreateReqVOList = updateReqVO.getCropsCreateReqVOList();
        if (cropsCreateReqVOList != null) {
            cropsMapper.delete(new LambdaQueryWrapperX<CropsDO>().eqIfPresent(CropsDO::getLandId, updateReqVO.getId()));
            for (CropsCreateReqVO cropsCreateReqVO : cropsCreateReqVOList) {
                CropsDO cropsDO = CropsConvert.INSTANCE.convert(cropsCreateReqVO);
                cropsDO.setLandId(updateReqVO.getId());
                cropsMapper.insert(cropsDO);
            }
        }
        Mapper.updateById(updateObj);

        log.info("修改地块 地块解绑/更换 计划 地块编号:{}   计划编号:{}", updateReqVO.getId(), updateReqVO.getPlanId());
        //地块绑定计划
        planApi.landBindPlan(updateReqVO.getPlanId(), updateReqVO.getId());
    }

    @Override
    public void delete(Integer id) {
        // 校验存在
        this.validateExists(id);
        // 删除
        Mapper.deleteById(id);
    }

    private void validateExists(Integer id) {
        if (Mapper.selectById(id) == null) {
            throw exception(LAND_NOT_EXISTS);
        }
    }

    @Override
    public LandDO get(Integer id) {
        return Mapper.selectById(id);
    }

    @Override
    public List<LandDO> getList(Collection<Integer> ids, String landName) {
        return Mapper.selectList(new LambdaQueryWrapperX<LandDO>().inIfPresent(LandDO::getId, ids).likeIfPresent(LandDO::getLandName, landName).orderByDesc(LandDO::getId));
    }

    @Override
    public PageResult<LandDO> getPage(LandPageReqVO pageReqVO) {
        return Mapper.selectPage(pageReqVO);
    }

    @Override
    public List<LandDO> getList(LandExportReqVO exportReqVO) {
        return Mapper.selectList(exportReqVO);
    }

    @Override
    public void updateOutline(LandUpdateOutlineReqVO updateReqVO) {
        // 校验存在
        this.validateExists(updateReqVO.getId());
        // 更新
        LandDO updateObj = new LandDO();
        updateObj.setId(updateReqVO.getId());
        updateObj.setArea(updateReqVO.getArea());
        updateObj.setLandCoordinate(JSONObject.toJSONString(updateReqVO.getPosVoList()));
        updateObj.setLandCoordinateCenter(updateReqVO.getLandCoordinateCenter());
        Mapper.updateById(updateObj);
    }

    @Override
    public List<RaiseCropsRespVO> queryRaiseCrops() {
        return Mapper.queryRaiseCrops();
    }

    @Override
    public List<CropsVarietiesBaseVO> queryCropsVarieties(Integer id) {
        return Mapper.queryCropsVarieties(id);
    }

    @Override
    public RaiseCropsBaseVO queryRaiseCropsByCode(String crops) {
        return Mapper.queryRaiseCropsByCode(crops);
    }

    @Override
    public CropsVarietiesBaseVO queryCropsVarietiesByCode(Integer raiseCropsRespId, String cropsType) {
        return Mapper.queryCropsVarietiesByCode(raiseCropsRespId, cropsType);
    }

    @Override
    public void updatePlanByLandId(Integer id) {
        Mapper.updatePlanByLandId(id);
    }

    @Override
    public LandStatisticsVO queryLandStatistics() {
        return Mapper.queryLandStatistics();
    }

    @Override
    public void createInitLand(LandCreateReqVO createReqVO) {
        LandDO landDO = LandConvert.INSTANCE.convert(createReqVO);
        Mapper.insert(landDO);

        /**
         * {
         *  "crops": "zw002",
         *  "cropsIsOther": 0,
         *  "cropsTypeIsOther": 0,
         *  "cropsType": "1695283756214",
         *  "cropsOtherContent": "",
         *  "cropsTypeOtherContent": ""
         * }
         */

        CropsDO cropsDO = new CropsDO();
        cropsDO.setLandId(landDO.getId());
        cropsDO.setCrops(createReqVO.getCrops());

        if (createReqVO.getCrops().equals("zw001")) {
            cropsDO.setCropsType("1695283732776");
        }

        if (createReqVO.getCrops().equals("zw002")) {
            cropsDO.setCropsType("1695283756214");
        }

        if (createReqVO.getCrops().equals("zw003")) {
            cropsDO.setCropsType("1695283774175");
        }

        cropsDO.setCropsIsOther(0);
        cropsDO.setCropsTypeIsOther(0);
        cropsDO.setCropsOtherContent("");
        cropsDO.setCropsTypeOtherContent("");


        cropsMapper.insert(cropsDO);

    }

    @Override
    public List<CropsCreateReqVO> queryLandCropsByLandId(Integer id) {
        return Mapper.queryLandCropsByLandId(id);
    }

    @Override
    public List<LandRespVO> getLandList(String landName) {
        return Mapper.getLandList(landName);
    }

    @Override
    public List<CropsCreateReqVO> queryLandCrops() {
        return Mapper.queryLandCrops();
    }

}
