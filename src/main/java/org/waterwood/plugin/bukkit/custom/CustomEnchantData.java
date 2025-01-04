package org.waterwood.plugin.bukkit.custom;

import org.waterwood.enums.COLOR;
import org.waterwood.enums.RarityLevel;

import java.util.List;

public interface CustomEnchantData extends CustomData {
    /**
     * Return the min level of enchant
     * @return min level
     */
    int getDefaultMinLevel();
    /**
     * Return the max level of enchant
     * @return max level
     */
    int getDefaultMaxLevel();
    String getDefaultDisplay();
    List<String> getDefaultDescription();
    RarityLevel getDefaultRarityLevel();
    COLOR getDefaultNameColor();

    RarityLevel getRarityLevel();
    int getMinLevel();
    int getMaxLevel();
    String getDisplay();
    default List<String> getDescription(){
        return List.of("");
    }
    COLOR getNameColor();
    List<String> getAllowEnchantItems();
}
