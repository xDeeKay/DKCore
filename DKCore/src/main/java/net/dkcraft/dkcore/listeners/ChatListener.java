package net.dkcraft.dkcore.listeners;

import net.dkcraft.dkcore.Main;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatListener implements Listener {

	private Main plugin;

	public ChatListener(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		if (player.hasPermission("dkcore.hidestatus")) {
			event.setJoinMessage(null);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		
		Player player = event.getPlayer();
		String playerName = player.getName();
		
		if (plugin.playerReports.containsKey(playerName)) {
			plugin.playerReports.remove(playerName);
			
			for (Player online : plugin.getServer().getOnlinePlayers()) {
				if (online.hasPermission("dkcore.report.list")) {
					online.sendMessage(ChatColor.GREEN + playerName + ChatColor.YELLOW + " has left the game. Auto deleting their active report.");
				}
			}
		}
		
		if (player.hasPermission("dkcore.hidestatus")) {
			event.setQuitMessage(null);
		}
	}

	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		
		Player player = event.getPlayer();
		String playerName = player.getName();
		String message = event.getMessage();
		
		if (plugin.staffChat.contains(playerName)) {
			for (Player online : plugin.getServer().getOnlinePlayers()) {
				if (online.hasPermission("dkcore.staffchat")) {
					online.sendMessage(ChatColor.BLACK + "[S] " + setGroupFormat(player) + playerName + ChatColor.WHITE + ": " + message);
					event.setCancelled(true);
				}
			}
			System.out.println("[S] " + playerName + ": " + message);
		}
	}
	
	public String groupFormat(ChatColor color, String group) {
		return ChatColor.WHITE + "[" + color + group + ChatColor.WHITE + "] " + color;
	}
	
	public String setGroupFormat(Player player) {
		
		PermissionUser user = PermissionsEx.getUser(player);
		List<String> groups = user.getParentIdentifiers();
		String group = groups.get(0);
		
		String groupFormat = null;
		
		if (group.equalsIgnoreCase("owner")) {
			groupFormat = groupFormat(ChatColor.RED, "Owner");
		} else if (group.equalsIgnoreCase("admin")) {
			groupFormat = groupFormat(ChatColor.BLUE, "Admin");
		} else if (group.equalsIgnoreCase("operator")) {
			groupFormat = groupFormat(ChatColor.AQUA, "Operator");
		} else if (group.equalsIgnoreCase("developer")) {
			groupFormat = groupFormat(ChatColor.DARK_AQUA, "Developer");
		} else if (group.equalsIgnoreCase("moderator")) {
			groupFormat = groupFormat(ChatColor.GREEN, "Moderator");
		} else if (group.equalsIgnoreCase("trusted")) {
			groupFormat = groupFormat(ChatColor.GOLD, "Trusted");
		} else if (group.equalsIgnoreCase("member")) {
			groupFormat = groupFormat(ChatColor.GRAY, "Member");
		} else if (group.equalsIgnoreCase("guest")) {
			groupFormat = groupFormat(ChatColor.WHITE, "Guest");
		} else {
			groupFormat = groupFormat(ChatColor.WHITE, "Unknown");
		}
		return groupFormat;
	}
}
