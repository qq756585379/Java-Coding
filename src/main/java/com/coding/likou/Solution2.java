package com.coding.likou;

/**
 * 给定一个整数数组，判断是否存在重复元素。
 * 如果任何值在数组中出现至少两次，函数返回 true。如果数组中每个元素都不相同，则返回 false。
 */
public class Solution2 {

    private static boolean containsDuplicate(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int temp = nums[0];
        boolean isExist = false;
        for (int pre = 1; pre < nums.length; pre++) {
            if (temp == nums[pre]) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 3, 3, 4, 3, 2, 4, 2};
        if (containsDuplicate(nums)) {
            System.out.println("exist");
        } else {
            System.out.println("not exist");
        }
    }
}
