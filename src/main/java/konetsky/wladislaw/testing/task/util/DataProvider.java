package konetsky.wladislaw.testing.task.util;

import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public final class DataProvider {

    public static final String CONFIG_DATA = "config";
    public static final String TEST_DATA = "test";

    private static final Logger logger = LogManager.getLogger(DataProvider.class);
    private static final String NODE_SEPARATOR = "/";

    private static JsonElement configData;
    private static JsonElement testData;
    private static Gson gson = new Gson();

    static {
        try {
            BufferedReader configDataReader = new BufferedReader(new FileReader("src/main/resources/configdata.json"));
            BufferedReader testDataReader = new BufferedReader(new FileReader("src/main/resources/testdata.json"));
            configData = JsonParser.parseReader(configDataReader);
            testData = JsonParser.parseReader(testDataReader);
        } catch (FileNotFoundException e) {
            logger.error("An error occurred while reading json files data: " + e.getMessage());
        }
    }

    private DataProvider() {}

    /**
     * The method allows to get the value from json as an object through json path, that allows to travers the json as tree.
     * @param dataType the type of data to be read from json file.
     * @param jsonPath the json path, written in the following format: for instance "/node1/node2/node3" (in case of the object structure) or
     * /0/2/1 (in case of the array structure)
     * @param clazz defines the type of value received.
     * @return the string from json file according to the path
     * @throws IllegalArgumentException if the provided data type is incorrect
     * @throws NullPointerException if the provided json path is incorrect
     */
    public static <T> T getValue(String dataType, String jsonPath, Class<T> clazz) {
        return parseValue(getValueAsString(dataType, jsonPath), clazz);
    }

    private static String getValueAsString(String dataType, String jsonPath) throws IllegalArgumentException, NullPointerException {
        JsonElement jsonElement = assignJsonElement(dataType);
        logger.info(String.format("The selected data type to read is '%s'", dataType.toUpperCase()));
        String[] nodes = parseJsonPathIntoNodes(jsonPath);
        logger.info(String.format("Json path is split into %d nodes", (nodes.length -1)));
        if (nodes.length == 0) {
            return jsonElement.toString();
        }
        for (int i = 1; i < nodes.length; i++) {
            if (stringContainsOnlyDigits(nodes[i])) {
                logger.info(String.format("The node %d is json array", i));
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                jsonElement = jsonArray.get(Integer.parseInt(nodes[i]));
            } else {
                logger.info(String.format("The node %d is json object", i));
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                jsonElement = jsonObject.get(nodes[i]);
            }
        }
        return jsonElement.toString();
    }

    private static <T> T parseValue(String jsonString, Class<T> clazz) {
        logger.info(String.format("Parsing json string into an instance of '%s.class'", clazz.getSimpleName()));
        return gson.fromJson(jsonString, clazz);
    }

    private static JsonElement assignJsonElement(String dataType) {
        if (dataType.equals("config")) {
            return configData;
        }
        if (dataType.equals("test")){
            return testData;
        } else {
            throw new IllegalArgumentException("the provided data type is incorrect");
        }
    }

    private static String[] parseJsonPathIntoNodes(String jsonPath) {
        return jsonPath.split(NODE_SEPARATOR);
    }

    private static boolean stringContainsOnlyDigits(String analyzedString) {
        return analyzedString.matches("[0-9]+");
    }
}
