package org.waterwood.enums;

public enum COLOR {
    BLACK,DARKBLUE,DARK_GREEN,DARK_AQUA,DARK_RED,DARK_PURPLE,GOLD,GRAY,
    DARK_GRAY,BLUE,GREEN,AQUA,RED,LIGHT_PURPLE,YELLOW,WHITE,RESET,
    RAINBOW;
    public static COLOR WarnColor(){
        return YELLOW;
    }
    public static COLOR ErrorColor(){
        return RED;
    }
    public static COLOR SuccessColor(){
        return GREEN;
    }
}
