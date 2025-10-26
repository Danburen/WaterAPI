package org.waterwood.plugin.velocity.util;

import org.waterwood.io.FileConfiguration;
import org.waterwood.plugin.WaterPlugin;

import java.util.logging.Logger;

public class MethodBase {
    private static final WaterPlugin plugin = WaterPlugin.getInstance();
    protected static FileConfiguration getConfigs(){
        return WaterPlugin.getConfigs();
    }
    protected static String getPluginMessage(String message){
        return WaterPlugin.getPluginMessage(message);
    }
    protected static Logger getLogger(){
        return WaterPlugin.getInstance().getLogger();
    }
    protected static String getMessage(String message){
        return WaterPlugin.getInstance().getMessage(message);
    }
    protected static String getMessage(String message,String language){
        return WaterPlugin.getInstance().getMessage(message,language);
    }
}
