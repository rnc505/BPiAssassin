package BP.domain;

import java.util.UUID;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
@PersistenceCapable
public class GameData {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private String uuidString;
	
	@JsonProperty
	@Persistent
	private String meanImage;
	
	@JsonProperty
	@Persistent
	private String covarEigen;
	
	@JsonProperty
	@Persistent
	private String workFunctEigen;
	
	@JsonProperty
	@Persistent
	private String projectedImages;
	
	public GameData(String meanImage, String covarEigen, String workFunctEigen,
			String projectedImages) {
		UUID uuid = new UUID(System.nanoTime(), System.nanoTime());
		this.uuidString = uuid.toString();
		this.meanImage = meanImage;
		this.covarEigen = covarEigen;
		this.workFunctEigen = workFunctEigen;
		this.projectedImages = projectedImages;
	}
	
	public String getUUID() {
		return this.uuidString;
	}
	
	public String getMeanImage() {
		return this.meanImage;
	}
	
	public String getCovarEigen() {
		return this.covarEigen;
	}
	
	public String getWorkFunctEigen() {
		return this.workFunctEigen;
	}
	
	public String getProjectedImages() {
		return this.projectedImages;
	}
	
}