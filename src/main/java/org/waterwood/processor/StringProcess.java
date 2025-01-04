package org.waterwood.processor;

import org.waterwood.utils.Colors;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * A class that processing and modifying string
 * @since 1.0.3
 * @author Danburen
 */
public abstract class StringProcess {
    private static final String PlaceHolderRegex = "\\{(.*?)}";
    private static final Pattern PlaceHolderPattern = Pattern.compile(PlaceHolderRegex);
    private static final String PercentPlaceHolderRegx = "%(.*?)%";
    private static final Pattern PercentPlaceHolder = Pattern.compile(PercentPlaceHolderRegx);
    /**
     * parse number to Roman number
     * @param number source number
     * @return converted number
     */
    public static String toRoman(int number) {
            StringBuilder result = new StringBuilder();
            int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
            String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

            for (int i = 0; i < values.length; i++) {
                while (number >= values[i]) {
                    result.append(symbols[i]);
                    number -= values[i];
                }
            }
            return result.toString();
    }
    public static boolean isNumeric(String str){
        return str != null && str.matches("-?\\d+");
    }

    /**
     * Replace template with value
     * template like {temple} or %temple%
     * @param template the template replace
     * @param values the values to replace
     * @return replaced String
     */
    public static String replacePlaceHolder(String template, Map<String,Object> values) {
        template = replacePercentPlaceHolder(template);
        Matcher matcher = PlaceHolderPattern.matcher(template);
        String result = template;
        while (matcher.find()) {
            String placeHolder = matcher.group(0); //{name}
            String variableName = matcher.group(1); //name
            if (values.containsKey(variableName)) {
                result = result.replace(placeHolder, String.valueOf(values.get(variableName)));
            }
        }
        return result;
    }

    private static String replacePercentPlaceHolder(String input){
        Matcher matcher = PercentPlaceHolder.matcher(input);
        String result = input;
        while(matcher.find()){
            result = result.replace(matcher.group(0),"{" + matcher.group(1) + "}");
        }
        return result;
    }

    /**
     * Replace template from  each string of String List
     * @param list list string to replace template
     * @param values the value map for replace placeHolder
     * @return placeholder replaced list String
     */
    public static List<String> ListStringReplace(List<String> list,Map<String,Object> values){
        return list.stream()
                .map(str -> StringProcess.replacePlaceHolder(str,values))
                .collect(Collectors.toList());
    }
}
