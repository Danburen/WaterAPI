package org.waterwood.plugin.bukkit.command;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface CommandHandler {
    boolean execute(CommandSender sender,String args[]);
    List<String> tabComplete(String args[]);
}
