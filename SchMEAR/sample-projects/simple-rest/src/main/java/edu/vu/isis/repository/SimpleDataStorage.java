package edu.vu.isis.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import edu.vu.isis.domain.StoryData;

public class SimpleDataStorage {

	private Map<UUID, StoryData> stories;

	public SimpleDataStorage() {
		HashMap<UUID, StoryData> rValue = new HashMap<UUID, StoryData>();
		int MAX_DEFAULT_STORIES = 10;
		for (int i = 0; i < MAX_DEFAULT_STORIES; i++) {
			UUID uuid = new UUID(System.nanoTime(), System.nanoTime());
			StoryData storyData = new StoryData(0, 0, "a", "b", "c", "d", "e",
					"f", "g", 0, 0, 0.0, 0.0);
			storyData.uuid = uuid;
			rValue.put(uuid, storyData);
		}
		this.stories = Collections.unmodifiableMap(rValue);
	}

	public SimpleDataStorage(final Map<UUID, StoryData> stories) {
		this.stories = Collections.unmodifiableMap(stories);
	}

	public synchronized StoryData saveStoryData(StoryData storyData) {
		Map<UUID, StoryData> modifiableOrders = new HashMap<UUID, StoryData>(
				stories);
		modifiableOrders.put(storyData.uuid, storyData);
		this.stories = Collections.unmodifiableMap(modifiableOrders);

		return storyData;
	}

	public List<StoryData> getAllStoryData() {
		return Collections.unmodifiableList(new ArrayList<StoryData>(stories
				.values()));
	}

	public StoryData getStoryData(UUID key) {
		return stories.get(key);
	}

	public synchronized boolean deleteStoryData(UUID key) {
		if (stories.containsKey(key)) {
			Map<UUID, StoryData> modifiableOrders = new HashMap<UUID, StoryData>(
					stories);
			modifiableOrders.remove(key);
			this.stories = Collections.unmodifiableMap(modifiableOrders);
			return true;
		} else {
			return false;
		}
	}

	public synchronized boolean deleteStoryDataAll() {
		if (stories.size() > 0) {
			Map<UUID, StoryData> modifiableOrders = new HashMap<UUID, StoryData>(
					stories);
			// TODO: probably a better way to do this, but it 'works' for now
			// this is only TEMP class anyways, so 'good enough' for now.
			for (UUID key : stories.keySet()) {
				modifiableOrders.remove(key);
			}
			this.stories = Collections.unmodifiableMap(modifiableOrders);
			return true;
		} else {
			return false;
		}
	}

	public boolean doesStoryExist(UUID uuid){
		return this.stories.containsKey(uuid);
	}
}
