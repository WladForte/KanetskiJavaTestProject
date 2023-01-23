package konetsky.wladislaw.testing.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Составить алгоритм: если введенное имя совпадает с заданным именем, то вывести “Привет, Вячеслав”, если нет, то вывести "Нет такого имени"
 */
public class TaskTwo implements Runnable{

    private static final Logger logger = LogManager.getLogger(TaskTwo.class);

    private final String requiredNameForGreeting;

    public TaskTwo(String requiredNameForGreeting) {
        this.requiredNameForGreeting = requiredNameForGreeting;
    }

    @Override
    public void run() {
        logger.info("Starting task two...");
        System.out.println("Running task two");
        logger.info("Task two has finished");
    }

    public static void main(String[] args) {

    }
}
