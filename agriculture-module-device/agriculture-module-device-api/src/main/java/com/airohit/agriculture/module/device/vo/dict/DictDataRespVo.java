package com.airohit.agriculture.module.device.vo.dict;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("RPC 服务 - 字典数据 Response DTO")
@Data
public class DictDataRespVo {

    @ApiModelProperty(value = "字典标签", required = true, example = "农业")
    private String label;
    @ApiModelProperty(value = "字典值", required = true, example = "airohit")
    private String value;
    @ApiModelProperty(value = "字典类型", required = true, example = "sys_common_sex")
    private String dictType;
    @ApiModelProperty(value = "状态", required = true, example = "1", notes = "见 CommonStatusEnum 枚举")
    private Integer status;

    @ApiModelProperty(value = "备注", required = false)
    private String remark;

    private String cssClass;
}
