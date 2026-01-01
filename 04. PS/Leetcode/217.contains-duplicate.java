/*
 * @lc app=leetcode id=217 lang=java
 *
 * [217] Contains Duplicate
 */

// @lc code=start

import java.util.Arrays;

class Solution {
    public boolean containsDuplicate(int[] nums) {
        int len = nums.length;

        Arrays.sort(nums);

        for(int i=0; i<len-1; i++){
            if(nums[i] == nums[i+1])
                return true;
        }
        return false;
    }
}
// @lc code=end

