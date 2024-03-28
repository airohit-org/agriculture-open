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
 * @date :2023/3/14 14:13
 */
@TableName("province")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("省")
public class Province {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("id")
    private Long id;
    /**
     * code
     */
    @ApiModelProperty("编码")
    private String code;
    /**
     * 省
     */
    @ApiModelProperty("省")
    private String name;
}
