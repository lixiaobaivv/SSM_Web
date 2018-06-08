package com.jnshu.service.impl;

import com.jnshu.model.StudentCustom;
import com.jnshu.service.ServiceMail;
import org.springframework.stereotype.Service;

/**
 * @program: SSM_WEB_SERVER
 * @description: 邮件SDK SendCloud实现
 * @author: Mr.xweiba
 * @create: 2018-06-09 01:35
 **/
@Service
public class ServiceMailSendCloud implements ServiceMail {
    @Override
    public Boolean sendMail(String httpUrl, StudentCustom studentCustom) {
        return null;
    }

    @Override
    public Boolean sendMail(String httpUrl, StudentCustom studentCustom, String toSubject) {
        return null;
    }

    @Override
    public Boolean sendMail(String httpUrl, StudentCustom studentCustom, String toSubject, String fromName) {
        return null;
    }
}
