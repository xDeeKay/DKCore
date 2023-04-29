package net.dkcraft.dkcore.listeners;

import net.dkcraft.dkcore.Main;
import net.dkcraft.dkcore.util.Methods;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemUseListener implements Listener {

	public Main plugin;

	public Methods methods;

	public ItemUseListener(Main plugin) {
		this.plugin = plugin;
		this.methods = this.plugin.methods;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		Action action = event.getAction();
		Block block = event.getClickedBlock();

		if (methods.hands(player, Material.FLINT_AND_STEEL) || (methods.hands(player, Material.FIRE_CHARGE))) {
			if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
				if (!player.hasPermission("dkcore.use.fire")) {
					if (!block.getType().equals(Material.OBSIDIAN) || (!block.getType().equals(Material.NETHERRACK))) {
						player.sendMessage(ChatColor.RED + "You don't have permission to use fire here.");
						player.sendMessage(ChatColor.RED + "You are only permitted to use fire on obsidian or netherrack.");
						event.setCancelled(true);
					}
				}
			}
		}

		if (methods.hands(player, Material.WATER) || (methods.hands(player, Material.WATER_BUCKET))) {
			if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
				if (!player.hasPermission("dkcore.use.water")) {
					player.sendMessage(ChatColor.RED + "You don't have permission to use water.");
					player.sendMessage(ChatColor.RED + "Use /helpop to request water placement by a staff member.");
					event.setCancelled(true);
				}
			}
		}

		if (methods.hands(player, Material.LAVA) || (methods.hands(player, Material.LAVA_BUCKET))) {
			if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
				if (!player.hasPermission("dkcore.use.lava")) {
					player.sendMessage(ChatColor.RED + "You don't have permission to use lava.");
					player.sendMessage(ChatColor.RED + "Use /helpop to request lava placement by a staff member.");
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		if (event.getEntity().getType().equals(EntityType.ENDER_CRYSTAL)) {
			event.blockList().clear();
		}
	}
}
