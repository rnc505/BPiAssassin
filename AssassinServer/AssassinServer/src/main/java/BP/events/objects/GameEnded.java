package BP.events.objects;

import java.util.ArrayList;
import java.util.HashMap;

public class GameEnded {
	
	
	private ArrayList<HashMap<String, String>> data;
	
	public GameEnded(ArrayList<HashMap<String, String>> a) {
		this.data = a;
	}
	
	public ArrayList<HashMap<String, String>>getData() {
		return this.data;
	}

}