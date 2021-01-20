package com.other;

public class Other {
    public static void quickSort(int[] nums,int left,int right) {
        if (left - right <= 0){
            return;
        }
        int l = left;
        int r = right;

        int select = nums[l];

        while (l < r) {
            while (r > l && nums[r] >= select) {
                r--;
            }

            nums[l] = nums[r];

            while (l < r && nums[l] <= select) {
                l++;
            }

            nums[r] = nums[l];
        }

        nums[l] = select;
        quickSort(nums,left,l - 1);
        quickSort(nums,r + 1,right);
    }
}
