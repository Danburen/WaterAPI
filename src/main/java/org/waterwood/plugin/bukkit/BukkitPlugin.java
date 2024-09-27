package org.waterwood.plugin.bukkit;

import org.waterwood.common.LineFontGenerator;
import org.waterwood.io.FileConfigProcess;
import org.waterwood.io.web.Updater;
import org.bukkit.plugin.java.JavaPlugin;
import org.waterwood.common.Colors;
import org.waterwood.plugin.Plugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

public class BukkitPlugin extends JavaPlugin implements Plugin {
    private static Logger logger;
    private static final FileConfigProcess config = new FileConfigProcess();
    private static final FileConfigProcess pluginMessages = new FileConfigProcess();
    private static final Map<String,FileConfigProcess> messages = new HashMap<>();
    private static  FileConfigProcess pluginData;
    private static boolean locale = false;
    public void initialization(){
        if(pluginData == null){
            try {
                pluginData = new FileConfigProcess();
                pluginData.loadSource("org/waterwood/plugin", "yml");
            }catch (IOException e){
                Logger.getLogger(this.getClass().getName()).warning("Plugin not founded");
            }
        }
        if (logger == null){ logger = Logger.getLogger(getPluginInfo("name"));}
    }
    public BukkitPlugin(){
        initialization();
    }

    public static void logMsg(String message){
        logger.info(Colors.parseColor(message));
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
    public String getDefaultFilePath(String file){
        return config.getJarDir() + "\\" +getPluginName() + "\\" + file;
    }
    public String getPluginFilePath(String fileName){
        return getDataFolder() + "\\" + fileName;
    }
    public static String getPluginInfo(String path){
        return pluginData.getString(path);
    }
    @Override
    public void loadConfig(boolean loadMessage){
        String lang = Locale.getDefault().getLanguage();
        try {
            config.createFileByPath("config",getDataFolder().toString());
            config.loadFile(getPluginFilePath("config.yml"));
            pluginMessages.loadSource("locale/" + lang , "properties");
            locale = "locale".equals(config.getString("player-locale"));
            if(loadMessage) {loadLocalMsg(lang,loadMessage);}
        }catch(Exception e){
            e.printStackTrace();
            getLogger().warning("Error when load config file, missing lang:" + lang + "\nUsing default lang en");
            loadDefaultSource("en");
        }
    }
    @Override
    public void loadConfig(){
        loadConfig(true);
    }

    @Override
    public void reloadConfig(){
        String lang = config.getString("locale");
        try {
            config.loadFile(getPluginFilePath("config.yml"));
            locale = "locale".equals(config.getString("player-locale"));
            pluginMessages.loadSource("locale/" + lang , "properties");
        }catch(Exception e){
            logger.warning("Error when load config file, missing lang:" + lang + "\nUsing default lang en");
            loadDefaultSource("en");
        }
    }

    public void reloadConfig(String dataName) throws IOException{
        switch (dataName) {
            case "config" -> config.loadFile(getPluginFilePath("config.yml"));
            case "message" -> pluginMessages.loadSource("locale/" + config.getString("locale"), "properties");
            default -> reloadConfig();
        }
    }

    public void loadLocalMsg(String lang, boolean  load) throws IOException {
        if(load) {
            config.createFileByPath("message", getDataFolder().toString());
            messages.put(lang, new FileConfigProcess().loadFile(getPluginFilePath("message.yml")));
        }
    }

    @Override
    public final String getDefaultSourcePath(String source, String extension, String lang){
        return source + "/" + lang +"."+ extension;
    }

    @Override
    public void loadDefaultSource(String lang){
        try {
            config.loadSource(getDefaultSourcePath("config","yml","en"));
            pluginMessages.loadSource("locale/en.properties");
        }catch (IOException e){
            getLogger().warning("Source not founded!");
        }
    }
    @Override
    public void checkUpdate(String owner, String repositories){
        if (!Boolean.TRUE.equals(config.getBoolean("check-update.enable"))) { return; }
        getLogger().info(getPluginMessage("checking-update-message"));
        Updater.CheckForUpdata(owner, repositories, Updater.parseVersion(getPluginInfo("version"))).thenAccept(updateInfo -> {
            if(updateInfo == null){
                getLogger().warning(getPluginMessage("error-check-update-message"));
            }else{
                if((boolean)updateInfo.get("hasNewVersion")){
                    if(Boolean.TRUE.equals(config.get("check-update.auto-download"))){
                        String link = (String) updateInfo.get("downloadLink");
                        logMsg(getPluginMessage("new-version-download-message").formatted(updateInfo.get("latestVersion")));
                        String pathDownload = config.getJarDir() + "\\" + getPluginName() + updateInfo.get("latestVersion") +".jar";
                        Updater.downloadFile(link, pathDownload).thenAccept(
                                result -> {
                                    if(result){
                                        logger.info(Colors.parseColor(getPluginMessage("successfully-download-message").formatted(pathDownload)));
                                    }else{
                                        logger.warning(getPluginMessage("error-download-message").formatted(link));

                                    }
                                }
                        );
                    }else{
                        logMsg(getPluginMessage("new-version-founded-message").formatted(updateInfo.get("latestVersion"),
                                updateInfo.get("downloadLink")));
                    }
                }else{
                    logMsg(getPluginMessage("latest-version-message"));
                }
            }
        });
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
    public static String getMessage(String key){return messages.get(Locale.getDefault().getLanguage()).getString(key);}
    public static String getPluginInfo(){
        return "§6§l%s§r §ev§7%s§r".formatted(getPluginInfo("name"), getPluginInfo("version")) +
                "§6§l by: §7%s".formatted( getPluginInfo("author"));
    }
    public void showPluginTitle(String lineTitleDisplay){
        for(String str : LineFontGenerator.parseLineText(lineTitleDisplay)) {
            logMsg("§6%s§r".formatted(str));
        }
        logMsg("§e%s §6author:§7%s §6version:§7%s".formatted(getPluginInfo("name")
                , getPluginInfo("author"), getPluginInfo("version")));
    }
}