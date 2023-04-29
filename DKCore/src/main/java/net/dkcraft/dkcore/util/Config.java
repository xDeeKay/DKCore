package net.dkcraft.dkcore.util;

import java.util.List;
import java.util.ListIterator;

import net.dkcraft.dkcore.Main;

public class Config {

	public Main plugin;

	public Config(Main plugin) {
		this.plugin = plugin;
	}
	
	private List<String> ranks;
	
	private String hidestatusLogin;
	private String hidestatusLogout;
	
	private String mysqlUser;
	private String mysqlPort;
	private String mysqlPassword;
	private String mysqlHost;
	private String mysqlDatabase;
	
	public void loadConfig() {
		this.setRanks(plugin.getConfig().getStringList("ranks"));
		
		this.setHidestatusLogin(plugin.getConfig().getString("hidestatus.login"));
		this.setHidestatusLogout(plugin.getConfig().getString("hidestatus.logout"));
		
		this.setMysqlUser(plugin.getConfig().getString("mysql.user"));
		this.setMysqlPort(plugin.getConfig().getString("mysql.port"));
		this.setMysqlPassword(plugin.getConfig().getString("mysql.password"));
		this.setMysqlHost(plugin.getConfig().getString("mysql.host"));
		this.setMysqlDatabase(plugin.getConfig().getString("mysql.database"));
	}
	
	// ranks
	
	public void setRanks(List<String> ranks) {
		ListIterator<String> iterator = ranks.listIterator();
		while (iterator.hasNext()) {
			iterator.set(iterator.next());
		}
		this.ranks = ranks;
	}
	
	public List<String> getRanks() {
		return this.ranks;
	}
	
	// hidestatus
	
		public void setHidestatusLogin(String hidestatusLogin) {
			this.hidestatusLogin = hidestatusLogin;
		}

		public String getHidestatusLogin() {
			return this.hidestatusLogin;
		}
		
		public void setHidestatusLogout(String hidestatusLogout) {
			this.hidestatusLogout = hidestatusLogout;
		}

		public String getHidestatusLogout() {
			return this.hidestatusLogout;
		}
	
	// mysql

	public void setMysqlUser(String mysqlUser) {
		this.mysqlUser = mysqlUser;
	}

	public String getMysqlUser() {
		return this.mysqlUser;
	}

	public void setMysqlPort(String mysqlPort) {
		this.mysqlPort = mysqlPort;
	}

	public String getMysqlPort() {
		return this.mysqlPort;
	}

	public void setMysqlPassword(String mysqlPassword) {
		this.mysqlPassword = mysqlPassword;
	}

	public String getMysqlPassword() {
		return this.mysqlPassword;
	}

	public void setMysqlHost(String mysqlHost) {
		this.mysqlHost = mysqlHost;
	}

	public String getMysqlHost() {
		return this.mysqlHost;
	}

	public void setMysqlDatabase(String mysqlDatabase) {
		this.mysqlDatabase = mysqlDatabase;
	}

	public String getMysqlDatabase() {
		return this.mysqlDatabase;
	}
}
