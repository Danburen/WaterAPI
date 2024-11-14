package org.waterwood.plugin.bukkit.util;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class CustomItemData{
    private NamespacedKey namespacedKey;
    private Map<String,Object> data = new HashMap<>();
    public CustomItemData(NamespacedKey key){
        this.namespacedKey = key;
    }
    public CustomItemData(NamespacedKey key,Map<String,Object> data){
        this.data = data;
        this.namespacedKey = key;
    }
    public abstract String getName();
    public NamespacedKey getKey(){
        return namespacedKey;
    }
    public Map<String,Object> getData(){
        return data;
    }
    public void putData(Map<String,Object> data){
        this.data.putAll(data);
    }
    public void putData(String key,Object data){
        this.data.put(key,data);
    }
}
