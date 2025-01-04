package org.waterwood.utils;

import org.waterwood.adapter.DataAdapter;
import org.waterwood.io.FileConfigProcess;
import org.waterwood.processor.StringProcess;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Config Reader for {@link FileConfigProcess}
 * @since 1.1-SNAPSHOT
 * @version 1.0
 * @author waterwood
 */
public class ConfigReader {
    private static final Random rnd = new Random();
    /**
     * Get String List Description from File Config({@link FileConfigProcess})
     * @param fcp {@link FileConfigProcess} of file config
     * @param path key path where description located
     * @param defaultVal default description while not found
     * @return String list
     */
    public static List<String> getDescription(FileConfigProcess fcp,String path,List<String> defaultVal){
        return DataAdapter.stringListVal(fcp.get(path,defaultVal),defaultVal);
    }

    /**
     * et String List Description from File Config({@link FileConfigProcess})
     * value replace with placeHolder
     * @param fcp {@link FileConfigProcess} of file config
     * @param values the value map for replace
     * @param path key path where description located
     * @param defaultVal default description while not found
     * @return placeholder replaced string list
     */
    public static List<String> getAndPlaceDescription(FileConfigProcess fcp, String path, Map<String,Object> values, List<String> defaultVal){
        List<String> list = getDescription(fcp,path,defaultVal);
        return list.stream()
                .map(str -> StringProcess.replacePlaceHolder(str,values))
                .collect(Collectors.toList());
    }

    /**
     * Get percentage rate from file
     * output keep one decimal place
     * data input type: {@code int} (will force to convert)
     * use percentage.
     * Automatically decide whether to return a <b>fixed</b> value or a <b>random </b>value
     * @param fcp the {@link FileConfigProcess} to read
     * @param key path key that contain like ".rate"
     * @param defaultVal default value that didn't get the data from file
     * @param minKey minKey inner {@code rate} field
     * @param maxKey maxKey inner {@code rate} field
     * @return the rate
     */
    public static double getPercentRateDouble(FileConfigProcess fcp, String key, double defaultVal, String minKey, String maxKey) {
        try {
            Object value = fcp.getRaw(key);

            // If value is Double or Integer, return it directly and round it
            if (value instanceof Double) {
                return DataAdapter.roundToOneDecimal((Double) value);
            } else if (value instanceof Integer) {
                return DataAdapter.roundToOneDecimal(Double.valueOf((Integer) value));
            } else if (value instanceof Map<?, ?>) {
                Map<String, Object> rateMap = (Map<String, Object>) value;

                // Check if minKey and maxKey exist and are of type Integer or Double.
                Object minValue = rateMap.get(minKey);
                Object maxValue = rateMap.get(maxKey);

                if ((minValue instanceof Integer || minValue instanceof Double)
                        && (maxValue instanceof Integer || maxValue instanceof Double)) {

                    double min = (minValue instanceof Integer) ? ((Integer) minValue).doubleValue() : (Double) minValue;
                    double max = (maxValue instanceof Integer) ? ((Integer) maxValue).doubleValue() : (Double) maxValue;

                    if (min == max) {
                        return DataAdapter.roundToOneDecimal(min); // If min and max are equal, return the fixed value
                    } else {
                        // Generate a random integer between min and max, then convert to double and round to 1 decimal
                        int randomValue = (int) (min + Math.random() * (max - min + 1)); // Generating random int
                        return DataAdapter.roundToOneDecimal((double) randomValue); // Convert to double and round to 1 decimal
                    }
                } else if (rateMap.containsKey(key) && (rateMap.get(key) instanceof Integer || rateMap.get(key) instanceof Double)) {
                    // If rate is a single numeric value, return it directly (either Integer or Double)
                    Object rateValue = rateMap.get(key);
                    if (rateValue instanceof Integer) {
                        return DataAdapter.roundToOneDecimal(Double.valueOf((Integer) rateValue));
                    } else {
                        return DataAdapter.roundToOneDecimal((Double) rateValue);
                    }
                } else {
                    return defaultVal; // Return default value if conditions are not met
                }
            }
        } catch (ClassCastException e) {
            return defaultVal; // Return default value if there is a casting error
        }

        return defaultVal; // Fallback return
    }

    public static int getPercentRateInt(FileConfigProcess fcp, String key, int defaultVal, String minKey, String maxKey) {
        return Double.valueOf(getPercentRateDouble(fcp, key, defaultVal, minKey, maxKey)).intValue();
    }
}
