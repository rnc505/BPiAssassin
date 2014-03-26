package BP.events;

import java.util.ArrayList;
import java.util.HashMap;

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
import BP.events.objects.UserKilled;
import BP.game.Game;


public class GameManager implements GameManagerInterface {
	
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
		return g.getUUID();
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
		String gameUUID;
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
			gameUUID = g.getUUID();
			pm.makePersistent(g);
		} finally {
			pm.close();
		}
		GameCreated retObject = new GameCreated(faceImages, gameUUID);
		return retObject;
	}
	
	public GameStarted startGame(String gameUUID, GameData data) {
		PersistenceManager pm = getPersistenceManager();
		GameStarted retObject;
		ArrayList<HashMap<String, String>> array =
				new ArrayList<HashMap<String, String>>();
		try {
			Game g = pm.getObjectById(Game.class, gameUUID);
			g.setGamePlayData(data);
			g.startGame();
			String hostUUID = g.getHost().getUUID();
			for (GameUser a: g.getPlayerList()) {
				if (a.getUUID() != hostUUID) {
					HashMap<String, String> entry = 
							new HashMap<String, String>();
					entry.put("apn", a.getAPN());
					entry.put("platformId", a.getPlatformID());
					array.add(entry);
				}
			}
		} finally {
			pm.close();
		}
		retObject = new GameStarted(array);
		return retObject;
	}

	public GameData getGamePlayData(String gameUUID) {
		PersistenceManager pm = getPersistenceManager();
		GameData retObject;
		try {
			Game g = pm.getObjectById(Game.class, gameUUID);
			retObject = g.getGamePlayData();
		} finally {
			pm.close();
		}
		return retObject;
	}

	public GameStarted restartGame(String gameUUID) {
		PersistenceManager pm = getPersistenceManager();
		GameStarted retObject;
		ArrayList<HashMap<String, String>> array =
				new ArrayList<HashMap<String, String>>();
		try {
			Game g = pm.getObjectById(Game.class, gameUUID);
			g.startGame();
			String hostUUID = g.getHost().getUUID();
			for (GameUser a: g.getPlayerList()) {
				if (a.getUUID() != hostUUID) {
					HashMap<String, String> entry = 
							new HashMap<String, String>();
					entry.put("apn", a.getAPN());
					entry.put("platformId", a.getPlatformID());
					array.add(entry);
				}
			}
		} finally {
			pm.close();
		}
		retObject = new GameStarted(array);
		return retObject;
	}
	
	//Game Play
	public String getTarget(String gameUUID, String userUUID) {
		PersistenceManager pm = getPersistenceManager();
		String retVal;
		try {
			GameUser a = pm.getObjectById(GameUser.class, userUUID);
			retVal = a.getTarget(gameUUID).getUUID();
		} finally {
			pm.close();
		}
		return retVal;
	}
	
	public UserKilled killUser(String gameUUID, String assassinUUID, String victimUUID) {
		PersistenceManager pm = getPersistenceManager();
		ArrayList<HashMap<String, String>> victimAPN = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> otherAPN = new ArrayList<HashMap<String, String>>();
		String victimCodeName;
		String nextTargetUUID;
		try {
			Game g = pm.getObjectById(Game.class, gameUUID);
			GameUser assassin = pm.getObjectById(GameUser.class, assassinUUID);
			GameUser victim = pm.getObjectById(GameUser.class, victimUUID);
			GameUser nextTarget = g.killUser(assassin, victim);
			victimCodeName = victim.getUserCodeName();
			nextTargetUUID = nextTarget.getUUID();
			for (GameUser a: g.getPlayerList()) {
				HashMap<String, String> entry = new HashMap<String, String>();
				entry.put("apn",a.getAPN());
				entry.put("platformId", a.getPlatformID());
				if (a.getUUID() == victimUUID) {
					victimAPN.add(entry);
				} else if (a.getUUID() != assassinUUID) {
					otherAPN.add(entry);
				}
			}
		} finally {
			pm.close();
		}
		UserKilled retObject = new UserKilled(otherAPN, victimAPN, 
				victimCodeName, nextTargetUUID);
		return retObject;
	}
	
	public GameEnded endGame(String gameUUID, String winnerUUID) {
		PersistenceManager pm = getPersistenceManager();
		GameEnded retObject;
		ArrayList<HashMap<String, String>> array =
				new ArrayList<HashMap<String, String>>();
		String winnerCode_Name;
		try {
			Game g = pm.getObjectById(Game.class, gameUUID);
			GameUser winner = pm.getObjectById(GameUser.class, winnerUUID);
			winnerCode_Name = winner.getUserCodeName();
			g.endGame(winner);
			for (GameUser a: g.getPlayerList()) {
				if (a.getUUID() != winnerUUID) {
					HashMap<String, String> entry = 
							new HashMap<String, String>();
					entry.put("apn", a.getAPN());
					entry.put("platformId", a.getPlatformID());
					array.add(entry);
				}
			}
		} finally {
			pm.close();
		}
		retObject = new GameEnded(array, winnerCode_Name);
		return retObject;
	}
	
	//Returns an instance of the PersistenceManager
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional")
				.getPersistenceManager();
	}
	
		


}