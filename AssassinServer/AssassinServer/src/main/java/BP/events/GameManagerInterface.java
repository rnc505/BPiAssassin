package BP.events;

import BP.domain.GameData;
import BP.users.GameUserImage;
import BP.events.objects.GameCreated;
import BP.events.objects.GameStarted;
import BP.events.objects.GameEnded;
import BP.events.objects.TargetInfo;
import BP.events.objects.UserKilled;

import java.util.ArrayList;
import java.util.HashMap;

public interface GameManagerInterface {
	
	
	//User Management
	String RegisterUser(String uuid, String code_name, GameUserImage thumbnail, 
			ArrayList<GameUserImage> faceImages, String apn, String platformID);
	void deleteUser(String uuid);
	public HashMap<String, String> GetUserStatus(String userId);
	
	//Game Management
	GameCreated createGame(String hostUUID, ArrayList<String> playerUUIDs);
	GameStarted startGame(String gameUUID, GameData data);
	GameData getGamePlayData(String gameUUID);
	//GameStarted restartGame(String gameUUID);
	
	//Game Play
	//Do we also want a getTargetThumbnail method?
	TargetInfo getTarget(String gameUUID, String userUUID);
	UserKilled killUser(String gameUUID, String assassinUUID, String victimUUID);
	GameEnded endGame(String gameUUID, String winnerUUID);
	
	
	
	//GameEnded contains:
	//1. APN information -- Tell users who won the game
	
	//GameStarted contains:
	//1. APN information - tell users to call "getGamePlayData"
	//
	
	//GameCreated contains:
	//1. ArrayList of ArrayList of UserImages
	//  This is returned to the host(requester)
	
	
	/*
	 *  Design Idea
	 * for GameStarted & GameEnded
	 *
	 *  Each has an ArrayList of Maps (for push notification information)
	 *  Each element in array represents a user
	 *  Each map contains 2 keys: APN and PlatformID
	 *  
	 */
	
		
}