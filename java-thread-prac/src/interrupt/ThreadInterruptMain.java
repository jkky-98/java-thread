package interrupt;

import static util.MyLogger.log;

public class ThreadInterruptMain {

    public static void main(String[] args) {
        Thread thread = new Thread(new Task(), "task");
        thread.start();

        thread.interrupt();
        log("main 스레드에서 인터럽트 건 직후 task 스레드 인터럽트 플래그 상태 : " + thread.isInterrupted());
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    log("task start");
                    Thread.sleep(3000);
                }
            } catch (InterruptedException e) {
                log("인터럽트 예외 발생 : " + e.getMessage());
                log("인터럽트 예외 발생 후 현재 스레드 상태 : " + Thread.currentThread().getState());
                log("인터럽트 예외 발생 후 현재 스레드 인터럽트 플래그 상태 : " + Thread.currentThread().isInterrupted());
            }

            log("인터럽트 후 정상흐름");
            log("자원정리 및 종료");
        }
    }
}
