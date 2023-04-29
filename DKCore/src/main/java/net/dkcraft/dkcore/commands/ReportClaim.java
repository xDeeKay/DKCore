package net.dkcraft.dkcore.commands;

import net.dkcraft.dkcore.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportClaim implements CommandExecutor {
	
	private Main plugin;
	
	public ReportClaim(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("reportclaim")) {
			if (args.length == 1) {
				Player player = Bukkit.getPlayer(args[0]);
				if (player != null) {
					if (plugin.playerReports.containsKey(player.getName())) {
						plugin.playerReports.remove(player.getName());
						((Player) sender).teleport(player.getLocation());
						sender.sendMessage(ChatColor.YELLOW + "Claimed " + ChatColor.GREEN + player.getName() + "'s " + ChatColor.YELLOW + "active report.");
					} else {
						sender.sendMessage(ChatColor.RED + "This player has no active report to claim.");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "This player has no active report to claim.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /reportclaim <player>");
			}
		}
		return true;
	}
}
