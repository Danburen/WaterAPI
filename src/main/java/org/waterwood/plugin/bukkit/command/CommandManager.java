package org.waterwood.plugin.bukkit.command;

import org.bukkit.command.CommandSender;
import org.waterwood.plugin.bukkit.BukkitPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {
    private final Map<String, CommandHandler> commands = new HashMap<>();
    private final CommandNode root = new CommandNode("root");
    public void registerCommand(String path,CommandHandler handler){
        CommandNode currentNode = root;
        for(String segment : path.split("\\.")){
            if(currentNode.getChilds().containsKey(segment)){
                currentNode = currentNode.getChild(segment);
            }else{
                CommandNode childNode = new CommandNode(segment);
                currentNode.addChild(segment,childNode);
                currentNode = childNode;
            }
        }
        currentNode.setHandler(handler);
    }

    public boolean execute(CommandSender sender,String path,String args[]) {
        CommandNode currentNode = root;
        for(String segment : path.split("\\.")){
            if(!currentNode.getChilds().containsKey(segment)){
                BukkitPlugin.logMsg(BukkitPlugin.getPluginMessage("unknow-command-message"));
                return false;
            }
            currentNode = currentNode.getChild(segment);
        }
        return currentNode.execute(sender, args);
    }

    public List<String> tabComplete(String commandLabel, String[] args){
        CommandHandler handler = commands.get(commandLabel);
        if (handler != null) {
            return handler.tabComplete(args);
        }
        return new ArrayList<>();
    }
}
