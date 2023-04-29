package net.dkcraft.dkcore.commands;

import java.util.List;

import net.dkcraft.dkcore.Main;
import net.dkcraft.dkcore.util.Config;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Ranks implements CommandExecutor {

	public Main plugin;
	public Config config;

	public Ranks(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ranks")) {
			if (args.length == 0) {
				List<String> ranks = config.getRanks();
				for (String rank : ranks) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes ('&', rank));
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /ranks");
			}
		}
		return true;
	}
}
