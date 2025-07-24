package join;

import static util.MyLogger.log;

public class YesJoin {

    public static void main(String[] args) throws InterruptedException {
        Sum sumTask1 = new Sum(1, 50);
        Sum sumTask2 = new Sum(51, 100);

        Thread thread1 = new Thread(sumTask1, "thread1");
        Thread thread2 = new Thread(sumTask2, "thread2");

        thread1.start();
        thread2.start();

        log("join 시작 - main 스레드 잠깐 멈춤");
        thread1.join();
        thread2.join();
        log("잠에서 깸");

        log("sumTask1 : " + sumTask1.result);
        log("sumTask2 : " + sumTask2.result);

        log("result : " + (sumTask1.result + sumTask2.result));
    }

    static class Sum implements Runnable {

        int startValue;
        int endValue;
        int result;

        public Sum(int startValue, int endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        public void run() {
            log("작업 시작");
            for (int i = startValue; i <= endValue; i++) {
                result += i;
            }
            log("작업 종료");
        }
    }
}
