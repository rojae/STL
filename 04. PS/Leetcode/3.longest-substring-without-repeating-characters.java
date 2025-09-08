/*
 * @lc app=leetcode id=3 lang=java
 *
 * [3] Longest Substring Without Repeating Characters
 */

// @lc code=start
class Solution {
    
    public int lengthOfLongestSubstring(String s) {
        int max = 0;
        char[] charArry = s.toCharArray();
        
        for(int i = 0; i < charArry.length; i++) {
            boolean[] visited = new boolean[128];
            int step = 0;

            for(int j = i; j < charArry.length; j++) {
                if(!visited[charArry[j]]) {
                    visited[charArry[j]] = true;
                    step += 1;
                }
                else{
                    break;
                }
            
                if(max < step) 
                    max = step;
            }
        }

        return max;
    }
}

// @lc code=end

