package com.aop;

public class PermissionVerification {

    public void canLogin() {
        //做一些登陆校验
        System.err.println("我正在校验啦！！！！");
    }

    public void saveMessage() {
        //做一些后置处理
        System.err.println("我正在处理啦！！！！");
    }

}
