package net.dkcraft.dkcore.listeners;

import net.dkcraft.dkcore.Main;
import net.dkcraft.dkcore.util.Methods;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class FreezeListener implements Listener {

	private Main plugin;
	
	private Methods methods;

	public FreezeListener(Main plugin) {
		this.plugin = plugin;
		this.methods = this.plugin.methods;
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		if (plugin.freeze.contains(playerName)) {
			player.teleport(player);
		}

		if (plugin.freeze.contains(playerName)) {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				if (p.isInsideVehicle()) {
					p.getVehicle().eject();
				}
			}
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		if (plugin.freeze.contains(playerName)) {
			player.sendMessage(ChatColor.RED + "You are frozen and cannot place blocks.");
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		if (plugin.freeze.contains(playerName)) {
			player.sendMessage(ChatColor.RED + "You are frozen and cannot destroy blocks.");
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		Action action = event.getAction();
		
		if (plugin.freeze.contains(playerName)) {
			if (methods.hands(player, Material.ENDER_PEARL)) {
				if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
					player.sendMessage(ChatColor.RED + "You are frozen and cannot use enderpearls.");
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		if (plugin.freeze.contains(playerName)) {
			player.sendMessage(ChatColor.RED + "You are frozen and cannot run commands.");
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		String playerName = event.getPlayer().getName();
		if (plugin.freeze.contains(playerName)) {
			event.getPlayer().sendMessage(ChatColor.RED + "You were frozen before disconnecting. Resuming freeze.");
		}
	}
}
