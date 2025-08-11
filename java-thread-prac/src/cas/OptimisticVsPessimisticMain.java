package cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class OptimisticVsPessimisticMain {

    /**
     * ReentrantLock vs Custom CAS Lock
     * 비관적 락 구조 vs 낙관적 락 구조
     * 속도 분석
     */
    private static final Lock lockPessimistic = new ReentrantLock();
    private static final CasLock casLock = new CasLock();
    private static int dummy;
    private static final int ThreadCount = 10000;
    private static final int dummyCount = 9999999;

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threadsPessimistic = new ArrayList<>(); // <1>
        List<Thread> threadsOptimistic = new ArrayList<>(); // <1>

        long stTimePessimistic = System.currentTimeMillis();
        for (int i = 0; i < ThreadCount; i++) {
            Thread thread = new Thread(() -> {
                lockPessimistic.lock();
                try {
                    for (int j = 0; j < dummyCount; j++) {
                        dummy++;
                    }
                } finally {
                    lockPessimistic.unlock();
                }
            });
            thread.start();
            threadsPessimistic.add(thread);
        }

        for (Thread thread : threadsPessimistic) {
            thread.join();
        }

        long endTimePessimistic = System.currentTimeMillis();
        log("pessimistic lock 시간 = " + (endTimePessimistic - stTimePessimistic));

        long stTimeOptimistic = System.currentTimeMillis();
        for (int i = 0; i < ThreadCount; i++) {
            Thread thread = new Thread(() -> {
                casLock.lock();
                try {
                    for (int j = 0; j < dummyCount; j++) {
                        dummy++;
                    }
                } finally {
                    casLock.unlock();
                }
            });
            thread.start();
            threadsOptimistic.add(thread);
        }

        for (Thread thread : threadsOptimistic) {
            thread.join();
        }

        long endTimeOptimistic = System.currentTimeMillis();
        log("optimistic lock 시간 = " + (endTimeOptimistic - stTimeOptimistic));
    }
}
