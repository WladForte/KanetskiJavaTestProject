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
    private static int generateNumber(int min, int max) {
        logger.info("Preparing to generated a random integer number...");
        int result = innerRandom.nextInt(max - min + 1) + min;
        logger.info(String.format("The generated random integer number: %d", result));
        return result;
    }
}
