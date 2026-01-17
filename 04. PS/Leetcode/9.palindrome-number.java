/*
 * @lc app=leetcode id=9 lang=java
 *
 * [9] Palindrome Number
 */

// @lc code=start
class Solution {
    public boolean isPalindrome(int x) {
        String str = String.valueOf(x);
        String reversedStr = new StringBuilder(str).reverse().toString();
        
        return str.equals(reversedStr);
    }
}
// @lc code=end

