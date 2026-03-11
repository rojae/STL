/*
 * @lc app=leetcode id=400 lang=java
 *
 * [400] Nth Digit
 */

// @lc code=start
class Solution {
    public int findNthDigit(int n) {
        // 1자리: 1-9 (9개, 9자리)
        // 2자리: 10-99 (90개, 180자리)
        // 3자리: 100-999 (900개, 2700자리)
        
        int digits = 1;
        long count = 9;
        long start = 1;
        
        // n이 몇 자리 숫자 구간에 있는지 찾기
        while (n > digits * count) {
            n -= digits * count;
            digits++;
            count *= 10;
            start *= 10;
        }
        
        // 해당 구간에서 몇 번째 숫자인지
        long number = start + (n - 1) / digits;
        
        // 그 숫자의 몇 번째 자릿수인지
        int index = (n - 1) % digits;
        
        return String.valueOf(number).charAt(index) - '0';
    }
}
// @lc code=end

