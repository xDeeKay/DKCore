package net.dkcraft.dkcore.vote;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.dkcraft.dkcore.Main;

public class VoteMethods {

	public Main plugin;

	private int points;
	private List<String> list = new ArrayList<String>();
	private List<String> store = new ArrayList<String>();

	private File file;
	private FileConfiguration configuration;

	public VoteMethods(Main plugin) {
		this.plugin = plugin;
	}

	public ItemStack item(Material material, String name, List<String> lore, int amount) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		List<String> loreList = new ArrayList<String>();
		loreList.addAll(lore);
		meta.setLore(loreList);
		item.setItemMeta(meta);
		item.setAmount(amount);
		return item;
	}

	public void openVoteGUI(Player player) {

		Inventory inventory = plugin.getServer().createInventory(null, 9, "Vote");

		Material paper = Material.PAPER;
		String voteList = ChatColor.GRAY + "List";
		List<String> voteListLore = new ArrayList<String>();
		voteListLore.add(ChatColor.WHITE + "Click to view the vote list");

		Material gold_ingot = Material.GOLD_INGOT;
		String voteStore = ChatColor.GRAY + "Store";
		List<String> voteStoreLore = new ArrayList<String>();
		voteStoreLore.add(ChatColor.WHITE + "Click to view the vote store");

		Material exp_bottle = Material.EXPERIENCE_BOTTLE;
		String votePoints = ChatColor.GRAY + "Points";
		List<String> votePointsLore = new ArrayList<String>();
		votePointsLore.add(ChatColor.WHITE + "Click to view your vote points");

		inventory.setItem(2, item(paper, voteList, voteListLore, 1));
		inventory.setItem(4, item(gold_ingot, voteStore, voteStoreLore, 1));
		inventory.setItem(6, item(exp_bottle, votePoints, votePointsLore, 1));

		player.openInventory(inventory);
	}

	public void openVoteList(Player player) {
		for (String voteList : getList()) {
			player.sendMessage(ChatColor.translateAlternateColorCodes ('&', voteList));
		}
	}

	public void openVoteStoreGUI(Player player, String uuid) {

		int storeSize = store.size();
		int inventorySize = 9*(int)Math.ceil(storeSize/9.0);

		Inventory inventory = plugin.getServer().createInventory(null, inventorySize, "Vote Store");

		int count = 0;
		for (String reward : store) {

			String[] rewardParts = reward.split(":");

			int rewardAmount = Integer.valueOf(rewardParts[0]);
			String rewardMaterialString = rewardParts[1].toUpperCase();
			Material rewardMaterial = Material.getMaterial(rewardMaterialString);
			int rewardPrice = Integer.valueOf(rewardParts[2]);

			String rewardName = rewardParts[1].substring(0, 1).toUpperCase() + rewardParts[1].substring(1);
			List<String> rewardLore = new ArrayList<String>();
			rewardLore.add(ChatColor.WHITE + "Price: " + rewardPrice + " vote points");

			inventory.setItem(count, item(rewardMaterial, ChatColor.WHITE + rewardName, rewardLore, rewardAmount));

			count++;
		}

		player.openInventory(inventory);
	}

	public void openVotePoint(Player player, String uuid) {
		if (getVoteConfig().contains(uuid)) {
			int points = getVoteConfig().getInt(uuid);
			player.sendMessage(ChatColor.YELLOW + "You have " + ChatColor.GREEN + points + ChatColor.YELLOW + " vote points.");
		}
	}

	public void loadConfig() {
		file = new File(plugin.getDataFolder(), "votepoints.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		configuration = YamlConfiguration.loadConfiguration(file);
		loadValues();
	}

	public File getVoteFile() {
		return file;
	}

	public FileConfiguration getVoteConfig() {
		return configuration;
	}

	public void saveVoteConfig() {
		if (configuration == null || file == null) return;
		try {
			configuration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addVotePoint(String uuid, int amount) {
		if (getVoteConfig().contains(uuid)) {
			getVoteConfig().set(uuid, getVoteConfig().getInt(uuid) + amount);
		} else {
			getVoteConfig().set(uuid, amount);
		}
		saveVoteConfig();
	}

	public void removeVotePoint(String uuid, int amount) {
		if (getVoteConfig().contains(uuid)) {
			if (getVoteConfig().getInt(uuid) != 0) {
				getVoteConfig().set(uuid, getVoteConfig().getInt(uuid) - amount);
				saveVoteConfig();
			}
		}
	}

	public int getVotePoints(String uuid) {
		return getVoteConfig().getInt(uuid);
	}

	public void loadValues() {
		this.setPoints(plugin.getConfig().getInt("vote.points"));
		this.setList(plugin.getConfig().getStringList("vote.list"));
		this.setStore(plugin.getConfig().getStringList("vote.store"));
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getPoints() {
		return this.points;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public List<String> getList() {
		return this.list;
	}

	public void setStore(List<String> store) {
		this.store = store;
	}

	public List<String> getStore() {
		return this.store;
	}

	public boolean inventoryFree(Player player) {

		int freeSlotsBefore = 0;

		for (ItemStack item : player.getInventory().getContents()) {
			if (item == null) {
				freeSlotsBefore++;
			}
		}
		int freeSpotsAfter = freeSlotsBefore - 5;

		if (freeSpotsAfter != 0) {
			return true;
		} else {
			return false;
		}
	}

	public void logVote(final String uuid, final String username, final String ip, final Timestamp timestamp, final String service) {

		Connection connection = null;
		PreparedStatement statement = null;

		String query = "INSERT INTO dkc_votes(uuid, username, ip, timestamp, service) " + "VALUES(?,?,?,?,?)";

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setString(1, uuid);
			statement.setString(2, username);
			statement.setString(3, ip);
			statement.setTimestamp(4, timestamp);
			statement.setString(5, service);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void sendPlayerVoteMessage(String playerName) {
		if (plugin.getServer().getPlayer(playerName) != null) {
			Player player = plugin.getServer().getPlayer(playerName);
			player.sendMessage(ChatColor.YELLOW + "Thank you for voting! Please vote daily to support the server.");
			player.sendMessage(ChatColor.GREEN + "+ 1 vote point");
			player.sendMessage(ChatColor.GREEN + "+ $500");
		}
	}

	public void sendServerVoteMessage(String playerName, String service) {
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (!player.getName().equals(playerName)) {
				player.sendMessage(ChatColor.GREEN + playerName + ChatColor.YELLOW + " has voted for the server @ " + service);
			}
		}
	}

	public void sendRewardCommands(String playerName) {
		plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "eco give " + playerName + " 500");
	}

	/*
	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}

	public String getAnnouncement() {
		return this.announcement;
	}

	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	public String getReminder() {
		return this.reminder;
	}
	 */
}
