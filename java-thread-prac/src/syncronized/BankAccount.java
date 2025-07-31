package syncronized;

import static util.MyLogger.log;

public class BankAccount {
    // `id`, `ownerName`, `balance`
    public Long id;
    public String ownerName;
    public Double balance;

    public BankAccount(Long id, String ownerName, Double balance) {
        this.id = id;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    //`deposit(int amount)`
    //        - `withdraw(int amount)`
    //        - `transferTo(BankAccount target, int amount)` – 이체 로직
    public synchronized void deposit(int amount) {
        balance = balance + amount;
    }

    public synchronized void withdraw(int amount) {

        // 검증
        if (balance < amount) {
            log("출금 실패 - 사유 : 잔액 부족");
            throw new RuntimeException("출금 실패 : 잔액 부족");
        }

        // 출금
        balance = balance - amount;
        log("출금 성공 - 남은 잔액 : " + balance);
    }

    public synchronized void transferTo(BankAccount target, int amount) {
        try {
            withdraw(amount);
            target.deposit(amount);
            log("이체 성공 - 내 잔액 : " + balance + ", " + target.ownerName + "님에게 " + amount + "원 이체");
        } catch (RuntimeException e) {
            log("이체 실패 - 사유 : " + e.getMessage());
        }
    }
}
