package BP.domain;

import java.util.UUID;

/**
 * Data object to be stored
 * 
 */
public class StoryData {

	public UUID uuid;
	public long loginId;
	public long storyId;
	public String title;
	public String body;
	public String audioLink;
	public String videoLink;
	public String imageName;
	public String imageLink;
	public String tags;
	public long creationTime;
	public long storyTime;
	public double latitude;
	public double longitude;

	/**
	 * Constructor WITHOUT UUID
	 * 
	 * @param loginId
	 * @param storyId
	 * @param title
	 * @param body
	 * @param audioLink
	 * @param videoLink
	 * @param imageName
	 * @param imageLink
	 * @param tags
	 * @param creationTime
	 * @param storyTime
	 * @param latitude
	 * @param longitude
	 */
	public StoryData(long loginId, long storyId, String title, String body,
			String audioLink, String videoLink, String imageName,
			String imageLink, String tags, long creationTime, long storyTime,
			double latitude, double longitude) {
		this.loginId = loginId;
		this.storyId = storyId;
		this.title = title;
		this.body = body;
		this.audioLink = audioLink;
		this.videoLink = videoLink;
		this.imageName = imageName;
		this.imageLink = imageLink;
		this.tags = tags;
		this.creationTime = creationTime;
		this.storyTime = storyTime;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Constructor WITH UUID
	 * 
	 * @param uuid
	 * @param loginId
	 * @param storyId
	 * @param title
	 * @param body
	 * @param audioLink
	 * @param videoLink
	 * @param imageName
	 * @param imageLink
	 * @param tags
	 * @param creationTime
	 * @param storyTime
	 * @param latitude
	 * @param longitude
	 */
	public StoryData(UUID uuid, long loginId, long storyId, String title,
			String body, String audioLink, String videoLink, String imageName,
			String imageLink, String tags, long creationTime, long storyTime,
			double latitude, double longitude) {
		this.uuid = uuid;
		this.loginId = loginId;
		this.storyId = storyId;
		this.title = title;
		this.body = body;
		this.audioLink = audioLink;
		this.videoLink = videoLink;
		this.imageName = imageName;
		this.imageLink = imageLink;
		this.tags = tags;
		this.creationTime = creationTime;
		this.storyTime = storyTime;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Override of the toString() method, for testing/logging
	 */
	@Override
	public String toString() {
		return " uuid: " + uuid + " loginId: " + loginId + " storyId: "
				+ storyId + " title: " + title + " body: " + body
				+ " audioLink: " + audioLink + " videoLink: " + videoLink
				+ " imageName: " + imageName + " imageLink: " + imageLink
				+ " tags: " + tags + " creationTime: " + creationTime
				+ " storyTime: " + storyTime + " latitude: " + latitude
				+ " longitude: " + longitude;
	}

	/**
	 * Clone this object into a new StoryData
	 */
	public StoryData clone() {
		return new StoryData(uuid, loginId, storyId, title, body, audioLink,
				videoLink, imageName, imageLink, tags, creationTime, storyTime,
				latitude, longitude);
	}

}