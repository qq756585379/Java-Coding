package com.coding.sort;

public class Test {

    private static void f(int arr[]) {
        int[] temp = new int[21];
        for (int i : arr) {
            // System.out.println(i);
            // System.out.println(temp[i]);
            temp[i]++;
            // System.out.println(temp[i]);
        }
        System.out.println("-----------------");
        for (int i : temp) {
            System.out.println(i);
        }
        System.out.println("-----------------");
        //顺序打印
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < temp[i]; j++) {
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) {
        int arr[] = {20, 19, 16, 18, 17, 2, 5, 1};
        f(arr);
    }
}
