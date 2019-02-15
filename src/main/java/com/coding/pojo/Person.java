package com.coding.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.PriorityBlockingQueue;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private String name;
    private int age;

}
