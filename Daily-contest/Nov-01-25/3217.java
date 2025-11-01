
class Solution {
    
    public ListNode modifiedList(int[] nums, ListNode head) {
        Set<Integer> toRemove = new HashSet<>();
        for (int num : nums) {
            toRemove.add(num);
        }
        
        while (head != null && toRemove.contains(head.val)) {
            head = head.next;
        }
        
        if (head == null) {
            return null;
        }
        
        ListNode prev = head;
        ListNode current = head.next;
        
        while (current != null) {
            if (toRemove.contains(current.val)) {
                prev.next = current.next;
            } else {
                prev = current;
            }
            current = current.next;
        }
        
        return head;
    }
}