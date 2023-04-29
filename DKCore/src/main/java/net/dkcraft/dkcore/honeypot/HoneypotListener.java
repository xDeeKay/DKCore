package net.dkcraft.dkcore.honeypot;

import net.dkcraft.dkcore.Main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class HoneypotListener implements Listener {
	
	public Main plugin;

	public HoneypotListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		// if block is honeypot
		
		// if player doesn't have permission
		
		// if player isn't in honeypot hash - add them + 1
		
		// if they have 4, run command
		
		// else + 1
		
	}
}
