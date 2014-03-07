package BP.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import BP.repository.SimpleDataStorage;
import BP.domain.StoryData;

@Controller
@RequestMapping("/v1/")
public class StoryDataControllerV1 {

	// a simple hash-table based storage class, no persistence
	SimpleDataStorage data;

	public StoryDataControllerV1() {
		data = new SimpleDataStorage();
	}

	public StoryDataControllerV1(SimpleDataStorage storage) {
		data = storage;
	}

	/**
	 * Get a list of story data.
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "stories/")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<StoryData> getStoryDatas() {
		return data.getAllStoryData();
	}

	/**
	 * Get a single story
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "stories/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public StoryData viewStoryData(@PathVariable String id) {
		return data.getStoryData(UUID.fromString(id));
	}

	/**
	 * Create a new story, uses ResponseEntity
	 * 
	 * @param builder
	 * @return
	 */
	// This would normally be a POST method, but simplified for demonstration
	@RequestMapping(method = RequestMethod.GET, value = "stories/new")
	public ResponseEntity<StoryData> createStoryData(
			UriComponentsBuilder builder) {

		StoryData newStoryData = newStoryData();
		data.saveStoryData(newStoryData);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/v1/stories/{id}")
				.buildAndExpand(newStoryData.uuid.toString()).toUri());

		return new ResponseEntity<StoryData>(newStoryData, headers,
				HttpStatus.CREATED);
	}

	/**
	 * Delete a story, NOTE: RequestMethod.DELETE, meaning not able to access
	 * via simple web-browser, will need special browser plugin/app to use.
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "stories/{id}")
	public ResponseEntity<StoryData> cancelStoryData(@PathVariable String id) {

		StoryData storyData = null;
		if (data.doesStoryExist(UUID.fromString(id)) == true) {
			storyData = data.getStoryData(UUID.fromString(id));
			data.deleteStoryData(UUID.fromString(id));
			return new ResponseEntity<StoryData>(storyData, HttpStatus.OK);
		}
		return new ResponseEntity<StoryData>(storyData, HttpStatus.FORBIDDEN);
	}

	/**
	 * Helper method.
	 * 
	 * @return
	 */

	private static StoryData newStoryData() {
		return new StoryData(new UUID(System.nanoTime(), System.nanoTime()), 0,
				0, "a", "b", "c", "d", "e", "f", "g", 0, 0, 0.0, 0.0);
	};

}
