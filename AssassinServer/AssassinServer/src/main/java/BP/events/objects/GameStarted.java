package BP.events.objects;

import java.util.ArrayList;
import java.util.HashMap;

public class GameStarted {
	
	
	private ArrayList<HashMap<String, String>> data;
	
	public GameStarted(ArrayList<HashMap<String, String>> a) {
		this.data = a;
	}
	
	public ArrayList<HashMap<String, String>>getData() {
		return this.data;
	}

}