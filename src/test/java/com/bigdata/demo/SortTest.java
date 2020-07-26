package com.bigdata.demo;

import io.swagger.models.auth.In;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SortTest {

    public static int demo1(int[] arr, int lenth, int currentValue) {
        int low = 0;
        int height = lenth - 1;
        int mid;
        while (low < height) {
            mid = (height - low) * (currentValue - arr[low]) / (arr[height] - arr[low]);
            currentValue = arr[mid];
            if (arr[mid] > currentValue) {
                height = mid - 1;
            } else if (arr[mid] < currentValue) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static boolean isBug(int versionNo) {
        //假设 4 出现问题
        if (versionNo == 4) {
            return true;
        }
        return false;
    }

    public static Map demo2(String str) {
        TreeMap<String, Long> result = Arrays.stream(str.split(""))
                .sorted()
                .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));
        return result;
    }

    /**
     * 有一个字符串A 有一个字符串B 想要从A转换到B，只能一次一次转换，每次转换要把字符串A中的一个字符全部转换成另一个字符，
     * 求字符串A能不能转换成字符串B。例如 "abc" -- "bbc" --- "ddc" 判断转换是否成立
     */
    public static boolean isConvert(String A, String B, int index) {
        // 字符串转换为字符数组
        char[] source = A.toCharArray();
        char[] target = B.toCharArray();
        // 获取要替换的字符
        char m = target[index];
        // 获取被替换的字符
        char f = source[index];
        // 遍历原字符数组
        for (int i = 0; i < source.length; i++) {
            // 如果是和需要替换的字符相同
            if (source[i] == f) {
                source[i] = m;
            }
        }
        // 字符数组转换为String类型
        A = arrayToString(source);
        B = arrayToString(target);

        // 判断是否到了最后一位
        if ((index == A.length() - 1)) {
            if (A.trim().equals(B.trim())) {
                return true;
            } else {
                return false;
            }
        }
        index++;
        // 递归判断
        return isConvert(A, B, index);

    }

    public static String arrayToString(char[] c) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < c.length; i++) {
            sb.append(c[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int[] ints = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
        System.out.println(demo1(ints, 19, 7));
        System.out.println(demo2("aaaabbbccc"));
        String s = "abb";
        String s1 = "aca";
        System.out.println(isConvert(s, s1, 0));
        int[] array = new int[]{14, 1, 56, 11, 222, 555, 2, 44, 12, 52, 86, 112};
        int[] merge = quick(array,7);
        for (int i = 0;i<array.length;i++){
            System.out.println(merge[i]);
        }
    }

    public static int[] maopao(int[] arr) {
        int temp;
        int maxIndex = arr.length-1;
        for (int i = 0; i < arr.length-1; i++) {
            for (int j=arr.length-i-1;j>=0;j--){
                if (arr[j]>arr[maxIndex]){
                    temp = arr[maxIndex];
                    arr[maxIndex]=arr[j];
                    arr[j]=temp;
                }
            }
            maxIndex--;
        }
        return arr;
    }
    public static int[] choose(int[] arr){
        int temp ;
        for (int k=0;k<arr.length;k++) {
            int minIndex = k;
            for (int i = k+1 ; i < arr.length; i++) {
                if (arr[i] < arr[minIndex]) {
                    minIndex = i;
                }
            }
            temp = arr[minIndex];
            arr[minIndex] = arr[k];
            arr[k] = temp;
        }
        return arr;
    }

    public static int[] insert(int[] arr){
        for (int i =1 ;i<arr.length;i++){
            int currIndex = i;
            int currValue = arr[i];
            int inserIndex =-1;
            for (int j =i-1;j>=0;j--){
                if (currValue<arr[j]){
                    inserIndex=j;
                    arr[j+1] = arr[j];
                    continue;
                } else break;
            }
            if (inserIndex!=-1)arr[inserIndex]=currValue;
        }
        return arr;
    }

    public static int[] merge(int[] arr){
        int left = 0;
        int mid = -1 ;
        int right = arr.length;
        if (arr.length==1)return arr;
        if (arr.length==2){
            if (arr[0]>arr[1]){
                int temp = arr[0];
                arr[0] = arr[1];
                arr[1] = temp;
            }
            return arr;
        }
        mid = arr.length/2;
        int[] lowArry = new int[mid];
        int[] heightArry = new int[right-mid];
        for (int i = 0;i<mid;i++){
            lowArry[i] = arr[i];
        }
        for (int i = 0;i<right-mid;i++){
            heightArry[i] = arr[mid+i];
        }
        return reduce(merge(lowArry),merge(heightArry));
    }

    public static int[] reduce(int[] leftArr,int[] rightArr){
        int[] result = new int[leftArr.length+rightArr.length];
        int leftIndex =0,rightIndex = 0;
        int currIndex = 0;
        while (currIndex<result.length&&leftIndex<leftArr.length&&rightIndex<rightArr.length){
            if (leftArr[leftIndex]<=rightArr[rightIndex]){
                result[currIndex] = leftArr[leftIndex];
                leftIndex++;
            }else if (rightArr[rightIndex]<leftArr[leftIndex]){
                result[currIndex] = rightArr[rightIndex];
                rightIndex++;
            }
            currIndex ++;
        }
        if (leftIndex<leftArr.length){
            for (int i = leftIndex;leftIndex<leftArr.length;leftIndex++){
                result[currIndex] = leftArr[leftIndex];
                currIndex++;
            }
        }
        if (rightIndex<rightArr.length){
            for (int i = rightIndex;rightIndex<rightArr.length;rightIndex++){
                result[currIndex] = rightArr[rightIndex];
                currIndex++;
            }
        }
        return result;
    }

    public static int[]  quick(int[] arr,int midIndex){
        int left =0;
        int right = arr.length;
        if (arr.length<=1)return arr;
        if (arr.length==2){
            if (arr[0]>arr[1]){
                int temp = arr[0];
                arr[0] = arr[1];
                arr[1] = temp;
            }
            return arr;
        }
        int[] leftArr ;
        int[] rightArr;
        int midValue = arr[midIndex];
        int leftIndex=0,rightIndex=0;
        int leftLenth=0,rightLenth=0;
        for (int i = 0;i<arr.length;i++){
           if (arr[i]<midValue){
                leftLenth++;
            }
            if (arr[i]>midValue){
               rightLenth++;
            }
        }
        leftArr = new int[leftLenth];
        rightArr = new int[rightLenth];
        for (int i = 0;i<arr.length;i++){
            if (arr[i]<midValue){
                leftArr[leftIndex++]=arr[i];
            }
            if (arr[i]>midValue){
                rightArr[rightIndex++]=arr[i];
            }
        }
        return concatIntArray(quick(leftArr,leftArr.length/2) , new int[]{ midValue},quick(rightArr,rightArr.length/2));
    }

    public static int[] concatIntArray(int[]... args){
        int length =0;
        for (int[] arr:args){
            length+=arr.length;
        }
        int[] result = new int[length];
        int resultIndex = 0;
        for (int[] arr:args){
           for (int i =0;i< arr.length;i++){
               result[resultIndex++] = arr[i];
           }
        }
        return result;
    }

}
