package net.dkcraft.dkcore.commands;

import net.dkcraft.dkcore.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Unfreeze implements CommandExecutor {
	
	private Main plugin;

	public Unfreeze(Main plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("unused")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("unfreeze")) {
			if (args.length == 1) {
				Player player = Bukkit.getPlayer(args[0]);
				String playerName = player.getName();
				if (player != null) {
					if (plugin.freeze.contains(playerName)) {
						plugin.freeze.remove(playerName);
						player.sendMessage(ChatColor.RED + "You have been unfrozen by a staff member.");
						sender.sendMessage(ChatColor.GREEN + playerName + ChatColor.YELLOW + " has been unfrozen.");
					} else {
						sender.sendMessage(ChatColor.RED + "That player is not frozen.");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "That player is not frozen.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /unfreeze <player>");
			}
		}
		return true;
	}
}
