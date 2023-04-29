package net.dkcraft.dkcore.commands;

import net.dkcraft.dkcore.Main;
import net.dkcraft.dkcore.util.ProtectionStones;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class GiveCoalProtection implements CommandExecutor {
	
	private Main plugin;
	
	private ProtectionStones ps;

	public GiveCoalProtection(Main plugin) {
		this.plugin = plugin;
		this.ps = this.plugin.ps;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("givecoalprotection")) {
			if (args.length == 1) {

				Player target = Bukkit.getPlayer(args[0]);
				
				if (target != null) {
					PlayerInventory playerinventory = target.getInventory();
					if (target.getInventory().firstEmpty() != -1) {
						playerinventory.addItem(ps.block1(1));
						target.sendMessage(ChatColor.GREEN + "You were given 1 Coal Protection. Use /ps for more info.");
						sender.sendMessage(ChatColor.GREEN + "You gave " + target.getName()  + " 1 Coal Protection.");
					} else {
						sender.sendMessage(ChatColor.RED + "This players inventory is full. Ask them to clear it and try again.");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "This player is offline.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /givecoalprotection <player>");
			}
		}
		return true;
	}
}
