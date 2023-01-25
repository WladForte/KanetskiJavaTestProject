package konetsky.wladislaw.testing.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Составить алгоритм: на входе есть числовой массив, необходимо вывести элементы массива кратные заданному натуральному числу
 */
public class TaskThree implements Runnable {

    private static final Logger logger = LogManager.getLogger(TaskThree.class);

    private final int numberMultipleOf;
    private final int[] numberArray;

    public TaskThree(int numberMultipleOf, int[] numberArray) {
        this.numberMultipleOf = numberMultipleOf;
        this.numberArray = Arrays.copyOf(numberArray, numberArray.length);
    }

    @Override
    public void run() {
        logger.info("Starting task three...");
        System.out.println(String.format("Исходный массив: %s", Arrays.toString(numberArray)));
        System.out.println(String.format("Пытаемся найти элементы, кратные %d", numberMultipleOf));
        List<Integer> tmpList = new ArrayList<>();
        for (int i = 0; i < numberArray.length; i++) {
            if (isNumberMultipleOf(numberMultipleOf, numberArray[i])) {
                tmpList.add(numberArray[i]);
            }
        }
        System.out.println(String.format("Массив чисел, кратных %d: %s", numberMultipleOf, tmpList));
        logger.info("Task three has finished");
    }

    private boolean isNumberMultipleOf(int numberMultipleOf, int examinedNumber) {
        return examinedNumber % numberMultipleOf == 0 ? true : false;
    }
}
