package com.airohit.agriculture.module.system.entity.app.earlywarning;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/7/5 17:25
 */
@ApiModel("预警类型")
@Data
public class EarlyMessageTypeVo {
    @ApiModelProperty("类型,0气象预警,2虫害预警,3地块预警")
    private Integer warningType;
    @ApiModelProperty("数量")
    private Integer count;
}
