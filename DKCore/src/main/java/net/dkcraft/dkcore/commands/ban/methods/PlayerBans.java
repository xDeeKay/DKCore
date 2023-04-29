package net.dkcraft.dkcore.commands.ban.methods;

public class PlayerBans {

	private int id;
	private String targetUUID;
	private String targetName;
	private String senderUUID;
	private String senderName;
	private long banDate;
	private long banLength;
	private String banReason;
	private boolean active;
	private boolean permanent;
	private String unbanUUID;
	private String unbanName;
	private long unbanDate;
	private String unbanReason;

	public PlayerBans(int id, String targetUUID, String targetName, String senderUUID, String senderName, long banDate, long banLength, String banReason, boolean active, boolean permanent, String unbanUUID, String unbanName, long unbanDate, String unbanReason) {
		this.id = id;
		this.targetUUID = targetUUID;
		this.targetName = targetName;
		this.senderUUID = senderUUID;
		this.senderName = senderName;
		this.banDate = banDate;
		this.banLength = banLength;
		this.banReason = banReason;
		this.active = active;
		this.permanent = permanent;
		this.unbanUUID = unbanUUID;
		this.unbanName = unbanName;
		this.unbanDate = unbanDate;
		this.unbanReason = unbanReason;
	}
	
	public int getID() {
		return id;
	}
	
	public String getTargetUUID() {
		return targetUUID;
	}

	public String getTargetName() {
		return targetName;
	}
	
	public String getSenderUUID() {
		return senderUUID;
	}

	public String getSenderName() {
		return senderName;
	}

	public long getBanDate() {
		return banDate;
	}

	public long getBanLength() {
		return banLength;
	}

	public String getBanReason() {
		return banReason;
	}

	public boolean getActive() {
		return active;
	}

	public boolean getPermanent() {
		return permanent;
	}
	
	public String getUnbanUUID() {
		return unbanUUID;
	}
	
	public String getUnbanName() {
		return unbanName;
	}
	
	public long getUnbanDate() {
		return unbanDate;
	}
	
	public String getUnbanReason() {
		return unbanReason;
	}
}
