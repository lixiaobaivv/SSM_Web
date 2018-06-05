package com.jnshu.tools;


import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


/**
 * @program: QINIUSDKImage
 * @description: MultFile 转换为 IoFile 文件流
 * @author: Mr.xweiba
 * @create: 2018-06-01 00:23
 **/

public class MultFileToIoFile {
    public static DiskFileItem multipartFile(MultipartFile multipartFile){
        CommonsMultipartFile cf = (CommonsMultipartFile) multipartFile;
        return (DiskFileItem) cf.getFileItem();
    }
}
