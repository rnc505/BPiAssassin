package BP.users;

import java.util.UUID;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.fasterxml.jackson.annotation.*;

@JsonAutoDetect
@PersistenceCapable
public class GameUserImage {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private String uuidString;
	
	@JsonProperty
	@Persistent
	private String image;
	
	public GameUserImage(String base64Image) {
		UUID uuid = new UUID(System.nanoTime(), System.nanoTime());
		this.uuidString = uuid.toString();
		this.image = base64Image;
	}
	
	public String getUUID() {
		return this.uuidString;
	}
	
	public String getImage() {
		return this.image;
	}
	
}
