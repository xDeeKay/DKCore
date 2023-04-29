package net.dkcraft.dkcore.commands;

import net.dkcraft.dkcore.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Freeze implements CommandExecutor {

	private Main plugin;

	public Freeze(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("freeze")) {
			if (args.length > 0) {
				Player player = Bukkit.getPlayer(args[0]);
				if (player != sender) {
					if (player != null) {
						String playerName = player.getName();
						if (!plugin.freeze.contains(playerName)) {
							String message = "";
							if (args.length > 1) {
								for (int i = 1; i < args.length; ++i) {
									message += args[i] + " ";
								}
							} else {
								plugin.freeze.add(playerName);
								player.sendMessage(ChatColor.RED + "You have been frozen by a staff member.");
								sender.sendMessage(ChatColor.GREEN + playerName + ChatColor.YELLOW + " has been frozen.");
								return true;
							}
							plugin.freeze.add(playerName);
							player.sendMessage(ChatColor.RED + "You have been frozen by a staff member. Reason: " + ChatColor.YELLOW + message);
							sender.sendMessage(ChatColor.GREEN + playerName + ChatColor.YELLOW + " has been frozen.");
						} else {
							sender.sendMessage(ChatColor.RED + "This player is already frozen.");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "You cannot freeze an offline player.");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "You cannot freeze yourself.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /freeze <player> [reason]");
			}
		}
		return true;
	}
}
