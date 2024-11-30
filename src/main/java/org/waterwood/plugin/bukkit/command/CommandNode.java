package org.waterwood.plugin.bukkit.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.waterwood.common.Colors;
import org.waterwood.consts.COLOR;
import org.waterwood.plugin.bukkit.BukkitPlugin;

import java.util.*;

public class CommandNode {
    private final String name;
    private final Map<String,CommandNode> children = new HashMap<>();
    private CommandHandler handler;
    public CommandNode(String name) {
        this.name = name;
    }

    public void setHandler(CommandHandler handler){
        this.handler = handler;
    }
    public void addChild(String name,CommandNode child){
        children.put(name,child);
    }
    public void addChild(CommandNode child){
        children.put(child.getName(),child);
    }
    public boolean execute(CommandSender sender, String[] args){
        if(handler != null){
            return handler.execute(sender,args);
        }
        sender.sendMessage(Colors.coloredText(BukkitPlugin.getPluginMessage("command-not-found-message"), COLOR.RED));
        return false;
    }

    /**
     * default tab Complete usually command didn't set handler.
     * @param sender CommandSender
     * @param args args of command
     * @return List of Completes.
     */
    public List<String> tabComplete(CommandSender sender,String[] args){;
        List<String> suggestions = new ArrayList<>();
        if(handler == null) {
            if (args.length > 0) {
                String lastArg = args[args.length - 1];
                for (String child : children.keySet()) {
                    if (child.startsWith(lastArg)) {
                        suggestions.add(child);
                    }
                }
            } else {
                suggestions.addAll(children.keySet());
            }
        }else{
            return handler.tabComplete(sender,args);
        }
        return suggestions;
    }

    public CommandNode getChild(String name){
        return children.get(name);
    }

    public Map<String,CommandNode> getChilds(){
        return children;
    }
    public CommandHandler getHandler(){
        return handler;
    }

    public String getName(){
        return name;
    }
}
