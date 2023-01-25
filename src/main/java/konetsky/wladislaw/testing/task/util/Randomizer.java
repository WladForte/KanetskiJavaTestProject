package konetsky.wladislaw.testing.task.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

/**
 * A utility class for creating random values;
 */
public final class Randomizer {

    private static final Random innerRandom = new Random();
    private static final Logger logger = LogManager.getLogger(Randomizer.class);

    private Randomizer() {};

    /**
     * The method generates a random integer number within a range from min to max.
     * @param min the lower bound of the range (including)
     * @param max the upper bound of the range (including)
     * @return a random number within a specified range;
     */
    public static int generateNumber(int min, int max) {
        logger.info("Preparing to generated a random integer number...");
        int result = innerRandom.nextInt(max - min + 1) + min;
        logger.info(String.format("The generated random integer number: %d", result));
        return result;
    }

    /**
     * The method generates an array of random integer numbers within a range from min to max with the specified length.
     * @param arrayLength the length of the future array;
     * @param arrayLowerBound the lower bound of the range of possible array elements (including)
     * @param arrayUpperBound the upper bound of the range of possible array elements (including)
     * @return an array of random integer numbers;
     */
    public static int[] generateArray(int arrayLength, int arrayLowerBound, int arrayUpperBound) {
        logger.info("Preparing to generated an array of random integer numbers...");
        int[] result = new int[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            result[i] = generateNumber(arrayLowerBound, arrayUpperBound);
        }
        logger.info("The array of random integer numbers has been generated");
        return result;
    }
}
