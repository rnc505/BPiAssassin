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
	
	private String victimCodeName;
	
	private ArrayList<HashMap<String, String>> victimAPN;
	
	@JsonIgnore
	private ArrayList<HashMap<String, String>> data;
	
	public UserKilled(ArrayList<HashMap<String, String>> usersAPN, 
			ArrayList<HashMap<String, String>> victimAPN, 
		   String victimCode_name, String targetUUID) {
		this.data = usersAPN;
		this.victimAPN = victimAPN;
		this.victimCodeName = victimCode_name;
		this.nextTarget = targetUUID;
	}
	
	public UserKilled(ArrayList<HashMap<String, String>> APNdata) {
		this.data = APNdata;
	}
	
	public UserKilled createUKObjectforVictim() {
		UserKilled victim = new UserKilled(this.victimAPN);
		return victim;
	}
	
	public String getNextTarget() {
		return nextTarget;
	}
	
	public String getVictimCodeName() {
		return this.victimCodeName;
	}

	@JsonIgnore
	@Override
	public ArrayList<HashMap<String, String>> getNotificationObject() {
		return data;
	}
	@JsonIgnore
	@Override
	public String getActionIdentifier() {
		return "userKilled";
	}
}
