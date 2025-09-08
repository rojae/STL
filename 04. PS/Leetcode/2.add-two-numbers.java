/*
 * @lc app=leetcode id=2 lang=java
 *
 * [2] Add Two Numbers
 */

// @lc code=start
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution { 
    
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if(l1 == null && l2 == null) return new ListNode(0);
        else if(l1 == null) return l2;
        else if(l2 == null) return l1;

        ListNode result = null;

        byte carry = 0;
        int num1 = l1.val;
        int num2 = l2.val;

        while(true){
            byte sum = (byte) (num1 + num2);

            if(carry == 1){
                sum += carry;
                carry = 0;
            }
            if(sum >= 10){
                carry = 1;
                sum -= 10;
            }

            if(result == null){
                result = new ListNode(sum);
            }
            else {
                ListNode temp = result;
                while(temp.next != null){
                    temp = temp.next;
                }
                temp.next = new ListNode(sum);
            }

            if(l1.next == null && l2.next == null && carry == 0){
                break;
            }
            else{
                if(l1.next != null) {
                    l1 = l1.next;
                    num1 = l1.val;
                }
                else {
                    num1 = 0;
                }

                if(l2.next != null) {
                    l2 = l2.next;
                    num2 = l2.val;
                }
                else {
                    num2 = 0;
                }
            }
        }

        return result;
    }

}
// @lc code=end

