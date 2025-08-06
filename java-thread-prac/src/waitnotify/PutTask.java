package waitnotify;

import static util.MyLogger.log;

public class PutTask implements Runnable {

    private BufferedQueue queue;
    private String task;

    public PutTask(BufferedQueue queue, String task) {
        this.queue = queue;
        this.task = task;
    }

    @Override
    public void run() {
        queue.put(task);
        log("put task : " + task);
    }
}
