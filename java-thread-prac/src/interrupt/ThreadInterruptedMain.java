package interrupt;

import java.util.concurrent.ConcurrentLinkedDeque;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ThreadInterruptedMain {

    public static void main(String[] args) {
        Thread thread = new Thread(new Task(), "task");
        thread.start();
        sleep(50);
        thread.interrupt();
        log("main에서 인터럽트 걸음 task 스레드 인터럽트 플래그 상태 : " + thread.isInterrupted());
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                log("task running");
            }

            log("task END");
        }
    }
}
