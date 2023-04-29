package net.dkcraft.dkcore.commands.ban;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.dkcraft.dkcore.Main;
import net.dkcraft.dkcore.commands.ban.methods.BanMethods;
import net.dkcraft.dkcore.commands.ban.methods.BanMethodsMySQL;
import net.dkcraft.dkcore.util.Methods;

public class Ban implements CommandExecutor {

	public Main plugin;
	public Methods methods;
	public BanMethods banmethods;
	public BanMethodsMySQL banmysql;

	long banLength;
	long unbanDateLong;
	boolean permanent;

	public Ban(Main plugin) {
		this.plugin = plugin;
		this.methods = this.plugin.methods;
		this.banmethods = this.plugin.banmethods;
		this.banmysql = this.plugin.banmysql;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ban")) {

			if (args[0].equalsIgnoreCase("view")) {
				if (sender.hasPermission("dkcore.ban.view")) {
					if (args.length == 2) {

						String targetNameArgs = args[1];

						if (targetNameArgs.contains(".")) {
							String ip = targetNameArgs;
							banmysql.fetchIPBans(ip);
							if (!banmethods.hasIPBeenBanned(ip)) {
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c%target% has no bans on record.".replace("%target%", ip)));
								return true;
							}

							int amount = plugin.ipBans.get(ip).size();

							sender.sendMessage(ChatColor.AQUA + ip + ChatColor.GRAY + " has a total of " + ChatColor.AQUA + amount + ChatColor.GRAY + " bans on record.");

							for (int i = 0; i < amount; i++) {

								//int id = plugin.playerBans.get(targetUUID).get(i).getID();
								//String targetUUID = plugin.playerBans.get(targetUUID).get(i).getTargetUUID();
								//String targetName = plugin.playerBans.get(targetUUID).get(i).getTargetName();
								String senderUUID = plugin.ipBans.get(ip).get(i).getSenderUUID();
								String senderName = plugin.ipBans.get(ip).get(i).getSenderName();
								long banDate = plugin.ipBans.get(ip).get(i).getBanDate();
								long banLength = plugin.ipBans.get(ip).get(i).getBanLength();
								long banExpire = banDate + banLength;
								String banReason = plugin.ipBans.get(ip).get(i).getBanReason();
								boolean active = plugin.ipBans.get(ip).get(i).getActive();
								boolean permanent = plugin.ipBans.get(ip).get(i).getPermanent();
								String unbanUUID = plugin.ipBans.get(ip).get(i).getUnbanUUID();
								String unbanName = plugin.ipBans.get(ip).get(i).getUnbanName();
								long unbanDate = plugin.ipBans.get(ip).get(i).getUnbanDate();
								String unbanReason = plugin.ipBans.get(ip).get(i).getUnbanReason();

								banmethods.sendIPBanInfo(sender, null, senderUUID, senderName, banDate, banLength, banExpire, banReason, active, permanent, unbanUUID, unbanName, unbanDate, unbanReason);
							}

						} else {
							String targetUUID = Bukkit.getOfflinePlayer(targetNameArgs).getUniqueId().toString();
							banmysql.fetchPlayerBans(targetUUID);
							if (!banmethods.hasPlayerBeenBanned(targetUUID)) {
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c%target% has no bans on record.".replace("%target%", targetNameArgs)));
								return true;
							}

							int amount = plugin.playerBans.get(targetUUID).size();

							sender.sendMessage(ChatColor.AQUA + targetNameArgs + ChatColor.GRAY + " has a total of " + ChatColor.AQUA + amount + ChatColor.GRAY + " bans on record.");

							for (int i = 0; i < amount; i++) {

								//int id = plugin.playerBans.get(targetUUID).get(i).getID();
								//String targetUUID = plugin.playerBans.get(targetUUID).get(i).getTargetUUID();
								//String targetName = plugin.playerBans.get(targetUUID).get(i).getTargetName();
								String senderUUID = plugin.playerBans.get(targetUUID).get(i).getSenderUUID();
								String senderName = plugin.playerBans.get(targetUUID).get(i).getSenderName();
								long banDate = plugin.playerBans.get(targetUUID).get(i).getBanDate();
								long banLength = plugin.playerBans.get(targetUUID).get(i).getBanLength();
								long banExpire = banDate + banLength;
								String banReason = plugin.playerBans.get(targetUUID).get(i).getBanReason();
								boolean active = plugin.playerBans.get(targetUUID).get(i).getActive();
								boolean permanent = plugin.playerBans.get(targetUUID).get(i).getPermanent();
								String unbanUUID = plugin.playerBans.get(targetUUID).get(i).getUnbanUUID();
								String unbanName = plugin.playerBans.get(targetUUID).get(i).getUnbanName();
								long unbanDate = plugin.playerBans.get(targetUUID).get(i).getUnbanDate();
								String unbanReason = plugin.playerBans.get(targetUUID).get(i).getUnbanReason();

								banmethods.sendPlayerBanInfo(sender, targetUUID, null, senderUUID, senderName, banDate, banLength, banExpire, banReason, active, permanent, unbanUUID, unbanName, unbanDate, unbanReason);
							}
						}
					} else {
						sender.sendMessage(ChatColor.RED + "Incorrect syntax. Usage: /ban view <player>");
					}
				} else {
					sender.sendMessage("You do not have permission to do that.");
				}

			} else if (args[0].equalsIgnoreCase("recent")) {
				if (sender.hasPermission("dkcore.ban.recent")) {
					if (args.length == 2) {

						if (!methods.isInt(args[1])) {
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cIncorrect command usage. Usage: %usage%".replace("%usage%", "/banrecent <amount>")));
							return true;
						}

						int amount = Integer.parseInt(args[1]);

						banmysql.fetchGlobalPlayerBans(amount);

						int amountLimit = plugin.globalPlayerBans.size();

						sender.sendMessage(ChatColor.GRAY + "Showing " + ChatColor.AQUA + amount + ChatColor.GRAY + " recent bans on record.");

						for (int i = 0; i < amount; i++) {
							if (i <= amountLimit - 1) {

								//int id = plugin.globalBans.get(i).getID();
								String targetUUID = plugin.globalPlayerBans.get(i).getTargetUUID();
								String targetName = plugin.globalPlayerBans.get(i).getTargetName();
								String senderUUID = plugin.globalPlayerBans.get(i).getSenderUUID();
								String senderName = plugin.globalPlayerBans.get(i).getSenderName();
								long banDate = plugin.globalPlayerBans.get(i).getBanDate();
								long banLength = plugin.globalPlayerBans.get(i).getBanLength();
								long banExpire = banDate + banLength;
								String banReason = plugin.globalPlayerBans.get(i).getBanReason();
								boolean active = plugin.globalPlayerBans.get(i).getActive();
								boolean permanent = plugin.globalPlayerBans.get(i).getPermanent();
								String unbanUUID = plugin.globalPlayerBans.get(i).getUnbanUUID();
								String unbanName = plugin.globalPlayerBans.get(i).getUnbanName();
								long unbanDate = plugin.globalPlayerBans.get(i).getUnbanDate();
								String unbanReason = plugin.globalPlayerBans.get(i).getUnbanReason();

								banmethods.sendPlayerBanInfo(sender, targetUUID, targetName, senderUUID, senderName, banDate, banLength, banExpire, banReason, active, permanent, unbanUUID, unbanName, unbanDate, unbanReason);
							}
						}
					} else {
						sender.sendMessage(ChatColor.RED + "Incorrect Syntax. Usage: /ban recent <amount>");
					}
				} else {
					sender.sendMessage("You do not have permission to do that.");
				}

			} else if (args[0].equalsIgnoreCase("remove")) {
				if (sender.hasPermission("dkcore.ban.remove")) {
					if (args.length >= 3) {

						String unbanName = sender.getName();
						String unbanUUID = Bukkit.getOfflinePlayer(unbanName).getUniqueId().toString();
						
						String targetName = args[1];

						if (targetName.contains(".")) {
							String targetIP = targetName;
							
							long unbanDate = methods.getCurrentTimestampInSeconds();

							String unbanReason = StringUtils.join(args, ' ', 2, args.length);

							banmysql.fetchIPBans(targetIP);
							if (banmethods.isIPBanned(targetIP)) {
								banmysql.unbanIP(unbanUUID, unbanName, unbanDate, unbanReason, targetIP);
							} else {
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c%target% is not banned.".replace("%target%", targetIP)));
								return true;
							}

							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b%target% &7has been unbanned.".replace("%target%", targetIP)));
							
						} else {
							String targetUUID = Bukkit.getOfflinePlayer(targetName).getUniqueId().toString();
							
							long unbanDate = methods.getCurrentTimestampInSeconds();

							String unbanReason = StringUtils.join(args, ' ', 2, args.length);

							banmysql.fetchPlayerBans(targetUUID);
							if (banmethods.isPlayerBanned(targetUUID)) {
								banmysql.unbanPlayer(unbanUUID, unbanName, unbanDate, unbanReason, targetUUID);
							} else {
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c%target% is not banned.".replace("%target%", targetName)));
								return true;
							}

							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b%target% &7has been unbanned.".replace("%target%", targetName)));
						}
						
					} else {
						sender.sendMessage(ChatColor.RED + "Incorrect Syntax. Usage: /ban remove <player> <reason>");
					}
				} else {
					sender.sendMessage("You do not have permission to do that.");
				}

			} else if (args[0].equalsIgnoreCase("add")) {
				if (sender.hasPermission("dkcore.ban.add")) {
					if (args.length >= 4) {

						String targetName = args[1];
						Player target = Bukkit.getPlayer(targetName);

						String senderName = sender.getName();
						String senderUUID = Bukkit.getOfflinePlayer(senderName).getUniqueId().toString();

						long banDate = methods.getCurrentTimestampInSeconds();

						String banLengthString = args[2];

						String banReason = StringUtils.join(args, ' ', 3, args.length);

						if (methods.isPermanent(banLengthString)) {
							banLength = 0;
							permanent = true;
						} else if (methods.isValidTimeString(banLengthString)) {
							banLength = methods.parse(banLengthString);
							permanent = false;
						}

						if (targetName.contains(".")) {
							String targetIP = targetName;

							banmysql.fetchIPBans(targetIP);
							if (!banmethods.isIPBanned(targetIP)) {
								banmysql.banIP(targetIP, senderUUID, senderName, banDate, banLength, banReason, permanent);
							} else {
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c%target% is already banned.".replace("%target%", targetIP)));
								return true;
							}

							unbanDateLong = banDate + banLength;

							String banTime = methods.getDurationString(banLength);
							String unbanDate = methods.getUnbanDate(unbanDateLong);

							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b%target% &7has been banned.".replace("%target%", targetIP)));

							for (Player online : plugin.getServer().getOnlinePlayers()) {
								if (online.getAddress().toString().replaceAll("/", "").split(":")[0].equals(targetIP)) {
									target.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&cYour IP has been banned from the server by %sender% for %time%. Your ban will expire: %date%. Reason: %message%".replace("%sender%", senderName).replace("%time%", banTime).replace("%date%", unbanDate).replace("%message%", banReason)));
								}
							}

						} else {
							String targetUUID = Bukkit.getOfflinePlayer(targetName).getUniqueId().toString();

							banmysql.fetchPlayerBans(targetUUID);
							if (!banmethods.isPlayerBanned(targetUUID)) {
								banmysql.banPlayer(targetUUID, targetName, senderUUID, senderName, banDate, banLength, banReason, permanent);
							} else {
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c%target% is already banned.".replace("%target%", targetName)));
								return true;
							}

							unbanDateLong = banDate + banLength;

							String banTime = methods.getDurationString(banLength);
							String unbanDate = methods.getUnbanDate(unbanDateLong);

							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b%target% &7has been banned.".replace("%target%", targetUUID)));

							if (methods.isPlayerOnline(target)) {
								target.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&cYou have been banned from the server by %sender% for %time%. Your ban will expire: %date%. Reason: %message%".replace("%sender%", senderName).replace("%time%", banTime).replace("%date%", unbanDate).replace("%message%", banReason)));
							}

						}
					} else {
						sender.sendMessage(ChatColor.RED + "Incorrect Syntax. Usage: /ban add <player> <time> <reason>");
					}
				} else {
					sender.sendMessage("You do not have permission to do that.");
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Incorrect Syntax. Usage: /ban <add,remove,view,recent>");
			}
		}
		return true;
	}
}
