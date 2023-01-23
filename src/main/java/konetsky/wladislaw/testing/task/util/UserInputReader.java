package konetsky.wladislaw.testing.task.util;

import java.io.IOException;

/**
 * A utility interface for reading input of the user.
 */
public interface UserInputReader {

    String read() throws IOException;

    void shutDown();
}
