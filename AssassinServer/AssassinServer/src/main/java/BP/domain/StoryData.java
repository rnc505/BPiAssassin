package BP.domain;

import java.util.ArrayList;
import java.util.UUID;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class StoryData {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private String uuidString;
	
	@Persistent
	private String data1;
	
	@Persistent
	private ArrayList<String> array1;
	
	public StoryData(String data, ArrayList<String> array) {
		UUID uuid = new UUID(System.nanoTime(), System.nanoTime());
		this.uuidString = uuid.toString();
		this.data1 = data;
		this.array1 = array;
	}
	
}