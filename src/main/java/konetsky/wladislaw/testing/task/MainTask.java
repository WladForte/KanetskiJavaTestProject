package konetsky.wladislaw.testing.task;

import konetsky.wladislaw.testing.task.util.ConsoleInputReader;
import konetsky.wladislaw.testing.task.util.DataProvider;
import konetsky.wladislaw.testing.task.util.Randomizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class MainTask {

    private static final ConsoleInputReader CONSOLE_INPUT_READER = ConsoleInputReader.getInstance();
    private static final Logger logger = LogManager.getLogger(MainTask.class);

    private static final String ARRAY_MIN_LENGTH_PATH = "/arrayMinLength";
    private static final int ARRAY_MIN_LENGTH = DataProvider.getValue(DataProvider.CONFIG_DATA, ARRAY_MIN_LENGTH_PATH, int.class);
    private static final String ARRAY_MAX_LENGTH_PATH = "/arrayMaxLength";
    private static final int ARRAY_MAX_LENGTH = DataProvider.getValue(DataProvider.CONFIG_DATA, ARRAY_MAX_LENGTH_PATH, int.class);
    private static final String ARRAY_MIN_ELEMENT_PATH = "/arrayMinElement";
    private static final int ARRAY_MIN_ELEMENT = DataProvider.getValue(DataProvider.CONFIG_DATA, ARRAY_MIN_ELEMENT_PATH, int.class);
    private static final String ARRAY_MAX_ELEMENT_PATH = "/arrayMaxElement";
    private static final int ARRAY_MAX_ELEMENT = DataProvider.getValue(DataProvider.CONFIG_DATA, ARRAY_MAX_ELEMENT_PATH, int.class);

    private static final String REQUIRED_NUMBER_FOR_GREETING_PATH = "/taskOne/requiredNumberForGreeting";
    private static final int REQUIRED_NUMBER_FOR_GREETING = DataProvider.getValue(DataProvider.TEST_DATA, REQUIRED_NUMBER_FOR_GREETING_PATH, int.class);
    private static final String REQUIRED_NAME_FOR_GREETING_PATH = "/taskTwo/requiredNameForGreeting";
    private static final String REQUIRED_NAME_FOR_GREETING = DataProvider.getValue(DataProvider.TEST_DATA, REQUIRED_NAME_FOR_GREETING_PATH, String.class);
    private static final String NUMBER_MULTIPLE_OF_PATH = "/taskThree/numberMultipleOf";
    private static final int NUMBER_MULTIPLE_OF = DataProvider.getValue(DataProvider.TEST_DATA, NUMBER_MULTIPLE_OF_PATH, int.class);

    public static void main(String[] args) {
        try {
            System.out.println(String.format("Добро пожаловать в программу! Эта программа может выполнить одно из следующих заданий:\n" +
                            "1) Вы можете ввести любое целое число, и если оно больше %d, перед вами появится приветствие\n" +
                            "2) Вы можете ввести имя человека, и если оно будет \"%s\", перед вами появится приветствие с этим именем\n" +
                            "3) Вы можете попросить программу сгенерировать массив случайных целых числе с заданными параметрами, " +
                            "а в свою очередь программ выведет все числа, кратные %d",
                    REQUIRED_NUMBER_FOR_GREETING, REQUIRED_NAME_FOR_GREETING, NUMBER_MULTIPLE_OF));

            System.out.println("Введите индекс выбранной подпрограммы");
            Integer taskToRunIndex = null;
            while (taskToRunIndex == null || taskToRunIndex < 1 || taskToRunIndex > 3) {
                String tmpResult = null;
                try {
                    tmpResult = CONSOLE_INPUT_READER.read();
                    try {
                        taskToRunIndex = Integer.parseInt(tmpResult);
                        if (taskToRunIndex >= 1 && taskToRunIndex <= 3) {
                            System.out.println(String.format("Выбранная подпрогромма: %d", taskToRunIndex));
                        } else {
                            System.out.println("Введеное число находится за пределами допустимых значений, попробуйте еще раз");
                            taskToRunIndex = null;
                        }
                    } catch (NumberFormatException nfe) {
                        logger.warn(String.format("The provided string \"%s\" cannot be parsed into the integer number", tmpResult));
                        System.out.println("Вы ввели что-то другое, но не целое число, попробуйте еще раз");
                    }
                } catch (IOException e) {
                    logger.error("An exception occurred while reading console output");
                    taskToRunIndex = Randomizer.generateNumber(1, 3);
                    System.out.println(String.format("Произошла ошибка чтения данных из консоли, числу пользователя будет присвоено значение %d", taskToRunIndex));
                    break;
                }
            }
            try {
                activateTask(taskToRunIndex);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
        finally {
            CONSOLE_INPUT_READER.shutDown();
            System.out.println("Программа завершила свое выполнение");
        }
    }

    private static void activateTask(int taskIndex) throws InterruptedException {
        Thread taskThread = null;
        switch(taskIndex) {
            case 1:
                taskThread = new Thread(new TaskOne(REQUIRED_NUMBER_FOR_GREETING));
                taskThread.start();
                taskThread.join();
            break;
            case 2:
                taskThread = new Thread(new TaskTwo(REQUIRED_NAME_FOR_GREETING));
                taskThread.start();
                taskThread.join();
                break;
            case 3:
                int[] generatedArray = generateIntArrayFromUserInput(ARRAY_MIN_LENGTH, ARRAY_MAX_LENGTH, ARRAY_MIN_ELEMENT, ARRAY_MAX_ELEMENT);
                taskThread = new Thread(new TaskThree(NUMBER_MULTIPLE_OF, generatedArray));
                taskThread.start();
                taskThread.join();
                break;
            default: break;
        }
    }

    private static int[] generateIntArrayFromUserInput(int minArrayLength, int maxArrayLength, int arrayLowerBound, int arrayUpperBound) {
        int[] result = null;
        Integer arrayLength = null;
        Integer minArrayElement = null;
        Integer maxArrayElement = null;
        System.out.println(String.format("Необходимо сгенерировать массив, длина которого от %d до %d, " +
                "минимально возможный элемент = %d, максимально возможный элемент = %d",
                minArrayLength, maxArrayLength, arrayLowerBound, arrayUpperBound));
        System.out.println("Введите длину будущего массива");
        while (arrayLength == null) {
            String tmpResult = null;
            try {
                tmpResult = CONSOLE_INPUT_READER.read();
                try {
                    arrayLength = Integer.parseInt(tmpResult);
                    if (arrayLength >= minArrayLength && arrayLength <= maxArrayLength) {
                        System.out.println(String.format("Длина будущего массива = %d", arrayLength));
                    } else {
                        System.out.println(String.format("Введеное число находится за пределами диапозона [%d, %d], попробуйте еще раз", minArrayLength, maxArrayLength));
                        arrayLength = null;
                    }
                } catch (NumberFormatException nfe) {
                    logger.warn(String.format("The provided string \"%s\" cannot be parsed into the integer number", tmpResult));
                    System.out.println("Вы ввели что-то другое, но не целое число, попробуйте еще раз");
                }
            } catch (IOException e) {
                logger.error("An exception occurred while reading console output");
                System.out.println("Произошла ошибка чтения данных из консоли, числу пользователя будет присвоено значение 1");
                arrayLength = 1;
                break;
            }
        }
        System.out.println("Введите минимально возможный элемент в массиве");
        while (minArrayElement == null) {
            String tmpResult = null;
            try {
                tmpResult = CONSOLE_INPUT_READER.read();
                try {
                    minArrayElement = Integer.parseInt(tmpResult);
                    if (minArrayElement >= arrayLowerBound && minArrayElement <= arrayUpperBound) {
                        System.out.println(String.format("Минимально возможный элемент будущего массива = %d", minArrayElement));
                    } else {
                        System.out.println(String.format("Введеное число находится за пределами диапозона [%d, %d], попробуйте еще раз",arrayLowerBound, arrayUpperBound));
                        minArrayElement = null;
                    }
                } catch (NumberFormatException nfe) {
                    logger.warn(String.format("The provided string \"%s\" cannot be parsed into the integer number", tmpResult));
                    System.out.println("Вы ввели что-то другое, но не целое число, попробуйте еще раз");
                }
            } catch (IOException e) {
                logger.error("An exception occurred while reading console output");
                System.out.println("Произошла ошибка чтения данных из консоли, числу пользователя будет присвоено значение 1");
                minArrayElement = 1;
                break;
            }
        }
        System.out.println("Введите максимально возможный элемент в массиве");
        while (maxArrayElement == null) {
            String tmpResult = null;
            try {
                tmpResult = CONSOLE_INPUT_READER.read();
                try {
                    maxArrayElement = Integer.parseInt(tmpResult);
                    if (maxArrayElement >= arrayLowerBound && maxArrayElement <= arrayUpperBound && maxArrayElement >= minArrayElement) {
                        System.out.println(String.format("Максимально возможный элемент будущего массива = %d", maxArrayElement));
                    } else {
                        System.out.println(String.format("Введеное число находится за пределами диапозона [%d, %d] или меньше %d, " +
                                "попробуйте еще раз", arrayLowerBound, arrayUpperBound, minArrayElement));
                        maxArrayElement = null;
                    }
                } catch (NumberFormatException nfe) {
                    logger.warn(String.format("The provided string \"%s\" cannot be parsed into the integer number", tmpResult));
                    System.out.println("Вы ввели что-то другое, но не целое число, попробуйте еще раз");
                }
            } catch (IOException e) {
                logger.error("An exception occurred while reading console output");
                System.out.println(String.format("Произошла ошибка чтения данных из консоли, числу пользователя будет присвоено значение %d", minArrayElement));
                maxArrayElement = 10;
                break;
            }
        }
        result = Randomizer.generateArray(arrayLength, minArrayElement, maxArrayElement);
        return result;
    }
}
