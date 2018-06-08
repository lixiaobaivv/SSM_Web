package com.jnshu.service.impl;

import com.jnshu.service.ServiceCache;
import com.whalin.MemCached.MemCachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @program: SSM_WEB_SERVER
 * @description: 缓存 Memcached实现类
 * @author: Mr.xweiba
 * @create: 2018-06-09 00:37
 **/
@Service
public class ServiceCacheMemImpl implements ServiceCache {

    @Override
    public void delete(String key) {
    }

    @Override
    public void set(String key, Object object) {

    }

    @Override
    public void set(String key, Object object, Date date) {

    }

    @Override
    public Object get(String key) {
        return null;
    }
}
