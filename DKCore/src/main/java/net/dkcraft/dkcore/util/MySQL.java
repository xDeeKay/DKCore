package net.dkcraft.dkcore.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

import net.dkcraft.dkcore.Main;

public class MySQL {

	public Main plugin;
	public Config config;

	public MySQL(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
	}

	public void openConnection() {

		plugin.log.info("[DKCore] Opening MySQL connection...");

		String host = config.getMysqlHost();
		String port = config.getMysqlPort();
		String database = config.getMysqlDatabase();
		String user = config.getMysqlUser();
		String password = config.getMysqlPassword();

		plugin.ds = new HikariDataSource();
		plugin.ds.setMaximumPoolSize(10);
		plugin.ds.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		plugin.ds.addDataSourceProperty("serverName", host);
		plugin.ds.addDataSourceProperty("port", port);
		plugin.ds.addDataSourceProperty("databaseName", database);
		plugin.ds.addDataSourceProperty("user", user);
		plugin.ds.addDataSourceProperty("password", password);
	}

	@SuppressWarnings("resource")
	public synchronized void createTables() {

		Connection connection = null;
		PreparedStatement statement = null;

		String queryPlayerBans = "CREATE TABLE IF NOT EXISTS dkc_player_bans ("
				+ "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," 
				+ "target_uuid VARCHAR(36)," 
				+ "target_name VARCHAR(16)," 
				+ "sender_uuid VARCHAR(36)," 
				+ "sender_name VARCHAR(16)," 
				+ "ban_date BIGINT(13)," 
				+ "ban_length BIGINT(13)," 
				+ "ban_reason VARCHAR(255)," 
				+ "active BOOLEAN," 
				+ "permanent BOOLEAN," 
				+ "unban_uuid VARCHAR(36)," 
				+ "unban_name VARCHAR(16)," 
				+ "unban_date BIGINT(13)," 
				+ "unban_reason VARCHAR(255));";
		
		String queryIPBans = "CREATE TABLE IF NOT EXISTS dkc_ip_bans ("
				+ "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," 
				+ "ip VARCHAR(15)," 
				+ "sender_uuid VARCHAR(36)," 
				+ "sender_name VARCHAR(16)," 
				+ "ban_date BIGINT(13)," 
				+ "ban_length BIGINT(13)," 
				+ "ban_reason VARCHAR(255)," 
				+ "active BOOLEAN," 
				+ "permanent BOOLEAN," 
				+ "unban_uuid VARCHAR(36)," 
				+ "unban_name VARCHAR(16)," 
				+ "unban_date BIGINT(13)," 
				+ "unban_reason VARCHAR(255));";

		String queryVotes = "CREATE TABLE IF NOT EXISTS dkc_votes (" 
				+ "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," 
				+ "uuid VARCHAR(36)," 
				+ "username VARCHAR(16)," 
				+ "ip TEXT," 
				+ "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP," 
				+ "service TEXT);";

		try {
			connection = plugin.ds.getConnection();

			DatabaseMetaData metadata = connection.getMetaData();
			ResultSet resultSet;

			resultSet = metadata.getTables(null, null, "dkc_player_bans", null);
			if (!resultSet.next()) {
				statement = connection.prepareStatement(queryPlayerBans);
				statement.executeUpdate();
				plugin.log.info("[DKCore] Created MySQL dkc_player_bans table.");
			}
			
			resultSet = metadata.getTables(null, null, "dkc_ip_bans", null);
			if (!resultSet.next()) {
				statement = connection.prepareStatement(queryIPBans);
				statement.executeUpdate();
				plugin.log.info("[DKCore] Created MySQL dkc_ip_bans table.");
			}

			resultSet = metadata.getTables(null, null, "dkc_votes", null);
			if (!resultSet.next()) {
				statement = connection.prepareStatement(queryVotes);
				statement.executeUpdate();
				plugin.log.info("[DKCore] Created MySQL dkc_votes table.");
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
}
