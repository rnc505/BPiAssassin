package BP.events;

import BP.domain.GameData;
import BP.users.GameUserImage;
import BP.events.objects.GameCreated;
import BP.events.objects.GameStarted;
import BP.events.objects.GameEnded;

import java.util.ArrayList;

public interface GameManagerInterface {
	
	
	//User Management
	String RegisterUser(String code_name, GameUserImage thumbnail, 
			ArrayList<GameUserImage> faceImages, String apn, String platformID);
	void deleteUser(String uuid);
	
	//Game Management
	GameCreated createGame(String hostUUID, ArrayList<String> playerUUIDs);
	GameStarted startGame(String gameUUID, GameData data);
	GameData getGamePlayData(String gameUUID);
	GameStarted restartGame(String gameUUID);
	
	//Game Play
	String getTarget(String gameUUID, String userUUID);
	String killUser(String gameUUID, String assassinUUID, String victimUUID);
	GameEnded endGame(String gameUUID);
		
}