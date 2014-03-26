package BP.users;

import javax.jdo.annotations.Extension;
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
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String uuidString;
	
	@JsonProperty
	@Persistent
	private String image;
	
	public GameUserImage(String base64Image) {
		this.image = base64Image;
	}
	
}
