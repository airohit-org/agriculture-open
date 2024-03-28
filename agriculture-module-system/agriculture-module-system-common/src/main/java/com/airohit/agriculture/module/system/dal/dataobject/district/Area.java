package com.airohit.agriculture.module.system.dal.dataobject.district;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/3/14 14:18
 */
@TableName("area")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("区/县")
public class Area {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("id")
    private Long id;
    /**
     * code
     */
    @ApiModelProperty("编码")
    private String code;
    /**
     * 区县名称
     */
    @ApiModelProperty("区县名称")
    private String name;
    /**
     * 省份编码
     */
    @ApiModelProperty("省份编码")
    private String provinceCode;
    /**
     * 城市编码
     */
    @ApiModelProperty("城市编码")
    private String cityCode;
}
