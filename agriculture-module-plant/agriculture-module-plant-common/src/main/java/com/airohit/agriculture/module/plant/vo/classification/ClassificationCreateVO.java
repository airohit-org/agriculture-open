package com.airohit.agriculture.module.plant.vo.classification;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2024/1/26 09:21
 */
@Data
public class ClassificationCreateVO {

    @ApiModelProperty(value = "作物类型,0水稻,1蓝莓,2土豆,3苹果,4樱桃,5葡萄,6桃子,7大豆,8辣椒,9草莓,10西红柿")
    private String cropType;

    @ApiModelProperty(value = "文件")
    private MultipartFile file;

    @ApiModelProperty(value = "农场编号", required = true, hidden = true)
    @NotNull(message = "农场编号不能为空")
    private Long farmTenantId;

    @ApiModelProperty(value = "地块ID")
    private Integer landId;


    @ApiModelProperty(value = "是否保存,1是0否")
    private Integer isSave = 1;

    @ApiModelProperty(value = "经纬度")
    private String latitudeLongitude;

    @ApiModelProperty(value = "用户经纬度")
    private String userLatitudeLongitude;

    @ApiModelProperty(value = "1是0否", hidden = true)
    private Integer isPc;

}
