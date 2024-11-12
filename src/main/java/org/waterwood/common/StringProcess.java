package org.waterwood.common;

public abstract class StringProcess extends Colors{
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
}
