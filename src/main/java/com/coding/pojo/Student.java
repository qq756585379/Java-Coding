package com.coding.pojo;

import lombok.Data;

@Data
public class Student {

    private Integer sno;
    private String name;
    private String ssex;
    private Integer sclass;
    private StudentMajor studentmajor;
}
