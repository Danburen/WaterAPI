package org.waterwood.common;

import org.waterwood.consts.COLOR;

import java.awt.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Colors {
    private static final Pattern PATTERN = Pattern.compile("§[0-9a-fA-Fr]");
    private static final String BLACK = "\u001B[30m";
    private static final String DARK_RED = "\u001B[31m";
    private static final String DARK_GREEN = "\u001B[32m";
    private static final String GOLD = "\u001B[33m";
    private static final String DARK_BLUE = "\u001B[34m";
    private static final String DARK_PURPLE = "\u001B[35m";
    private static final String DARK_AQUA = "\u001B[36m";
    private static final String GRAY = "\u001B[37m";
    private static final String DARK_GRAY = "\u001B[90m";
    private static final String RED = "\u001B[91m";
    private static final String GREEN = "\u001B[92m";
    private static final String YELLOW = "\u001B[93m";
    private static final String BLUE = "\u001B[94m";
    private static final String LIGHT_PURPLE = "\u001B[95m";
    private static final String AQUA = "\u001B[96m";
    private static final String WHITE = "\u001B[97m";
    private static final String RESET = "\u001B[0m";


    protected static final Map<String, String> COLOR_MAP = Map.ofEntries(
            Map.entry("§0", BLACK), Map.entry("§1", DARK_BLUE),
            Map.entry("§2", DARK_GREEN), Map.entry("§3", DARK_AQUA), Map.entry("§4", DARK_RED),
            Map.entry("§5", DARK_PURPLE), Map.entry("§6", GOLD), Map.entry("§7", GRAY),
            Map.entry("§8", DARK_GRAY), Map.entry("§9", BLUE), Map.entry("§a", GREEN),
            Map.entry("§b", AQUA), Map.entry("§c", RED), Map.entry("§d", LIGHT_PURPLE),
            Map.entry("§e", YELLOW), Map.entry("§f", WHITE), Map.entry("§r", RESET)
    );
    public static final Map<String, COLOR> COLOR_MAP_MC = Map.ofEntries(
            Map.entry("0", COLOR.BLACK),
            Map.entry("1", COLOR.DARKBLUE),
            Map.entry("2", COLOR.DARK_GREEN),
            Map.entry("3", COLOR.DARK_AQUA),
            Map.entry("4", COLOR.DARK_RED),
            Map.entry("5", COLOR.DARK_PURPLE),
            Map.entry("6", COLOR.GOLD),
            Map.entry("7", COLOR.GRAY),
            Map.entry("8", COLOR.DARK_GRAY),
            Map.entry("9", COLOR.BLUE),
            Map.entry("a", COLOR.GREEN),
            Map.entry("b", COLOR.AQUA),
            Map.entry("c", COLOR.RED),
            Map.entry("d", COLOR.LIGHT_PURPLE),
            Map.entry("e", COLOR.YELLOW),
            Map.entry("f", COLOR.WHITE),
            Map.entry("r", COLOR.RESET)
    );

    public static String getColorCode(COLOR color) {
        return switch (color) {
            case BLACK -> "0";
            case DARKBLUE -> "1";
            case DARK_GREEN -> "2";
            case DARK_AQUA -> "3";
            case DARK_RED -> "4";
            case DARK_PURPLE -> "5";
            case GOLD -> "6";
            case GRAY -> "7";
            case DARK_GRAY -> "8";
            case BLUE -> "9";
            case GREEN -> "a";
            case AQUA -> "b";
            case RED -> "c";
            case LIGHT_PURPLE -> "d";
            case YELLOW -> "e";
            case WHITE -> "f";
            case RESET -> "r";
            default -> "r"; // default reset color
        };
    }
    public static COLOR getColorTitle(String ColorStr){
        return COLOR_MAP_MC.getOrDefault(ColorStr.replaceAll("&|§",""), COLOR.RESET);
    }

    /**
     * Parses {@link Colors color-code} to show in the terminal.
     * Original text contains code §.
     *
     * @param origin   Original text
     * @param isDisable Whether to parse color
     * @return Parsed color text (with ANSI)
     */
    public static String parseColor(String origin, boolean isDisable) {
        if (isDisable) {
            return origin.replaceAll("§[0-9a-fA-Fr]", "");
        }
        // Initializing StringBuilder for the final result
        StringBuilder sb = new StringBuilder();
        Matcher matcher = PATTERN.matcher(origin);
        int lastIndex = 0;
        // Process and replace color codes
        while (matcher.find()) {
            sb.append(origin, lastIndex, matcher.start());
            String colorCode = matcher.group();
            String ansiCode = COLOR_MAP.getOrDefault(colorCode, "");
            sb.append(ansiCode);
            lastIndex = matcher.end();
        }
        // Append the remaining part of the original string
        sb.append(origin, lastIndex, origin.length());
        // Ensure the final string ends with RESET code if necessary
        String result = sb.toString();
        if (!result.endsWith(RESET)) {
            result += RESET;
        }
        return result;
    }
    //Only in minecraft use
    private static String getRainbowColor(int index, int length) {
        float hue = (index * 360.0f) / length; // hue
        // hue to rgb
        int rgb = Color.HSBtoRGB(hue / 360f, 1.0f, 1.0f);
        // generate the minecraft color code
        return String.format("§#%02x%02x%02x", (rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
    }

    public static String applyRainbow(String input) {
        StringBuilder rainbowText = new StringBuilder();
        int length = input.length();

        for (int i = 0; i < length; i++) {
            String color = getRainbowColor(i, length); // get the color of each char
            rainbowText.append(color).append(input.charAt(i)); // color the char
        }

        return rainbowText.toString();
    }
    public static String parseColor(String origin) {
        return parseColor(origin, false);
    }
    public static String coloredText(String original,String colorCode){
        return "§"+ colorCode +original + "§r";
    }
    public static String coloredText(String original, COLOR color){
        if(color == COLOR.RAINBOW){
            return applyRainbow(original);
        }
        String colorCode = getColorCode(color);
        return coloredText(original, colorCode);
    }

    /**
     * Color the texts by split with codes
     * @param original original text
     * @param split split string
     * @param codes color codes to color text
     * @return colored text
     */
    public static String coloredText(String original, String split, COLOR... codes){
        StringBuilder sb = new StringBuilder();
        String[] children = original.split(split);
        int n = children.length;
        for(int i = 0 ; i < n ; i++){
            sb.append(coloredText(children[i],codes[i % codes.length]));
            sb.append(split);
        }
        return sb.toString();
    }
}