package org.waterwood.plugin.bukkit.custom;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public abstract class CustomItem extends CustomBasicData implements CustomItemData {
    protected Material material;
    public CustomItem(NamespacedKey key) {
        super(key);
    }
    @Override
    public Material getMaterial() {
        return material == null ? getDefaultMaterial() : material;
    }
}
