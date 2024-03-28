package com.airohit.agriculture.module.infra.api.file;

import com.airohit.agriculture.framework.common.pojo.CommonResult;
import com.airohit.agriculture.module.infra.api.file.dto.FileCreateReqDTO;

public interface FileApi {


    /**
     * 保存文件，并返回文件的访问路径
     *
     * @param content 文件内容
     * @return 文件路径
     */
    default String createFile(byte[] content) {
        return createFile(null, null, content);
    }

    /**
     * 保存文件，并返回文件的访问路径
     *
     * @param path    文件路径
     * @param content 文件内容
     * @return 文件路径
     */
    default String createFile(String path, byte[] content) {
        return createFile(null, path, content);
    }

    /**
     * 保存文件，并返回文件的访问路径
     *
     * @param name    原文件名称
     * @param path    文件路径
     * @param content 文件内容
     * @return 文件路径
     */
    default String createFile(String name, String path, byte[] content) {
        return createFile(new FileCreateReqDTO().setName(name).setPath(path).setContent(content)).getCheckedData();
    }

    CommonResult<String> createFile(FileCreateReqDTO createReqDTO);

    CommonResult<String> beijingTHImage(byte[] content);

}
