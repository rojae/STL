/*
 * @lc app=leetcode id=322 lang=java
 *
 * [322] Coin Change
 */

// @lc code=start

import java.util.Arrays;

class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        for(int i=1; i<amount+1; i++){
            for(int coin : coins){
                if(coin <= i){
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        return dp[amount] > amount? -1 : dp[amount];
    }
}
// @lc code=end

