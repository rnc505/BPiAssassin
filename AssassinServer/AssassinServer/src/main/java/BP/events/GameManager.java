package BP.events;

import java.util.ArrayList;

import BP.domain.GameData;
import BP.events.objects.GameCreated;
import BP.events.objects.GameStarted;
import BP.events.objects.GameEnded;
import BP.users.GameUserImage;

public class GameManager implements GameManagerInterface {
	
	/**
	 * Constructor 
	 */
	public GameManager() {
		
	}

	//User Management
	public String RegisterUser(String code_name, GameUserImage thumbnail, 
			ArrayList<GameUserImage> faceImages, String apn, String platformID) {
		String a = new String();
		return a;
	}
	public void deleteUser(String uuid) {
	
	}
	
	//Game Management
	public GameCreated createGame(String hostUUID, ArrayList<String> playerUUIDs) {
		GameCreated a = new GameCreated();
		return a;
	}
	public GameStarted startGame(String gameUUID, GameData data) {
		GameStarted a = new GameStarted();
		return a;
	}
	public GameData getGamePlayData(String gameUUID) {
		GameData a = new GameData();
		return a;
	}
	public GameStarted restartGame(String gameUUID) {
		GameStarted a = new GameStarted();
		return a;
	}
	
	//Game Play
	public String getTarget(String gameUUID, String userUUID) {
		String a = new String();
		return a;
	}
	public String killUser(String gameUUID, String assassinUUID, String victimUUID) {
		String a = new String();
		return a;
	}
	public GameEnded endGame(String gameUUID) {
		GameEnded a = new GameEnded();
		return a;
	}


}