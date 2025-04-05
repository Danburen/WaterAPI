package org.waterwood.enums;

import org.waterwood.utils.Translate;
import javax.annotation.Nullable;
import java.util.Arrays;

public enum TAGS {
    FEATURES("Features", "特性"),
    FIXES("Fixes", "修复"),
    CHANGED("Changed", "改动"),
    ISSUES("Issues", "已知问题");

    private final String englishName;
    private final String chineseName;

    TAGS(String englishName, String chineseName) {
        this.englishName = englishName;
        this.chineseName = chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    /**
     * 根据标签名（支持中英文）返回对应的TAGS枚举
     * @param tagName 标签名（如 "Features" 或 "特性"）
     * @param lang 语言代码（可选，如 "EN" 或 "ZH"）
     */
    public static @Nullable TAGS getTag(String tagName, @Nullable String lang) {
        if (lang != null) {
            // 如果指定语言，尝试翻译后匹配
            String translated = Translate.parseLang(tagName, lang);
            return Arrays.stream(values())
                    .filter(tag -> tag.englishName.equalsIgnoreCase(translated) ||
                            tag.chineseName.equals(translated))
                    .findFirst()
                    .orElse(null);
        } else {
            // 未指定语言时，直接匹配中英文
            return Arrays.stream(values())
                    .filter(tag -> tag.englishName.equalsIgnoreCase(tagName) ||
                            tag.chineseName.equals(tagName))
                    .findFirst()
                    .orElse(null);
        }
    }
}