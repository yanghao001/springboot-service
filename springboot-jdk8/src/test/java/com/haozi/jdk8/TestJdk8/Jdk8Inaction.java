package com.haozi.jdk8.TestJdk8;

import com.haozi.jdk8.model.Dish;
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
import java.util.function.IntSupplier;
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
     * lamada表达式
     */
    @Test
    public void testLamada1() {
        System.out.println("lamada test");
    }

    /**
     * lamada表达式
     */
    @Test
    public void testLamada2() {

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
        Stream.generate(Math::random)
                .limit(10)
                .forEach(System.out::println);
    }


}
