package BP.events.objects;

import BP.users.GameUserImage;

public class TargetInfo {
	
	public String targetUUId;
	public String code_name;
	public GameUserImage thumbnail;
	
	
	public TargetInfo (String uuid, String codeName, GameUserImage image) {
		this.targetUUId = uuid;
		this.code_name = codeName;
		this.thumbnail = image;
	}
	

}
