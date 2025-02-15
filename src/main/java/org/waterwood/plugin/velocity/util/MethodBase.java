package org.waterwood.plugin.velocity.util;

import org.waterwood.io.FileConfiguration;
import org.waterwood.plugin.WaterPlugin;

import java.util.logging.Logger;

public class MethodBase {
    protected static FileConfiguration getConfigs(){
        return WaterPlugin.getConfigs();
    }
    protected static String getPluginMessage(String message){
        return WaterPlugin.getPluginMessage(message);
    }
    protected static Logger getLogger(){
        return WaterPlugin.getLogger();
    }
    protected static String getMessage(String message){
        return WaterPlugin.getMessage(message);
    }
    protected static String getMessage(String message,String language){
        return WaterPlugin.getMessage(message,language);
    }
}
