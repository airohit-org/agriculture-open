package com.airohit.agriculture.module.system.convert.logger;

import com.airohit.agriculture.framework.common.pojo.PageResult;
import com.airohit.agriculture.module.system.api.logger.dto.LoginLogCreateReqDTO;
import com.airohit.agriculture.module.system.dal.dataobject.logger.LoginLogDO;
import com.airohit.agriculture.module.system.entity.admin.logger.vo.loginlog.LoginLogExcelVO;
import com.airohit.agriculture.module.system.entity.admin.logger.vo.loginlog.LoginLogRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LoginLogConvert {

    LoginLogConvert INSTANCE = Mappers.getMapper(LoginLogConvert.class);

    PageResult<LoginLogRespVO> convertPage(PageResult<LoginLogDO> page);

    List<LoginLogExcelVO> convertList(List<LoginLogDO> list);

    LoginLogDO convert(LoginLogCreateReqDTO bean);

}
