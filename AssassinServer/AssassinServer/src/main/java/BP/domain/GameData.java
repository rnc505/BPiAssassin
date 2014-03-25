package BP.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class GameData {

	@JsonProperty
	String meanImage;
	
	@JsonProperty
	String covarEigen;
	
	@JsonProperty
	String workFunctEigen;
	
	@JsonProperty
	String projectedImages;
	
	public GameData(String meanImage, String covarEigen, String workFunctEigen,
			String projectedImages) {
		this.meanImage = meanImage;
		this.covarEigen = covarEigen;
		this.workFunctEigen = workFunctEigen;
		this.projectedImages = projectedImages;
	}
	
}