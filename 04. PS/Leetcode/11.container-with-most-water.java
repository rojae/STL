/*
 * @lc app=leetcode id=11 lang=java
 *
 * [11] Container With Most Water
 */

// @lc code=start
class Solution {
    // jvm warm-up
    static {
        for (int i = 0; i < 100; i++) {
            maxArea(new int[] { 0, 0 });
        }
    }

    public static int maxArea(int[] height) {
        int len = height.length;
        int left = 0;
        int right = len - 1;
        int answer = 0;

        while(left < right){
            int w = right - left;
            int h = Math.min(height[left], height[right]);
            answer = Math.max(answer, w * h);

            if(height[left] < height[right])
                left++;
            else
                right--;
        }

        return answer;
    }
}
// @lc code=end

