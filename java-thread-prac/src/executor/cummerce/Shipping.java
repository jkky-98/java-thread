package executor.cummerce;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class Shipping {

    public void ship() {
        sleep(1);
        log("shipping...");
    }
}
