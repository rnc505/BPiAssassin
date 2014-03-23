package BP.events.objects;

import BP.users.GameUserImage;
import java.util.ArrayList;

public class GameCreated {
	
	
	public ArrayList<ArrayList<GameUserImage>> usrImageCompilation;
	
	public GameCreated (ArrayList<ArrayList<GameUserImage>> a){
		this.usrImageCompilation = a;
	}
	
	public ArrayList<GameUserImage> getImages(int i) {
		return this.usrImageCompilation.get(i);
	}
	
	
}