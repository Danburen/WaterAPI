package org.waterwood.plugin.bukkit.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.waterwood.plugin.bukkit.BukkitPlugin;

import java.util.*;

public class CommandManager implements CommandExecutor, TabCompleter {
    private final BukkitPlugin plugin;
    private final Map<String, CommandHandler> commands = new HashMap<>();
    private final CommandNode root = new CommandNode("root");
    public CommandManager(BukkitPlugin plugin,String rootName){
        this.plugin = plugin;
    }

    public CommandManager(BukkitPlugin plugin){
        this(plugin, plugin.getName());
    }

    public void registerCommands(BukkitCommand... commands){
        for(BukkitCommand command : commands){
            registerCommand(command.getPath(),command);
        }
    }
    public void registerCommand(String path,CommandHandler handler){
        if (path.equals("root")) {
            root.setHandler(handler);
            return;
        }
        CommandNode currentNode = root;
        for(String segment : path.split("\\.")){
            if(currentNode.getChilds().containsKey(segment) && ! segment.equals("root")){
                currentNode = currentNode.getChild(segment);
            }else{
                CommandNode childNode = new CommandNode(segment);
                currentNode.addChild(segment,childNode);
                currentNode = childNode;
            }
        }
        currentNode.setHandler(handler);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        CommandNode currentNode = root;
        for(String arg: args){
            if(currentNode.getChilds().containsKey(arg)){
                currentNode = currentNode.getChild(arg);
            }
        }
        return currentNode.execute(sender, args);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        CommandNode currentNode= root;
        for(String arg : args){
            if(! currentNode.getChilds().containsKey(arg)){
                break;
            }
            currentNode = currentNode.getChild(arg);
        }
        return currentNode.tabComplete(sender,args);
    }
}
