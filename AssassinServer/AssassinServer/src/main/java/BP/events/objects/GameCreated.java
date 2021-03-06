package BP.events.objects;

import BP.users.GameUserImage;

import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class GameCreated {
	
	@JsonProperty
	public HashMap<String,ArrayList<GameUserImage>> usrImageCompilation;
	
	@JsonProperty
	public String gameUUID;
	
	public GameCreated (HashMap<String,ArrayList<GameUserImage>> a, String gameUUID){
		this.usrImageCompilation = a;
		this.gameUUID = gameUUID;
	}
	
	public ArrayList<GameUserImage> getUserImages(String userUUID) {
		return this.usrImageCompilation.get(userUUID);
	}
	
	public String getGameUUID() {
		return this.gameUUID;
	}
	
	
}