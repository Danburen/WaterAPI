package org.waterwood.processor;

import org.waterwood.enums.COLOR;
import org.waterwood.plugin.bukkit.BukkitPlugin;
import org.waterwood.utils.Colors;

public class MessageProcessor extends StringProcess{
    public static String warnMessage(String msgKey){
        return colorMessage(msgKey,COLOR.WarnColor());
    }
    public static String warnMessage(String msgKey,String... args){
        return colorMessage(msgKey,COLOR.WarnColor(),args);
    }
    public static String warnMessage(String msgKey, COLOR color,String split){
        return colorMessage(msgKey,color,COLOR.WarnColor(),split);
    }
    public static String warnMessage(String msgKey, COLOR color,String split,String... args) {
        return colorMessage(msgKey, color, COLOR.WarnColor(), split, args);
    }


    public static String errorMessage(String msgKey){
        return colorMessage(msgKey,COLOR.ErrorColor());
    }
    public static String errorMessage(String msgKey,String... args){
        return colorMessage(msgKey,COLOR.ErrorColor(),args);
    }
    public static String errorMessage(String msgKey, COLOR color,String split){
        return colorMessage(msgKey,color,COLOR.ErrorColor(),split);
    }
    public static String errorMessage(String msgKey, COLOR color,String split,String... args){
        return colorMessage(msgKey,color,COLOR.ErrorColor(),split,args);
    }

    public static String successMessage(String msgKey){
        return colorMessage(msgKey,COLOR.SuccessColor());
    }
    public static String successMessage(String msgKey,String... args){
        return colorMessage(msgKey,COLOR.SuccessColor(),args);
    }
    public static String successMessage(String msgKey, COLOR color,String split){
        return colorMessage(msgKey,color,COLOR.SuccessColor(),split);
    }
    public static String successMessage(String msgKey, COLOR color,String split,String... args){
        return colorMessage(msgKey,color,COLOR.SuccessColor(),split,args);
    }

    /**
     * Get message from plugin message {@link BukkitPlugin}
     * @param msgKey message key to message
     * @param color color for color message
     * @return colored plugin message
     */
    public static String colorMessage(String msgKey,COLOR color){
        return Colors.coloredText(
                BukkitPlugin.getPluginMessage(msgKey),
                color
        );
    }
    public static String colorMessage(String msgKey,COLOR centerColor,COLOR color,String split){
        return Colors.coloredText(
                BukkitPlugin.getPluginMessage(msgKey),split,
                color,centerColor,color
        );
    }
    public static String colorMessage(String msgKey,COLOR centerColor,COLOR color,String split,String... args){
        return Colors.coloredText(
                BukkitPlugin.getPluginMessage(msgKey).formatted((Object) args),split,
                color,centerColor,centerColor
        );
    }
    public static String colorMessage(String msgKey,COLOR color,String... args){
        return Colors.coloredText(
                BukkitPlugin.getPluginMessage(msgKey).formatted((Object) args),
                color
        );
    }
}
