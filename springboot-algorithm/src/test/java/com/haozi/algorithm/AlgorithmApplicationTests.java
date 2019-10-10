package com.haozi.algorithm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlgorithmApplicationTests {

    @Test
    public void contextLoads() {
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


    @Test
    public void shellSort() {

    }

    /**
     * 选择排序
     */
    @Test
    public void selectTest() {
        int arr[] = {49, 38, 65, 97, 76, 13, 27, 49};
        int[] selectionArray = selectSort(arr);
        Arrays.stream(selectionArray).forEach(System.out::println);
    }

    public int[] selectSort(int[] arr) {
        int i, j, temp, min, len = arr.length;
        // 1. 第一步找出最小值
        for (i = 0; i < len; i++) {
            min = i;
            for (j = i; j < len - i; j++) { // 从len-i个元素中找出最小值
                if (arr[min] > arr[j]) {
                    min = j;
                }
            }
            // 2. 交换位置
            temp = arr[min];
            arr[min] = arr[i];
            arr[i] = temp;
        }
        return arr;
    }

    /**
     * 桶排序
     */
    @Test
    public void bucketTest() {
        int[] arr = {8, 2, 3, 4, 3, 6, 6, 3, 9};
        bucketSort(arr, 10);
        System.out.println(Arrays.toString(arr));
    }

    public void bucketSort(int[] arr, int max) {
        int[] buckets;
        if (arr.length == 0) {
            return;
        }

        // 1. 创建buckets数组(arr数组值为其下标， 数组值为出现次数)，max比arr数组最大值大，buckets初始化
        buckets = new int[max];
        for (int i = 0; i < arr.length; i++) {
            buckets[arr[i]]++; // arr数组值为其下标， 数组值为出现次数
        }

        // 2. 按照buckets数组下标排序
        for (int i = 0, j = 0; i < arr.length; i++) {
            while (buckets[i]-- > 0) {
                arr[j++] = i; // buckets下标值为arr数组值
            }
        }
    }

    /**
     * 堆排序
     */
    @Test
    public void heapTest() {
        int[] arr = {8, 2, 3, 4, 3, 6, 6, 3, 9};
        int size = arr.length;
        for (int i = 0; i < size; i++) {
            maxHeapify(arr, size - i);

            // 交换
            int temp = arr[0];
            arr[0] = arr[(size - 1) - i];
            arr[(size - 1) - i] = temp;
        }
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 完成一次建堆，最大值在堆的顶部(根节点)
     */
    public void maxHeapify(int[] arrays, int size) {
        // 从数组的尾部开始，直到第一个元素(角标为0)
        for (int i = size - 1; i >= 0; i--) {
            heapify(arrays, i, size);
        }
    }

    /**
     * 建堆
     *
     * @param arrays          看作是完全二叉树
     * @param currentRootNode 当前父节点位置
     * @param size            节点总数
     */
    public void heapify(int[] arrays, int currentRootNode, int size) {

        if (currentRootNode < size) {
            // 1. 左子树和右子树位置
            int left = 2 * currentRootNode + 1;
            int right = 2 * currentRootNode + 2;

            // 2. 把当前父节点看作最大的
            int max = currentRootNode;

            // 3. 如果比当前根元素要大，记录它的位置
            if (left < size) {
                if (arrays[max] < arrays[left]) {
                    max = left;
                }
            }

            // 4. 如果比当前根元素要大，记录它的位置
            if (right < size) {
                if (arrays[max] < arrays[right]) {
                    max = right;
                }
            }

            // 5. 如果最大的不是根元素位置，那么就交换
            if (max != currentRootNode) {
                int temp = arrays[max];
                arrays[max] = arrays[currentRootNode];
                arrays[currentRootNode] = temp;

                // 6. 继续比较，直到完成一次建堆
                heapify(arrays, max, size);
            }
        }
    }

    /**
     * 归并排序
     */
    @Test
    public void test() {
        int[] a = {49, 38, 65, 97, 76, 13, 27, 50};
        mergeSort(a, 0, a.length - 1);
        System.out.println("排好序的数组：");
        for (int e : a)
            System.out.print(e + " ");
    }

    // 两路归并算法，两个排好序的子序列合并为一个子序列
    public void merge(int[] a, int left, int mid, int right) {
        int[] tmp = new int[a.length]; // 辅助数组
        int p1 = left, p2 = mid + 1, k = left; // p1、p2是检测指针，k是存放指针

        // 1. 比较数组排序
        while (p1 <= mid && p2 <= right) {
            if (a[p1] <= a[p2])
                tmp[k++] = a[p1++];
            else
                tmp[k++] = a[p2++];
        }

        // 2. 如果序列未检测完，直接将后面所有元素加到合并的序列中
        while (p1 <= mid)
            tmp[k++] = a[p1++]; // 如果第一个序列未检测完，直接将后面所有元素加到合并的序列中
        while (p2 <= right)
            tmp[k++] = a[p2++]; // 同上

        // 3. 复制回原素组
        for (int i = left; i <= right; i++)
            a[i] = tmp[i];
    }

    public void mergeSort(int[] a, int start, int end) {
        if (start < end) { // 当子序列中只有一个元素时结束递归
            int mid = (start + end) / 2; // 划分子序列
            mergeSort(a, start, mid); // 对左侧子序列进行递归排序
            mergeSort(a, mid + 1, end); // 对右侧子序列进行递归排序
            merge(a, start, mid, end); // 合并
        }
    }

}
