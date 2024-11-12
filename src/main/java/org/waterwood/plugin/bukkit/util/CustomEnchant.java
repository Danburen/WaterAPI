package org.waterwood.plugin.bukkit.util;

import org.bukkit.NamespacedKey;
import org.waterwood.common.Colors;
import org.waterwood.consts.ColorCode;

public abstract class CustomEnchant extends CustomItemData {
    public CustomEnchant(NamespacedKey key) {
        super(key);
    }

    public abstract String getName();
    public abstract int getMinLevel();
    public abstract int getMaxLevel();
    public String getDescription(){
        return "";
    }
    public abstract ColorCode getNameColor();
    /**
     * Get display name , colored
     * @return colored display name
     */
    public String getDisplayName(){
        return Colors.coloredText(this.getName(),getNameColor());
    }
}
