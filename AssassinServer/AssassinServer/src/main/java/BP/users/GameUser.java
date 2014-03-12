package BP.users;

import BP.repository.GameDataStorage;

import java.util.ArrayList;

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
	public final int NUM_IMAGES = 4;
	
	@Persistent
	public String userID;
	@Persistent
	public GameUserImage thumbnail;
	@Persistent
	public GameUserImage usrImage1;
	@Persistent
	public GameUserImage usrImage2;
	@Persistent
	public GameUserImage usrImage3;
	@Persistent
	public GameUserImage usrImage4;
	@Persistent
	public String targetUUID;
	
	
	public int numKills;
	public int numDeaths;
	public int numWins;

	/**
	 * Default Constructor 
	 *  
	 * @param hash
	 * @param userID
	 * @param thumbnail
	 * @param usrImage1
	 * @param usrImage2
	 * @param usrImage3
	 * @param usrImage4
	 */
	public GameUser(String uuid, String userID,  GameUserImage thumbnail, 
			GameUserImage usrImage1,GameUserImage usrImage2, 
			GameUserImage usrImage3, GameUserImage usrImage4) {
		this(uuid, userID, thumbnail, usrImage1, 
				usrImage2, usrImage3, usrImage4, null, 0, 0 ,0, true);
	}
	
	
	/**
	 * Special Constructor 
	 * @param hash
	 * @param userID
	 * @param thumbnail
	 * @param usrImage1
	 * @param usrImage2
	 * @param usrImage3
	 * @param usrImage4
	 * @param usrTarget
	 * @param numKills
	 * @param numDeaths
	 * @param numWins
	 * @param storeData
	 */
	public GameUser(String uuid, String userID,  GameUserImage thumbnail, 
			GameUserImage usrImage1,GameUserImage usrImage2, 
			GameUserImage usrImage3, GameUserImage usrImage4, 
			String usrTarget, int numKills, int numDeaths, int numWins, 
			boolean storeData) {
		this.uuidString = uuid;
		this.userID = userID;
		this.thumbnail = thumbnail;
		this.usrImage1 = usrImage1;
		this.usrImage2 = usrImage2;
		this.usrImage3 = usrImage3;
		this.usrImage4 = usrImage4;
		this.targetUUID = usrTarget;
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