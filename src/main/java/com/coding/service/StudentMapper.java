package com.coding.service;

import com.coding.pojo.Student;

import java.util.List;

public interface StudentMapper {

    /**
     * 全表查询
     */
    List<Student> selectall();

    /**
     * 根据专业查人员,给一对多用
     */
    List<Student> selectz(Integer major);
}
