package net.dkcraft.dkcore.util;

import java.util.ArrayList;
import java.util.List;

import net.dkcraft.dkcore.Main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ProtectionStones {
	
	public Main plugin;

	public ProtectionStones(Main plugin) {
		this.plugin = plugin;
	}
	
	public ItemStack block1(int amount) {

		String block = plugin.getConfig().getString("protection_blocks.block1.block");
		String metaName = plugin.getConfig().getString("protection_blocks.block1.meta_name");
		List<String> metaLore = plugin.getConfig().getStringList("protection_blocks.block1.meta_lore");

		ItemStack item = new ItemStack(Material.valueOf(block.toUpperCase()));
		ItemMeta im = item.getItemMeta();

		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', metaName));

		List<String> loreList = new ArrayList<String>();

		for (String string : metaLore) {
			loreList.add(ChatColor.translateAlternateColorCodes('&', string));
		}

		im.setLore(loreList);

		item.setAmount(amount);
		item.setItemMeta(im);
		return item;
	}

	public ItemStack block2(int amount) {

		String block = plugin.getConfig().getString("protection_blocks.block2.block");
		String metaName = plugin.getConfig().getString("protection_blocks.block2.meta_name");
		List<String> metaLore = plugin.getConfig().getStringList("protection_blocks.block2.meta_lore");

		ItemStack item = new ItemStack(Material.valueOf(block.toUpperCase()));
		ItemMeta im = item.getItemMeta();

		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', metaName));

		List<String> loreList = new ArrayList<String>();

		for (String string : metaLore) {
			loreList.add(ChatColor.translateAlternateColorCodes('&', string));
		}

		im.setLore(loreList);

		item.setAmount(amount);
		item.setItemMeta(im);
		return item;
	}

	public ItemStack block3(int amount) {

		String block = plugin.getConfig().getString("protection_blocks.block3.block");
		String metaName = plugin.getConfig().getString("protection_blocks.block3.meta_name");
		List<String> metaLore = plugin.getConfig().getStringList("protection_blocks.block3.meta_lore");

		ItemStack item = new ItemStack(Material.valueOf(block.toUpperCase()));
		ItemMeta im = item.getItemMeta();

		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', metaName));

		List<String> loreList = new ArrayList<String>();

		for (String string : metaLore) {
			loreList.add(ChatColor.translateAlternateColorCodes('&', string));
		}

		im.setLore(loreList);

		item.setAmount(amount);
		item.setItemMeta(im);
		return item;
	}
}
