package waitnotify;

import java.util.ArrayDeque;
import java.util.Queue;

import static util.MyLogger.log;

public class BufferedQueue {

    private Queue<String> queue = new ArrayDeque<>();
    private int queueMax = 5;
    private int waitCount = 0;

    public synchronized void put(String task) {

        // 꽉찼을 때
        while (queue.size() == queueMax) {
            try {
                // 기다린 횟수 기록
                waitCount++;
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        queue.offer(task);
        notify();
    }

    public synchronized String get() {
        // 비었을 때
        while (queue.isEmpty()) {
            try {
                waitCount++;
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        notify();
        return queue.poll();
    }

    public synchronized int getWaitCount() {
        return waitCount;
    }
}
