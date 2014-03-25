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

	@Override
	public ArrayList<HashMap<String, String>> getNotificationObject() {
		// TODO Auto-generated method stub
		return data;
	}

}