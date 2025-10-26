package org.waterwood.service.impl;

import org.waterwood.io.FileConfigProcess;
import org.waterwood.plugin.WaterPlugin;
import org.waterwood.service.LoggerService;
import org.waterwood.service.MessageService;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MessageServiceImpl implements MessageService {
    private Map<String, FileConfigProcess> messages = new HashMap<>();
    private final WaterPlugin plugin;
    private final LoggerService loggerService;

    protected final static String LOCAL_LANGUAGE = Locale.getDefault().getLanguage();
    protected final static String DEFAULT_LANGUAGE = "en";
    public MessageServiceImpl(WaterPlugin plugin) {
        this.loggerService = plugin.getLoggerService();
        this.plugin = plugin;

        loadFromResource("message/" + DEFAULT_LANGUAGE + ".yml", DEFAULT_LANGUAGE);
        loadFromResource("message/" + LOCAL_LANGUAGE + ".yml", LOCAL_LANGUAGE);
    }
    @Override
    public @Nullable String getMessage(String key) {
       return getLocalMessage(key, LOCAL_LANGUAGE);
    }

    @Override
    public @Nullable String getLocalMessage(String key, String lang) {
        if(! messages.containsKey(lang)){
            if(! loadFromPath(plugin.getDefaultFilePath("message.yml"))){
                if(loadFromResource("message/" + lang + ".yml", lang)){
                    loggerService.info("could not found message.yml from config file path, " +
                            "Loaded message file for " + lang + " from resources");
                }
            }
        }
        if(! messages.containsKey(lang)){
            return null;
        }
        return messages.get(lang).get(key);
    }

    @Override
    public boolean loadFromPath(String path) {
        try{
            FileConfigProcess fileConfigProcess = new FileConfigProcess();
            fileConfigProcess.loadFile(path);
            messages.put(LOCAL_LANGUAGE,fileConfigProcess);
            return true;
        }catch (Exception e){
            loggerService.warning("Error loading message file from the plugin config files. " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean loadFromResource(String resourcePath, String lang) {
        try{
            FileConfigProcess fileConfigProcess = new FileConfigProcess();
            fileConfigProcess.loadSource(resourcePath);
            messages.put(lang, fileConfigProcess);
            return true;
        }catch (Exception e){
            loggerService.warning("Error loading message file from the plugin jar package. " + e.getMessage());
        }
        return false;
    }
    @Override
    public boolean isLangExist(String lang){
        return messages.containsKey(lang);
    }
}
