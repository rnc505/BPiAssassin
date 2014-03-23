package BP.users;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable
public class GameUser {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	public String uuidString;
	
	@Persistent
	public String code_name;
	@Persistent
	public GameUserImage thumbnail;
	@Persistent
	public ArrayList<GameUserImage> usrImages;
	@Persistent
	public HashMap<String, GameUser>  gameTargets;
	@Persistent
	public int numKills;
	@Persistent
	public int numDeaths;
	@Persistent
	public int numWins;
	
	@Persistent
	public String apn;
	@Persistent 
	public String platformID;
	

	/**
	 * JDO Constructor 
	 * no-args constructor for use by JDO
	 */
	public GameUser() {
	}
	
	public GameUser(String code_name,  GameUserImage thumbnail, 
			ArrayList<GameUserImage> usrImages) {
		UUID uuid = new UUID(System.nanoTime(), System.nanoTime());
		this.uuidString = uuid.toString();
		this.code_name = code_name;
		this.thumbnail = thumbnail;
		this.usrImages = usrImages;
		this.gameTargets =null;
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
	public GameUserImage getThumbnail() {
		return this.thumbnail;
	}
	
	/**
	 * getUsrImages() 
	 * Returns an ArrayList of the UserImages
	 * @return
	 */
	public ArrayList<GameUserImage> getUsrImages() {
		return this.usrImages;
	}
	
	/**
	 * setTarget() 
	 * Sets user target
	 * @param target
	 */
	public void setTarget(String gameUUID, GameUser target) {
		this.gameTargets.put(gameUUID, target);
	}
	
	/**
	 * getTarget() 
	 * @return Returns user target
	 */
	public GameUser getTarget(String gameUUID) {
		return this.gameTargets.get(gameUUID);
	}
	/**
	 * removeGame () 
	 * Removes the game and associated target from 
	 * gameTargets
	 * @param gameUUID
	 */
	public void removeTarget(String gameUUID) {
		this.gameTargets.remove(gameUUID);
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