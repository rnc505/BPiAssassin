package BP.users;

import com.fasterxml.jackson.annotation.*;
@JsonAutoDetect
public class GameUserImage {

	@JsonProperty
	String image;
	
	public GameUserImage(String base64Image) {
		this.image = base64Image;
	}
	
}
