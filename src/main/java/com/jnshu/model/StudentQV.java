package com.jnshu.model;

/**
 * @program: smsdemo
 * @description: 综合类
 * @author: Mr.xweiba
 * @create: 2018-05-29 23:16
 **/

public class StudentQV {
    private Student student;
    private StudentCustom studentCustom;

    @Override
    public String toString() {
        return "StrudentQV{" +
                "student=" + student +
                ", studentCustom=" + studentCustom +
                '}';
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudentCustom getStudentCustom() {
        return studentCustom;
    }

    public void setStudentCustom(StudentCustom studentCustom) {
        this.studentCustom = studentCustom;
    }
}
