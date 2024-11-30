package org.waterwood.plugin.bukkit.command;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface CommandHandler {
    default boolean execute(CommandSender sender, String[] args){
        return true;
    }

    /**
     * Command tab Complete
     * <b>Args include current arg</b>
     * @param sender the sender of command sender
     * @param args args of command
     * @return String list
     */
    default List<String> tabComplete(CommandSender sender,String[] args){
        return List.of();
    }
}
