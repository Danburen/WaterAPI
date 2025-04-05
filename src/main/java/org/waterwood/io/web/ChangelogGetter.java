package org.waterwood.io.web;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.waterwood.enums.TAGS;
import org.waterwood.utils.Translate;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangelogGetter extends WebIO{

    /**
     * Get the changelog file from repositories
     * @param lang lang to get if contain none than get en file
     * @return changelog map of log.
     */
    public static @Nullable Map<TAGS,String> getChangelog(String owner, String repo, int major,int minor, int patch, String lang){
        String filePrefix = String.format("changelog/v%d/changelog_v%d.%d_", major, major, minor);
        String defaultPath = filePrefix + "en.md";
        String filePath = filePrefix + lang + ".md";
        String apiURL = "https://api.github.com/repos/" + owner + "/" + repo + "/contents/" + filePath;
        String response = sendGetRequest(apiURL);
        if(response == null){ return null; }
        try{
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
            String content = jsonObject.get("content").getAsString();
            String encoding = jsonObject.get("encoding").getAsString();
            if(encoding.equalsIgnoreCase("base64")){
                content = new String(Base64.getMimeDecoder().decode(content), StandardCharsets.UTF_8);
            }
            return extractLogSections(content,major,minor,patch,lang);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * Extract log sections for a specific version from a changelog string
     * @param original The original changelog content
     * @param major Major version number to extract
     * @param minor Minor version number to extract
     * @param patch Patch version number to extract
     * @return Map of sections for the specified version
     */
    public static Map<TAGS,String> extractLogSections(String original,int major,int minor, int patch,String lang){
        Map<TAGS, String> sections = new LinkedHashMap<>();
        String[] lines = original.split("\n");
        String currentTitle = null;
        StringBuilder currentContent = null;
        boolean inTargetVersion = false;
        String targetVersionHeader = String.format("## [%d.%d.%d]", major, minor, patch);

        for (String line : lines) {
            // Check if we've found our target version section
            if (line.startsWith("## ") && line.contains(targetVersionHeader)) {
                inTargetVersion = true;
                continue;
            }
            // If we encounter another version header while processing, stop
            if (inTargetVersion && line.startsWith("## ")) {
                break;
            }
            // Only process content if we're in the target version section
            if (inTargetVersion) {
                if (line.startsWith("###")) {
                    if (currentTitle == null) {
                        currentTitle = line.replace("#", "").trim();
                    } else {
                        if (currentContent != null) {
                            putSection(sections, currentTitle, currentContent.toString(),lang);
                            currentContent = null;
                        }
                        currentTitle = line.replace("#", "").trim();
                    }
                } else {
                    if (currentTitle == null) continue;
                    if (currentContent == null) {
                        currentContent = new StringBuilder(line);
                    } else {
                        currentContent.append("\n").append(line);
                    }
                }
            }
        }
        // Add the last section if it exists
        if (currentContent != null) {
            putSection(sections, currentTitle, currentContent.toString(),lang);
        }
        return sections;
    }

    private static void putSection(Map<TAGS,String> section,String title, String content,String lang){
        TAGS tag = TAGS.getTag(title,lang);
        System.out.println(tag);
        if(tag != null){
            section.put(tag, content);
        }
    }
}
