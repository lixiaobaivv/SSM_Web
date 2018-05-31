package com.jnshu.model;

import java.io.Serializable;
import java.util.List;

/**
 * @program: smsdemo
 * @description: Student 集合序列化实例
 * @author: Mr.xweiba
 * @create: 2018-05-30 20:26
 **/

public class StudentList implements Serializable{
    private static final Long serialVersionUID = -3213232132133232232L;

    private List<StudentCustom> studentCustomList;

    public List<StudentCustom> getStudentList() {
        return studentCustomList;
    }

    public void setStudentList(List<StudentCustom> studentCustomList) {
        this.studentCustomList = studentCustomList;
    }

    @Override
    public String toString() {
        return "StudentList{" +
                "studentList=" + studentCustomList +
                '}';
    }
}
