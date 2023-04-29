package net.dkcraft.dkcore.honeypot;

import net.dkcraft.dkcore.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Honeypot implements CommandExecutor {
	
	public Main plugin;

	public Honeypot(Main plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("honeypot")) {
			if (args.length == 0) {
				
				

			} else {
				cs.sendMessage("§cIncorrect syntax. Usage: /honeypot");
			}
		}
		return true;
	}
}
