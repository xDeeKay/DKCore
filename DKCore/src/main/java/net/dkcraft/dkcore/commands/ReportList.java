package net.dkcraft.dkcore.commands;

import java.util.Map.Entry;

import net.dkcraft.dkcore.Main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReportList implements CommandExecutor {
	
	private Main plugin;

	public ReportList(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("reportlist")) {
			if (args.length == 0) {
				if (!plugin.playerReports.isEmpty()) {
					sender.sendMessage(ChatColor.YELLOW + "There are " + ChatColor.GREEN + plugin.playerReports.size() + ChatColor.YELLOW + " active reports.");
					for (Entry<String, String> entry : plugin.playerReports.entrySet()) {
						sender.sendMessage(ChatColor.GREEN + entry.getKey() + ": " + ChatColor.YELLOW + entry.getValue());
					}
				} else {
					sender.sendMessage(ChatColor.YELLOW + "There are " + ChatColor.GREEN + "0" + ChatColor.YELLOW + " active reports.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /reportlist");
			}
		}
		return true;
	}
}
