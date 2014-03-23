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
	public String uuidString;
	
	@Persistent
	public GameData gamePlayData; //Data used for facial recognition
	@Persistent
	public ArrayList<GameUser> playerList;
	@Persistent
	public GameUser host; //player who "created" game
	
	@Persistent
	public boolean gameInProgress;
	
	
	
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
	public Game(GameUser host, ArrayList<GameUser> playerList) {
		UUID uuid = new UUID(System.nanoTime(), System.nanoTime());
		this.uuidString = uuid.toString();
		this.host = host;
		this.playerList = playerList;
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
	public ArrayList<ArrayList<GameUserImage>> getGameInitData() {
		ArrayList<ArrayList<GameUserImage>> data = 
				new ArrayList<ArrayList<GameUserImage>>();
		for (GameUser usr: playerList) {
			data.add(usr.getUsrImages());
		}
		return data;
	}
	
	/**
	 * setGamePlayData() 
	 * Sets the game data used for Facial Recognition
	 * @param gameData
	 */
	public void setGamePlayData(GameData gameData) {
		this.gamePlayData = gameData;
	}
	
	/**
	 * getGamePlayData() 
	 * Returns the game data used for facial recognition
	 * @return
	 */
	public GameData getGamePlayData() {
		return this.gamePlayData;
	}
	
	/**
	 * startGame() 
	 * Assigns user targets and sets indicator flag
	 */
	public void startGame() {
		this.gameInProgress = true;
		assignTargets();
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
	public GameUser killUser(GameUser assassin, GameUser victim) {
		assassin.addKill();
		victim.addDeath();
		assassin.setTarget(uuidString, victim.getTarget(uuidString)); //Assigns new target to assassin
		victim.removeTarget(uuidString);
		return assassin.getTarget(uuidString);
	}
	
	/**
	 * endGame() 
	 * Ends games and increments win count for winner
	 * @param winner
	 * @return 
	 */
	public void endGame(GameUser winner) {
		this.gameInProgress = false;
		winner.addWin();
	}
	
	
	//Private Helper Methods
	
	/**
	 * assignTargets() 
	 * Assigns user initial user targets and returns map
	 * @return
	 */
	private void assignTargets() {
		Collections.shuffle(playerList);
		int numPlayers = playerList.size();
		for (int i= 0; i < numPlayers; i ++) {
			playerList.get(i).setTarget(uuidString, playerList.get((i+1)%numPlayers));
		}
	}
}	