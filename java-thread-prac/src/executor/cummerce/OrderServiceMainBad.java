package executor.cummerce;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static util.MyLogger.log;

public class OrderServiceMainBad {

    static Account account = new Account(0);
    static Stock stock = new Stock(new AtomicInteger(100));
    static Shipping shipping = new Shipping();
    static List<Thread> threads = new CopyOnWriteArrayList<>();
    public static void main(String[] args) throws InterruptedException {

        long l = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            Thread orderTaskThread = new Thread(new OrderTask());
            orderTaskThread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        log("total time = " + (System.currentTimeMillis() - l) + "ms");
        log("최종 수익 : " + account.getBalance());
        log("최종 재고 : " + stock.getNumber());
    }

    static class OrderTask implements Runnable {
        @Override
        public void run() {
            Thread acThread = new Thread(new AccountTask());
            Thread stThread = new Thread(new StockTask());
            Thread shThread = new Thread(new ShippingTask());

            threads.add(acThread);
            threads.add(stThread);
            threads.add(shThread);

            acThread.start();
            stThread.start();
            shThread.start();
        }
    }

    static class AccountTask implements Runnable {
        @Override
        public void run() {
            account.addBalance(100);
        }
    }

    static class StockTask implements Runnable {
        @Override
        public void run() {
            stock.donwStock();
        }
    }

    static class ShippingTask implements Runnable {
        @Override
        public void run() {
            shipping.ship();
        }
    }

}
