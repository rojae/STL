/*
 * @lc app=leetcode id=153 lang=java
 *
 * [153] Find Minimum in Rotated Sorted Array
 */

// @lc code=start
class Solution {
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while(left < right){
            // escape overflow
            // case1. (left + right) / 2 (warn overflow)
            // case2. left + (right - left) / 2 
            // case1 is same case2
            int mid = left + (right - left) / 2;
            if(nums[right] < nums[mid]){
                left = mid + 1;
            }
            else{
                right = mid;
            }
        }

        return nums[left];
    }
}

// @lc code=end

