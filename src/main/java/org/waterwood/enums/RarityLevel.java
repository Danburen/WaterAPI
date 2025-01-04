package org.waterwood.enums;

import java.util.Map;

public enum RarityLevel {
    NORMAL(0,"normal"),
    ADVANCE(1,"advance"),
    RARE(2,"rare"),
    EPIC(3,"epic"),
    LEGEND(4,"legend"),
    MYTHIC(5,"mythic");
    private final int worth;
    private final String key;
    RarityLevel(int worth,String key){
        this.worth = worth;
        this.key = key;
    }

    public int getWorth() {
        return worth;
    }
    public String getKey(){
        return key;
    }
    public static RarityLevel getRarityLevel(String strRarityLevel){
        strRarityLevel = strRarityLevel.toLowerCase();
        return switch (strRarityLevel){
            case "common","normal" -> RarityLevel.NORMAL;
            case "advance" -> RarityLevel.ADVANCE;
            case "rare" -> RarityLevel.RARE;
            case "epic" -> RarityLevel.EPIC;
            case "legend" -> RarityLevel.LEGEND;
            default -> RarityLevel.NORMAL;
        };
    }
    public static RarityLevel getRarityLevel(int rarityLevel){
        return switch (rarityLevel) {
            case 1 -> RarityLevel.ADVANCE;
            case 2 -> RarityLevel.EPIC;
            case 3 -> RarityLevel.LEGEND;
            case 4 -> RarityLevel.MYTHIC;
            default -> RarityLevel.NORMAL;
        };
    }
    public static Map<String,String> getRarityLevelDisplayMap(){
        return Map.of(
                NORMAL.key,NORMAL.key,
                ADVANCE.key,ADVANCE.key,
                RARE.key,RARE.key,
                EPIC.key,EPIC.key,
                LEGEND.key,LEGEND.key,
                MYTHIC.key,MYTHIC.key
        );
    }
}
