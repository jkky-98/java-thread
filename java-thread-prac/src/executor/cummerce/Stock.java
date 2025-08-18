package executor.cummerce;

import java.util.concurrent.atomic.AtomicInteger;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class Stock {
    private AtomicInteger number;

    public Stock(AtomicInteger number) {
        this.number = number;
    }

    public void donwStock() {
        sleep(1);
        log("donwStock 현재 재고 : " + number);
        number.decrementAndGet();
    }

    public int getNumber() {
        return number.get();
    }
}
