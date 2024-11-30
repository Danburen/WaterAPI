package org.waterwood.plugin.bukkit.util;

import org.bukkit.ChatColor;
import org.waterwood.common.Colors;

public class BColor extends Colors {
    public static String coloredText(String original,ChatColor color){
        return color + original + ChatColor.RESET;
    }
    /**
     * Color the texts by split with codes
     * @param original original text
     * @param split split string
     * @param colors color codes to color text
     * @return colored text
     */
    public static String coloredText(String original,String split,ChatColor... colors){
        StringBuilder sb = new StringBuilder();
        String[] children = original.split(split);
        int n = children.length;
        for(int i = 0 ; i < n ; i++){
            sb.append(coloredText(children[i],colors[i % colors.length]));
            sb.append(split);
        }
        return sb.substring(0,sb.toString().length() - split.length());
    }
}
