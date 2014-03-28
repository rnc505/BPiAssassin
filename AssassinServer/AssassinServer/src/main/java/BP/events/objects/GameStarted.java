package BP.events.objects;

import java.util.ArrayList;
import java.util.HashMap;

import BP.events.PushNotificationInterface;

public class GameStarted implements PushNotificationInterface {
	
	
	private ArrayList<HashMap<String, String>> data;
	
	public GameStarted(ArrayList<HashMap<String, String>> a) {
		this.data = a;
	}
	
	public ArrayList<HashMap<String, String>>getData() {
		return this.data;
	}

	@Override
	public ArrayList<HashMap<String, String>> getNotificationObject() {
		return data;
	}

	@Override
	public String getActionIdentifier() {
		return "gameStarted";
	}

}