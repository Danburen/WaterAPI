package org.waterwood.plugin.bukkit.custom;

import org.bukkit.Material;
import org.waterwood.enums.COLOR;
import org.waterwood.enums.RarityLevel;

import java.util.List;

public interface CustomItemData extends CustomData {
    RarityLevel getDefaultRarityLevel();
    List<String> getDefaultDescription();
    COLOR getDefaultNameColor();
    Material getDefaultMaterial();

    RarityLevel getRarityLevel();
    default public List<String> getDescription(){ return List.of(""); }
    COLOR getNameColor();
    Material getMaterial();
}
