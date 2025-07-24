package join;

import static util.MyLogger.log;

public class JoinGoodMain {

    public static void main(String[] args) throws InterruptedException {
        log("main start");

        SumTask task1 = new SumTask(1, 50000000);
        SumTask task2 = new SumTask(50000001, 100000000);
        SumTask taskSolo = new SumTask(1, 100000000);

        Thread thread1 = new Thread(task1, "thread-1");
        Thread thread2 = new Thread(task2, "thread-2");
        Thread threadSolo = new Thread(taskSolo, "thread-solo");

        long startTimeSolo = System.currentTimeMillis();
        threadSolo.start();
        threadSolo.join();
        long endTimeSolo = System.currentTimeMillis();
        long timeSolo = endTimeSolo - startTimeSolo;
        log("스레드 1개 소모시간 = " + timeSolo + "ms");
        log("스레드 1개 결과 값 = " + taskSolo.result);
        Thread.sleep(5000);

        long startTimeMulti = System.currentTimeMillis();
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        long endTimeMulti = System.currentTimeMillis();
        long timeMulti = endTimeMulti - startTimeMulti;
        log("스레드 2개 소모시간 = " + timeMulti + "ms");
        log("스레드 2개 결과 값 = " + (task1.result + task2.result));

    }

    static class SumTask implements Runnable {

        int startValue;
        int endValue;
        int result;

        public SumTask(int startValue, int endValue) {
            this.endValue = endValue;
            this.startValue = startValue;
        }

        @Override
        public void run() {
            log("task start");

            for (int i = startValue; i <= endValue ; i++) {
                result += i;
            }

            log("task end");
        }
    }
}
