package BP.events.objects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import BP.users.GameUserImage;

@JsonAutoDetect
public class TargetInfo {
	
	@JsonProperty
	public String targetUUID;
	@JsonProperty
	public String code_name;
	@JsonProperty
	public GameUserImage thumbnail;
	
	
	public TargetInfo (String uuid, String codeName, GameUserImage image) {
		this.targetUUID = uuid;
		this.code_name = codeName;
		this.thumbnail = image;
	}
	

}
