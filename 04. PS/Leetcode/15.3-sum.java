/*
 * @lc app=leetcode id=15 lang=java
 *
 * [15] 3Sum
 */

// @lc code=start

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> answer = new ArrayList<>();
        Arrays.sort(nums);

        for(int i=0; i<nums.length - 2; i++){
            // duplicate let skip
            if(i > 0 && nums[i] == nums[i-1]){
                continue;
            }
            else{
                // two pointer
                int left = i + 1;
                int right = nums.length - 1;
                int target = -1 * nums[i];

                while(left < right){
                    int sum = nums[left] + nums[right];

                    if(sum == target){
                        answer.add(Arrays.asList(nums[i], nums[left], nums[right]));
                        // duplicate let skip
                        while(left < right && nums[left] == nums[left+1])
                            left++;
                        while(left < right && nums[right] == nums[right-1])
                            right--;
                        left++;
                        right--;
                    }
                    else if(sum < target){
                        left++;
                    }
                    else{
                        right--;
                    }
                }
            }
        }

        return answer;
    }
}
// @lc code=end

