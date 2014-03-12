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
		return new String();
	}
	public void deleteUser(String uuid) {
	
	}
	
	//Game Management
	public GameCreated createGame(String hostUUID, ArrayList<String> playerUUIDs) {
		return new GameCreated();
	}
	public GameStarted startGame(String gameUUID, GameData data) {
		return new GameStarted();
	}
	public GameData getGamePlayData(String gameUUID) {
		return new GameData();
	}
	public GameStarted restartGame(String gameUUID) {
		return new GameStarted();
	}
	
	//Game Play
	public String getTarget(String gameUUID, String userUUID) {
		return new String();
	}
	public String killUser(String gameUUID, String assassinUUID, String victimUUID) {
		return new String();
	}
	public GameEnded endGame(String gameUUID) {
		return new GameEnded();
	}


}