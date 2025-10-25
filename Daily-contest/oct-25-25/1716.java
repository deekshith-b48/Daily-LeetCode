
class Solution {
    public int totalMoney(int n) {
        // 📊 Calculate complete weeks and remaining days
        int weeks = n / 7;
        int days = n % 7;
        
        // 💰 Sum of complete weeks: weeks*28 + 7*weeks*(weeks-1)/2
        int completeWeeksSum = weeks * 28 + 7 * weeks * (weeks - 1) / 2;
        
        // 💰 Sum of partial week: days*(weeks+1) + days*(days-1)/2
        int partialWeekSum = days * (weeks + 1) + days * (days - 1) / 2;
        
        // 🎯 Return total savings
        return completeWeeksSum + partialWeekSum;
    }
}