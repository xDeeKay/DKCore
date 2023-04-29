package net.dkcraft.dkcore.commands;

import net.dkcraft.dkcore.Main;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Report implements CommandExecutor {
	
	private Main plugin;

	public Report(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("report")) {
			if (args.length > 0) {
				Player player = (Player) sender;
				String reportSender = player.getName();
				String message = StringUtils.join(args, ' ', 0, args.length);
				if (!plugin.playerReports.containsKey(reportSender)) {
					plugin.playerReports.put(reportSender, message);
					player.sendMessage(ChatColor.YELLOW + "Report submitted. Please wait for a staff members response.");
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.hasPermission("dkcore.report.list")) {
							p.sendMessage(ChatColor.RED + reportSender + ChatColor.DARK_RED + " has submitted a report: " + ChatColor.RED + message);
						}
					}
				} else {
					player.sendMessage(ChatColor.RED + "You have already submitted a report. Please wait.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /report [message]");
			}
		}
		return true;
	}
}
