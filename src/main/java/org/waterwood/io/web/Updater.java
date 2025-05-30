package org.waterwood.io.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.waterwood.adapter.DataAdapter;
import org.waterwood.io.web.utils.UpdateINFO;
import org.waterwood.utils.Parser;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * A class to get and download latest data from GitHub
 * @since 1.0.1
 */
public abstract class Updater extends WebIO {

    /**
     * Checking for update , config  will automatically load.
     * API GET LIKE: https://api.github.com/repos/[NAME]/[REPOSITORIES]/releases/latest
     * @param owner GitHubUsername
     * @param repo your project's repositories
     * @param currentVer current version
     * @return CompletableFuture contains update info
     */
    public static CompletableFuture<UpdateINFO> CheckForUpdate(String owner, String repo, Double currentVer){
        return CompletableFuture.supplyAsync(() ->{
            String url = "https://api.github.com/repos/"+ owner +"/"+ repo +"/releases/latest";
            String latestJSON = sendGetRequest(url);
            try {
                JsonObject jsonObject = JsonParser.parseString(latestJSON).getAsJsonObject();
                String downloadLink = null;
                long downloadSize = 0;
                JsonArray assets = jsonObject.getAsJsonArray("assets");
                for (JsonElement asset : assets) {
                    downloadLink = asset.getAsJsonObject().get("browser_download_url").getAsString();
                    downloadSize = asset.getAsJsonObject().get("size").getAsLong();
                    if (downloadLink != null) break;
                }
                String latestVersion = jsonObject.get("tag_name").getAsString();
                double latest = DataAdapter.parseVersion(latestVersion);
                if (currentVer >= latest) {
                    return new UpdateINFO();
                } else {
                    int[] version = Parser.parseVersionToArray(latestVersion);
                    int major = version[0];
                    int minor = version[1];
                    int patch = version[2];
                    return new UpdateINFO(downloadLink,downloadSize,latestVersion,true,
                            ChangelogGetter.getChangelog(owner,repo,major,minor,patch,
                                    Locale.getDefault().getLanguage()));
                }
            }catch (Exception e){
                return null;
            }
        });
    }
    /**
     * async download from url
     * @param fileUrl location of the url
     * @param savedPath where file would be downloaded at.
     * @return CompletableFuture
     */
    public static CompletableFuture<Boolean> downloadFile(String fileUrl, String savedPath,long expiredFileSize){
        return CompletableFuture.supplyAsync(() -> {
            try {
                download(fileUrl,savedPath,expiredFileSize);
                return true;
            } catch (IOException e) {
                System.out.println("Error downloading file");
                return false;
            }
        });
    }

}



