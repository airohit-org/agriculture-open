package com.airohit.agriculture.module.content.vo.news;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 新闻信息创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NewsCreateReqVO extends NewsBaseVO {

    @ApiModelProperty(value = "图片")
    private String imageUrl;

    @ApiModelProperty(value = "新闻内容")
    private String content;

}
