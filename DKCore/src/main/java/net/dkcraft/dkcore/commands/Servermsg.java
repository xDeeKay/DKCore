package net.dkcraft.dkcore.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Servermsg implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("servermsg")) {
			if (args.length > 0) {
				String message = StringUtils.join(args, ' ', 0, args.length);
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes ('&', message));
			} else {
				sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /servermsg <message>");
			}
		}
		return true;
	}
}
