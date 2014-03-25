package BP.events.objects;

import BP.users.GameUserImage;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class GameCreated {
	
	@JsonProperty
	public ArrayList<ArrayList<GameUserImage>> usrImageCompilation;
	
	public GameCreated (ArrayList<ArrayList<GameUserImage>> a){
		this.usrImageCompilation = a;
	}
	
	public ArrayList<GameUserImage> getImages(int i) {
		return this.usrImageCompilation.get(i);
	}
	
	
}