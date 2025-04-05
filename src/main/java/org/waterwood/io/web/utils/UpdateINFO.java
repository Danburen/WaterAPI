package org.waterwood.io.web.utils;

import org.waterwood.enums.TAGS;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * A class to storing update info
 * @since 1.1.0
 * @author Danburen
 */
public class UpdateINFO {
    private final String DOWNLOAD_URL;
    private final String LATEST_VERSION;
    private final boolean NEW_VERSION_AVAILABLE;
    private final Map<TAGS,String> CHANGE_INFO;

    public UpdateINFO(String downloadURL, String latestVersion, boolean newVersionAvailable, Map<TAGS,String> changeInfo) {
        this.DOWNLOAD_URL = downloadURL;
        this.LATEST_VERSION = latestVersion;
        this.NEW_VERSION_AVAILABLE = newVersionAvailable;
        this.CHANGE_INFO = changeInfo;
    }

    public String DOWNLOAD_URL() {
        return DOWNLOAD_URL;
    }

    public String LATEST_VERSION() {
        return LATEST_VERSION;
    }

    public Double LatestVersion() {
        return Double.parseDouble(LATEST_VERSION);
    }

    public boolean IS_NEW_VERSION_AVAILABLE() {
        return NEW_VERSION_AVAILABLE;
    }

    public @Nullable Map<TAGS, String> CHANGE_INFO() {
        return CHANGE_INFO;
    }

    public void printUpdateInfo(){
        CHANGE_INFO.forEach((k,v)->{
            System.out.println(k.getEnglishName());
            if(v != null){
                System.out.println(v);
            }
        });
    }
}
