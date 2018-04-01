package com.aop;

import com.service.Subject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringAopTest {

    /**
     * AOP中的基本概念
     * <p>
     * 1.通知(Adivce) 通知有5种类型：
     * Before 在方法被调用之前调用
     * After 在方法完成后调用通知，无论方法是否执行成功
     * After-returning 在方法成功执行之后调用通知
     * After-throwing 在方法抛出异常后调用通知
     * Around 通知了好、包含了被通知的方法，在被通知的方法调用之前后调用之后执行自定义的行为
     * <p>
     * 2.切点（Pointcut）
     * <p>
     * 3.连接点（Join point）
     * <p>
     * 4.切面（Aspect）
     * <p>
     * 5.引入（Introduction）引用允许我们向现有的类添加新的方法或者属性
     * <p>
     * 6.织入（Weaving）
     */
    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("SpringAOP.xml");

        Subject subject1 = (Subject) ctx.getBean("SubjectImpl1");
        Subject subject2 = (Subject) ctx.getBean("SubjectImpl2");

        subject1.login();
        subject1.download();

        System.err.println("==================");

        subject2.login();
        subject2.download();
    }

}

