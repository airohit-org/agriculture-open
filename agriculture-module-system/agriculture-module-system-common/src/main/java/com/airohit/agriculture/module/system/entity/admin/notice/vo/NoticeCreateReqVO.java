package com.airohit.agriculture.module.system.entity.admin.notice.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel("管理后台 - 通知公告创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class NoticeCreateReqVO extends NoticeBaseVO {
}
