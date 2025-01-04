/**
 * use kyori component
 * only in paper or component support server.
 */
package org.waterwood.plugin.bukkit.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.ArrayList;
import java.util.List;

public abstract class ComponentProcessor {

    public static Component convertChatColorString(String input) {
        List<Component> components = new ArrayList<>();
        input = input.replace('§','&');
        String[] parts = input.split("(?<=&[0-9a-fk-or])"); // keep the split code
        TextColor currentColor = NamedTextColor.WHITE; // set default color WHITE
        for (String part : parts) {
            if (part.startsWith("&")) {
                char colorCode;
                if(part.length() > 1) {
                    colorCode = part.charAt(1);
                }else{
                    colorCode = 'r'; //default color
                }
                currentColor = getNamedColorFromCode(colorCode);
            } else {
                if (!part.isEmpty()) { // ensure is not an empty color
                    components.add(Component.text(part.substring(part.indexOf("&") + 1), currentColor));
                }
            }
        }
        return Component.text().append(components).build();
    }
    private static NamedTextColor getNamedColorFromCode(char code) {
        return switch (code) {
            case '0' -> NamedTextColor.BLACK;
            case '1' -> NamedTextColor.DARK_BLUE;
            case '2' -> NamedTextColor.DARK_GREEN;
            case '3' -> NamedTextColor.DARK_AQUA;
            case '4' -> NamedTextColor.DARK_RED;
            case '5' -> NamedTextColor.DARK_PURPLE;
            case '6' -> NamedTextColor.GOLD;
            case '7' -> NamedTextColor.GRAY;
            case '8' -> NamedTextColor.DARK_GRAY;
            case '9' -> NamedTextColor.BLUE;
            case 'a' -> NamedTextColor.GREEN;
            case 'b' -> NamedTextColor.AQUA;
            case 'c' -> NamedTextColor.RED;
            case 'd' -> NamedTextColor.LIGHT_PURPLE;
            case 'e' -> NamedTextColor.YELLOW;
            case 'f' -> NamedTextColor.WHITE;
            /*case 'k' -> currentColor; // 随机颜色 (这里不处理，保留当前颜色)
            case 'l' -> currentColor; // 粗体 (这里不处理，保留当前颜色)
            case 'm' -> currentColor; // 删除线 (这里不处理，保留当前颜色)
            case 'n' -> currentColor; // 下划线 (这里不处理，保留当前颜色)
            case 'o' -> currentColor; // 斜体 (这里不处理，保留当前颜色)*/
            case 'r' -> NamedTextColor.WHITE;// 重置为白色 (可以调整为其他默认颜色)
            default -> NamedTextColor.WHITE; // 保持当前颜色
        };
    }
    public static List<Component> mixTextColor(List<Component> components, NamedTextColor... colors){
        for(int i = 0; i < components.size() ; i++){
            components.set(i,components.get(i).color(colors[i]));
        }
        return components;
    }
    public static List<Component> mixDecoration(List<Component> components, TextDecoration... decoration){
        for(int i = 0; i < components.size() ; i++){
            components.set(i,components.get(i).decorate(decoration[i]));
        }
        return components;
    }
    public static List<Component> splitToComponent(String origin,String regex){
        List<Component> components = new ArrayList<>();
        String[] parts = origin.split(regex);
        for(String part : parts){
            components.add(Component.text(part));
        }
        return components;
    }
    public static Component packageComponent(Component... components){
        return Component.text().append(components).build();
    }
    public static String extractText(Component component) {
        StringBuilder textBuilder = new StringBuilder();
        component.children();
        if (component instanceof TextComponent) {
            textBuilder.append(((TextComponent) component).content());
        }
        if(! component.children().isEmpty()){
            for(Component child : component.children()){
                textBuilder.append(extractText(child));
            }
        }
        return textBuilder.toString();
    }
    public static boolean containsText(Component component, String searchText) {
        if (component == null || searchText == null) {
            return false;
        }
        return extractText(component).contains(searchText);
    }
}
