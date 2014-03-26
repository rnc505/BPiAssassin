package BP.users;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.fasterxml.jackson.annotation.*;
@JsonAutoDetect
@PersistenceCapable
public class GameUserImage {

	@JsonProperty
	@Persistent
	private String image;
	
	public GameUserImage(String base64Image) {
		this.image = base64Image;
	}
	
}
