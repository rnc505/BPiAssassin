package BP.events.objects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class UserKilled {
	
	@JsonProperty
	private String nextTarget;
	
	public UserKilled() {
		
	}
	
	public UserKilled(String nxt) {
		this.nextTarget = nxt;
	}
	
	public String getNextTarget() {
		return nextTarget;
	}
}
