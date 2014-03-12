package BP.events;

import BP.users.GameUserImage;

import java.util.ArrayList;

public interface GameManagerInterface {
	
	/*
	 * [UUID] RegisterUser(userID, Images, thumbnail, APN, PLatformId)
	 * deleteUser(UUID)
	 * 
	 * [ArrayList] createGame(hostUUID, playerUUIDs) 
	 * 
	 *  
	 */
	
	//User Management
	String RegisterUser(String userId, GameUserImage thumbnail, 
			ArrayList<GameUserImage> faceImages, String apn, String platformID);
	
	void deleteUser(String uuid);
	
	
	//Game Management
	String createGame(String hostUUID, ArrayList<String> playerUUIDs);
	
	
	
	//Game Play
}