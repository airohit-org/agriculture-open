package com.airohit.agriculture.module.infra.api.file;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.infra.api.file.dto.FileCreateReqDTO;
import com.airohit.agriculture.module.infra.service.file.FileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.airohit.agriculture.framework.common.pojo.CommonResult.success;

@Service
public class FileApiImpl implements FileApi {

    @Resource
    private FileService fileService;

    @Override
    public CommonResult<String> createFile(FileCreateReqDTO createReqDTO) {
        return success(fileService.createFile(createReqDTO.getName(), createReqDTO.getPath(),
                createReqDTO.getContent()));
    }

    @Override
    public CommonResult<String> beijingTHImage(byte[] content) {
        return success(fileService.beijingTHImage(null, null, content));
    }
}
