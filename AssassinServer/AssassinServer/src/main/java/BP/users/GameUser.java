package BP.users;

import BP.repository.GameDataStorage;

import java.util.ArrayList;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable
public class GameUser {
	
	public final int NUM_IMAGES = 4;
	
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
	public String targetUUID;
	@Persistent
	public int numKills;
	@Persistent
	public int numDeaths;
	@Persistent
	public int numWins;

	/**
	 * JDO Constructor 
	 * no-args constructor for use by JDO
	 */
	public GameUser() {
	}
	
	public GameUser(String uuid, String code_name,  GameUserImage thumbnail, 
			ArrayList<GameUserImage> usrImages) {
		this.uuidString = uuid;
		this.code_name = code_name;
		this.thumbnail = thumbnail;
		this.usrImages = usrImages;
		this.targetUUID =null;
		this.numKills = numKills;
		this.numDeaths = numDeaths;
		this.numWins = numWins;
		
}
	
	
	/**
	 * setTarget() 
	 * Sets user target
	 * @param target
	 */
	public void setTarget(String target) {
		this.targetUUID = target;
	}
	
	/**
	 * getTarget() 
	 * Returns user target
	 * @return 
	 */
	public GameUser getTarget() {
		return storage.getUser(this.usrTarget);
	}
	
	/**
	 * getTargetHash() 
	 * Returns the user's target's hash
	 * @return
	 */
	public String getTargetHash() {
		return this.usrTarget;
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
		ArrayList<GameUserImage> iArray = new ArrayList<GameUserImage>();
		iArray.add(this.usrImage1);
		iArray.add(this.usrImage2);
		iArray.add(this.usrImage3);
		iArray.add(this.usrImage4);
		return iArray;
	}
	
	/**
	 * getUserID() 
	 * Returns the user ID
	 * @return
	 */
	public String getUserID() {
		return this.userID;
	}
	
	/**
	 * addKill() 
	 * Increments number of user kills by 1
	 * @return
	 */
	public int addKill() {
		storage.updateUser(this, "numKills", ++this.numKills);
		return this.numKills;
	}
	
	/**
	 * getNumKills() 
	 * Returns number of kills
	 * @return
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
		storage.updateUser(this, "numDeaths", ++this.numDeaths);
		return this.numDeaths;
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
		storage.updateUser(this, "numWins", ++this.numWins);
		return this.numWins;
	}

	/**
	 * getNumWins() 
	 * Returns number of user wins
	 * @return
	 */
	public int getNumWins() {
		return this.numWins;
	}
	
	/**
	 * getHash()
	 * Returns the client hash for the Game User 
	 * @return 
	 */
	public String getHash() {
		return this.hash;
	}
}