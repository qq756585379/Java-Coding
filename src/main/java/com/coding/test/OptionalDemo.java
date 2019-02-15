package com.coding.test;

import java.util.Optional;

public class OptionalDemo {
    public static void main(String[] args) throws Throwable {
        //创建 Optional 实例，也可以通过方法返回值得到。
        Optional<String> name = Optional.of("Sanaulla");
        //创建没有值的 Optional 实例，例如值为'null'
        Optional empty = Optional.ofNullable(null);

        //isPresent 方法用来检查 Optional 实例是否有值。
        if (name.isPresent()) {
            //调用 get()返回 Optional 值。
            System.out.println(name.get());
        }

        try {
            //在 Optional 实例上调用 get()抛出 NoSuchElementException。
            System.out.println(empty.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //ifPresent 方法接受 lambda 表达式参数。
        //如果 Optional 值不为空，lambda 表达式会处理并在其上执行操作。
        name.ifPresent((value) -> {
            System.out.println("The length of the value is: " + value.length());
        });

        //如果有值 orElse 方法会返回 Optional 实例，否则返回传入的错误信息。
        System.out.println(empty.orElse("There is no value present!"));
        System.out.println(name.orElse("There is some value!"));

        //orElseGet 与 orElse 类似，区别在于传入的默认值。
        //orElseGet 接受 lambda 表达式生成默认值。
        System.out.println(empty.orElseGet(() -> "Default Value"));
        System.out.println(name.orElseGet(() -> "Default Value"));

        try {
            //orElseThrow 与 orElse 方法类似，区别在于返回值。
            //orElseThrow 抛出由传入的 lambda 表达式/方法生成异常。
            empty.orElseThrow(Exception::new);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //map 方法通过传入的 lambda 表达式修改 Optonal 实例默认值。
        //lambda 表达式返回值会包装为 Optional 实例。
        Optional<String> upperName = name.map((value) -> value.toUpperCase());
        System.out.println(upperName.orElse("No value found"));

        //flatMap 与 map(Funtion)非常相似，区别在于 lambda 表达式的返回值。
        //map 方法的 lambda 表达式返回值可以是任何类型，但是返回值会包装成 Optional 实例。
        //但是 flatMap 方法的 lambda 返回值总是 Optional 类型。
        upperName = name.flatMap((value) -> Optional.of(value.toUpperCase()));
        System.out.println(upperName.orElse("No value found"));

        //filter 方法检查 Optiona 值是否满足给定条件。
        //如果满足返回 Optional 实例值，否则返回空 Optional。
        Optional<String> longName = name.filter((value) -> value.length() > 6);
        System.out.println(longName.orElse("The name is less than 6 characters"));

        //另一个示例，Optional 值不满足给定条件。
        Optional<String> anotherName = Optional.of("Sana");
        Optional<String> shortName = anotherName.filter((value) -> value.length() > 6);
        System.out.println(shortName.orElse("The name is less than 6 characters"));
    }
}
