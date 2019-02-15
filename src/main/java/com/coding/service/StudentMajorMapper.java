package com.coding.service;

import com.coding.pojo.StudentMajor;

import java.util.List;

public interface StudentMajorMapper {

    /**
     * 全表查询
     */
    List<StudentMajor> selectAll();

    /**
     * 根据主键查数据,给多对一用
     */
    StudentMajor select(Integer id);
}
