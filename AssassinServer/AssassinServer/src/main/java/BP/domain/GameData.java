package BP.domain;

import java.util.UUID;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.appengine.api.datastore.Text;

@JsonAutoDetect
@PersistenceCapable
public class GameData {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private String uuidString;
	
	@JsonProperty
	@Persistent
	private Text meanImage;
	
	@JsonProperty
	@Persistent
	private Text covarEigen;
	
	@JsonProperty
	@Persistent
	private Text workFunctEigen;
	
	@JsonProperty
	@Persistent
	private Text projectedImages;
	
	public GameData(String meanImage, String covarEigen, String workFunctEigen,
			String projectedImages) {
		UUID uuid = new UUID(System.nanoTime(), System.nanoTime());
		this.uuidString = uuid.toString();
		this.meanImage = new Text(meanImage);
		this.covarEigen = new Text(covarEigen);
		this.workFunctEigen = new Text(workFunctEigen);
		this.projectedImages = new Text(projectedImages);
	}
	
	public String getUUID() {
		return this.uuidString;
	}
	
	public Text getMeanImage() {
		return this.meanImage;
	}
	
	public Text getCovarEigen() {
		return this.covarEigen;
	}
	
	public Text getWorkFunctEigen() {
		return this.workFunctEigen;
	}
	
	public Text getProjectedImages() {
		return this.projectedImages;
	}
	
}