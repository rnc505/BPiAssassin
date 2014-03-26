package BP.events.objects;

import java.util.ArrayList;
import java.util.HashMap;

import BP.events.PushNotificationInterface;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class UserKilled implements PushNotificationInterface {
	
	@JsonProperty
	private String nextTarget;
	
	@JsonIgnore
	private ArrayList<HashMap<String, String>> data;
	
	public UserKilled() {
		
	}
	
	public UserKilled getEveryoneExceptAssassinAndVictim(String assassinId, String victimId) {
		// create new user killed object
		// fill in data with everyones APNS except assassin and victim
		// set nextTarget to the NAME/Codename of the victim
		return null;
	}
	
	public UserKilled getVictim(String victimId) {
		// create new user killed object
		// fill in data with ONLY victims APN
		// don't need to set nextTarget
		return null;
	}
	
	public UserKilled(String nxt) {
		this.nextTarget = nxt;
	}
	
	public String getNextTarget() {
		return nextTarget;
	}

	@Override
	public ArrayList<HashMap<String, String>> getNotificationObject() {
		// TODO Auto-generated method stub
		return data;
	}

	@Override
	public String getActionIdentifier() {
		// TODO Auto-generated method stub
		return "userKilled";
	}
}
