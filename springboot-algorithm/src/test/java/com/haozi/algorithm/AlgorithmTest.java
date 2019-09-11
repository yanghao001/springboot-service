package com.haozi.algorithm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * 排序方式
 *
 * @author hao.yang
 * @date 2019/8/22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AlgorithmTest {

    /**
     * 冒泡排序
     * 复杂度： 2^n
     */
    @Test
    public void bubbling() {
        int[] b = {1, 4, 2, 5, 7, 8, 3, 9};
        System.out.println("数组长度=" + b.length);

        int lastExchangeIndex = 0;
        int temp = 0;
        int sortBorder = b.length -1;
        for (int i = 0; i < b.length -1; i++) {
            boolean isSorted = true;
            for (int j = 0; j < sortBorder; j++) {
                if (b[j] < b[j+1]) {
                    temp = b[j];
                    b[j] = b[j + 1];
                    b[j + 1] = temp;
                    isSorted = false; //说明无序
                }
                lastExchangeIndex = j;  //把无序数列的边界更新为最后一次交换元素的位置
            }
            sortBorder = lastExchangeIndex; // 记录最后一次循环边界

            if (isSorted) { // 若有序，跳出循环
                break;
            }
        }

        for (int a : b) {
            System.out.println("数组值：" + a);
        }
    }

    /**
     * 插入排序
     * 复杂度：
     */
    @Test
    public void insertion() {

    }

    /**
     * 快速排序
     */
    @Test
    public void quick() {
        int[] arr = new int[] {4,7,6,5,3,2,10,1};
        arr = quickSort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    public static int[] quickSort(int[] arr, int startIndex, int endIndex) {
        // 递归结束条件：startIndex大等于endIndex的时候
        if (startIndex >= endIndex) {
            return null;
        }
        // 得到基准元素位置
        int pivotIndex = partition(arr, startIndex, endIndex);
        // 根据基准元素，分成两部分递归排序
        quickSort(arr, startIndex, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, endIndex);
        return arr;
    }

    private static int partition(int[] arr, int startIndex, int endIndex) {
        // 取第一个位置的元素作为基准元素
        int pivot = arr[startIndex];
        int left = startIndex;
        int right = endIndex;

        while( left != right) {
            // 控制right指针比较并左移
            while(left<right && arr[right] > pivot){
                right--;
            }
            // 控制left指针比较并右移
            while( left<right && arr[left] <= pivot) {
                left++;
            }
            // 交换left和right指向的元素
            if(left<right) {
                int p = arr[left];
                arr[left] = arr[right];
                arr[right] = p;
            }
        }

        // pivot和指针重合点交换
        int p = arr[left];
        arr[left] = arr[startIndex];
        arr[startIndex] = p;

        return left;
    }


    /**
     * 选择排序
     */
    @Test
    public void choose() {

    }


    /**
     * 堆排序
     */
    @Test
    public void heap() {

    }

    /**
     * 桶排序
     */
    @Test
    public void bucket() {

    }


    /**
     * 计数排序
     */
    @Test
    public void count() {

    }

}
