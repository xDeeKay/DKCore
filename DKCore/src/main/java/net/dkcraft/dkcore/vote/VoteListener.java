package net.dkcraft.dkcore.vote;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import net.dkcraft.dkcore.Main;
import net.dkcraft.dkcore.util.Methods;

public class VoteListener implements Listener {

	public Main plugin;

	private Methods methods;
	private VoteMethods vote;

	public VoteListener(Main plugin) {
		this.plugin = plugin;
		this.methods = this.plugin.methods;
		this.vote = this.plugin.vote;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onVotifierEvent(VotifierEvent event) {

		Vote votifier = event.getVote();
		String voteUsername = votifier.getUsername();
		String voteUUID = plugin.getServer().getOfflinePlayer(voteUsername).getUniqueId().toString();
		String voteIP = votifier.getAddress();
		final String service = votifier.getServiceName();

		String voteTimestamp = votifier.getTimeStamp();
		//System.out.println("voteTimestamp:" + voteTimestamp);

		System.out.println("username:" + voteUsername + " address:" + voteIP + " timestamp:" + voteTimestamp + " service:" + service);

		if (!methods.isInt(voteTimestamp)) {

			String voteTimestampString = voteTimestamp.replace(" +0000", "");
			//System.out.println("voteTimestampString:" + voteTimestampString);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

			Date date = null;
			try {
				date = dateFormat.parse(voteTimestampString);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			long voteTimestampLong = (long)date.getTime() / 1000;
			//System.out.println("voteTimestampLong:" + voteTimestampLong);

			voteTimestamp = Long.toString(voteTimestampLong);
			//System.out.println("voteTimestamp:" + voteTimestamp);
		}

		final long timestampLong = Long.parseLong(voteTimestamp);
		Date date = new Date(timestampLong * 1000);
		final Timestamp timestamp = new Timestamp(date.getTime());

		vote.logVote(voteUUID, voteUsername, voteIP, timestamp, service);
		vote.addVotePoint(voteUUID, vote.getPoints());
		vote.sendRewardCommands(voteUsername);

		vote.sendPlayerVoteMessage(voteUsername);
		vote.sendServerVoteMessage(voteUsername, service);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {

		final Player player = (Player) event.getWhoClicked();
		final String uuid = player.getUniqueId().toString();

		Inventory inventory = event.getInventory();
		InventoryView view = event.getView();
		String inventoryName = ChatColor.translateAlternateColorCodes('&', view.getTitle());

		ItemStack item = event.getCurrentItem();

		if (event.getRawSlot() < inventory.getSize()) {
			if (item != null && (!item.getType().equals(Material.AIR))) {

				String itemName = item.getItemMeta().getDisplayName();

				if (inventoryName.equals("Vote")) {
					event.setCancelled(true);

					if (itemName.equals(ChatColor.GRAY + "List")) {
						vote.openVoteList(player);
					}

					if (itemName.equals(ChatColor.GRAY + "Store")) {
						vote.openVoteStoreGUI(player, uuid);
					}

					if (itemName.equals(ChatColor.GRAY + "Points")) {
						vote.openVotePoint(player, uuid);
					}
				}

				if (inventoryName.equals("Vote Store")) {
					event.setCancelled(true);

					Material material = item.getType();
					int amount = item.getAmount();
					ItemMeta meta = item.getItemMeta();
					String name = item.getType().toString().toLowerCase();
					List<String> loreList = meta.getLore();
					String lore = loreList.get(0);
					int price = Integer.parseInt(lore.replaceAll("[\\D]", ""));

					if (vote.getVotePoints(uuid) >= price) {
						if (vote.inventoryFree(player)) {

							ItemStack reward = new ItemStack(material);
							reward.setAmount(amount);

							player.getInventory().addItem(reward);
							player.sendMessage(ChatColor.YELLOW + "You received " + ChatColor.GREEN + amount + " " + name + "(s)" + ChatColor.YELLOW + ".");

							vote.removeVotePoint(uuid, price);

						} else {
							player.sendMessage(ChatColor.RED + "You need at least 1 free inventory slot to purchase this reward.");
						}
					} else {
						int difference = price - vote.getVotePoints(uuid);
						player.sendMessage(ChatColor.RED + "You need " + difference + " vote points to purchase this reward.");
					}
				}
			}
		} 
	}
}
