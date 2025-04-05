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
                "Feature",
                "Fixes",
                "Changed",
                "Issues"
        }),
        ZH(new String[]{
                "特性",
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

    public static String parseLang(String input, String lang) {
        LANG targetLang = LANG.valueOf(lang.toUpperCase());
        String[] sourceTranslations = LANG.EN.getTranslations();
        String[] targetTranslations = targetLang.getTranslations();

        // 如果是中文输入，尝试反向匹配英文
        if (lang.equalsIgnoreCase("ZH")) {
            for (int i = 0; i < targetTranslations.length; i++) {
                if (targetTranslations[i].equals(input)) {
                    return sourceTranslations[i]; // 返回对应的英文
                }
            }
        }

        // 默认行为：英文→中文翻译
        for (int i = 0; i < sourceTranslations.length; i++) {
            if (sourceTranslations[i].equalsIgnoreCase(input)) {
                return targetTranslations[i];
            }
        }
        return input; // 未找到时返回原输入
    }

    public @Nullable LANG getLang(String engStr) {
        return LANG.valueOf(engStr.toUpperCase());
    }
}
