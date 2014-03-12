package BP.repository;

import java.util.ArrayList;
import java.util.List;
import BP.users.*;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.*;


public class GameDataStorage {

	public DatastoreService datastore;
	public Entity entity;
	
	/**
	 * Constructor 
	 */
	public GameDataStorage(){	
		datastore = DatastoreServiceFactory.getDatastoreService();
	}
	
	//***************************
	//Methods used by GameUsers
	//***************************
	
	/**
	 * create(GameUser) 
	 * Creates a DataStore entity for the new user
	 * @param user
	 * @return The UUID of the stored user
	 */
	public void createUser(GameUser user) {
		entity = new Entity("User");
		updateUserAll(user, entity);
		datastore.put(entity);
	}
	
	/**
	 * <T> updateUser(GameUser, String, T) 
	 * Updates the value of user's propertyName with newValue
	 * @param user
	 * @param propertyName
	 * @param newValue
	 */
	public <T> void updateUser(GameUser user, String propertyName, T newValue) {
		updateEntity(getUserEntity(user), propertyName, newValue);
	}
	
	//This method might not be necessary??
	/**
	 * updateUserAll(GameUser) 
	 * Updates the user's corresponding entity values
	 * @param user
	 */
	public void updateUserAll(GameUser user) {
		entity = getUserEntity(user);
		updateUserAll(user, entity);
	}
	
	/**
	 * getProperty(GameUser, String) 
	 * Returns an Object of the property
	 * @param user
	 * @param propertyName
	 * @return
	 */
	public Object getProperty(GameUser user, String propertyName) {
		entity = getUserEntity(user);
		return entity.getProperty(propertyName);
	}
	
	/**
	 * getUser(String) 
	 * Returns the GameUser associated with hash that
	 * is stored in remote memory
	 * @param hash
	 * @return
	 */
	public GameUser getUser(String hash) {
		entity = getUserEntity("hash", hash);
		String userID = (String) entity.getProperty("userID");
		GameUserImage thumbnail= 
				new GameUserImage((Blob) entity.getProperty("thumbnail")); 
		GameUserImage usrImage1 = 
				new GameUserImage((Blob) entity.getProperty("usrImage1"));
		GameUserImage usrImage2 = 
				new GameUserImage((Blob) entity.getProperty("usrImage2"));
		GameUserImage usrImage3 = 
				new GameUserImage((Blob) entity.getProperty("usrImage3"));
		GameUserImage usrImage4 = 
				new GameUserImage((Blob) entity.getProperty("usrImage4"));
		String target = (String) entity.getProperty("target");
		int numKills = (int) entity.getProperty("numKills");
		int numDeaths = (int) entity.getProperty("numDeaths");
		int numWins = (int) entity.getProperty("numWins");
		
		GameUser a=  new GameUser(hash, userID, thumbnail, usrImage1, 
				usrImage2, usrImage3, usrImage4, target, numKills, numDeaths, numWins, 
				false);
		return a;
	}
	
	
	
	//***************************
	//Methods used by Game
	//***************************
		
	/**
	 * createGame(String, ArrayList<String>) 
	 * Creates and Entity for the Game Object 
	 * and store's its information in remote storage
	 * @param host
	 * @param playerList
	 */
	public void createGame(String host, ArrayList<String> playerList) {
		entity = new Entity("Game");
		entity.setProperty("host", host);
		entity.setProperty("playerList", playerList);
		entity.setProperty("gameBoolean", false);
		datastore.put(entity);
	}
	
	
	
	//************************
	//Private Helper Methods
	//************************
	
	/**
	 * <T> getUserEntity(String, T)  
	 * Finds the datastore entity that has the given property value
	 * @param propertyName
	 * @param value
	 * @return
	 */
 	private <T> Entity getUserEntity(String propertyName, T value) {
		Filter f =
				  new FilterPredicate(propertyName,
				                      FilterOperator.EQUAL,
				                      value);
		Query q = new Query("User").setFilter(f);
		PreparedQuery pq = datastore.prepare(q);
		return pq.asSingleEntity();
	}
	
	
 	/**
	 * getUserEntity(GameUser) 
	 * Returns the datastore entity for given GameUser
	 * @param user
	 * @return
	 */
	private Entity getUserEntity(GameUser user) {
		return getUserEntity("hash", user.getHash());
	}
	
	/**
	 * <T> updateEntity(Entity, String, T) 
	 * Updates the value of an entity's property with newValue
	 * @param user
	 * @param propertyName
	 * @param newValue
	 */
	private <T> void updateEntity(Entity e, String propertyName, T newValue) {
		e.setProperty(propertyName, newValue);
	}

	/**
	 * updateUserAll(GameUser, Entity)
	 * Updates the entity properties with all the current
	 * values of the user
	 * @param user
	 * @param entity
	 */
	private void updateUserAll(GameUser user, Entity entity) {
		entity.setProperty("hash", user.getHash());
		entity.setProperty("userID", user.getUserID());
		entity.setProperty("numKills", user.getNumKills());
		entity.setProperty("numDeaths", user.getNumDeaths());
		entity.setProperty("numWins", user.getNumWins());;
		entity.setProperty("target", user.getTarget().getHash());
		//Stores Image Information
		entity.setProperty("thumbnail", user.getThumbnail().toBlob());
		entity.setProperty("usrImage1", user.getUsrImages().get(0).toBlob());
		entity.setProperty("usrImage2", user.getUsrImages().get(1).toBlob());
		entity.setProperty("usrImage3", user.getUsrImages().get(2).toBlob());
		entity.setProperty("usrImage4", user.getUsrImages().get(3).toBlob());
	}

}
