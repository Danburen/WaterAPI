package org.waterwood.io;


public interface FileConfigBase {
    static Object get(String path) {
        return null;
    }
    static Object get(String path,Object def) {return null;}
    void set(String path, Object val);
}
