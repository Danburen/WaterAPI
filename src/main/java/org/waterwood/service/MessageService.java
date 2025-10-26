package org.waterwood.service;

public interface MessageService {
    /**
     * Get the message from config file or properties.
     * @param key the key of the message.
     */
    String getMessage(String key);
    /**
     * Get the message from config file or properties.
     * default from resource files of jar package.
     * @param lang the language of the message.
     * @param key the key of the message.
     */
    String getLocalMessage(String lang, String key);

    boolean loadFromPath(String path);

    boolean loadFromResource(String resourcePath, String lang);

    boolean isLangExist(String lang);
}
