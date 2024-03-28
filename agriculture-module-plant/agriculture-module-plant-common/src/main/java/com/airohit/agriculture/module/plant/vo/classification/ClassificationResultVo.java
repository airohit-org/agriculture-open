package com.airohit.agriculture.module.plant.vo.classification;

import lombok.Data;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2024/1/25 14:53
 */
@Data
public class ClassificationResultVo {
    private String fileName;
    private List<OddsVo> result;
}
