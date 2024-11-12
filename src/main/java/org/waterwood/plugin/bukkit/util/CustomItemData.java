package org.waterwood.plugin.bukkit.util;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.waterwood.common.Colors;
import org.waterwood.consts.ColorCode;

public abstract class CustomItemData{
    private NamespacedKey namespacedKey;
    public CustomItemData(NamespacedKey key){
        this.namespacedKey = key;
    }
    public abstract String getName();
    public NamespacedKey getKey(){
        return namespacedKey;
    }
    public boolean canEnchantItem(ItemStack itemStack){
        return true;
        //if (itemStack.getType().toString().endsWith("sward".toUpperCase())) return true;
    }
}
