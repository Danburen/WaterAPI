package org.waterwood.utils;

import javax.annotation.Nullable;

/**
 * A class for translation
 * Only for signal words
 * @since 1.1.0
 * @author Danburen
 */
public class Translate {
    public enum LANG {
        EN(new String[]{
                "New Feature",
                "Fixed",
                "Changed",
                "Known Bugs"
        }),
        ZH(new String[]{
                "新特性",
                "修复",
                "改动",
                "已知问题"
        });

        private final String[] translations;

        LANG(String[] translations) {
            this.translations = translations;
        }

        public String[] getTranslations() {
            return translations;
        }
    }

    public static String parseLang(String engStr,String lang) {
        try{
            LANG code = LANG.valueOf(lang.toUpperCase());
            int ind = 0;
            for(int i = 0 ; i < LANG.EN.translations.length ; i++) {
                if(LANG.EN.translations[i].equals(engStr)) {
                    ind = i;
                }
            }
            return code.getTranslations()[ind];
        }catch(IllegalArgumentException e){
            return engStr;
        }
    }

    public @Nullable LANG getLang(String engStr) {
        return LANG.valueOf(engStr.toUpperCase());
    }
}
