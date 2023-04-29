package net.dkcraft.dkcore.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import net.dkcraft.dkcore.Main;

public class Methods {

	public Main plugin;

	public Methods(Main plugin) {
		this.plugin = plugin;
	}

	public boolean isInt(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean hands(Player player, Material material) {
		Material mainHand = player.getInventory().getItemInMainHand().getType();
		Material offHand = player.getInventory().getItemInOffHand().getType();

		if (mainHand.equals(material) || offHand.equals(material)) {
			return true;
		}
		return false;
	}

	public String getDurationString(long duration) {
		long days = TimeUnit.SECONDS.toDays(duration);
		duration -= TimeUnit.DAYS.toSeconds(days);

		long hours = TimeUnit.SECONDS.toHours(duration);
		duration -= TimeUnit.HOURS.toSeconds(hours);

		long minutes = TimeUnit.SECONDS.toMinutes(duration);
		duration -= TimeUnit.MINUTES.toSeconds(minutes);

		long seconds = TimeUnit.SECONDS.toSeconds(duration);

		StringBuilder result = new StringBuilder();
		if (days != 0) {
			result.append(days + " day(s) ");
		}
		if (hours != 0) {
			result.append(hours + " hours(s) ");
		}
		if (minutes != 0) {
			result.append(minutes + " minutes(s) ");
		}
		if (seconds != 0) {
			result.append(seconds + " seconds(s)");
		}
		return result.toString();
	}

	public String getUnbanDate(long banLength) {
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-YYYY HH:mm:ss");
		String date = dateFormat.format(banLength * 1000);
		return date;
	}

	public long getCurrentTimestampInSeconds() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Long currentTimestampInSeconds = timestamp.getTime() / 1000;
		return currentTimestampInSeconds;
	}

	public boolean isPermanent(String banLengthString) {
		if (banLengthString.equalsIgnoreCase("permanent") || 
				banLengthString.equalsIgnoreCase("perma") || 
				banLengthString.equalsIgnoreCase("perm") || 
				banLengthString.equalsIgnoreCase("p")) {
			return true;
		}
		return false;
	}

	public boolean isValidTimeString(String banLengthString) {
		if (banLengthString.contains("s") || 
				banLengthString.contains("m") || 
				banLengthString.contains("h") || 
				banLengthString.contains("d")|| 
				banLengthString.contains("w")) {
			return true;
		} else {
			return false;
		}
	}

	public long parse(String input) {
		long result = 0;
		String number = "";
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (Character.isDigit(c)) {
				number += c;
			} else if (Character.isLetter(c) && !number.isEmpty()) {
				result += convert(Integer.parseInt(number), c);
				number = "";
			}
		}
		return result;
	}

	public long convert(int value, char unit) {
		switch (unit) {
		case 'w':
			return value * 604800;
		case 'd':
			return value * 86400;
		case 'h':
			return value * 3600;
		case 'm':
			return value * 60;
		case 's':
			return value * 1;
		}
		return 0;
	}

	public boolean isPlayerOnline(Player target) {
		if (target != null) {
			return true;
		}
		return false;
	}
}
