package net.dkcraft.dkcore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.zaxxer.hikari.HikariDataSource;

import net.dkcraft.dkcore.announcement.AnnouncementMethods;
import net.dkcraft.dkcore.commands.Freeze;
import net.dkcraft.dkcore.commands.GiveCoalProtection;
import net.dkcraft.dkcore.commands.Login;
import net.dkcraft.dkcore.commands.Logout;
import net.dkcraft.dkcore.commands.Ranks;
import net.dkcraft.dkcore.commands.Report;
import net.dkcraft.dkcore.commands.ReportClaim;
import net.dkcraft.dkcore.commands.ReportDelete;
import net.dkcraft.dkcore.commands.ReportList;
import net.dkcraft.dkcore.commands.Servermsg;
import net.dkcraft.dkcore.commands.Staffchat;
import net.dkcraft.dkcore.commands.Unfreeze;
import net.dkcraft.dkcore.commands.Warn;
import net.dkcraft.dkcore.commands.ban.methods.PlayerBans;
import net.dkcraft.dkcore.commands.ban.methods.BanMethods;
import net.dkcraft.dkcore.commands.ban.methods.BanMethodsMySQL;
import net.dkcraft.dkcore.commands.ban.methods.IPBans;
import net.dkcraft.dkcore.listeners.BuySign;
import net.dkcraft.dkcore.listeners.ChatListener;
import net.dkcraft.dkcore.listeners.FreezeListener;
import net.dkcraft.dkcore.listeners.ItemUseListener;
import net.dkcraft.dkcore.util.Config;
import net.dkcraft.dkcore.util.Methods;
import net.dkcraft.dkcore.util.MySQL;
import net.dkcraft.dkcore.util.ProtectionStones;
import net.dkcraft.dkcore.vote.Vote;
import net.dkcraft.dkcore.vote.VoteListener;
import net.dkcraft.dkcore.vote.VoteMethods;
import net.dkcraft.dkcore.commands.ban.Ban;
import net.dkcraft.dkcore.commands.ban.BanListener;

public class Main extends JavaPlugin {

	public Main instance;

	public Config config;
	public Methods methods;
	public ProtectionStones ps;
	public AnnouncementMethods announcement;
	public VoteMethods vote;
	public MySQL mysql;
	
	public BanMethods banmethods;
	public BanMethodsMySQL banmysql;
	public HikariDataSource ds;
	
	public HashMap<Integer, PlayerBans> globalPlayerBans = new HashMap<Integer, PlayerBans>();
	public HashMap<String, ArrayList<PlayerBans>> playerBans = new HashMap<String, ArrayList<PlayerBans>>();
	
	public HashMap<Integer, IPBans> globalIPBans = new HashMap<Integer, IPBans>();
	public HashMap<String, ArrayList<IPBans>> ipBans = new HashMap<String, ArrayList<IPBans>>();

	public ArrayList<String> freeze = new ArrayList<String>();
	public ArrayList<String> staffChat = new ArrayList<String>();
	public HashMap<String, String> playerReports = new HashMap<String, String>();
	public HashMap<String, Integer> honeypotCount = new HashMap<String, Integer>();
	public ArrayList<Location> honeypotRollback = new ArrayList<Location>();
	
	public void loadConfiguration() {
		this.getConfig().options().copyDefaults(true);
		saveDefaultConfig();
	}
	
	public Logger log = Logger.getLogger("Minecraft");

	public void onEnable() {

		config = new Config(this);
		methods = new Methods(this);
		ps = new ProtectionStones(this);
		announcement = new AnnouncementMethods(this);
		vote = new VoteMethods(this);
		mysql = new MySQL(this);
		
		banmethods = new BanMethods(this);
		banmysql = new BanMethodsMySQL(this);

		String pluginFolder = this.getDataFolder().getAbsolutePath();
		(new File(pluginFolder)).mkdirs();
		
		File votePointsFile = new File(pluginFolder, "votepoints.yml");
		if (!votePointsFile.exists()) {
			try {
				votePointsFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		final PluginManager pm = getServer().getPluginManager();

		loadConfiguration();

		pm.registerEvents(new BuySign(this), this);
		pm.registerEvents(new ChatListener(this), this);
		pm.registerEvents(new FreezeListener(this), this);
		pm.registerEvents(new ItemUseListener(this), this);
		
		pm.registerEvents(new VoteListener(this), this);
		
		pm.registerEvents(new BanListener(this), this);
		
		this.getCommand("freeze").setExecutor(new Freeze(this));
		this.getCommand("givecoalprotection").setExecutor(new GiveCoalProtection(this));
		this.getCommand("login").setExecutor(new Login(this));
		this.getCommand("logout").setExecutor(new Logout(this));
		this.getCommand("ranks").setExecutor(new Ranks(this));
		this.getCommand("report").setExecutor(new Report(this));
		this.getCommand("reportclaim").setExecutor(new ReportClaim(this));
		this.getCommand("reportdelete").setExecutor(new ReportDelete(this));
		this.getCommand("reportlist").setExecutor(new ReportList(this));
		this.getCommand("servermsg").setExecutor(new Servermsg());
		this.getCommand("staffchat").setExecutor(new Staffchat(this));
		this.getCommand("unfreeze").setExecutor(new Unfreeze(this));
		this.getCommand("vote").setExecutor(new Vote(this));
		this.getCommand("warn").setExecutor(new Warn(this));
		
		this.getCommand("ban").setExecutor(new Ban(this));
		
		config.loadConfig();
		
		mysql.openConnection();
		mysql.createTables();

		announcement.loadConfig();
		vote.loadConfig();
	}

	public void onDisable() {
	}
}
