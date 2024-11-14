package org.waterwood.io;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class FileConfigProcess extends FileConfiguration {
    private Map<String,Object> data;
    Yaml yaml = new Yaml(getDumperOptions());

    @Override
    public void set(String path, Object val, Map<String, Object>  data){
        String[] keys= path.split("\\.");
        Map<String,Object> map = (Map<String,Object>)getHashMapData(keys,data);
        if (map != null) {
            map.put(keys[keys.length - 1], val);
        }
    }

    @Override
    public void set(String path,Object val){
        set(path,val,data);
    }

    @Override
    public void save(File file,Map<String,Object> data) throws Exception {
        try(FileWriter writer = new FileWriter(file)){
            yaml.dump(data,writer);
        }catch(Exception e){
            throw new Exception();
        }
    }
    public FileConfigProcess loadFile(String filePath) throws IOException{
        data = getFileMapData(filePath);
        return this;
    }
    public FileConfigProcess loadFile(File file) throws IOException{
        data = getFileMapData(file);
        return this;
    }

    @Override
    public Map<String, Object> getFileMapData(String filePath) throws IOException {
        File file = new File(filePath);
        return getFileMapData(file);
    }
    @Override
    public Map<String,Object> getFileMapData(File file) throws IOException{
        try(InputStream FIS = new FileInputStream(file)){
            return yaml.load(FIS);
        }catch (IOException e){
            throw new IOException(e);
        }
    }
    @Override
    public FileConfigProcess reload(String resourcePath) throws IOException {
        try {
            data.putAll(getFileMapData(resourcePath));
        }catch(IOException e){
            throw new IOException();
        }
        return this;
    }
    public Map<String,Object> getSourceMapData(String source) throws  IOException{
        String extension = source.substring(source.lastIndexOf(".") + 1);
        try(InputStream IS = getClass().getResourceAsStream(source)){
            if(extension.equals("yml")) {
                return  yaml.load(IS);
            }else if(extension.equals("properties")){
                Properties prop = new Properties();
                prop.load(new InputStreamReader(IS, StandardCharsets.UTF_8));
                return ((Map<Object,Object>) prop).entrySet().stream().collect(
                        Collectors.toMap(e -> e.getKey().toString(),Map.Entry::getValue));
            }else{
                return null;
            }
        }catch (IOException e){
            throw new IOException();
        }
    }
    @Override
    public void loadSource(String... paths) throws IOException {
        if (data == null) data = new HashMap<>();
        for(String path : paths){
            data.putAll(getSourceMapData("/" + path));
        }
    }

    @Override
    public final Object get(String path){
        return get(path,data);
    }
    @Override
    public final Object get(String path,Object defaultVal){
        Object val = get(path,data);
        return val == null ? defaultVal : val;
    }
    public static Object get(String path,Map<String,Object> data){
        String[] keys = path.split("\\.");
        return getHashMapData(keys,data);
    }

    public Map<String,Object> getMapData(){
        return data == null ? new HashMap<>() : data;
    }
    public static Object getHashMapData(String[] keys, Map<String,Object> data){
        Object currentData = data;
        for (String key : keys) {
            if (currentData instanceof Map) {
                Map<String, Object> currentMap = (Map<String, Object>) currentData;
                if (currentMap.containsKey(key)) {
                    currentData = currentMap.get(key);
                } else {
                    return null;
                }
            } else if (currentData instanceof List<?> currentList) {
                try {
                    int index = Integer.parseInt(key);
                    if (index >= 0 && index < currentList.size()) {
                        currentData = currentList.get(index);
                    } else {
                        return null;
                    }
                } catch (NumberFormatException e) {
                    return null;
                }
            } else {
                return null;
            }
        }
        return currentData;
    }

    public void putAll(Map<String,Object> data){
        this.data.putAll(data);
    }

    public static DumperOptions getDumperOptions(){
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        return options;
    }
}
