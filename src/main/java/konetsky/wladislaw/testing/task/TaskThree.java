package konetsky.wladislaw.testing.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * Составить алгоритм: на входе есть числовой массив, необходимо вывести элементы массива кратный заданному натуральному числу
 */
public class TaskThree implements Runnable{

    private static final Logger logger = LogManager.getLogger(TaskThree.class);

    public final int numberMultipleOf;
    private final int[] numbersArray;

    public TaskThree(int numberMultipleOf, int[] numbersArray) {
        this.numberMultipleOf = numberMultipleOf;
        this.numbersArray = Arrays.copyOf(numbersArray, numbersArray.length);
    }

    @Override
    public void run() {
        logger.info("Starting task three...");
        System.out.println("Running task three");
        logger.info("Task three has finished");
    }

    public static void main(String[] args) {

    }
}
