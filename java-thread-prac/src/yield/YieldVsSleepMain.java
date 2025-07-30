package yield;

import static util.MyLogger.log;

public class YieldVsSleepMain {

    public static void main(String[] args) throws InterruptedException {
        TaskYield taskYield = new TaskYield();
        TaskSleep taskSleep = new TaskSleep();

        // yield 시간 재기
        long startTimeY = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(taskYield);
            thread.start();
            thread.join();
        }
        log("yield 시간 = " + (System.currentTimeMillis() - startTimeY));

        // sleep 시간 재기
        long startTimeS = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(taskSleep);
            thread.start();
            thread.join();
        }
        log("sleep 시간 = " + (System.currentTimeMillis() - startTimeS));
    }

    static class TaskYield implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
//                System.out.println(Thread.currentThread().getName() + " : " + i);
                Thread.yield();
            }
        }
    }

    static class TaskSleep implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
//                System.out.println(Thread.currentThread().getName() + " : " + i);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
