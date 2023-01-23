package konetsky.wladislaw.testing.task;

import konetsky.wladislaw.testing.task.util.ConsoleInputReader;
import konetsky.wladislaw.testing.task.util.DataProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainTask {

    private static final ConsoleInputReader CONSOLE_INPUT_READER = ConsoleInputReader.getInstance();
    private static final String REQUIRED_NUMBER_FOR_GREETING_PATH = "/taskOne/requiredNumberForGreeting";
    private static final int REQUIRED_NUMBER_FOR_GREETING = DataProvider.getValue("test", REQUIRED_NUMBER_FOR_GREETING_PATH, int.class);
    private static final String REQUIRED_NAME_FOR_GREETING_PATH = "/taskTwo/requiredNameForGreeting";
    private static final String REQUIRED_NAME_FOR_GREETING = DataProvider.getValue("test", REQUIRED_NAME_FOR_GREETING_PATH, String.class);
    private static final String NUMBER_MULTIPLE_OF_PATH = "/taskThree/numberMultipleOf";
    private static final int NUMBER_MULTIPLE_OF = DataProvider.getValue("test", NUMBER_MULTIPLE_OF_PATH, int.class);
    private static final List<Runnable> taskList = new ArrayList<>();

    static {
        Runnable taskOne = new TaskOne(REQUIRED_NUMBER_FOR_GREETING);
        Runnable taskTwo = new TaskTwo(REQUIRED_NAME_FOR_GREETING);
        Runnable taskThree = new TaskThree(1, new int[1]);
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(String.format("Добро пожаловать в программу! Эта программа может выполнить одно из следующих заданий:\n" +
                "1) Вы можете ввести любое целое число, и если он больше %d, перед вами появится приветствие\n" +
                "2) Вы можете ввести имя человека, и если оно будет \"%s\", перед вами появится приветствие с этим именем\n" +
                "3) Вы можете попросить программу сгенерировать массив случайных целых числе с заданными параметрами, " +
                "а в свою очередь программ выведет все числа, кратные %d",
                REQUIRED_NUMBER_FOR_GREETING, REQUIRED_NAME_FOR_GREETING, NUMBER_MULTIPLE_OF));
        System.out.println();
        int TASK_TO_RUN_INDEX = 1;
        TaskOne taskOne = new TaskOne(1);
        TaskTwo taskTwo = new TaskTwo("134");
        TaskThree taskThree = new TaskThree(1, new int[1]);
        List<Runnable> taskList = Arrays.asList(taskOne, taskTwo, taskThree);

        Runnable taskToRun = taskList.get(TASK_TO_RUN_INDEX - 1);
        Thread taskThread = new Thread(taskToRun);
        System.out.println("STARTING");
        taskThread.start();
        taskThread.join();

        System.out.println("FINISHED");
    }
}
