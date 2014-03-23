package BP.events;

import java.util.ArrayList;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import BP.users.GameUser;
import BP.users.GameUserImage;
import BP.domain.GameData;
import BP.events.objects.GameCreated;
import BP.events.objects.GameStarted;
import BP.events.objects.GameEnded;
import BP.game.Game;
import BP.repository.GameDataStorage;


public class GameManager implements GameManagerInterface {
	
	public static final GameDataStorage storage = new GameDataStorage();
	
	/**
	 * Constructor 
	 */
	public GameManager() {
	}

	//User Management
	public String RegisterUser(String code_name, GameUserImage thumbnail, 
			ArrayList<GameUserImage> faceImages, String apn, String platformID) {
		GameUser g = new GameUser(code_name, thumbnail, faceImages);
		g.setAPN(apn);
		g.setPlatformID(platformID);
		
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(g);
		} finally {
			pm.close();
		}
		return g.uuidString;
	}
	
	public void deleteUser(String uuid) {
		PersistenceManager pm = getPersistenceManager();
		try {
			GameUser GameUserToDelete = pm.getObjectById(GameUser.class, uuid);
			pm.deletePersistent(GameUserToDelete);
		} finally {
			pm.close();
		}
	}
	
	//Game Management
	public GameCreated createGame(String hostUUID, ArrayList<String> playerUUIDs) {
		PersistenceManager pm = getPersistenceManager();
		ArrayList<ArrayList<GameUserImage>> faceImages = 
				new ArrayList<ArrayList<GameUserImage>>();
		try {
			GameUser host = pm.getObjectById(GameUser.class, hostUUID);
			ArrayList<GameUser> players = new ArrayList<GameUser>();
			GameUser player;
			for (String a: playerUUIDs) {
				player = pm.getObjectById(GameUser.class, a);
				players.add(player);
				faceImages.add(player.getUsrImages());
			}
			Game g = new Game(host, players);
			pm.makePersistent(g);
		} finally {
			pm.close();
		}
		GameCreated retObject = new GameCreated(faceImages);
		return retObject;
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
	
	//Returns an instance of the PersistenceManager
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
		


}