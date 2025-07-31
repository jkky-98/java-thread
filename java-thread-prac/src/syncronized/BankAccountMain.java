package syncronized;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BankAccountMain {
    public static void main(String[] args) {
        BankAccount bankAccountA = new BankAccount(1L, "A", 10000.0);
        BankAccount bankAccountB = new BankAccount(2L, "B", 10000.0);

        //10명이 동시에 bankAccountA에 동시 출금. (초기 금액 10000, 0~9가 2000원씩 출금해가기)
        for (int i = 0; i < 10; i++) {
            try {
                Thread thread = new Thread(new WithdrawTask(bankAccountA, 2000), "DepositTask" + i);
                thread.start();
            } catch (Exception e) {
                log("DepositTask" + i + " - " + e.getMessage());
            }
        }


        sleep(3000);

        // `A → B`, `B → A`로 교차 이체
        for (int i = 0; i < 10; i++) {
            try {
                if (i % 2 == 0) {
                    Thread thread = new Thread(new TransferTask(bankAccountA, bankAccountB, 2000), "TransferTask" + i);
                    thread.start();
                } else {
                    Thread thread = new Thread(new TransferTask(bankAccountB, bankAccountA, 2000), "TransferTask" + i);
                    thread.start();
                }
            } catch (Exception e) {
                log("TransferTask" + i + " - " + e.getMessage());
            }
        }
    }

    static class DepositTask implements Runnable {
        BankAccount bankAccount;
        int amount;

        public DepositTask(BankAccount bankAccount, int amount) {
            this.bankAccount = bankAccount;
            this.amount = amount;
        }

        @Override
        public void run() {
            bankAccount.deposit(amount);
        }
    }

    static class WithdrawTask implements Runnable {
        BankAccount bankAccount;
        int amount;

        public WithdrawTask(BankAccount bankAccount, int amount) {
            this.bankAccount = bankAccount;
            this.amount = amount;
        }

        @Override
        public void run() {
            bankAccount.withdraw(amount);
        }
    }

    static class TransferTask implements Runnable {
        BankAccount bankAccountA;
        BankAccount bankAccountB;
        int amount;

        public TransferTask(BankAccount bankAccountA, BankAccount bankAccountB, int amount) {
            this.bankAccountA = bankAccountA;
            this.bankAccountB = bankAccountB;
            this.amount = amount;
        }

        @Override
        public void run() {
            bankAccountA.transferTo(bankAccountB, amount);
        }
    }
}
