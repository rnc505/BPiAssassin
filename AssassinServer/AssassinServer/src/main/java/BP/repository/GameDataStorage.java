package BP.repository;

import java.util.ArrayList;
import java.util.List;
import BP.users.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


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
	
	public int create(GameUser user) {
		entity = new Entity("User");
		entity.setProperty("userID", user.getUserID());
		entity.setProperty("numKills", user.getNumKills());
		entity.setProperty("numDeaths", user.getNumDeaths());
		entity.setProperty("numWins", user.getNumWins());
		entity.setProperty("target", user.getTarget().getUUID());
		return 0;
	}
	
	
}
