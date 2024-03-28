package com.airohit.agriculture.module.content.vo.news;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ApiModel("管理后台 - 新闻信息更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NewsUpdateReqVO extends NewsBaseVO {

    @ApiModelProperty(value = "编码", required = true)
    @NotNull(message = "编码不能为空")
    private Integer id;

    @ApiModelProperty(value = "图片")
    private String imageUrl;

    @ApiModelProperty(value = "阅读次数")
    private Integer readNum;

    @ApiModelProperty(value = "新闻内容")
    private String content;

}
