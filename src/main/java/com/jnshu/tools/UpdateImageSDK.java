package com.jnshu.tools;

import com.google.gson.Gson;
import com.jnshu.model.StudentCustom;
import com.jnshu.service.ServiceDao;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.whalin.MemCached.MemCachedClient;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @program: SSM_WEB
 * @description: 头像上传SDK
 * @author: Mr.xweiba
 * @create: 2018-06-04 00:03
 **/

public class UpdateImageSDK {
    private static Logger logger = LoggerFactory.getLogger(UpdateImageSDK.class);
    private String bucketname;    //空间名
    private Auth auth;
    private String upToken;
    private String key = null;    //默认不指定key的情况下，以文件内容的hash值作为文件名
    private Configuration cfg = new Configuration(Zone.zone0());
    private UploadManager uploadManager = new UploadManager(cfg);
    private BucketManager bucketManager;
    private StudentCustom studentCustom = new StudentCustom();
    private String fileUrl;
    private Boolean authFlag = false;

    @Autowired
    ServiceDao serviceDao;
    @Autowired
    MemCachedClient memCachedClient;

    UpdateImageSDK(String ak, String sk, String bucketname, String fileUrl) {
        this.bucketname = bucketname;
        this.fileUrl = fileUrl;
        this.authFlag = true;
        try {
            this.auth = Auth.create(ak, sk);
            this.upToken = auth.uploadToken(bucketname);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("七牛 SDK 认证失败");
            this.authFlag = false;
        }

        bucketManager = new BucketManager(auth, cfg);
    }

    public boolean delete(String keyFile) {
        try {
            bucketManager.delete(bucketname, keyFile);
            return true;
        } catch (QiniuException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateFile(Integer id, MultipartFile multipartFile) {
        logger.debug("七牛 SDK 认证状态: " + authFlag);
        if (authFlag) {
            logger.debug("上传文件名: " + multipartFile.getOriginalFilename());
            logger.debug("上传文件类型: " + multipartFile.getContentType());
            DiskFileItem fi = MultFileToIoFile.multipartFile(multipartFile);
            Response qresponse;

            try {
                qresponse = uploadManager.put(fi.getInputStream(), key, upToken, null, null);
                if (qresponse != null) {
                    // 解析上传结果
                    DefaultPutRet putRet = new Gson().fromJson(qresponse.bodyString(), DefaultPutRet.class);
                    logger.debug("上传结果: key: " + putRet.key + " hash: " + putRet.hash);
                    String fileName = fileUrl + putRet.key;
                    logger.debug("入库文件名: " + fileUrl);
                    studentCustom.setHeadurl(fileName);
                    studentCustom.setId(id);
                    // 写入数据库
                    if (serviceDao.updateStudent(studentCustom)) {
                        logger.debug("上传成功");
                        return true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.debug("七牛SDK api 连接失败");
            } catch (Exception e) {
                e.printStackTrace();
                logger.debug("写入数据库失败");
            }
        }
        return false;
    }
}