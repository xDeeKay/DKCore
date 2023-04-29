package net.dkcraft.dkcore.commands.ban.methods;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.dkcraft.dkcore.Main;
import net.dkcraft.dkcore.util.Methods;

public class BanMethods {

	public Main plugin;
	public Methods methods;

	public BanMethods(Main plugin) {
		this.plugin = plugin;
		this.methods = this.plugin.methods;
	}

	public boolean hasPlayerBeenBanned(String targetUUID) {
		if (plugin.playerBans.get(targetUUID).size() != 0) {
			return true;
		}
		return false;
	}

	public boolean isPlayerBanned(String targetUUID) {
		if (hasPlayerBeenBanned(targetUUID)) {
			return plugin.playerBans.get(targetUUID).get(0).getActive();
		}
		return false;
	}

	public boolean isPlayerPermanentlyBanned(String targetUUID) {
		if (isPlayerBanned(targetUUID)) {
			return plugin.playerBans.get(targetUUID).get(0).getPermanent();
		}
		return false;
	}

	public boolean hasIPBeenBanned(String ip) {
		if (plugin.ipBans.get(ip).size() != 0) {
			return true;
		}
		return false;
	}

	public boolean isIPBanned(String ip) {
		if (hasIPBeenBanned(ip)) {
			return plugin.ipBans.get(ip).get(0).getActive();
		}
		return false;
	}

	public boolean isIPPermanentlyBanned(String ip) {
		if (isIPBanned(ip)) {
			return plugin.ipBans.get(ip).get(0).getPermanent();
		}
		return false;
	}

	public void sendPlayerBanInfo(CommandSender sender, String targetUUID, String targetName, String senderUUID, String senderName, long banDate, long banLength, long banExpire, String banReason, boolean active, boolean permanent, String unbanUUID, String unbanName, long unbanDate, String unbanReason) {

		ChatColor AQUA = ChatColor.AQUA;
		ChatColor GRAY = ChatColor.GRAY;

		//sender.sendMessage(AQUA + targetName + GRAY + " banned by " + AQUA + senderName + GRAY + " on " + AQUA + methods.getUnbanDate(banDate) + " (UTC)");

		if (targetName != null) {
			sender.sendMessage(AQUA + "Player: " + GRAY + targetName);
		}

		if (active) {
			sender.sendMessage(AQUA + "Active: " + GRAY + "True");
		} else {
			sender.sendMessage(AQUA + "Active: " + GRAY + "False");
		}

		sender.sendMessage(AQUA + "Banned By: " + GRAY + senderName);

		sender.sendMessage(AQUA + "Ban Date: " + GRAY + methods.getUnbanDate(banDate) + " (UTC)");

		if (permanent) {
			sender.sendMessage(AQUA + "Ban Length: " + GRAY + "Permanent");
			sender.sendMessage(AQUA + "Ban Expires: " + GRAY + "Never");
		} else {
			sender.sendMessage(AQUA + "Ban Length: " + GRAY + methods.getDurationString(banLength));
			sender.sendMessage(AQUA + "Ban Expires: " + GRAY + methods.getUnbanDate(banExpire) + " (UTC)");
		}

		sender.sendMessage(AQUA + "Ban Reason: " + GRAY + banReason);

		if (!active && unbanUUID != null) {
			sender.sendMessage(AQUA + "Unbanned By: " + GRAY + unbanName);
			sender.sendMessage(AQUA + "Unban Date: " + GRAY + methods.getUnbanDate(unbanDate) + " (UTC)");
			sender.sendMessage(AQUA + "Unban Reason: " + GRAY + unbanReason);
		}

		sender.sendMessage(AQUA + "----------------------------------------");
	}

	public void sendIPBanInfo(CommandSender sender, String targetIP, String senderUUID, String senderName, long banDate, long banLength, long banExpire, String banReason, boolean active, boolean permanent, String unbanUUID, String unbanName, long unbanDate, String unbanReason) {

		ChatColor AQUA = ChatColor.AQUA;
		ChatColor GRAY = ChatColor.GRAY;

		if (targetIP != null) {
			sender.sendMessage(AQUA + "IP: " + GRAY + targetIP);
		}

		if (active) {
			sender.sendMessage(AQUA + "Active: " + GRAY + "True");
		} else {
			sender.sendMessage(AQUA + "Active: " + GRAY + "False");
		}

		sender.sendMessage(AQUA + "Banned By: " + GRAY + senderName);

		sender.sendMessage(AQUA + "Ban Date: " + GRAY + methods.getUnbanDate(banDate) + " (UTC)");

		if (permanent) {
			sender.sendMessage(AQUA + "Ban Length: " + GRAY + "Permanent");
			sender.sendMessage(AQUA + "Ban Expires: " + GRAY + "Never");
		} else {
			sender.sendMessage(AQUA + "Ban Length: " + GRAY + methods.getDurationString(banLength));
			sender.sendMessage(AQUA + "Ban Expires: " + GRAY + methods.getUnbanDate(banExpire) + " (UTC)");
		}

		sender.sendMessage(AQUA + "Ban Reason: " + GRAY + banReason);

		if (!active && unbanUUID != null) {
			sender.sendMessage(AQUA + "Unbanned By: " + GRAY + unbanName);
			sender.sendMessage(AQUA + "Unban Date: " + GRAY + methods.getUnbanDate(unbanDate) + " (UTC)");
			sender.sendMessage(AQUA + "Unban Reason: " + GRAY + unbanReason);
		}

		sender.sendMessage(AQUA + "----------------------------------------");
	}
}
