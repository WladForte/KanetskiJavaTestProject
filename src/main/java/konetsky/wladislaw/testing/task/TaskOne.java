package konetsky.wladislaw.testing.task;

import konetsky.wladislaw.testing.task.util.ConsoleInputReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Составить алгоритм: если введенное целое число больше заданного, то вывести “Привет”
 */
public class TaskOne implements Runnable {

    private static final Logger logger = LogManager.getLogger(TaskOne.class);

    private final int thresholdNumber;

    public TaskOne(int thresholdNumber) {
        this.thresholdNumber = thresholdNumber;
    }

    @Override
    public void run() {
        logger.info("Starting task one...");
        ConsoleInputReader consoleInputReader = ConsoleInputReader.getInstance();
        Integer inputNumber = null;
        System.out.println(String.format("Введите целое число, больше %d, чтобы увидеть приветствие", thresholdNumber));
        while (inputNumber == null) {
            String tmpResult = null;
            try {
                tmpResult = consoleInputReader.read();
            } catch (IOException e) {
                logger.error("An exception occurred while reading console output");
                System.out.println("Произошла ошибка чтения данных из консоли, числу пользователя будет присовено значение, больше " +
                        "порогового на единицу");
                inputNumber = thresholdNumber + 1;
                break;
            }
            try {
                inputNumber = Integer.parseInt(tmpResult);
            } catch (NumberFormatException nfe) {
                logger.warn(String.format("The provided string \"%s\" cannot be parsed into the integer number", tmpResult));
                System.out.println("Вы ввели что-то другое, но не целое число, попробуйте еще раз");
            }
        }
        if (inputNumber > thresholdNumber) {
            System.out.println("Привет!");
        } else {
            System.out.println("* * * тишина * * *");
        }
        logger.info("Task one has finished");
    }

}
