package org.waterwood.plugin.bukkit.command;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return false;
    }
    public List<String> tabComplete(String[] args){
        List<String> suggestions = new ArrayList<>();
        if(args.length > 0 ){
            String lastArg = args[args.length - 1];
            for(String child : children.keySet()){
                if(child.startsWith(lastArg)){
                    suggestions.add(child);
                }
            }
        }else{
            suggestions.addAll(children.keySet());
        }
        return suggestions;
    }

    public CommandNode getChild(String name){
        return children.get(name);
    }

    public Map<String,CommandNode> getChilds(){
        return children;
    }

    public String getName(){
        return name;
    }
}
