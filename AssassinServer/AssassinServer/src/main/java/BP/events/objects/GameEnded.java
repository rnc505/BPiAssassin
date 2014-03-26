package BP.events.objects;

import java.util.ArrayList;
import java.util.HashMap;

import BP.events.PushNotificationInterface;

public class GameEnded implements PushNotificationInterface {
	
	
	private ArrayList<HashMap<String, String>> data;
	
	public GameEnded(ArrayList<HashMap<String, String>> a) {
		this.data = a;
	}
	
	public ArrayList<HashMap<String, String>>getData() {
		return this.data;
	}

	public String getWinner(String winnersId) {
		// need to get Name/Codename from Winners Id
		return null;
	}
	
	@Override
	public ArrayList<HashMap<String, String>> getNotificationObject() {
		return data;
	}

	@Override
	public String getActionIdentifier() {
		return "gameEnded";
	}

}