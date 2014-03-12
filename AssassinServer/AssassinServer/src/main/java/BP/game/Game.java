package BP.game;

import BP.users.*;
import BP.repository.GameDataStorage;
import BP.domain.GameData;
import java.util.HashMap;
import java.util.Collections;
import java.util.ArrayList;


public class Game {

	public GameDataStorage storage = new GameDataStorage();
	
	public GameData gamePlayData; //Data used for facial recognition
	public ArrayList<String> playerList;
	public String hostHash; //player who "created" game
	
	public boolean gameInProgress;
	
	/**
	 * Constructor 
	 * 
	 * @param host
	 * @param playerList
	 */
	public Game(String host, ArrayList<String> playerList) {
		this.hostHash = host;
		this.playerList = playerList;
		this.gameInProgress = false;
		storage.createGame(host, playerList);
		//Ensures all game users information is up-to-date for Game Set Up
		for (String a: playerList) {
			storage.getUser(a).refreshUserData();
		}
	}
	
	/**
	 * getGameInitData() 
	 * Returns an Array of an Array containing the 
	 * ImageData of the game users.
	 * @return
	 */
	public ArrayList<ArrayList<GameUserImage>> getGameInitData() {
		if (gameInProgress)
			throw new RuntimeException(
					"Cannot collect Init Data. Game in Progress.");
		
		ArrayList<ArrayList<GameUserImage>> data = 
				new ArrayList<ArrayList<GameUserImage>>();
		int numUsers = this.playerList.size();
		for (int i =0; i < numUsers; i++) {
			data.add(storage.getUser(playerList.get(i)).getUsrImages());
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
	 * Assigns user targets and returns a
	 * map of user targets
	 */
	public HashMap<GameUser, GameUser> startGame() {
		if (gameInProgress)
			throw new RuntimeException("Game is already in Progress.");
		this.gameInProgress = true;
		return assignTargets();
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
		assassin.addDeath();
		victim.addDeath();
		assassin.setTarget(victim.getTarget()); //Assigns new target to assassin
		victim.setTarget(null);
		return victim.getTarget();
	}
	
	/**
	 * endGame() 
	 * Ends games and increments win count for winner
	 * @param winner
	 * @return 
	 */
	public void endGame(GameUser winner) {
		if (!gameInProgress)
			throw new RuntimeException("Game has not been started yet.");
		this.gameInProgress = false;
		winner.addWin();
	}
	
	
	//Private Helper Methods
	
	/**
	 * assignTargets() 
	 * Assigns user initial user targets and returns map
	 * @return
	 */
	private HashMap<GameUser, GameUser> assignTargets() {
		Collections.shuffle(playerList);
		int numPlayers = playerList.size();
		HashMap<GameUser,GameUser> map = new HashMap<GameUser, GameUser>(numPlayers);
		for (int i =0; i < numPlayers; i++) {
			map.put(storage.getUser(playerList.get(i)), 
					storage.getUser(playerList.get((i+1)%numPlayers)));
		}
		return map;
	}
}	