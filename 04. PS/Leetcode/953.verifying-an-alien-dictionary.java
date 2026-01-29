/*
 * @lc app=leetcode id=953 lang=java
 *
 * [953] Verifying an Alien Dictionary
 */

// @lc code=start
class Solution {
    public boolean isAlienSorted(String[] words, String order) {
        // order에서 각 문자의 우선순위를 저장
        int[] priority = new int[26];
        for (int i = 0; i < order.length(); i++) {
            priority[order.charAt(i) - 'a'] = i;
        }
        
        // 인접한 단어들끼리 비교
        for (int i = 0; i < words.length - 1; i++) {
            if (!isOrdered(words[i], words[i + 1], priority)) {
                return false;
            }
        }
        
        return true;
    }

        private boolean isOrdered(String word1, String word2, int[] priority) {
        int minLen = Math.min(word1.length(), word2.length());
        
        for (int i = 0; i < minLen; i++) {
            char c1 = word1.charAt(i);
            char c2 = word2.charAt(i);
            
            if (c1 != c2) {
                return priority[c1 - 'a'] < priority[c2 - 'a'];
            }
        }
        
        // 앞부분 다 같으면 짧은 게 먼저여야 함
        return word1.length() <= word2.length();
    }

    
}
// @lc code=end

