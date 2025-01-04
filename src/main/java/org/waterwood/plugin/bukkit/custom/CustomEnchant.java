package org.waterwood.plugin.bukkit.custom;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.waterwood.utils.Colors;

import java.util.List;

public abstract class CustomEnchant extends CustomBasicData implements CustomEnchantData {
    public CustomEnchant(NamespacedKey key) {
        super(key);
    }
    protected int maxLevel;
    protected int minLevel;
    protected String display;
    @Override
    public int getMinLevel() {
        return minLevel == 0 ? getDefaultMinLevel() : minLevel;
    }
    @Override
    public int getMaxLevel() { return maxLevel == 0 ? getDefaultMaxLevel() : maxLevel; }
    @Override
    public String getDisplay(){
        return display == null ? getDefaultDisplay() : display;
    }
    public boolean canEnchantItem(ItemStack itemStack){
        if(getAllowEnchantItems().contains("all")) return true;
        return getAllowEnchantItems()
                .stream()
                .anyMatch(ItemPrefix -> itemStack.toString().contains(ItemPrefix.toUpperCase()));
    }
    public abstract List<String> getAllowEnchantItems();
}
