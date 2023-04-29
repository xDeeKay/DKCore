package net.dkcraft.dkcore.commands;

import net.dkcraft.dkcore.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Warn implements CommandExecutor {
	
	public Warn(Main plugin) {
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("warn")) {
			if (args.length > 0) {
				Player player = Bukkit.getPlayer(args[0]);
				if (player != sender) {
					if (player != null) {
						String message = "";
						if (args.length > 1) {
							for (int i = 1; i < args.length; ++i) {
								message += args[i] + " ";
							}
						} else {
							player.sendMessage(ChatColor.RED + "You have been warned by a staff member.");
							sender.sendMessage(ChatColor.GREEN + player.getName() + ChatColor.YELLOW + " has been warned.");
							return true;
						}
						player.sendMessage(ChatColor.RED + "You have been warned by a staff member. Reason: " + ChatColor.YELLOW + message);
						sender.sendMessage(ChatColor.GREEN + player.getName() + ChatColor.YELLOW + " has been warned.");
					} else {
						sender.sendMessage(ChatColor.RED + "You cannot warn an offline player.");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "You cannot warn yourself.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Incorrect syntax. Use: /warn <player> [reason]");
			}
		}
		return true;
	}
}
