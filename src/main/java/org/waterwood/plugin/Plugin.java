package org.waterwood.plugin;

import org.waterwood.io.FileConfigProcess;

import java.util.logging.Logger;


public interface Plugin {
    void initialization();
    static Logger getLogger() {
        return null;
    }
    void loadConfig();
    void loadDefaultSource(String lang);

    /**
     * get default file path depends server type.
     * <b>file must be completed which contains file extension</b>
     * @param file file that contain <b>file extension</b>
     * @return return the <b>adequate file path</b>
     */
    String getDefaultFilePath(String file);
    void loadConfig(boolean loadMessage);
    void reloadConfig();
    void checkUpdate(String owner, String repositories);
    String getLocale();
    void checkUpdate(String owner, String repositories, String configVersion);

    /**
     * Get a source loaded {@link FileConfigProcess} class
     * source in {@link FileConfigProcess#loadSource(String...)}
     * if source not found that return a empty data class
     * @param sourcePath source in the files
     * @return source loaded {@link FileConfigProcess}
     */
    FileConfigProcess loadSource(String sourcePath);

    /**
     * Get data from a locale file
     * load file from <i>{@link Plugin#getDefaultFilePath(String) default file}</i>
     * if file not found that return an empty data class
     * @param fileName file which in default filepath(Like plugin/filename)
     * @return file loaded {@link FileConfigProcess}
     */
    FileConfigProcess loadFile(String fileName);

    /**
     * Get data from a local file
     * inner {@link Plugin#loadFile(String)}
     * which split file name to simple file name and extension
     * @param simpleName raw file name without extension
     * @param extension the extension of file
     * @return file loaded {@link FileConfigProcess}
     */
    FileConfigProcess loadFile(String simpleName,String extension);
}
