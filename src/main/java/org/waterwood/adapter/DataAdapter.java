package org.waterwood.adapter;

import java.util.List;

/**
 * Data adapter for original data
 * @since 1.1-SNAPSHOT
 * @version 1.0
 * @author waterwood
 */
public class DataAdapter {
    public static <T> T toValue(Object value, T defaultVal){
        if(value == null){
            return defaultVal;
        }
        Class<?> targetType = defaultVal.getClass();
        try {
            // Double target
            if (targetType == Double.class) {
                if (value instanceof Integer) {
                    return (T) Double.valueOf((Integer) value);  // Integer -> Double
                } else if (value instanceof Long) {
                    return (T) Double.valueOf((Long) value);  // Long -> Double
                } else if (value instanceof Double) {
                    return (T) value;  // Double -> Double
                }
            }

            // Integer target
            if (targetType == Integer.class) {
                if (value instanceof Double) {
                    return (T) Integer.valueOf(((Double) value).intValue());  // Double -> Integer
                } else if (value instanceof Long) {
                    return (T) Integer.valueOf(((Long) value).intValue());  // Long -> Integer
                } else if (value instanceof Integer) {
                    return (T) value;  // Integer -> Integer
                }
            }

            // Long value
            if (targetType == Long.class) {
                if (value instanceof Double) {
                    return (T) Long.valueOf(((Double) value).longValue());  // Double -> Long
                } else if (value instanceof Integer) {
                    return (T) Long.valueOf((Integer) value);  // Integer -> Long
                } else if (value instanceof Long) {
                    return (T) value;  // Long -> Long
                }
            }

            // String value
            if (targetType == String.class) {
                return (T) value.toString();  // 转换为 String
            }

            // default Value
            return (T) value;
        } catch (ClassCastException e) {
            System.err.println("Invalid type " + value.getClass().getSimpleName() + ". Returning default value.");
            return  defaultVal;
        }
    }

    public static List<String> stringListVal(Object value, List<String> defaultVal){
        if (value instanceof List<?> tempList) {
            if (tempList.stream().allMatch(item -> item instanceof String)) {
                return (List<String>) tempList;
            } else {
                return defaultVal;
            }
        } else {
            return defaultVal;
        }
    }

    public static Double roundToOneDecimal(Double value){
        return Math.round(value * 10.0) / 10.0;
    }
}
