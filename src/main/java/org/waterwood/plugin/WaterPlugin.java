package org.waterwood.plugin;

import lombok.Getter;
import org.waterwood.adapter.DataAdapter;
import org.waterwood.enums.COLOR;
import org.waterwood.io.FileConfiguration;
import org.waterwood.service.LoggerService;
import org.waterwood.service.MessageService;
import org.waterwood.service.impl.MessageServiceImpl;
import org.waterwood.utils.Colors;
import org.waterwood.utils.LineFontGenerator;
import org.waterwood.io.FileConfigProcess;
import org.waterwood.io.web.Updater;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public abstract class WaterPlugin implements Plugin {
    protected static LoggerService loggerService;
    private static final FileConfigProcess config = new FileConfigProcess();
    private static final FileConfigProcess pluginMessages = new FileConfigProcess();
    private static final FileConfigProcess pluginData = new FileConfigProcess();
    private MessageService msgService;
    private static boolean playerLocal = false;
    @Getter
    private static WaterPlugin instance = null;

    private static final String LOCAL_LANG = Locale.getDefault().getLanguage();
    public void initialization(){
        try {
            pluginData.loadSource("plugin.yml");
        }catch (IOException e){
            Logger.getLogger(this.getClass().getName()).warning("Plugin not founded");
        }
        loggerService = new LoggerService(getPluginInfo("name"));
        this.msgService = new MessageServiceImpl(this);
    }
    public WaterPlugin(){
        instance = this;
        initialization();
    }

    @Override
    public Logger getLogger(){
        return loggerService.getLogger();
    }

    public void logMsg(String message){
        loggerService.logMessage(message);
    }

    public void logMsg(String message, COLOR color){
        loggerService.logMessage(message,color);
    }

    public static FileConfigProcess getConfigs(){
        return config;
    }

    public static String getPluginMessage(String path){
        return pluginMessages.getString(path);
    }

    public static String getPluginMessage(String path,Object... args){
        return pluginMessages.getString(path).formatted(args);
    }

    public static String getPluginName(){
        return getPluginInfo("name");
    }

    @Override
    public String getDefaultFilePath(String filePath){
        return config.getPluginFilePath(getPluginName(), filePath);
    }

    @Override
    public void loadConfig(){
        try {
            loadConfigs(LOCAL_LANG);
        }catch(Exception e){
            getLogger().warning("Error when load config file , please check your config file.");
            getLogger().warning("Please check if there are any formatting issues in file(config.yml & message.yml)");
            getLogger().warning(e.getMessage());
            loadDefaultSource("en");
        }
    }

    public void loadConfigs(String lang) throws  Exception{
        config.createYmlFileByPath("config",getDefaultFilePath(""));
        config.loadFile(getDefaultFilePath("config.yml"));
        playerLocal = "locale".equals(config.getString("player-locale", "global"));
        config.createYmlFileByPath("message", getDefaultFilePath(""));

        FileConfigProcess messageConfig = new FileConfigProcess();
        if(playerLocal){
            msgService.loadFromResource("message/" + lang + ".yml", lang);
        }else{
            msgService.loadFromPath(getDefaultFilePath("message.yml"));
        }
        pluginMessages.loadSource("locale/" + lang +  ".properties", "default/" + lang + ".properties");
    }
    @Override
    public void reloadConfig(){
        try {
            loadConfigs(config.getString("locale", "en"));
        }catch (Exception e){
            loggerService.warning("Error when reloading config,Please check config file!");
        }
    }

    @Override
    public FileConfigProcess loadSource(String sourcePath){
        FileConfigProcess fcp = new FileConfigProcess();
        try {
            fcp.loadSource(sourcePath);
        } catch (IOException e) {
            loggerService.warning("Error loading source " + sourcePath);
        }
        return fcp;
    }
    @Override
    public FileConfigProcess loadFile(String fileName) {
        int dotInd = fileName.indexOf(".");
        String simpleName = fileName.substring(0,dotInd);
        String extension = fileName.substring(dotInd + 1);
        return loadFile(simpleName,extension);
    }

    @Override
    public FileConfigProcess loadFile(String fileName,String extension) {
        FileConfigProcess fcp = new FileConfigProcess();
        String fileString = fileName+"." + extension;
        try {
            fcp.createFileByPath(fileName,getDefaultFilePath(""),extension);
            File file = new File(getDefaultFilePath(fileString));
            return fcp.loadFile(file);
        } catch (IOException e) {
            loggerService.warning("Error loading file " + fileString);
            e.printStackTrace();
        }
        return fcp;
    }

    public void reloadPluginMessage(){
        try {
            pluginMessages.loadSource("locale/" + config.getString("locale") + ".properties");
        }catch (Exception e){
            loadDefaultSource("en");
        }
    }

    @Override
    public void loadDefaultSource(String lang){
        try {
            config.loadSource("config/en.yml");
            pluginMessages.loadSource("locale/en.properties");
        }catch (IOException e){
            getLogger().warning("Source not founded!");
        }
    }
    @Override
    public void checkUpdate(String owner, String repositories){
        if (! config.getBoolean("check-update.enable",false)) return;
        getLogger().info(getPluginMessage("checking-update-message"));
        Updater.CheckForUpdate(owner, repositories, DataAdapter.parseVersion(getPluginInfo("version")))
                .thenAccept(updateInfo -> {
            if(updateInfo == null){
                getLogger().warning(getPluginMessage("error-check-update-message"));
            }else{
                if(updateInfo.IS_NEW_VERSION_AVAILABLE()){
                    if(Boolean.TRUE.equals(config.get("check-update.auto-download"))){
                        updateInfo.printUpdateInfo();
                        String link = updateInfo.DOWNLOAD_URL();
                        logMsg(getPluginMessage("new-version-download-message").formatted(updateInfo.LATEST_VERSION()));
                        String pathDownload = "plugins/" + getPluginName() + updateInfo.LATEST_VERSION() +".jar";
                        Updater.downloadFile(link, pathDownload,updateInfo.DOWNLOAD_SIZE()).thenAccept(result -> {
                            if(result){
                                logMsg(getPluginMessage("successfully-download-message").formatted(pathDownload));
                            }else{
                                loggerService.warning(getPluginMessage("error-download-message").formatted(link));
                            }
                        });
                    }else{
                        logMsg(getPluginMessage("new-version-info-message"));
                    }
                }else{
                    logMsg(getPluginMessage("latest-version-message"));
                }
            }
        });
    }

    @Override
    public void checkUpdate(String owner, String repositories, String configVersion, FileConfiguration... configs){
        checkUpdate(owner,repositories);
        List<Double> configVersions = Arrays.stream(configs)
                .map(config-> config.getString("config-version","0.0.0"))
                .map(DataAdapter::parseVersion)
                .toList();
        List<String> outDataFileName = new ArrayList<>();
        final double AVAILABLE_VERSION = DataAdapter.parseVersion(configVersion);
        boolean isOutOfData = false;
        for(int i = 0 ; i < configVersions.size() ; i++){
            if(configVersions.get(i) < AVAILABLE_VERSION){
                outDataFileName.add(new File(configs[i].getDataFilePath()).getName());
                isOutOfData = true;
            }
        }
        if(isOutOfData){
            loggerService.warning(getPluginMessage("config-file-out-date-message").formatted(outDataFileName));
        }
    }
    @Override
    public String getLocale(){
        return config.getString("locale") == null ? "en" : config.getString("locale");
    }

    public void loadLocale(String lang){
        if(msgService.isLangExist(lang)) return;
        msgService.loadFromResource("message/" + lang + ".yml", lang);
        loggerService.logMessage(getPluginMessage("successfully-load-local-message").formatted(lang));
    }

    public String getMessage(String key,String lang) {
        return msgService.getLocalMessage(key, lang);
    }
    public String getMessage(String key) {
        return msgService.getMessage(key);

    }
    public static String getPluginInfo(String key){
        return (String)pluginData.get(key);
    }

    public void showPluginTitle(String lineTitleDisplay){
        for(String str : LineFontGenerator.parseLineText(lineTitleDisplay,1)) {
            logMsg(Colors.coloredText(str,COLOR.GOLD));
        }
        logMsg(Colors.coloredText("Author: %s Version: %s".formatted(
                getPluginInfo("author"), getPluginInfo("version")),
                " ",COLOR.GOLD,COLOR.GRAY,COLOR.GOLD,COLOR.GRAY));
    }

    public static String getPluginInfo(){
            return Colors.coloredText("%s by:%s v%s".formatted(getPluginName(),
                            getPluginInfo("author"), getPluginInfo("version")),
                    " ",COLOR.GRAY,COLOR.GOLD,COLOR.GOLD);
    }

    public LoggerService getLoggerService() {
        return loggerService;
    }
}