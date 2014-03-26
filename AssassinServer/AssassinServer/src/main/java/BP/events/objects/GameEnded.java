package BP.events.objects;

import java.util.ArrayList;
import java.util.HashMap;

import BP.events.PushNotificationInterface;

public class GameEnded implements PushNotificationInterface {
	
	
	private ArrayList<HashMap<String, String>> data;
	
	private String winnerCode_Name;
	
	/**
	 * 
	 * @param a
	 * @param code_name
	 */
	public GameEnded(ArrayList<HashMap<String, String>> a, String code_name) {
		this.data = a;
		this.winnerCode_Name = code_name;
	}
	
	public ArrayList<HashMap<String, String>>getData() {
		return this.data;
	}

	public String getWinner() {
		return this.winnerCode_Name;
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