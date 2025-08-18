package executor.cummerce;

import java.util.concurrent.atomic.AtomicInteger;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class Account {
    private AtomicInteger balance;

    public Account(int balance) {
        this.balance = new AtomicInteger(balance);
    }

    public int getBalance() {
        return balance.get();
    }

    public void addBalance(int amount) {
        sleep(1);
        int updated = balance.addAndGet(amount);  // CAS 기반 연산
        log("판매 수익 : " + updated);
    }
}

