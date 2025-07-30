package volatilee;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class NoVolatileMultiThreadMain {

    public static void main(String[] args) {
        Task task = new Task();
        Thread thread = new Thread(task, "noVolatile");
        thread.start();

        sleep(3000);
        // runFlag 바꾸기
        task.runFlag = false;
        log("runFlag 바꾸기 = " + task.runFlag);
    }

    static class Task implements Runnable {

        boolean runFlag = true;

        @Override
        public void run() {
            log("task start");

            while (runFlag) {
                continue;
            }

            log("task end");
        }
    }
}
