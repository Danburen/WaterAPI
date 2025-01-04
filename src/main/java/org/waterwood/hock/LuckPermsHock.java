package org.waterwood.hock;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;

/**
 * A class for fetching hock for <a href="https://luckperms.net/">LuckPerms</a>
 * <p></p>
 * If server don't have LuckPerm plugin than this will not be enabled all methods return negative.
 * @since 1.0.0
 * @author Danburen
 * @version 1.1.0
 */
public abstract class LuckPermsHock implements HockBase {
    private static boolean hasLuckPerms = false;
    private static LuckPerms api = null;
    public static void checkApi(){
        hasLuckPerms = hasLuckPerm();
    }
    @Override
    public Object getAPI(){
        return api;
    }

    public static boolean hasLuckPerm(){
        try {
            api = LuckPermsProvider.get();
        }catch(NoClassDefFoundError e){
            return false;
        }
        return true;
    }

    private static String getPlayerMetaData(String playerName, UserMetaDataFetcher fetcher){
        if(! hasLuckPerms){
            return "";
        }
        User user = api.getUserManager().getUser(playerName);
        if (user == null){ return ""; }
        return fetcher.getMetaData(user);
    }

    /**
     * Return player's prefix,if not exist return empty string instead.
     * @param playerName player's name
     * @return prefix
     */
    public static String getPlayerPrefix(String playerName){
        return getPlayerMetaData(playerName,user -> user.getCachedData().getMetaData().getPrefix());
    }

    /**
     * Return player's suffix , if not exist return empty string instead.
     * @param playerName player's name
     * @return suffix
     */
    public static String getPlayerSuffix(String playerName){
        return getPlayerMetaData(playerName,user -> user.getCachedData().getMetaData().getSuffix());
    }

    /**
     * Return player's group display text , if not exist return empty string instead.
     * @param playerName player's name
     * @return player group's display string
     */
    public static String getPlayerGroupDisplay(String playerName){
        if(hasLuckPerms) {
            User user = api.getUserManager().getUser(playerName);
            Group group = api.getGroupManager().getGroup(user.getPrimaryGroup());
            if (group == null) { return ""; }
            return group.getDisplayName();
        }
        return "";
    }

    private interface UserMetaDataFetcher {
        String getMetaData(User user);
    }
}
