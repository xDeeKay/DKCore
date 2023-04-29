package net.dkcraft.dkcore.commands.ban;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import net.dkcraft.dkcore.Main;
import net.dkcraft.dkcore.commands.ban.methods.BanMethods;
import net.dkcraft.dkcore.commands.ban.methods.BanMethodsMySQL;
import net.dkcraft.dkcore.util.Config;
import net.dkcraft.dkcore.util.Methods;

public class BanListener implements Listener {

	public Main plugin;
	public Methods methods;
	public Config config;
	public BanMethods banmethods;
	public BanMethodsMySQL banmysql;

	long banLength;
	long unbanDateLong;

	public BanListener(Main plugin) {
		this.plugin = plugin;
		this.methods = this.plugin.methods;
		this.config = this.plugin.config;
		this.banmethods = this.plugin.banmethods;
		this.banmysql = this.plugin.banmysql;
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {

		Player target = event.getPlayer();
		String targetUUID = target.getUniqueId().toString();
		String targetIP = event.getAddress().toString().replaceAll("/", "").split(":")[0];
		
		System.out.println(targetIP);

		long currentTime = methods.getCurrentTimestampInSeconds();

		banmysql.fetchPlayerBans(targetUUID);
		banmysql.fetchIPBans(targetIP);
		
		if (banmethods.isPlayerBanned(targetUUID)) {
			Long banDate = plugin.playerBans.get(targetUUID).get(0).getBanDate();
			banLength = plugin.playerBans.get(targetUUID).get(0).getBanLength();
			unbanDateLong = banDate + banLength;

			if (!banmethods.isPlayerPermanentlyBanned(targetUUID) && currentTime >= unbanDateLong) {
				banmysql.unbanPlayer(null, null, unbanDateLong, null, targetUUID);

			} else {
				String senderName = plugin.playerBans.get(targetUUID).get(0).getSenderName();
				String banReason = plugin.playerBans.get(targetUUID).get(0).getBanReason();

				String banTime = methods.getDurationString(banLength);
				String unbanDate = methods.getUnbanDate(unbanDateLong);
				
				boolean permanent = plugin.playerBans.get(targetUUID).get(0).getPermanent();
				
				if (permanent) {
					event.setKickMessage(ChatColor.translateAlternateColorCodes('&', "&cYou have been permanently banned by %sender%. Your ban will expire: Never. Reason: %reason%".replace("%sender%", senderName).replace("%reason%", banReason)));
				} else {
					event.setKickMessage(ChatColor.translateAlternateColorCodes('&', "&cYou have been banned by %sender% for %time%. Your ban will expire: %date%. Reason: %reason%".replace("%sender%", senderName).replace("%time%", banTime).replace("%date%", unbanDate).replace("%reason%", banReason)));
				}
				
				event.setResult(Result.KICK_BANNED);
			}
			
		} else if (banmethods.isIPBanned(targetIP)) {
			Long banDate = plugin.ipBans.get(targetIP).get(0).getBanDate();
			banLength = plugin.ipBans.get(targetIP).get(0).getBanLength();
			unbanDateLong = banDate + banLength;

			if (!banmethods.isIPPermanentlyBanned(targetIP) && currentTime >= unbanDateLong) {
				banmysql.unbanIP(null, null, unbanDateLong, null, targetIP);

			} else {
				String senderName = plugin.ipBans.get(targetIP).get(0).getSenderName();
				String banReason = plugin.ipBans.get(targetIP).get(0).getBanReason();

				String banTime = methods.getDurationString(banLength);
				String unbanDate = methods.getUnbanDate(unbanDateLong);
				
				boolean permanent = plugin.ipBans.get(targetIP).get(0).getPermanent();
				
				if (permanent) {
					event.setKickMessage(ChatColor.translateAlternateColorCodes('&', "&cYour IP has been permanently banned by %sender%. Your IP ban will expire: Never. Reason: %reason%".replace("%sender%", senderName).replace("%reason%", banReason)));
				} else {
					event.setKickMessage(ChatColor.translateAlternateColorCodes('&', "&cYour IP has been banned by %sender% for %time%. Your IP ban will expire: %date%. Reason: %reason%".replace("%sender%", senderName).replace("%time%", banTime).replace("%date%", unbanDate).replace("%reason%", banReason)));
				}
				
				event.setResult(Result.KICK_BANNED);
			}
		}
	}
}
