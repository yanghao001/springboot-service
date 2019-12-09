package com.haozi.jdk8.TestJdk8;

import com.haozi.jdk8.model.Dish;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author hao.yang
 * @date 2019/9/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Jdk8Inaction {

    /**
     * 函数式编程1
     */
    public void code() {
        // 传统
        Consumer c1 = new Consumer() {
            @Override
            public void accept(Object o) {
                System.out.println(o);
            }
        };

        // 函数式编程
        Consumer c2 = (o) -> {
            System.out.println(o);
        };

        // 或者
        Consumer c3 = (o) -> System.out.println(o);

        // 或者
        Consumer c4 = System.out::println;
    }

    /**
     * 常见函数式接口Consumer, Supplier, Function, Predicate,
     *  BinaryOperator, UnaryOperator
     * */

    /**
     * 1: consumer
     * 是消费，即针对某个东西我们来使用它，因此它包含一个有形参无返回值的accept接口方法
     * andThen方法可以多次调用consumer
     */
    @Test
    public void consumerTest() {
        Consumer f1 = System.out::println;
        Consumer f2 = n -> System.out.print(n + "-f2");
        f2.andThen(f1).andThen(f1).andThen(f1).accept("test1");
    }

    /**
     * Supplier 代表的含义是“提供者”，因此它含有一个get方法，没有入参只能输出
     */
    @Test
    public void supplierTest() {
        Supplier<String> supplier = () -> "hello";
        supplier.get();
    }

    /**
     * Function也是一个函数式编程接口；它代表的含义是“函数”，而函数经常是有输入输出的，因此它含有一个apply方法，
     * 包含一个入参与一个返回值，可以用作装箱或者拆箱某个对象
     */
    @Test
    public void functionTest() {
        Function<Integer, Integer> f = s -> s++;
        Function<Integer, Integer> g = s -> s * 2;
        /**
         * 下面表示在执行F时，先执行G，并且执行F时使用G的输出当作输入。
         * 相当于以下代码：
         * Integer a = g.apply(1);
         * System.out.println(f.apply(a));
         */
        System.out.println(f.compose(g).apply(1));

        /**
         * 表示执行F的Apply后使用其返回的值当作输入再执行G的Apply；
         * 相当于以下代码
         * Integer a = f.apply(1);
         * System.out.println(g.apply(a));
         */
        System.out.println(f.andThen(g).apply(1));
        System.out.println(Function.identity().apply("A"));
    }

    /**
     *  predicate
     *  Predicate为函数式接口，predicate的中文意思是“断定”，意为判断某个东西是否满足某种条件；因此它包含test方法，
     *  根据输入值来做逻辑判断，其结果为True或者False，可以用作过滤对象
     *
     * */
    @Test
    public void predicateTest() {
        Predicate<String> p = o -> o.equals("test");
        Predicate<String> g = o -> o.equals("t");

        /**
         * negate: 用于对原来的Predicate做取反处理；
         * 如当调用p.test("test")为True时，调用p.negate().test("test")就会是False；
         */
        Assert.assertFalse(p.negate().test("test"));

        /**
         * and: 针对同一输入值，多个Predicate均返回True时返回True，否则返回False；
         */
        Assert.assertTrue(p.and(g).test("test"));

        /**
         * or: 针对同一输入值，多个Predicate只要有一个返回True则返回True，否则返回False
         */
        Assert.assertTrue(p.or(g).test("ta"));
    }


    /**
     * 流式计算
     * list ==》流式处理
     */
    @Test
    public void testStream() {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));

        List<String> names = new ArrayList<>();
        Iterator<Dish> iterator = menu.iterator();
        while (iterator.hasNext()) {
            Dish d = iterator.next();
            names.add(d.getName());
        }
        System.out.println("一般做法while循环，得name集合:" + names.toString());

        List<String> names1 = menu.stream().map(Dish::getName).collect(Collectors.toList());
        System.out.println("转换成流式计算，得name集合:" + names1.toString());

        List<String> names2 = menu.parallelStream().map(Dish::getName).collect(Collectors.toList());
        System.out.println("并行流式计算，得name集合:" + names2.toString());

        List<String> names3 = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("条件循环，查找calories大于300的人且前3个，得集合name：" + names3.toString());

        List<String> names4 = menu.stream()
                .filter(d -> {
                    System.out.println("filtering:" + d.getName());
                    return d.getCalories() > 300;
                })
                .map(d -> {
                    System.out.println("mapping:" + d.getName());
                    return d.getName();
                })
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("条件查询后name:" + names4.toString());

        long count = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .distinct()
//                .limit(3) // 只显示前三个
                .skip(2) //查询之后从第三个开始显示
                .count();
        System.out.println("count:" + count);

        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    public void testStream1() {
        List<String> words = Arrays.asList("Java8", "Lambdas", "In", "Action");
        List<Integer> wordLengths = words.stream().map(String::length).collect(Collectors.toList());
        System.out.println("打印出字符串长度：" + wordLengths.toString());

        List<String[]> pp = words.stream()
                .map(word -> word.split(""))
                .distinct()
                .collect(Collectors.toList());
        System.out.println(pp);
    }

    @Test
    public void testStream2() {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));
        List<Integer> dishNameLengths = menu.stream().map(Dish::getName).map(String::length).collect(Collectors.toList());
        System.out.println("打印姓名长度：" + dishNameLengths);
    }

    /**
     * 文档读出不重复字符数
     */
    @Test
    public void testStream3() {
        long uniqueWords = 0;
        try (Stream<String> lines = Files.lines(Paths.get("data.txt"), Charset.defaultCharset())) {
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(""))) // 转化stream流
                    .distinct()  // 去除重复
                    .count(); // 计数
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("unique words:" + uniqueWords);
    }


    /**
     * 斐波拉契数列1
     */
    @Test
    public void printFibonacci() {
        // 传统
        int previous = 0;
        int current = 1;
        System.out.println(previous);
        System.out.println(current);
        for (int i = 0; i < 10; i++) {
            int oldPrevious = previous;
            int nextValue = previous + current;
            previous = current;
            current = nextValue;
            System.out.println(current);
        }

        // 流式处理
        Stream.iterate(new int[]{0, 1},
                t -> new int[]{t[1], t[0] + t[1]})
                .limit(20)
                .map(t -> t[0])
                .forEach(System.out::println);
    }

    /**
     * 斐波拉契数列2
     */
    @Test
    public void printFibonacci2() {
        IntSupplier fib = new IntSupplier() {
            private int previous = 0;
            private int current = 1;

            @Override
            public int getAsInt() {
                int oldPrevious = this.previous;
                int nextValue = this.previous + this.current;
                this.previous = current;
                this.current = nextValue;
                return oldPrevious;
            }
        };
        IntStream.generate(fib).limit(10).forEach(System.out::println);
    }

    /**
     * 随机数
     */
    @Test
    public void printRandom() {
        int p = (int) (Math.random() * 10);
        System.out.println("p=" + p);

        Stream.generate(Math::random)
                .limit(10)
                .forEach(System.out::println);
    }

    @Test
    public void test() {
        System.out.println(parallelSum(10));
    }

    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i+1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
    }

}
