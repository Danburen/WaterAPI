package org.waterwood.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public abstract class FileConfiguration extends MemoryProcess implements FileConfigBase {
    public abstract <T> T get(String path);
    public abstract <T> T get(String path, T defaultVal);
    public abstract void set(String path, Object val, Map<String,Object> data);
    public abstract void set(String path, Object val);
    public abstract void save(File file,Map<String,Object> data) throws Exception;
    public abstract Map<String,Object> getFileMapData(String filePath) throws IOException;

    public abstract Map<String,Object> getFileMapData(File file) throws IOException;

    public abstract FileConfigProcess reload(String resourcePath) throws IOException;
    public abstract void loadSource(String... path) throws IOException;
    public final Map<String,String> getMap(String path){
        return  getMap(path,String.class);
    }
    public final List<Object> getList(String path){return  get(path);}
    public final <T> Map<String, T> getMap(String path, Class<T> typeClass) {
        Object out = get(path);
        if (out instanceof Map<?, ?> rawMap) {
            Map<String, T> typedMap = new HashMap<>();
            for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
                if (entry.getKey() instanceof String && typeClass.isInstance(entry.getValue())) {
                    typedMap.put((String) entry.getKey(), typeClass.cast(entry.getValue()));
                }
            }
            return typedMap;
        } else {
            return null;
        }
    }
    public final Integer getInteger(String path){
        return  get(path);
    }

    public final Double getDouble(String path){
        return  get(path);
    }
    public final Boolean getBoolean(String path){
        return  get(path);
    }
    @Deprecated
    public final ArrayList<String> getStringList(String path){
        return get(path);
    }

    public final String getString(String path){
        return (String) get(path);
    }
    public final Integer getInteger(String path,Integer defaultVal){
        Integer val = (Integer) get(path);
        return val == null ? defaultVal : val;
    }

    public final Double getDouble(String path,Double defaultVal){
        Double val = (Double) get(path);
        return val == null ? defaultVal : val;
    }
    public final Boolean getBoolean(String path,Boolean defaultVal){
        Boolean val = (Boolean) get(path);
        return val == null ? defaultVal : val;
    }

    public final List<String> getStringList(String path,List<String> defaultVal){
        Object data = get(path);
        if (data instanceof List<?> tempList) {
            if (tempList.stream().allMatch(item -> item instanceof String)) {
                return (List<String>) tempList;
            } else {
                return defaultVal;
            }
        } else {
            return defaultVal;
        }
    }

    public final String getString(String path,String defaultVal){
        String val = (String) get(path);
        return val == null ? defaultVal : val;
    }

    /**
     * create file by jar dir path
     * like PLUGIN_JAR_FILE/PLUGIN_NAME/FILE_NAME
     * inner like {@link org.waterwood.io.FileConfigProcess#createFileByPath(String, String)}
     * @param fileName filename created
     * @param pluginName the file path output plus above add plugin_name instead
     * @throws FileNotFoundException file not found exception
     */
    public void createFileByDir(String fileName, String pluginName) throws FileNotFoundException {
        createFileByPath(fileName,getJarDir() + "/" + pluginName);
    }

    public void createFileByDir(String fileName, String pluginName,String extension) throws FileNotFoundException {
        createFileByPath(fileName,getJarDir() + "/" + pluginName,extension);
    }

    /**
     * create yml file by a path
     * search file like filename.yml
     * search resource in FILENAME/[JVM LANG].yml
     * @param fileName filename created
     * @param path the file path output
     * @throws FileNotFoundException file not found exception
     */
    public void createFileByPath(String fileName,String path) throws FileNotFoundException{
        createFileByPath(fileName,path,"yml");
    }
    /**
     * create file by a path
     * search file like filename.[EXTENSION]
     * search resource in FILENAME/[JVM LANG].[EXTENSION]
     * multiple level folder file name like test/filename.[EXTENSION]
     * @param fileName filename created
     * @param path the file path output
     * @throws FileNotFoundException file not found exception
     */
    public void createFileByPath(String fileName,String path,String extension) throws FileNotFoundException{
        String source = fileName + "/" + Locale.getDefault().getLanguage() + "." + extension;
        String targetPath = path + "/" + fileName + "." + extension;
        if(isResourceExist(source)) {
            extractResource(targetPath, source);
        }else{
            extractResource(targetPath,fileName + "/en" + "." + extension);
            throw new FileNotFoundException();
        }
    }
}
