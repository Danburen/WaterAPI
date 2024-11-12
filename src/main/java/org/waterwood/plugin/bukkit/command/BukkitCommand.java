package org.waterwood.plugin.bukkit.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.waterwood.common.StringProcess;
import org.waterwood.plugin.bukkit.BukkitPlugin;
import org.waterwood.plugin.bukkit.util.ComponentProcesser;

import java.util.List;

public abstract class BukkitCommand extends ComponentProcesser implements CommandHandler {
    private final String commandPath;
    private final String commandLabel;

    protected BukkitCommand(String path) {
        this.commandPath = path;
        int nodInd = path.indexOf('.');
        if (nodInd == -1) {
            commandLabel = path;
        } else {
            commandLabel = path.substring(nodInd + 1);
        }
    }

    public boolean checkPermission(CommandSender sender, String permission) {
        if (sender.hasPermission(permission)) {
            return true;
        }
        sender.sendMessage(log("no-permission"));
        return false;
    }

    public boolean isPlayerExecute(CommandSender sender) {
        if (sender instanceof Player) {
            return true;
        }
        sender.sendMessage(Component.text(log("only-in-game-message"), NamedTextColor.RED));
        return false;
    }

    public boolean isConsoleExecute(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            return true;
        }
        sender.sendMessage(Component.text(log("only-in-console-message"), NamedTextColor.RED));
        return false;
    }

    public String getPath() {
        return commandPath;
    }

    public String getName() {
        return commandLabel;
    }

    /**
     * check args length is available.
     * @param sender the sender
     * @param args args which would be checked
     * @param mixLen  min length of args
     * @param maxLen max length of args
     * @param templateShown template usage shown to sender
     * @return whether pass the check
     */
    public boolean checkArgs(CommandSender sender,String[] args, int mixLen, int maxLen, String templateShown) {
        if (args.length > maxLen || args.length < maxLen) {
            sender.sendMessage(Component.text(log("illegal-arg-raw-message"),NamedTextColor.RED));
            if (templateShown != null) BukkitPlugin.logMsg(templateShown);
            return false;
        }
        return true;
    }
    public boolean checkArgs(CommandSender sender,String[] args, int mixLen, int maxLen){
        return checkArgs(sender,args,mixLen,maxLen,null);
    }
    public boolean ckeckArgNumeric(CommandSender sender, String arg){
        if(StringProcess.isNumeric(arg)){
            return true;
        }else{
            sender.sendMessage(Component.text(log("illegal-arg-message","数字"),NamedTextColor.RED));
            return false;
        }
    }
    public boolean checkArgNumIn(CommandSender sender,int arg,int min,int max){
        if(arg > max || arg < min){
            sender.sendMessage(Component.text(
                   log("illegal-arg-message", String.valueOf(arg),String.format(" 数字 在(%d,%d)范围内任取一个数",min,max)
                    ),NamedTextColor.RED));
            return false;
        }
        return true;
    }
    private String log(String path){
        return BukkitPlugin.getPluginMessage(path);
    }
    private String log(String path,String... args){
        return BukkitPlugin.getPluginMessage(path).formatted((Object) args);
    }
    @Override
    public abstract boolean execute(CommandSender sender, String[] args);
    @Override
    public abstract List<String> tabComplete(CommandSender sender, String[] args);
}
