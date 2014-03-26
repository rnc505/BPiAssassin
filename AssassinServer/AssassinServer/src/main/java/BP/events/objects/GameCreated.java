package BP.events.objects;

import BP.users.GameUserImage;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class GameCreated {
	
	@JsonProperty
	public ArrayList<ArrayList<GameUserImage>> usrImageCompilation;
	
	@JsonProperty
	public String gameUUID;
	
	public GameCreated (ArrayList<ArrayList<GameUserImage>> a, String gameUUID){
		this.usrImageCompilation = a;
		this.gameUUID = gameUUID;
	}
	
	public ArrayList<GameUserImage> getImages(int i) {
		return this.usrImageCompilation.get(i);
	}
	
	public String getGameUUID() {
		return this.gameUUID;
	}
	
	
}