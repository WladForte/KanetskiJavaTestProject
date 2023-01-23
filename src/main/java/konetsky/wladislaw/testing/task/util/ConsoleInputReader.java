package konetsky.wladislaw.testing.task.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A utility class for reading user input from the console.
 */
public final class ConsoleInputReader implements UserInputReader{

    private static final ConsoleInputReader INSTANCE = new ConsoleInputReader();
    private static final Logger logger = LogManager.getLogger(ConsoleInputReader.class);

    private final BufferedReader innerReader = new BufferedReader(new InputStreamReader(System.in));

    private ConsoleInputReader() {};

    public static ConsoleInputReader getInstance() {
        return INSTANCE;
    }

    public String read() throws IOException {
        String result = null;
        result = innerReader.readLine();
        return result;
    }

    /**
     * The method closes the console stream from receiving any new input till the program is completed.
     */
    public void shutDown(){
        try {
            innerReader.close();
        } catch (IOException e) {
            logger.error("An error occurred while closing the console input reader");
        }
    }

}
