package com.haozi.algorithm;

import com.haozi.algorithm.model.TreeNode;
import com.haozi.algorithm.model.TreeRoot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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

    /**
     * 静态二叉树
     */
    @Test
    public void TreeTest() {
        //根节点-->10
        TreeNode treeNode1 = new TreeNode(10);

        //左孩子-->9
        TreeNode treeNode2 = new TreeNode(9);

        //右孩子-->20
        TreeNode treeNode3 = new TreeNode(20);

        //20的左孩子-->15
        TreeNode treeNode4 = new TreeNode(15);

        //20的右孩子-->35
        TreeNode treeNode5 = new TreeNode(35);

        // 构建二叉树
        // 根节点下2，3子节点
        treeNode1.setLeftNode(treeNode2);
        treeNode1.setRightNode(treeNode3);

        // 3节点下4，5子节点
        treeNode3.setLeftNode(treeNode4);
        treeNode3.setRightNode(treeNode5);

    }

    /**
     * 先序遍历：根-左-右
     */
    public void preTraverseTree(TreeNode rootTreeNode) {
        if (rootTreeNode != null) {
            System.out.println(rootTreeNode.getValue()); // 根
            preTraverseTree(rootTreeNode.getLeftNode()); // 左
            preTraverseTree(rootTreeNode.getRightNode()); // 右
        }
    }

    /**
     * 中序遍历： 左-根-右
     *
     * @param rootTreeNode 根节点
     */
    public void inTraverseTree(TreeNode rootTreeNode) {
        if (rootTreeNode != null) {
            inTraverseTree(rootTreeNode.getLeftNode()); // 左
            System.out.println(rootTreeNode.getValue()); // 根
            inTraverseTree(rootTreeNode.getRightNode()); // 右
        }
    }

    /**
     * 后序遍历： 左-右-根
     *
     * @param rootTreeNode 根节点
     */
    public void afterTraverseTree(TreeNode rootTreeNode) {
        if (rootTreeNode != null) {
            afterTraverseTree(rootTreeNode.getLeftNode()); // 左
            afterTraverseTree(rootTreeNode.getRightNode()); // 右
            System.out.println(rootTreeNode.getValue()); // 根
        }
    }

    /**
     * 二叉树创建
     */
    @Test
    public void dynamicTest() {
        int[] arr = {3, 2, 1, 4, 5};
        // 动态创建树
        TreeRoot root = new TreeRoot();
        for (int value : arr) {
            dynamicCreateTree(root, value);
        }

        System.out.println("先序遍历");
        preTraverseTree(root.getTreeroot());
        System.out.println("中序遍历");
        inTraverseTree(root.getTreeroot());

        int height = getHeight(root.getTreeroot());
        System.out.println("height=" + height);
    }

    /**
     * 动态创建二叉树
     *
     * @param treeRoot 根节点
     * @param value    节点的值
     */
    public void dynamicCreateTree(TreeRoot treeRoot, int value) {

        // 1. 如果根为空（第一次访问），将第一个值作为根节点
        if (treeRoot.getTreeroot() == null) {
            TreeNode treeNode = new TreeNode(value);
            treeRoot.setTreeroot(treeNode);
        } else {
            // 2.当前根
            TreeNode tempRoot = treeRoot.getTreeroot();
            while (tempRoot != null) {
                // 2.1 当前值大于根值，往右边走
                if (value > tempRoot.getValue()) {
                    // 2.1.0 右边没有根，则插入
                    if (tempRoot.getRightNode() == null) {
                        tempRoot.setRightNode(new TreeNode(value));
                        return;
                    } else {  // 2.1.1 右边有根，到树根右边
                        tempRoot = tempRoot.getRightNode();
                    }

                } else { // 2.2 当前值小于根值，向左走
                    // 2.2.0 左边没有根，直接插入
                    if (tempRoot.getLeftNode() == null) {
                        tempRoot.setLeftNode(new TreeNode(value));
                        return;
                    } else { // 2.2.1 左边有根，到根左边
                        tempRoot = tempRoot.getLeftNode();
                    }
                }
            }
        }
    }

    public static int getHeight(TreeNode treeNode) {
        if (treeNode == null) {
            return 0;
        } else {
            // 1. 左边的子树深度
            int left = getHeight(treeNode.getLeftNode());
            // 2. 右边的子树深度
            int right = getHeight(treeNode.getRightNode());

            int max = left;
            if (right > max) {
                max = right;
            }
            return max + 1;
        }
    }

    @Test
    public void test8() {
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        numbers.stream().map(i -> i * i).forEach(System.out::println);
    }


    /**
     * 测试string的不可变性
     */
    @Test
    public void testString() {
        String str = "abs -cdi45";
        String str2 = str.trim();
        System.out.println(str2); // 空格未去除，String不可变，实质String为final
    }

    @Test
    public void transfer() {
        System.out.println(new Date().toInstant().getEpochSecond());
    }

    @Test
    public void testTime() throws ParseException {
        String time = "2019-11-02 08:38:57";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        int timestamp = (int) sdf.parse(time).getTime();
//        int timestamp = sdf.parse(time).getTime() / 1000;
        int timestamps = (int) (sdf.parse(time).getTime() / 1000);
        System.out.println("timestamp:" + timestamps);
    }

    @Test
    public void UTCToLocalTime() {
        String utcTime = "2019-06-14 09:37:50.788";
        String utcTimePatten = "yyyy-MM-dd HH:mm:ss.SSSXXX";
        String localTimePatten = "yyyy-MM-dd HH:mm:ss.SSS";
        String localTime = "2019-11-07 10:52:43";
        Date time = localToUTC(localTime);
        System.out.println(time);
        System.out.println(utcToLocal(utcTime, "8"));
    }

    /**
     * Description: 本地时间转化为UTC时间
     *
     * @param localTime
     * @return
     */
    public static Date localToUTC(String localTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date localDate = null;
        try {
            localDate = sdf.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long localTimeInMillis = localDate.getTime();
        /** long时间转换成Calendar */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(localTimeInMillis);
        /** 取得时间偏移量 */
        int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);
        /** 取得夏令时差 */
        int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);
        /** 从本地时间里扣除这些差量，即可以取得UTC时间*/
        calendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        /** 取得的时间就是UTC标准时间 */
        Date utcDate = new Date(calendar.getTimeInMillis());
        return utcDate;
    }

    /**
     * Description:UTC时间转化为本地时间
     *
     * @param utcTime
     * @return
     */
    public static Date utcToLocal(String utcTime, String timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date utcDate = null;
        try {
            utcTime = utcTime + "+" + timeZone;
            utcDate = sdf.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.setTimeZone(TimeZone.getDefault());
        Date locatlDate = null;
        String localTime = sdf.format(utcDate.getTime());
        try {
            locatlDate = sdf.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return locatlDate;
    }

    @Test
    public void testDate() throws ParseException {
        Date date = new Date(1503544630000L);  // 对应的北京时间是2017-08-24 11:17:10
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat bjSdf = new SimpleDateFormat("yyyy-MM-dd HH:00:00");     // 北京
        bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));  // 设置北京时区

        SimpleDateFormat tokyoSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  // 东京
        tokyoSdf.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));  // 设置东京时区

        SimpleDateFormat londonSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 伦敦
        londonSdf.setTimeZone(TimeZone.getTimeZone("Europe/London"));  // 设置伦敦时区

        System.out.println("毫秒数:" + date.getTime() + ", UTC时间:" + sdf.format(date));
        System.out.println("毫秒数:" + date.getTime() + ", 北京时间:" + bjSdf.format(date));
        System.out.println("毫秒数:" + date.getTime() + ", 东京时间:" + tokyoSdf.format(date));
        System.out.println("毫秒数:" + date.getTime() + ", 伦敦时间:" + londonSdf.format(date));
        String time = "2019-11-22 14:08:12";
        System.out.println("test" + sdf.parse(time));
    }


    @Test
    public void testStringBuilder() {
        String tmpConnection = String.format("<", "111", ",", "2222", ">");
        System.out.println("=====" + tmpConnection);
    }

    @Test
    public void testConvertTime() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTimeStr = "2019-10-30 23:54:00";
        String stopTimeStr = "2019-10-31 00:54:00";
        long startTime = sdf.parse(startTimeStr).getTime();
        long stopTime = sdf.parse(stopTimeStr).getTime();
        long off = stopTime - startTime;
        int startMin = (int) (startTime / 60000);
        int stopMin = (int) (stopTime / 60000);
        System.out.println("startTime:" + startTime);
        System.out.println("stopTime:" + stopTime);
        System.out.println("off:" + (int) off/60000);

        // 求小时
        // 本地开始时间
        Date date = sdf.parse(startTimeStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        System.out.println("hour=" + hour);
        System.out.println("minute=" + minute);
    }

}
