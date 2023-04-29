package net.dkcraft.dkcore.honeypot;

public class HoneypotPlayer {
	
	private String uuid;
	private String playerName;
	//private Map<Location, Material> brokenBlock = new HashMap<Location, Material>();
	
	//private List<String> myList = new ArrayList<String>();
	
	//private Map<Location, Material> placedBlock = new HashMap<Location, Material>();
	
	public HoneypotPlayer(String uuid, String playerName) {
		this.uuid = uuid;
		this.playerName = playerName;
	}

	public void setUUID(String uuid) {
		this.uuid = uuid;
	}
	
	public String getUUID() {
		return uuid;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public String getPlayerName() {
		return playerName;
	}
}
