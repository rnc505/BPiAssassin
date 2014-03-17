package BP.domain;

public class GameData {

	String meanImage;
	String covarEigen;
	String workFunctEigen;
	String projectedImages;
	
	public GameData(String meanImage, String covarEigen, String workFunctEigen,
			String projectedImages) {
		this.meanImage = meanImage;
		this.covarEigen = covarEigen;
		this.workFunctEigen = workFunctEigen;
		this.projectedImages = projectedImages;
	}
	
}