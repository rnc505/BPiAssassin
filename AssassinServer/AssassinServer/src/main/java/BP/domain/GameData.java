package BP.domain;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.appengine.api.datastore.Text;

@JsonAutoDetect
@PersistenceCapable
public class GameData {

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