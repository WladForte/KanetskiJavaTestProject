package konetsky.wladislaw.testing.task;

import konetsky.wladislaw.testing.task.util.ConsoleInputReader;
import konetsky.wladislaw.testing.task.util.DataProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Составить алгоритм: если введенное имя совпадает с заданным именем, то вывести “Привет, Вячеслав”, если нет, то вывести "Нет такого имени"
 */
public class TaskTwo implements Runnable {

    private static final Logger logger = LogManager.getLogger(TaskTwo.class);
    private static final String DEFAULT_NAME_PATH = "/taskTwo/requiredNameForGreeting";
    private static final String DEFAULT_NAME = DataProvider.getValue(DataProvider.TEST_DATA, DEFAULT_NAME_PATH, String.class);

    private final String requiredNameForGreeting;

    public TaskTwo(String requiredNameForGreeting) {
        this.requiredNameForGreeting = requiredNameForGreeting;
    }

    @Override
    public void run() {
        logger.info("Starting task two...");
        String userInput = null;
        ConsoleInputReader consoleInputReader = ConsoleInputReader.getInstance();
        System.out.println(String.format("Введите имя \"%s\", чтобы получить именное приветствие", requiredNameForGreeting));
        while (userInput == null) {
            try {
                userInput = consoleInputReader.read();
            } catch (IOException e) {
                logger.warn("An exception occurred while reading console output");
                System.out.println(String.format("Произошла ошибка чтения данных из консоли, присвоенное имя пользователя - \"\" %s", requiredNameForGreeting));
                userInput = DEFAULT_NAME;
            }
        }
        if (userInput.equals(requiredNameForGreeting)) {
            System.out.println(String.format("Привет, %s!", userInput));
            logger.info("Greeting was successful");
        } else {
            System.out.println("Нет такого имени");
            logger.info("Greeting was unsuccessful");
        }
        logger.info("Task two has finished");
    }

}
