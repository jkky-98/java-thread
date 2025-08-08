package waitnotify;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BufferedQueueTestMain {
    public static void main(String[] args) {
        BufferedQueue queue = new BufferedQueue();
        putter(queue);
        taker(queue);

        sleep(30000);
        log("대기로 들어간 횟수 : " + queue.getWaitCount());
    }

    private static void taker(BufferedQueue queue) {
        for (int i = 0; i < 1000; i++) {
            new Thread(new TakeTask(queue), "TakeTask" + i).start();
        }
    }

    private static void putter(BufferedQueue queue) {
        for (int i = 0; i < 1000; i++) {
            new Thread(new PutTask(queue, "" + i), "PutTask" + i).start();
        }
    }
}
