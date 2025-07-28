package interrupt.printer;

import java.util.Deque;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class PrinterMainV1 {

    public static void main(String[] args) {
        Printer printer = new Printer();
        Thread printerThread = new Thread(printer, "printer");

        printerThread.start();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("프린터에 입력 : ");
            String task = sc.nextLine();

            if (task.equals("q")) {
                printerThread.interrupt();
                sc.close();
                break;
            }

            printer.put(task);
        }
    }

    static class Printer implements Runnable {

        private Deque<String> queue = new ConcurrentLinkedDeque<>();

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    if (queue.isEmpty()) {
                        continue;
                    }

                    String task = queue.poll();

                    log("출력 : " + task + " || 프린터 큐에 대기중인 출력 : " + queue);
                    Thread.sleep(3000);
                }
            } catch (InterruptedException e) {
                log("프린터 종료");
            }
        }

        public void put(String task) {
            queue.offer(task);
        }
    }
}
