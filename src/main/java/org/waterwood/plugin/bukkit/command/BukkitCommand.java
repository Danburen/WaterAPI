package org.waterwood.plugin.bukkit.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.waterwood.utils.Colors;
import org.waterwood.processor.StringProcess;
import org.waterwood.enums.COLOR;
import org.waterwood.plugin.bukkit.BukkitPlugin;
import org.waterwood.plugin.bukkit.util.ComponentProcessor;

import java.util.List;

public abstract class BukkitCommand extends ComponentProcessor implements CommandHandler {
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
        sender.sendMessage(getFromMessage("no-permission"));
        return false;
    }

    public boolean isPlayerExecute(CommandSender sender) {
        if (sender instanceof Player) {
            return true;
        }
        sender.sendMessage(Colors.coloredText(getFromMessage("only-in-game-message"), COLOR.RED));
        return false;
    }

    public boolean isConsoleExecute(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            return true;
        }
        sender.sendMessage(Colors.coloredText(getFromMessage("only-in-console-message"), COLOR.RED));
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
     * @return whether pass the check
     */
    public boolean checkArgs(CommandSender sender,String[] args, int mixLen, int maxLen) {
        if (args.length > maxLen || args.length < mixLen) {
            sender.sendMessage(Colors.coloredText(getFromMessage("illegal-arg-raw-message"),COLOR.RED));
            return false;
        }
        return true;
    }
    public boolean checkArgs(CommandSender sender, String[] args, int argLength){
        if (args.length!=argLength) {
            sender.sendMessage(Colors.coloredText(getFromMessage("illegal-arg-raw-message"),COLOR.RED));
            return false;
        }
        return true;
    }
    public boolean checkArgNumeric(CommandSender sender, String arg){
        if(StringProcess.isNumeric(arg)){
            return true;
        }else{
            sender.sendMessage(Colors.coloredText(getFromMessage("illegal-arg-message").formatted("数字"),COLOR.RED));
            return false;
        }
    }
    public boolean checkArgNumIn(CommandSender sender,int arg,int min,int max){
        if(arg > max || arg < min){
            sender.sendMessage(Colors.coloredText(
                   getFromMessage("illegal-arg-message").formatted(String.valueOf(arg),String.format(" 数字 在(%d,%d)范围内任取一个数",min,max)
                    ),COLOR.RED));
            return false;
        }
        return true;
    }
    private String getFromMessage(String path){
        return BukkitPlugin.getPluginMessage(path);
    }
    @Override
    public abstract boolean execute(CommandSender sender, String[] args);
    @Override
    public abstract List<String> tabComplete(CommandSender sender, String[] args);
}
