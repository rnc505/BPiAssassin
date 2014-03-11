package BP.repository;

import java.util.ArrayList;
import java.util.List;
import BP.users.*;

import com.google.appengine.api.datastore.*;


public class GameDataStorage {

	public DatastoreService datastore;
	public Entity entity;
	public Key key;
	
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
	 * create() 
	 * Creates a DataStore entity for the new user
	 * @param user
	 * @return The UUID of the stored user
	 */
	public long create(GameUser user) {
		entity = new Entity("User");
		entity.setProperty("hash", user.getHash());
		entity.setProperty("userID", user.getUserID());
		entity.setProperty("numKills", user.getNumKills());
		entity.setProperty("numDeaths", user.getNumDeaths());
		entity.setProperty("numWins", user.getNumWins());;
		datastore.put(entity);
		return entity.getKey().getId();
	}
	
	public void update(GameUser user, String userID, String propertyName) {
		
	}
	
	public void update(GameUser user, GameUser target, String propertyName) {
		
	}
	
	public void update(GameUser user, int value, String propertyName) {
		
	}
	
	
}
