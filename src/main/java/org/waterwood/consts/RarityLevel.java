package org.waterwood.consts;

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
    public static RarityLevel convertRarityLevel(String strRarityLevel){
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
}
