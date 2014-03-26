package BP.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.appengine.api.datastore.Text;

@JsonAutoDetect
public class GameData {

	@JsonProperty
	Text meanImage;
	
	@JsonProperty
	Text covarEigen;
	
	@JsonProperty
	Text workFunctEigen;
	
	@JsonProperty
	Text projectedImages;
	
	public GameData(String meanImage, String covarEigen, String workFunctEigen,
			String projectedImages) {
		this.meanImage = new Text(meanImage);
		this.covarEigen = new Text(covarEigen);
		this.workFunctEigen = new Text(workFunctEigen);
		this.projectedImages = new Text(projectedImages);
	}
	
}