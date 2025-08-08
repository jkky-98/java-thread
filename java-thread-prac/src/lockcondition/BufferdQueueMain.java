package lockcondition;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BufferdQueueMain {

    public static void main(String[] args) {
        BufferedQueue queue = new BufferedQueue();

        getTask(queue);
        putTask(queue);

        sleep(2000);

        log("wait 횟수 : " + queue.getWaitCount());
    }

    private static void putTask(BufferedQueue queue) {
        for (int i = 0; i < 10; i++) {
            new Thread(new PutTask(queue, "" + i), "PutTask" + i).start();
        }
    }

    private static void getTask(BufferedQueue queue) {
        for (int i = 0; i < 10; i++) {
            new Thread(new GetTask(queue), "GetTask" + i).start();
        }
    }
}
