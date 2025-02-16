package org.waterwood.io.web;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.waterwood.utils.Translate;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangelogGetter extends WebIO{

    /**
     * Get the changelog file from repositories
     * @param rawVer Raw version string that like 1.0.0
     * @param lang lang to get if contain none than get en file
     * @return changelog file.
     */
    public static @Nullable String getChangelog(String owner, String repo, String rawVer, String lang){
        String filePrefix = "changelog/v" + rawVer + "/" + "changelog_v" + rawVer + "_";
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
            // ...
            Map<String,String> sections = extractLogSections(content);
            sections.forEach((k,v) ->{
                System.out.println(k + ": " + v);
            });
            return sections.get(Translate.parseLang("New Feature",lang));
        }catch (Exception e){
            return null;
        }
    }

    /**
     * Extract log sections form a string
     * @param original original string
     * @return Map of sections
     */
    public static Map<String,String> extractLogSections(String original){
        Map<String,String> sections = new LinkedHashMap<>();
        String[] lines = original.split("\n");
        String currentTitle = null;
        StringBuilder currentContent = null;

        for (String line : lines) {
            if(line.startsWith("###")){
                if(currentTitle == null){
                    currentTitle = line.replace("#","").trim();
                }else{
                    if(currentContent != null){
                        sections.put(currentTitle, currentContent.toString());
                        currentContent = null;
                        currentTitle = null;
                    }
                }
            }else{
                if(currentContent == null){
                    currentContent = new StringBuilder(line);
                }else{
                    currentContent.append("\n").append(line);
                }
            }
        }
        if(currentContent != null){
            sections.put(currentTitle, currentContent.toString());
        }
        return sections;
    }
}
