/*
 * @lc app=leetcode id=91 lang=java
 *
 * [91] Decode Ways
 */

// @lc code=start
class Solution {
    public int numDecodings(String s) {
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = s.charAt(0) != '0' ? 1 : 0;

        for(int i = 2; i <= n; i++){
            int one = Integer.parseInt(s.substring(i-1, i));
            if(one >= 1 && one <= 9){
                dp[i] += dp[i-1];
            }
            
            int two = Integer.parseInt(s.substring(i-2, i));
            if(two >= 10 && two <= 26){
                dp[i] += dp[i-2];
            }
        }

        return dp[n];
    }
}
// @lc code=end

