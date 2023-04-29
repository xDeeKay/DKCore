package net.dkcraft.dkcore.commands.ban.methods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.dkcraft.dkcore.Main;
import net.dkcraft.dkcore.util.Config;
import net.dkcraft.dkcore.util.Methods;

public class BanMethodsMySQL {

	public Main plugin;
	public Methods methods;
	public Config config;
	public BanMethods banmethods;

	public BanMethodsMySQL(Main plugin) {
		this.plugin = plugin;
		this.methods = this.plugin.methods;
		this.config = this.plugin.config;
		this.banmethods = this.plugin.banmethods;
	}

	public void fetchPlayerBans(String targetUUID) {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		
		String query = "SELECT * FROM dkc_player_bans WHERE target_uuid = ? ORDER BY ban_date DESC";

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setString(1, targetUUID);

			result = statement.executeQuery();

			ArrayList<PlayerBans> bans = new ArrayList<PlayerBans>();

			while (result.next()) {

				int id = result.getInt(1);

				//String targetUUID = result.getString(2);
				String targetName = result.getString(3);

				String senderUUID = result.getString(4);
				String senderName = result.getString(5);

				long banDate = result.getLong(6);
				long banLength = result.getLong(7);
				String banReason = result.getString(8);

				boolean active = result.getBoolean(9);
				boolean permanent = result.getBoolean(10);

				String unbanUUID = result.getString(11);
				String unbanName = result.getString(12);
				long unbanDate = result.getLong(13);
				String unbanReason = result.getString(14);

				bans.add(new PlayerBans(id, targetUUID, targetName, senderUUID, senderName, banDate, banLength, banReason, active, permanent, unbanUUID, unbanName, unbanDate, unbanReason));
			}

			plugin.playerBans.put(targetUUID, bans);

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
	
	public void fetchIPBans(String ip) {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		
		String query = "SELECT * FROM dkc_ip_bans WHERE ip = ? ORDER BY ban_date DESC";

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setString(1, ip);

			result = statement.executeQuery();

			ArrayList<IPBans> bans = new ArrayList<IPBans>();

			while (result.next()) {

				int id = result.getInt(1);

				String targetIP = result.getString(2);

				String senderUUID = result.getString(3);
				String senderName = result.getString(4);

				long banDate = result.getLong(5);
				long banLength = result.getLong(6);
				String banReason = result.getString(7);

				boolean active = result.getBoolean(8);
				boolean permanent = result.getBoolean(9);

				String unbanUUID = result.getString(10);
				String unbanName = result.getString(11);
				long unbanDate = result.getLong(12);
				String unbanReason = result.getString(13);

				bans.add(new IPBans(id, targetIP, senderUUID, senderName, banDate, banLength, banReason, active, permanent, unbanUUID, unbanName, unbanDate, unbanReason));
			}

			plugin.ipBans.put(ip, bans);

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

	public void fetchGlobalPlayerBans(int amount) {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		
		String query = "SELECT * FROM dkc_player_bans ORDER BY ban_date DESC LIMIT ?";

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setInt(1, amount);

			result = statement.executeQuery();

			int i = 0;
			while (result.next()) {

				int id = result.getInt(1);

				String targetUUID = result.getString(2);
				String targetName = result.getString(3);

				String senderUUID = result.getString(4);
				String senderName = result.getString(5);

				long banDate = result.getLong(6);
				long banLength = result.getLong(7);
				String banReason = result.getString(8);

				boolean active = result.getBoolean(9);
				boolean permanent = result.getBoolean(10);

				String unbanUUID = result.getString(11);
				String unbanName = result.getString(12);
				long unbanDate = result.getLong(13);
				String unbanReason = result.getString(14);

				plugin.globalPlayerBans.put(i, new PlayerBans(id, targetUUID, targetName, senderUUID, senderName, banDate, banLength, banReason, active, permanent, unbanUUID, unbanName, unbanDate, unbanReason));

				i++;
			}

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
	
	public void fetchGlobalIPBans(int amount) {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		
		String query = "SELECT * FROM dkc_ip_bans ORDER BY ban_date DESC LIMIT ?";

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setInt(1, amount);

			result = statement.executeQuery();

			int i = 0;
			while (result.next()) {

				int id = result.getInt(1);

				String targetIP = result.getString(2);

				String senderUUID = result.getString(3);
				String senderName = result.getString(4);

				long banDate = result.getLong(5);
				long banLength = result.getLong(6);
				String banReason = result.getString(7);

				boolean active = result.getBoolean(8);
				boolean permanent = result.getBoolean(9);

				String unbanUUID = result.getString(10);
				String unbanName = result.getString(11);
				long unbanDate = result.getLong(12);
				String unbanReason = result.getString(13);

				plugin.globalIPBans.put(i, new IPBans(id, targetIP, senderUUID, senderName, banDate, banLength, banReason, active, permanent, unbanUUID, unbanName, unbanDate, unbanReason));

				i++;
			}

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

	public void banPlayer(String targetUUID, String targetName, String senderUUID, String senderName, long banDate, long banLength, String banReason, boolean permanent) {

		Connection connection = null;
		PreparedStatement statement = null;
		
		String query = "INSERT INTO dkc_player_bans(target_uuid, target_name, sender_uuid, sender_name, ban_date, ban_length, ban_reason, active, permanent) VALUES(?,?,?,?,?,?,?,?,?)";

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setString(1, targetUUID);
			statement.setString(2, targetName);
			statement.setString(3, senderUUID);
			statement.setString(4, senderName);
			statement.setLong(5, banDate);
			statement.setLong(6, banLength);
			statement.setString(7, banReason);
			statement.setBoolean(8, true);
			statement.setBoolean(9, permanent);
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

	public void unbanPlayer(String unbanUUID, String unbanName, long unbanDate, String unbanReason, String targetUUID) {

		Connection connection = null;
		PreparedStatement statement = null;
		
		String query = "UPDATE dkc_player_bans SET active = ?, unban_uuid = ?, unban_name = ?, unban_date = ?, unban_reason = ? WHERE target_uuid = ? AND active = ?";

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setBoolean(1, false);
			statement.setString(2, unbanUUID);
			statement.setString(3, unbanName);
			statement.setLong(4, unbanDate);
			statement.setString(5, unbanReason);
			statement.setString(6, targetUUID);
			statement.setBoolean(7, true);
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
	
	public void banIP(String targetIP, String senderUUID, String senderName, long banDate, long banLength, String banReason, boolean permanent) {

		Connection connection = null;
		PreparedStatement statement = null;
		
		String query = "INSERT INTO dkc_ip_bans(ip, sender_uuid, sender_name, ban_date, ban_length, ban_reason, active, permanent) VALUES(?,?,?,?,?,?,?,?)";

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setString(1, targetIP);
			statement.setString(2, senderUUID);
			statement.setString(3, senderName);
			statement.setLong(4, banDate);
			statement.setLong(5, banLength);
			statement.setString(6, banReason);
			statement.setBoolean(7, true);
			statement.setBoolean(8, permanent);
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

	public void unbanIP(String unbanUUID, String unbanName, long unbanDate, String unbanReason, String targetIP) {

		Connection connection = null;
		PreparedStatement statement = null;
		
		String query = "UPDATE dkc_ip_bans SET active = ?, unban_uuid = ?, unban_name = ?, unban_date = ?, unban_reason = ? WHERE ip = ? AND active = ?";

		try {
			connection = plugin.ds.getConnection();

			statement = connection.prepareStatement(query);
			statement.setBoolean(1, false);
			statement.setString(2, unbanUUID);
			statement.setString(3, unbanName);
			statement.setLong(4, unbanDate);
			statement.setString(5, unbanReason);
			statement.setString(6, targetIP);
			statement.setBoolean(7, true);
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
}
