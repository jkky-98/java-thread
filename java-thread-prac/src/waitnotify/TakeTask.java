package waitnotify;

import static util.MyLogger.log;

public class TakeTask implements Runnable{

    private BufferedQueue queue;

    public TakeTask(BufferedQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        String task = queue.get();
        log("take task : " + task);
    }
}
