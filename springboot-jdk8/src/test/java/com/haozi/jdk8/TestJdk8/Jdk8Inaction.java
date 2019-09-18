package com.haozi.jdk8.TestJdk8;

import com.haozi.jdk8.model.Dish;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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


}
