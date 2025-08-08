package lockcondition;

import static util.MyLogger.log;

public class GetTask implements Runnable {

    private BufferedQueue queue;

    public GetTask(BufferedQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        String task = queue.get();
        log("get task : " + task);
    }
}
