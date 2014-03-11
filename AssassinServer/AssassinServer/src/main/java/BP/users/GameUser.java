package BP.users;

import java.util.ArrayList;

public class GameUser {

	public final int NUM_IMAGES = 4;
	
	public int UUID;
	public String userID;

	//GameUserImage Information	
	public GameUserImage thumbnail;
	public GameUserImage usrImage1;
	public GameUserImage usrImage2;
	public GameUserImage usrImage3;
	public GameUserImage usrImage4;
	
	public GameUser usrTarget;
	public int numKills;
	public int numDeaths;
	public int numWins;

	
	/**
	 * Constructor 
	 *
	 * @param userID
	 * @param thumbnail
	 * @param usrImage1
	 * @param usrImage2
	 * @param usrImage3
	 * @param usrImage4
	 */
	public GameUser(String userID, GameUserImage thumbnail, 
			GameUserImage usrImage1,GameUserImage usrImage2, 
			GameUserImage usrImage3, GameUserImage usrImage4) {
		this.userID = userID;
		this.thumbnail = thumbnail;
		this.usrImage1 = usrImage1;
		this.usrImage2 = usrImage2;
		this.usrImage3 = usrImage3;
		this.usrImage4 = usrImage4;
		this.numKills = 0;
		this.numDeaths = 0;
		this.numWins = 0;
}
	
	/**
	 * setTarget() 
	 * Sets user target
	 * @param target
	 */
	public void setTarget(GameUser target) {
		this.usrTarget = target;
	}
	
	/**
	 * getTarget() 
	 * Returns user target
	 * @return 
	 */
	public GameUser getTarget() {
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
		return ++this.numKills;
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
	
	public int getUUID() {
		return this.UUID;
	}
}