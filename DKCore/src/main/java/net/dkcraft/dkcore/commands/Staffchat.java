package net.dkcraft.dkcore.commands;

import net.dkcraft.dkcore.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Staffchat implements CommandExecutor {
	
	private Main plugin;

	public Staffchat(Main plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("staffchat") || (cmd.getName().equalsIgnoreCase("sc") || (cmd.getName().equalsIgnoreCase("staff")))) {
			if (args.length == 0) {
				Player player = (Player) sender;
				String playerName = player.getName();
				if (!plugin.staffChat.contains(playerName)) {
					plugin.staffChat.add(playerName);
					player.sendMessage(ChatColor.YELLOW + "Now talking in staff chat.");
				} else {
					plugin.staffChat.remove(playerName);
					player.sendMessage(ChatColor.YELLOW + "No longer talking in staff chat.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /staffchat");
			}
		}
		return true;
	}
}
