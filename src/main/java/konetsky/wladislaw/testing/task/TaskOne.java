package konetsky.wladislaw.testing.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Составить алгоритм: если введенное целое число больше заданного, то вывести “Привет”
 */
public class TaskOne implements Runnable{

    private static final Logger logger = LogManager.getLogger(TaskOne.class);

    private final int thresholdNumber;

    public TaskOne(int thresholdNumber) {
        this.thresholdNumber = thresholdNumber;
    }

    @Override
    public void run() {
        logger.info("Starting task one...");
        System.out.println("Running task one");
        logger.info("Task one has finished");
    }

    public static void main(String[] args) {

    }
}
