package volatilee;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class VolatileVsNoVolatileMain {
    public static void main(String[] args) {
        Task task = new Task();
        Thread thread = new Thread(task, "task");

        thread.start();

        for (int i = 0; i < 10; i++) {
            sleep(100);
            log("flag = " + task.runFlag + ", count = " + task.count);
        }

        task.runFlag = false;
        log("flag = " + task.runFlag + ", count = " + task.count);
    }

    static class Task implements Runnable {

        boolean runFlag = true;
        long count = 0;

        @Override
        public void run() {
            while (runFlag) {
                count++;
                if (count % 100_000_000 == 0) {
                    log("flag = " + runFlag + ", count = " + count);
                }
            }
            log("종료 시점 flag = " + runFlag + ", count = " + count);
        }
    }
}
