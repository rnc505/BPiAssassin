package BP.domain;

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
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
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
		this.meanImage = meanImage;
		this.covarEigen = covarEigen;
		this.workFunctEigen = workFunctEigen;
		this.projectedImages = projectedImages;
	}
	
}