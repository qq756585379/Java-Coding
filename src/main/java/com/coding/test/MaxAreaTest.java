package com.coding.test;

/**
 * 求最大面积
 */
public class MaxAreaTest {

    public static void main(String[] args) {
        int[] heights = new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println("max = " + maxArea(heights));
    }

    private static int maxArea(int[] height) {
        int max = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                max = Math.max(max, Math.min(height[i], height[j]) * (j - i));
            }
        }
        return max;
    }

    private int maxArea2(int[] height) {
        int max = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                if (height[i] > height[j]) {
                    if (height[j] * (j - i) >= max) {
                        max = height[j] * (j - i);
                    }
                } else {
                    if (height[i] * (j - i) >= max) {
                        max = height[i] * (j - i);
                    }
                }
            }
        }
        return max;
    }
}
