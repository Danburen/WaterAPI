package org.waterwood.plugin;

import java.util.logging.Logger;


public interface Plugin {

    void initialization();

    static Logger getLogger() {
        return null;
    }

    String getDefaultSourcePath(String source, String extension, String lang);

    void loadConfig();

    void loadDefaultSource(String lang);

    String getDefaultFilePath(String file);

    void loadConfig(boolean loadMessage);

    void reloadConfig();
    void checkUpdate(String owner, String repositories);
}
