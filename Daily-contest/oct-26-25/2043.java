
class Bank {
    
    // 📊 Internal storage for account balances (0-indexed)
    private final long[] balances;
    private final int n;

    public Bank(long[] balance) {
        this.balances = balance;
        this.n = balance.length;
    }
    
    public boolean transfer(int account1, int account2, long money) {
        // 🚫 Validate account numbers
        if (!isValidAccount(account1) || !isValidAccount(account2)) {
            return false;
        }
        
        // 🚫 Validate sufficient funds in source account
        if (balances[account1 - 1] < money) {
            return false;
        }
        
        // 💰 Perform atomic transfer
        balances[account1 - 1] -= money;
        balances[account2 - 1] += money;
        return true;
    }
    
    public boolean deposit(int account, long money) {
        // 🚫 Validate account number
        if (!isValidAccount(account)) {
            return false;
        }
        
        // 💰 Perform deposit
        balances[account - 1] += money;
        return true;
    }
    public boolean withdraw(int account, long money) {
        // 🚫 Validate account number
        if (!isValidAccount(account)) {
            return false;
        }
        
        // 🚫 Validate sufficient funds
        if (balances[account - 1] < money) {
            return false;
        }
        
        // 💰 Perform withdrawal
        balances[account - 1] -= money;
        return true;
    }
    
    private boolean isValidAccount(int account) {
        return account >= 1 && account <= n;
    }
}