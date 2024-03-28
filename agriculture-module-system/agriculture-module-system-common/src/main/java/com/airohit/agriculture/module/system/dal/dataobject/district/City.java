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
 * @date :2023/3/14 14:17
 */
@TableName("city")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("市")
public class City {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("id")
    private Long id;
    /**
     * code
     */
    @ApiModelProperty("编码")
    private String code;
    /**
     * 市
     */
    @ApiModelProperty("市")
    private String name;
    /**
     * 省份编码
     */
    @ApiModelProperty("省份编码")
    private String provinceCode;
}
