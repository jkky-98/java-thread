package lockcondition;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferedQueue {

    private final static int queueMax = 5;

    private Queue<String> queue = new ArrayDeque<>();
    private final Lock lock = new ReentrantLock();
    private final Condition producerCondition = lock.newCondition();
    private final Condition consumerCondition = lock.newCondition();

    private int waitCount = 0;

    public void put(String task) {
        lock.lock();
        try {
            // 꽉 찼을 때
            while (queue.size() == queueMax) {
                waitCount++;
                producerCondition.await();
            }

            queue.offer(task);
            consumerCondition.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public String get() {
        lock.lock();
        try {
            // 비었을 때
            while (queue.isEmpty()) {
                waitCount++;
                consumerCondition.await();
            }

            String task = queue.poll();
            producerCondition.signal();
            return task;

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public synchronized int getWaitCount() {
        return waitCount;
    }
}
