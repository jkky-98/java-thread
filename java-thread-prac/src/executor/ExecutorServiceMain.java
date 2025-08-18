package executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static util.MyLogger.log;

public class ExecutorServiceMain {

    static int nThreads = 1000;
    public static void main(String[] args) {
        // 스레드 단순 생성 및 실행
        Thread[] threads = new Thread[nThreads];

        long tVanilla = System.currentTimeMillis();
        for (int i = 0; i < nThreads; i++) {
            threads[i] =  new Thread(new Task());
            threads[i].start();
        }

        for (int i = 0; i < nThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        long vanillaEnd = System.currentTimeMillis() - tVanilla;

        ExecutorService executorService = Executors.newFixedThreadPool(100); // 100개의 스레드가 담긴 풀

        long tPoolStart = System.currentTimeMillis();

        for (int i = 0; i < nThreads; i++) {
            executorService.execute(new Task());
        }

        // 더 이상 작업을 받지 않음
        executorService.shutdown();

        try {
            // 모든 작업이 끝날 때까지 최대 1분 기다림
            if (executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                long tPoolEnd = System.currentTimeMillis();
                log("ExecutorService 작업 총 실행 시간 = " + (tPoolEnd - tPoolStart) + "ms");
            } else {
                log("일부 작업이 제한 시간 내에 끝나지 않았습니다.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log("바닐라 작업 실행 시간 = " + vanillaEnd + "ms");
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            log("작업 실행");
        }
    }
}
