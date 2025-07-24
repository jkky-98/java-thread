package join;

import static util.MyLogger.log;

public class JoinBadMain {
    public static void main(String[] args) throws InterruptedException {
        log("main start");

        Thread thread = new Thread(new InfTask(), "inf task");
        thread.start();

        thread.join();
        // main end 출력이 안된다
        log("main end");
    }

    static class InfTask implements Runnable {

        @Override
        public void run() {
            log("inf task start");

            while (true) {
            }
        }
    }
}
