package cas;

import java.util.concurrent.atomic.AtomicBoolean;

public class CasLock {
    private AtomicBoolean lock = new AtomicBoolean(false);
    // false = don't have lock
    // true = lock

    public void lock() {
        while (!lock.compareAndSet(false, true)) {
            Thread.yield(); // or use backoff
        }
    }

    public void unlock() {
        lock.set(false);
    }
}
