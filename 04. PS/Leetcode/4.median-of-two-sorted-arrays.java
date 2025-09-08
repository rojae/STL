/*
 * @lc app=leetcode id=4 lang=java
 *
 * [4] Median of Two Sorted Arrays
 */

// @lc code=start
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int n = nums1.length;
        int m = nums2.length;
        int totalLeft = (n + m + 1) / 2;
        int low = 0;
        int high = n;

        while(low <= high){
            int i = (low + high) / 2;
            int j = totalLeft - i;

            int Aleft  = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int Aright = (i == n) ? Integer.MAX_VALUE : nums1[i];
            int Bleft  = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int Bright = (j == m) ? Integer.MAX_VALUE : nums2[j];

            if (Aleft <= Bright && Bleft <= Aright) {
                // 올바른 파티션
                if (((n + m) & 1) == 1) {
                    return Math.max(Aleft, Bleft); // 홀수
                } else {
                    return (Math.max(Aleft, Bleft) + Math.min(Aright, Bright)) / 2.0;
                }
            }
            else if (Aleft > Bright) {
                high = i - 1;  // i 줄이기
            } 
            else {
                low = i + 1;  // i 늘리기
            }
        }
        throw new IllegalArgumentException("Inputs must be sorted.");
    }
}
// @lc code=end
