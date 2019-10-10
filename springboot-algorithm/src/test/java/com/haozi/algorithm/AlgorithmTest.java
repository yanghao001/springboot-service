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
        int sortBorder = b.length - 1;
        for (int i = 0; i < b.length - 1; i++) {
            boolean isSorted = true;
            for (int j = 0; j < sortBorder; j++) {
                if (b[j] < b[j + 1]) {
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
        int[] arr = {3, 6, 4, 2, 9, 10, 11};
        int[] sort = insertionSort(arr);
        Arrays.stream(sort).forEach(System.out::println);
    }

    public static int[] insertionSort(int[] arr) {
        int i, j, temp, len = arr.length;

        // 从第2个元素开始，和前面有序的数列中的元素依次进行比较，直到找到小于它的位置
        for (i = 1; i < len; i++) {
            temp = arr[i];

            // 最多和前面的i-1个数进行比较
            for (j = i - 1; j >= 0 && arr[j] > temp; j--) {
                arr[j + 1] = arr[j]; // 如果arr[j]>arr[i]，则后移一个位置
            }
            // 如果arr[j] <= arr[i]，则将arr[i]插入到j+1的位置，当前这个位置正好有空位，因为后面比arr[i]大的元素都后移了一个位置
            arr[j + 1] = temp;
        }
        return arr;
    }

    /**
     * 快速排序
     */
    @Test
    public void quick() {
        int[] arr = new int[]{4, 7, 6, 5, 3, 2, 10, 1};
        arr = quickSort(arr, 0, arr.length - 1);
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

        while (left != right) {
            // 控制right指针比较并左移
            while (left < right && arr[right] > pivot) {
                right--;
            }
            // 控制left指针比较并右移
            while (left < right && arr[left] <= pivot) {
                left++;
            }
            // 交换left和right指向的元素
            if (left < right) {
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

    /**
     * 二分法
     */
    @Test
    public int biSearch(int[] array, int a) {
        int lo = 0;
        int hi = array.length - 1;
        int mid;
        while (lo <= hi) {
            mid = (lo + hi) / 2;
            if (array[mid] == a) {
                return mid + 1;
            } else if (array[mid] < a) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return -1;
    }


}
