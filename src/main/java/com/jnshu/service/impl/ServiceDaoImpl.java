package com.jnshu.service.impl;

import com.jnshu.model.*;
import com.jnshu.service.ServiceDao;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: smsdemo
 * @description: Service实现
 * @author: Mr.xweiba
 * @create: 2018-05-29 23:32
 **/
@Service
public class ServiceDaoImpl implements ServiceDao {

    @Override
    public List<StudentCustom> findListStudent(StudentQV studentQV) throws Exception {
        return null;
    }

    @Override
    public StudentCustom findStudentCustomById(Integer id) throws Exception {
        return null;
    }

    @Override
    public Integer insertStudent(StudentCustom studentCustom) throws Exception {
        return null;
    }

    @Override
    public Boolean deleteStudent(Integer id) throws Exception {
        return null;
    }

    @Override
    public Boolean updateStudent(StudentCustom studentCustom) throws Exception {
        return null;
    }

    @Override
    public Boolean updateEmail(StudentCustom studentCustom) throws Exception {
        return null;
    }

    @Override
    public Boolean updateTelephone(Integer id, String telePhone) throws Exception {
        return null;
    }

    @Override
    public Integer countStudent() throws Exception {
        return null;
    }

    @Override
    public Integer countWorkStundet() throws Exception {
        return null;
    }

    @Override
    public List<Profession> findByListProfession() throws Exception {
        return null;
    }

    @Override
    public Integer userAuth(UserAuth userAuth) throws Exception {
        return null;
    }

    @Override
    public Boolean findUserAuthByid(Integer id) throws Exception {
        return null;
    }

}
