package org.waterwood.plugin.bukkit.custom;

import org.bukkit.NamespacedKey;
import org.waterwood.enums.COLOR;
import org.waterwood.enums.RarityLevel;

import java.util.List;
import java.util.Map;

public interface CustomData {
    String getName();
    String getDefaultName();
    String getRarityDisplay();

    COLOR getNameColor();
    COLOR getDefaultNameColor();
    RarityLevel getRarityLevel();
    RarityLevel getDefaultRarityLevel();
    List<String> getDescription();
    void InitDescription();
    /**
     * Get display name , colored
     * @return colored display name
     */
    String getDisplayName();
    NamespacedKey getKey();
    Map<String,Object> getData();
    <T> T getDataInfo(String key, T defaultValue);
    Map<String,Object> putData(Map<String,Object> data);

    /**
     * instance put data to {@code DATA}
     * @param key the key to input
     * @param data the data to input
     * @return the data inputted
     */
    <T> T putData(String key,T data);
    /**
     * Input data from file , if file path data not found
     * return and put default data;
     * @param key the key path to data
     * @param defaultVal default val of data
     * @return return the gotten data
     * @param <T> T
     */
    <T> T inputData(String key,T defaultVal);
}
