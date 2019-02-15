package com.coding.test;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LambdaTest {

    public static void main(String[] args) {
        Predicate<String> startsWithJ = (n) -> n.startsWith("J");
        Predicate<String> fourLetterLong = (n) -> n.length() == 4;

    }

    private void test1() {
        // Java 8 之前:
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Before Java8, too much code for too little to do");
            }
        }).start();

        //Java 8 方式:
        new Thread(() -> System.out.println("In Java8, Lambda expression rocks !!")).start();
    }

    private void test2() {
        // Java 8 之前:
        List list = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        for (Object feature : list) {
            System.out.println(feature);
        }

        // Java 8 之后:
        List features2 = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        features2.forEach(n -> System.out.println(n));
    }

    private static void filter(List<String> names, Predicate condition) {
        for (String name : names) {
            if (condition.test(name)) {
                System.out.println(name + " ");
            }
        }
    }

    // filter 更好的办法--filter 方法改进
    public static void filter2(List<String> names, Predicate condition) {
        names.stream().filter((name) -> (condition.test(name))).forEach((name) -> {
            System.out.println(name + " ");
        });
    }

    private void test3() {
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);

        // 不使用 lambda 表达式为每个订单加上 12%的税
        for (Integer cost : costBeforeTax) {
            double price = cost + .12 * cost;
            System.out.println(price);
        }

        // 使用 lambda 表达式
        costBeforeTax.stream().map((cost) -> cost + .12 * cost).forEach(System.out::println);
    }

    private void test4() {
        // 创建一个字符串列表，每个字符串长度大于 2
        List<String> strList = Arrays.asList("abc", "bcd", "defg", "jk");
        List<String> filtered = strList.stream().filter(x -> x.length() > 2).collect(Collectors.toList());
        System.out.printf("Original List : %s, filtered list : %s %n", strList, filtered);
    }

    private void test5() {
        // 将字符串换成大写并用逗号链接起来
        List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.", "Canada");
        String G7Countries = G7.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
        System.out.println(G7Countries);
    }

    private void test6() {
        // 用所有不同的数字创建一个正方形列表
        List<Integer> numbers = Arrays.asList(9, 10, 3, 4, 7, 3, 4);
        List<Integer> distinct = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());
        System.out.printf("Original List : %s, Square Without duplicates : %s %n", numbers, distinct);
    }

    private void test7() {
        //获取数字的个数、最小值、最大值、总和以及平均值
        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
        IntSummaryStatistics stats = primes.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("Highest prime number in List : " + stats.getMax());
        System.out.println("Lowest prime number in List : " + stats.getMin());
        System.out.println("Sum of all prime numbers : " + stats.getSum());
        System.out.println("Average of all prime numbers : " + stats.getAverage());
    }
}
