package executor.cummerce;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static util.MyLogger.log;

public class OrderServiceMainGood {

    static Account account = new Account(0);
    static Stock stock = new Stock(new AtomicInteger(100));
    static Shipping shipping = new Shipping();
    static ExecutorService es = Executors.newFixedThreadPool(100);
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        long l = System.currentTimeMillis();
        List<Future> futureList = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 100; i++) {
            Future<Void> submit1 = es.submit(new AccountTask());
            Future<Void> submit2 = es.submit(new StockTask());
            Future<Void> submit3 = es.submit(new ShippingTask());

            futureList.add(submit1);
            futureList.add(submit2);
            futureList.add(submit3);
        }

        for (Future future : futureList) {
            future.get();
        }

        log("total time = " + (System.currentTimeMillis() - l) + "ms");
        log("최종 수익 : " + account.getBalance());
        log("최종 재고 : " + stock.getNumber());

        es.close();
    }

    static class AccountTask implements Callable<Void> {
        @Override
        public Void call() throws Exception {
            account.addBalance(100);
            return null;
        }
    }

    static class StockTask implements Callable<Void> {
        @Override
        public Void call() throws Exception {
            stock.donwStock();
            return null;
        }
    }

    static class ShippingTask implements Callable<Void> {

        @Override
        public Void call() throws Exception {
            shipping.ship();
            return null;
        }
    }

}
