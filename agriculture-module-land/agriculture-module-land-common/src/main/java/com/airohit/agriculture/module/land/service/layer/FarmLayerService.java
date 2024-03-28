package com.airohit.agriculture.module.land.service.layer;

import com.airohit.agriculture.module.land.vo.layer.FarmLayerVO;

import java.util.List;

/**
 * 农场图层 Service 接口
 *
 * @author zyg
 */
public interface FarmLayerService {
    List<FarmLayerVO> queryFarmLayer();
}
