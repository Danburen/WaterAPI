package org.waterwood.plugin.bukkit.util;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.waterwood.common.Colors;
import org.waterwood.consts.COLOR;
import org.waterwood.consts.RarityLevel;

import java.util.List;
import java.util.Map;

public abstract class CustomEnchant extends CustomItemData {
    public CustomEnchant(NamespacedKey key) {
        super(key);
    }
    public CustomEnchant(NamespacedKey key, Map<String,Object> data){
        super(key,data);
    }
    public abstract String getDefaultName();
    public abstract int getDefaultMinLevel();
    public abstract int getDefaultMaxLevel();
    public abstract COLOR getDefaultNameColor();
    public abstract List<String> getDefaultDescription();
    public abstract RarityLevel getDefaultRarityLevel();
    public abstract RarityLevel getRarityLevel();
    public abstract String getName();
    public abstract int getMinLevel();
    public abstract int getMaxLevel();
    public abstract String getDisplay();
    public abstract String getDefaultDisplay();
    public List<String> getDescription(){
        return List.of("");
    }
    public abstract COLOR getNameColor();
    /**
     * Get display name , colored
     * @return colored display name
     */
    public String getDisplayName(){
        return Colors.coloredText(this.getName(),getNameColor());
    }
    public boolean canEnchantItem(ItemStack itemStack){
        if(getAllowEnchantItems().contains("all")) return true;
        return getAllowEnchantItems()
                .stream()
                .anyMatch(ItemPrefix -> itemStack.toString().contains(ItemPrefix.toUpperCase()));
    }
    public abstract List<String> getAllowEnchantItems();
}
