package BP.users;

import java.util.UUID;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.fasterxml.jackson.annotation.*;
import com.google.appengine.api.datastore.Text;

@JsonAutoDetect
@PersistenceCapable
public class GameUserImage {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private String uuidString;
	
	@JsonProperty
	@Persistent
	private Text image;
	
	public GameUserImage(String base64Image) {
		UUID uuid = new UUID(System.nanoTime(), System.nanoTime());
		this.uuidString = uuid.toString();
		this.image = new Text(base64Image);
	}
	
	public String getUUID() {
		return this.uuidString;
	}
	
	public Text getImage() {
		return this.image;
	}
	
}
