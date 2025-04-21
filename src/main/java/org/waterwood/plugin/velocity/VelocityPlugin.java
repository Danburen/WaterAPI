package org.waterwood.plugin.velocity;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import org.waterwood.plugin.WaterPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VelocityPlugin extends WaterPlugin {
    private static ProxyServer server = null;
    protected VelocityPlugin(ProxyServer server) {
        VelocityPlugin.server = server;
        this.initialization();
    }
    public static ProxyServer getProxyServer() {
        return server;
    }

    public static List<String> getAllPlayerName(){
        return  server.getAllPlayers().stream().map(Player::getUsername).toList();
    }
    public static int getPlayerCount(){
        return server.getAllPlayers().size();
    }
    public static String getPlayerName(UUID playerUUID){
        return server.getPlayer(playerUUID).map(Player::getUsername).orElse(null);
    }
}
