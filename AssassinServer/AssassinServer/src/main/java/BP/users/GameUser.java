package BP.users;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable
public class GameUser {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private String uuidString;
	
	@Persistent
	private String code_name;
	@Persistent
	private String thumbnailUUID;
	@Persistent
	private ArrayList<String> usrImageUUIDs;
	@Persistent
	private HashMap<String, String>  gameTargetUUIDs;
	@Persistent
	private int numKills;
	@Persistent
	private int numDeaths;
	@Persistent
	private int numWins;
	
	@Persistent
	private String apn;
	@Persistent 
	private String platformID;
	

	/**
	 * JDO Constructor 
	 * no-args constructor for use by JDO
	 */
	public GameUser() {
	}
	
	public GameUser(String code_name,  String thumbnailUUID, 
			ArrayList<String> usrImageUUIDs) {
		UUID uuid = new UUID(System.nanoTime(), System.nanoTime());
		this.uuidString = uuid.toString();
		this.code_name = code_name;
		this.thumbnailUUID = thumbnailUUID;
		this.usrImageUUIDs = usrImageUUIDs;
		this.gameTargetUUIDs =null;
		this.numKills = 0;
		this.numDeaths = 0;
		this.numWins = 0;
}
	
	/**
	 * getUUID()
	 * @return Returns the client unique identifier for the Game User
	 */
	public String getUUID() {
		return this.uuidString;
	}
	
	/**
	 * getUserCodeName() 
	 * @return Returns the user's code name
	 */
	public String getUserCodeName() {
		return this.code_name;
	}
	
	/**
	 * getThumbnail() 
	 * Returns user GameUserImage thumbnail
	 * @return
	 */
	public String getThumbnailUUID() {
		return this.thumbnailUUID;
	}
	
	/**
	 * getUsrImageUUIDs() 
	 * Returns an ArrayList of the UserImages
	 * @return
	 */
	public ArrayList<String> getUsrImageUUIDs() {
		return this.usrImageUUIDs;
	}
	
	/**
	 * setTarget() 
	 * Sets user target
	 * @param target
	 */
	public void setTargetUUID(String gameUUID, String targetUUID) {
		this.gameTargetUUIDs.put(gameUUID, targetUUID);
	}
	
	/**
	 * getTarget() 
	 * @return Returns user target
	 */
	public String getTargetUUID(String gameUUID) {
		return this.gameTargetUUIDs.get(gameUUID);
	}
	/**
	 * removeGame () 
	 * Removes the game and associated target from 
	 * gameTargets
	 * @param gameUUID
	 */
	public void removeTarget(String gameUUID) {
		this.gameTargetUUIDs.remove(gameUUID);
	}
	
	/**
	 * addKill() 
	 * Increments number of user kills by 1
	 * @return
	 */
	public int addKill() {
		return ++this.numKills;
	}
	
	/**
	 * getNumKills() 
	 * @return Returns number of kills
	 */
	public int getNumKills() {
		return this.numKills;
	}
	
	/**
	 * addDeath() 
	 * Increments number of user deaths by 1
	 * @return
	 */
	public int addDeath() {
		return ++this.numDeaths;
	}
	
	/**
	 * getNumDeaths() 
	 * Returns number of deaths
	 * @return
	 */
	public int getNumDeaths() {
		return this.numDeaths;
	}

	/**
	 * addWin() 
	 * Increments number of user wins by 1.
	 *  Returns updated number of wins.
	 * @return
	 */
	public int addWin() {
		return ++this.numWins;
	}

	/**
	 * getNumWins() 
	 * Returns number of user wins
	 * @return
	 */
	public int getNumWins() {
		return this.numWins;
	}
	
	public void setAPN(String apn) {
		this.apn =apn;
	}
	
	public String getAPN() {
		return this.apn;
	}
	
	public void setPlatformID(String id) {
		this.platformID = id;
	}
	
	public String getPlatformID() {
		return this.platformID;
	}
	
}