package net.dkcraft.dkcore.listeners;

import net.dkcraft.dkcore.Main;
import net.dkcraft.dkcore.util.Methods;
import net.dkcraft.dkcore.util.ProtectionStones;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class BuySign implements Listener {

	public Main plugin;
	private Methods methods;
	private ProtectionStones ps;

	Essentials essentials = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");

	public BuySign(Main plugin) {
		this.plugin = plugin;
		this.methods = this.plugin.methods;
		this.ps = this.plugin.ps;
	}

	@EventHandler
	public void onSignChange(SignChangeEvent event) {

		Player player = event.getPlayer();

		String line1 = event.getLine(0);
		String line2 = event.getLine(1);
		String line3 = event.getLine(2);
		String line4 = event.getLine(3);

		String block1SignName = plugin.getConfig().getString("protection_blocks.block1.sign_name");
		String block2SignName = plugin.getConfig().getString("protection_blocks.block2.sign_name");
		String block3SignName = plugin.getConfig().getString("protection_blocks.block3.sign_name");

		if (line1.equals("[Buy PS]") || line1.contains("[Buy PS]")) {

			if (player.hasPermission("dkcore.create.buysign")) {

				if (line2.equals(block1SignName) || line2.equals(block2SignName) || line2.equals(block3SignName)) {

					if (methods.isInt(line3)) {

						if (line4.contains("$")) {

							event.setLine(0, ChatColor.DARK_BLUE + "[Buy PS]");

						} else {
							player.sendMessage(ChatColor.RED + "Line 4 must contain a price as an integer.");
							event.setLine(0, ChatColor.DARK_RED + "[Buy PS]");
						}
					} else {
						player.sendMessage(ChatColor.RED + "Line 3 must contain an amount as an integer.");
						event.setLine(0, ChatColor.DARK_RED + "[Buy PS]");
					}
				} else {
					player.sendMessage(ChatColor.RED + "Line 2 must be '" + block1SignName + "', '" + block2SignName + "', or '" + block3SignName + "'.");
					event.setLine(0, ChatColor.DARK_RED + "[Buy PS]");
				}

			} else {
				event.setLine(0, ChatColor.BLACK + "[Buy PS]");
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		Action action = event.getAction();
		Block block = event.getClickedBlock();

		if (action == Action.RIGHT_CLICK_BLOCK) {
			if (block.getType() == Material.OAK_SIGN || block.getType() == Material.OAK_WALL_SIGN) {

				Sign sign = (Sign) block.getState();

				String line1 = sign.getLine(0);
				String line2 = sign.getLine(1);
				String line3 = sign.getLine(2);
				String line4 = sign.getLine(3);

				String block1SignName = plugin.getConfig().getString("protection_blocks.block1.sign_name");
				String block2SignName = plugin.getConfig().getString("protection_blocks.block2.sign_name");
				String block3SignName = plugin.getConfig().getString("protection_blocks.block3.sign_name");

				if (!line1.isEmpty() && !line2.isEmpty() && !line3.isEmpty() && !line4.isEmpty()) {

					if (line1.equals(ChatColor.DARK_BLUE + "[Buy PS]")) {

						if (methods.isInt(line3)) {

							if (line4.contains("$")) {

								int amount = Integer.parseInt(line3);
								int price = Integer.parseInt(line4.substring(1));

								double balance = 0;
								try {
									balance = Double.valueOf(Economy.getMoney(player.getName()));
								} catch (UserDoesNotExistException e1) {
									e1.printStackTrace();
								}

								if (essentials != null) {
									if (balance >= price) {

										if (player.getInventory().firstEmpty() != -1) {

											PlayerInventory playerinventory = player.getInventory();

											if (line2.equals(block1SignName)) {

												playerinventory.addItem(ps.block1(amount));

											} else if (line2.equals(block2SignName)) {

												playerinventory.addItem(ps.block2(amount));

											} else if (line2.equals(block3SignName)) {

												playerinventory.addItem(ps.block3(amount));
											} else {
												return;
											}

											player.sendMessage(ChatColor.GREEN + "$" + price + " has been taken from your account.");

											try {
												Economy.subtract(player.getName(), price);
											} catch (NoLoanPermittedException e) {
												e.printStackTrace();
											} catch (UserDoesNotExistException e) {
												e.printStackTrace();
											}

										} else {
											player.sendMessage(ChatColor.RED + "Error: " + ChatColor.DARK_RED + "Inventory full");
										}

									} else {
										player.sendMessage(ChatColor.RED + "Error: " + ChatColor.DARK_RED + "You do not have sufficient funds.");
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
