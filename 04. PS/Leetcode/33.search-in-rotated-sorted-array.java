/*
 * @lc app=leetcode id=33 lang=java
 *
 * [33] Search in Rotated Sorted Array
 */

// @lc code=start
class Solution {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while(left <= right){
            int mid = left + (right - left) / 2;

            if(nums[mid] == target)
                return mid;     // answer!

            if (nums[left] <= nums[mid]) {
                // 왼쪽 구간이 정렬됨
                if (nums[left] <= target && target < nums[mid])
                    right = mid - 1;  // target이 왼쪽 정렬 구간에 있음
                else
                    left = mid + 1;
            } else {
                // 오른쪽 구간이 정렬됨
                if (nums[mid] < target && target <= nums[right])
                    left = mid + 1;  // target이 오른쪽 정렬 구간에 있음
                else
                    right = mid - 1;    // target이 오른쪽 정렬 구간 밖에 있음
                    //  (6 7 0 1 2 4 5) target is 7
                    //       L.  M.  R
            }
        }

        return -1;
    }
}

// @lc code=end

