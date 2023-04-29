package net.dkcraft.dkcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.dkcraft.dkcore.Main;
import net.dkcraft.dkcore.util.Config;

public class Login implements CommandExecutor {
	
	public Main plugin;
	public Config config;
	
	public Login(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("login")) {
			if (args.length == 0) {
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', config.getHidestatusLogin().replace("%player%", sender.getName())));
			} else {
				sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /login");
			}
		}
		return true;
	}
}
