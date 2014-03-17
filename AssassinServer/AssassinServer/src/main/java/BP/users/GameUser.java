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
	public String code_name;
	@Persistent
	public GameUserImage thumbnail;
	@Persistent
	public ArrayList<GameUserImage> usrImages;
	@Persistent
	public GameUser target;
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
		this.target =null;
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
	public void setTarget(GameUser target) {
		this.target = target;
	}
	
	/**
	 * getTarget() 
	 * @return Returns user target
	 */
	public GameUser getTarget() {
		return this.target;
	}
	
	/**
	 * getTargetUUID() 
	 * @return Returns the user's target's UUID
	 */
	public String getTargetUUID() {
		return this.target.getUUID();
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
	
}