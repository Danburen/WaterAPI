package org.waterwood.plugin;

import org.waterwood.adapter.DataAdapter;
import org.waterwood.enums.COLOR;
import org.waterwood.enums.TAGS;
import org.waterwood.io.FileConfiguration;
import org.waterwood.utils.Colors;
import org.waterwood.utils.LineFontGenerator;
import org.waterwood.io.FileConfigProcess;
import org.waterwood.io.web.Updater;
import org.waterwood.utils.Translate;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;


public abstract class WaterPlugin  implements Plugin {
    private static Logger logger;
    private static final FileConfigProcess config = new FileConfigProcess();
    private static final FileConfigProcess pluginMessages = new FileConfigProcess();
    private static final Map<String,FileConfigProcess>  messages = new HashMap<>();
    private static  FileConfigProcess pluginData;
    private static boolean locale = false;
    public void initialization(){
        if(pluginData == null){
            try {
                pluginData = new FileConfigProcess();
                pluginData.loadSource("plugin.yml");
            }catch (IOException e){
                Logger.getLogger(this.getClass().getName()).warning("Plugin not founded");
            }
        }
        if (logger == null){ logger = Logger.getLogger(getPluginInfo("name"));}
    }
    public WaterPlugin(){
        initialization();
    }
    public static Logger getLogger(){
        return logger;
    }

    public void logMsg(String message){
        logger.info(Colors.parseColor(message,false));
    }

    public void logMsg(String message, COLOR color){
        logger.info(Colors.parseColor(Colors.coloredText(message,color)));
    }

    public static FileConfigProcess getConfigs(){
        return config;
    }
    public static String getPluginMessage(String path){
        return pluginMessages.getString(path);
    }
    public String getPluginName(){
        return getPluginInfo("name");
    }

    @Override
    public String getDefaultFilePath(String filePath){
        return config.getPluginFilePath(getPluginName(), filePath);
    }

    @Override
    public void loadConfig(boolean loadMessage){
        String lang = Locale.getDefault().getLanguage();
        try {
            config.createYmlFileByPath("config",getDefaultFilePath(""));
            loadConfig(lang);
            if(loadMessage) {
                locale = "locale".equals(config.getString("player-locale"));
                config.createYmlFileByPath("message", getDefaultFilePath(""));
                messages.put(lang, new FileConfigProcess().loadFile(getDefaultFilePath("message.yml")));
            }
        }catch(Exception e){
            getLogger().warning("Error when load config file , please check your config file.");
            getLogger().warning("Please check if there are any formatting issues in file(config.yml & message.yml)");
            getLogger().warning(e.getMessage());
            loadDefaultSource("en");
        }
    }
    @Override
    public void loadConfig(){
        loadConfig(true);
    }

    public void loadConfig(String lang) throws  Exception{
            config.loadFile(getDefaultFilePath("config.yml"));
            locale = "locale".equals(config.getString("player-locale"));
            pluginMessages.loadSource("locale/" + lang +  ".properties", "default/" + lang + ".properties");
    }
    @Override
    public void reloadConfig(){
        try {
            loadConfig(config.getString("locale"));
        }catch (Exception e){
            logger.warning("Error when reloading config,Please check config file!");
        }
    }

    @Override
    public FileConfigProcess loadSource(String sourcePath){
        FileConfigProcess fcp = new FileConfigProcess();
        try {
            fcp.loadSource(sourcePath);
        } catch (IOException e) {
            logger.warning("Error loading source " + sourcePath);
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
            logger.warning("Error loading file " + fileString);
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
                                logger.warning(getPluginMessage("error-download-message").formatted(link));
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
            logger.warning(getPluginMessage("config-file-out-date-message").formatted(outDataFileName));
        }
    }
    @Override
    public String getLocale(){
        return config.getString("locale") == null ? "en" : config.getString("locale");
    }
    public void loadLocale(String lang){
        if(messages.containsKey(lang)) return;
        try {
            messages.put(lang,new FileConfigProcess().loadFile("message/" + lang + ".yml"));
            logger.info(getPluginMessage("successfully-load-local-message").formatted(lang));
        }catch (IOException e){
            logger.warning(pluginMessages.getString("fail-find-local-message").formatted(lang));
        }
    }
    public static String getMessage(String key,String lang) {
        return locale ? messages.get(lang).getString(key) : getMessage(key);
    }
    public static String getMessage(String key){
        return messages.get(Locale.getDefault().getLanguage()).getString(key);
    }

    public static String getPluginInfo(String key){
        return (String)pluginData.get(key);
    }
    public void showPluginTitle(String lineTitleDisplay){
        for(String str : LineFontGenerator.parseLineText(lineTitleDisplay,1)) {
            System.out.printf(Colors.parseColor( "§6%s§r%n"), str);
        }
        System.out.printf(Colors.parseColor("§e%s §6author: §7%s §6version: §7%s%n"), getPluginInfo("name")
                , getPluginInfo("author"), getPluginInfo("version"));
    }
    public String getPluginInfo(){
        return "§6%s§r §ev§7%s§r".formatted(getPluginInfo("name"), getPluginInfo("version")) +
                "§6 by: §7%s".formatted( getPluginInfo("author"));
    }
}