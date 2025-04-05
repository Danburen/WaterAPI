package org.waterwood.enums;

import org.waterwood.utils.Translate;

import javax.annotation.Nullable;

/**
 * An enum holder tag and words
 */
public enum TAGS {
    NEW_FEATURES("Features"),
    FIXED("Fixed"),
    CHANGED("Changed"),
    KNOWN_ISSUES("Issues");

    private final String friendlyName;
    TAGS(String friendlyName){
        this.friendlyName = friendlyName;
    }

    public String getFriendlyName(){
        return friendlyName;
    }

    public @Nullable static TAGS getTag(String tagName){
        return switch (tagName) {
            case "New Features" -> TAGS.NEW_FEATURES;
            case "Fixed" -> TAGS.FIXED;
            case "Changed" -> TAGS.CHANGED;
            case "Known Issues" -> TAGS.KNOWN_ISSUES;
            default -> null;
        };
    }
}
