package org.waterwood.utils;


import java.util.Arrays;

/**
 * Generate Line Font
 * @since 1.0-SNAPSHOT
 * @version 1.0
 * @author waterwood
 */
public abstract class LineFontGenerator {
    // Futuristic font style (already defined)
    public static final String[] FUTURISTIC_BUILD_RAW = {
            "     \n __  \n(__( \n     ", "     \n|__  \n|__) \n     ", "     \n __  \n(___ \n     ",
            // abc
            "     \n __| \n(__| \n     ", "      \n ___  \n(__/_ \n      ", "  _ \n_|_ \n |  \n    ",
            // def
            "     \n __  \n(__| \n __/ ", "     \n|__  \n|  ) \n     ", "  \no \n| \n  ",
            // ghi
            "     \n   | \n(__, \n     ", "     \n|__/ \n|  \\ \n     ", "    \n|   \n|_, \n    ",
            // jkl
            "        \n __ __  \n|  )  ) \n        ", "     \n __  \n|  ) \n     ", "     \n __  \n(__) \n    ",
            // mno
            "     \n __  \n|__) \n|    ", "     \n __  \n(__| \n   | ", "     \n __  \n|  ' \n     ",
            // pqr
            "     \n  __ \n__)  \n     ", "     \n_|_  \n |_, \n     ", "      \n      \n(__(_ \n      ",
            // stu
            "     \n\\  / \n \\/  \n     ","     \n        \n(__(__( \n        ", "    \n\\_/ \n/ \\ \n    ",
            //vwx
            "     \n     \n(__| \n  | ", "     \n__   \n (__ \n     ", " __  \n|  | \n|__| \n     ",
            "   \n'| \n | \n   ", " __  \n __) \n(___ \n     ", "___ \n _/ \n__) \n    ",
            "     \n(__| \n   | \n     ", " __  \n(__  \n___) \n     ", "     \n /_  \n(__) \n     ",
            "__  \n  / \n /  \n    ", " __  \n(__) \n(__) \n     ", " __  \n(__) \n  /  \n     "
    };

    // abc/def/ghi/jkl/mno/pqr/stu/vwx/yz0/123/456/789

    public static final String[][] FONT_STYLES = {
            FUTURISTIC_BUILD_RAW
    };


    public static final String[] FUTURISTIC_BUILD_SPLIT = Arrays.stream(FUTURISTIC_BUILD_RAW).flatMap(
            s -> Arrays.stream(s.split("\n"))).toArray(String[]::new);

    /**
     * parse text to line shape text
     * @param original source of text
     * @return converted text
     */
    public static String[] parseLineText(String original,int typeInd){
        char[] chars = original.toLowerCase().toCharArray();
        String[] temp = new String[4];
        Arrays.fill(temp, "");
        for(char c : chars){
            int ind = -1;
            if( c >='a' && c <= 'z'){
                ind = c - 'a';
            }else if( c >='0' && c <= '9'){
                ind = c - '0' + 26;
            }
            for(int i = 0 ; i < 4 ; i ++){
                if(ind == -1) {
                    temp[i] = temp[i].concat(String.valueOf(c));
                }else{
                    temp[i] = temp[i].concat(
                            Arrays.stream(FUTURISTIC_BUILD_RAW).flatMap(
                                    s -> Arrays.stream(s.split("\n"))).toArray(String[]::new)
                            [ind * 4 + i]);
                }
            }
        }
        return temp;
    }
}