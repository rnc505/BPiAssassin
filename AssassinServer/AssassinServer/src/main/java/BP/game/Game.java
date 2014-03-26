package BP.game;

import BP.users.*;
import BP.domain.GameData;
import java.util.Collections;
import java.util.ArrayList;
import java.util.UUID;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Game {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String uuidString;
	
	@Persistent
	private String gameDataUUID; //Data used for facial recognition
	@Persistent
	private ArrayList<String> playerUUIDList;
	@Persistent
	private String hostUUID; //player who "created" game
	
	@Persistent
	private boolean gameInProgress;
	
	
	
	/**
	 * JDO Constructor 
	 * no-args constructor for use by JDO
	 */
	public Game() {
	}
	
	/**
	 * Constructor 
	 * 
	 * @param uuid
	 * @param host
	 * @param playerList
	 */
	public Game(String hostUUID, ArrayList<String> playerUUIDs) {
		UUID uuid = new UUID(System.nanoTime(), System.nanoTime());
		this.uuidString = uuid.toString();
		this.hostUUID = hostUUID;
		this.playerUUIDList = playerUUIDs;
		this.gameDataUUID = null;
		this.gameInProgress = false;
	}
	
	public String getUUID() {
		return this.uuidString;
	}
	

	/**
	 * getGameInitData() 
	 * Returns an Array of an Array containing the 
	 * ImageData of the game users.
	 * @return
	 */
	/*public ArrayList<ArrayList<String>> getGameInitData() {
		ArrayList<ArrayList<String>> data = 
				new ArrayList<ArrayList<String>>();
		for (GameUser usr: playerUUIDList) {
			data.add(usr.getUsrImages());
		}
		return data;
	}
	*/
	
	/**
	 * setGamePlayData() 
	 * Sets the game data used for Facial Recognition
	 * @param gameData
	 */
	public void setGamePlayDataUUID(String gameDataUUID) {
		this.gameDataUUID = gameDataUUID;
	}
	
	/**
	 * getGamePlayData() 
	 * Returns the game data used for facial recognition
	 * @return
	 */
	public String getGamePlayDataUUID() {
		return this.gameDataUUID;
	}
	
	/**
	 * startGame() 
	 * Assigns user targets and sets indicator flag
	 */
	public void startGame() {
		this.gameInProgress = true;
	}
	
	/**
	 * killUser() 
	 * Increments deaths and kills for assassin and victim respectively.
	 * Assigns the assassin a new target (the victim's target).
	 * Returns the Assassin's new target.
	 * 
	 * @param assassin
	 * @param victim
	 * @return
	 */
	/*public GameUser killUser(GameUser assassin, GameUser victim) {
		assassin.addKill();
		victim.addDeath();
		assassin.setTarget(uuidString, victim.getTarget(uuidString)); //Assigns new target to assassin
		victim.removeTarget(uuidString);
		return assassin.getTarget(uuidString);
	}*/
	
	/**
	 * endGame() 
	 * Ends games and increments win count for winner
	 * @param winner 
	 */
	public void endGame(GameUser winner) {
		this.gameInProgress = false;
		//winner.addWin();
	}
	
	public ArrayList<String> getPlayerUUIDs() {
		return this.playerUUIDList;
	}
	
	public String getHostUUID() {
		return this.hostUUID;
	}
	
	
	//Private Helper Methods
	
	/**
	 * assignTargets() 
	 * Assigns user initial user targets and returns map
	 * @return
	 */
	/*private void assignTargets() {
		Collections.shuffle(playerList);
		int numPlayers = playerList.size();
		for (int i= 0; i < numPlayers; i ++) {
			playerList.get(i).setTarget(uuidString, playerList.get((i+1)%numPlayers));
		}
	}*/
}	