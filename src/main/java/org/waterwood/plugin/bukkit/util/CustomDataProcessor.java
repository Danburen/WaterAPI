package org.waterwood.plugin.bukkit.util;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class CustomDataProcessor extends ItemMetaProcessor{
    public static <T> boolean setCustomData(ItemStack item, NamespacedKey namespacedKey, PersistentDataType type, T data){
        return getOptionalItemMeta(item).map(
                itemMeta -> {
                    itemMeta.getPersistentDataContainer().set(namespacedKey, type, data);
                    return true;
                }
        ).orElse(false);
    }
}
