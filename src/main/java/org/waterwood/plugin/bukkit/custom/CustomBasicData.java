package org.waterwood.plugin.bukkit.custom;

import org.bukkit.NamespacedKey;
import org.waterwood.enums.COLOR;
import org.waterwood.enums.RarityLevel;
import org.waterwood.utils.Colors;
import org.waterwood.adapter.DataAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CustomBasicData implements CustomData{
    private final NamespacedKey namespacedKey;
    private Map<String,Object> data = new HashMap<>();
    protected List<String> Description;
    protected String name;
    protected COLOR nameColor;
    protected RarityLevel rarity;
    protected String rarityDisplay;
    public CustomBasicData(NamespacedKey key){
        this.namespacedKey = key;
    }
    @Override
    public String getName() {
        return name == null ? getDefaultName():name;
    }
    @Override
    public String getRarityDisplay(){
        return this.getRarityLevel() == null ? RarityLevel.NORMAL.getKey() : this.getRarityLevel().getKey();
    }
    @Override
    public COLOR getNameColor() {
        return nameColor == null ? getDefaultNameColor():nameColor;
    }
    @Override
    public List<String> getDescription(){
        return Description == null ? List.of("") : Description;
    }
    @Override
    public NamespacedKey getKey(){
        return namespacedKey;
    }
    @Override
    public COLOR getDefaultNameColor() {
        return Colors.getRarityColor(getRarityLevel());
    }
    @Override
    public RarityLevel getRarityLevel(){
        return rarity == null ? getDefaultRarityLevel() : rarity;
    }
    @Override
    public Map<String,Object> getData(){
        return data;
    }
    @Override
    public <T> T getDataInfo(String key, T defaultValue){
        return DataAdapter.toValue(getData().get(key), defaultValue);
    }
    @Override
    public Map<String,Object> putData(Map<String,Object> data){
        this.data.putAll(data);
        return data;
    }
    @Override
    public <T> T putData(String key,T data){
        this.data.put(key,data);
        return data;
    }
    @Override
    public String getDisplayName(){
        return Colors.coloredText(this.getName(),getNameColor());
    }
    /**
     * Initial Each data from elsewhere
     * Supper <b> Must pe placed </b> at the last row of override
     */
    public abstract void InitData();
}
