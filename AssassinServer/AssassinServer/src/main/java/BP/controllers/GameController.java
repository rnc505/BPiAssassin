package BP.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import BP.domain.GameData;
import BP.events.GameManager;
import BP.events.GameManagerInterface;
import BP.users.GameUserImage;


@Controller
@RequestMapping("/game")
public class GameController {
	
	GameManagerInterface gameManager;
	public GameController() {
		this.gameManager = new GameManager();
	}
	
	
	/**
	 * createGame - Creates a game instance and passes user image data back to host for initializing the 
	 * facial recognizer.
	 * 
	 * Expected format of JSON:
	 *	 {
	 *			"playerIds": ["4a35e8b0-a958-11e3-a5e2-0800200c9a66",
	 *					"b8757730-0b97-4c0b-81d1-c153a27684e3",
	 *					"b4616bbd-6407-4227-86cf-04761764fd0a",
	 *					"7ed41362-639c-461e-85f8-53ca2613c9f5"],
	 *			"hostId": "4a35e8b0-a958-11e3-a5e2-0800200c9a66"
	 *	 }
	 *
	 *	@return GameCreated Object
	 */
	
	@RequestMapping(method = RequestMethod.POST, value = "createGame")
	public @ResponseBody GameCreated createGame(
			@RequestParam(value = "hostId",required = true, defaultValue = "") final String hostID,
			@RequestParam(value = "playerIds", required = true, defaultValue = "") final ArrayList<String> playerIds
			){
	
		return gameManager.createGame(hostID, playerIds);
		
	}
	
	/**
	 * startGame - Starts a game with the initialized values from Facial Recognizer
	 * 
	 * The following four parameters are the base64-encoded binary data required by the recognizer to
	 * make the game work:
	 * 
	 * - meanImage
	 * - covarianceEigenvectors
	 * - largestEigenvectorsOfTheWorkFunction
	 * - projectedImages
	 * 
	 * Expected format of JSON:
	 *	 {
	 *			"gameId": "4a35e8b0-a958-11e3-a5e2-0800200c9a66",
	 *			"meanImage": "ImdhbWVJZCI6ICI0YTM1ZThiMC1hOTU4LTExZTMtYTVlMi0wODAwMjAwYzlhNjYiImdhbWVJZCI6ICI0YTM1ZThiMC1hOTU4LTExZTMtYTVlMi0wODAwMjAwYzlhNjYi",
	 *			"covarEigen": "ImdhbWVJZCI6ICI0YTM1ZThiMC1hOTU4LTExZTMtYTVlMi0wODAwMjAwYzlhNjYiImdhbWVJZCI6ICI0YTM1ZThiMC1hOTU4LTExZTMtYTVlMi0wODAwMjAwYzlhNjYi",
	 *			"workFunctEigen": "ImdhbWVJZCI6ICI0YTM1ZThiMC1hOTU4LTExZTMtYTVlMi0wODAwMjAwYzlhNjYiImdhbWVJZCI6ICI0YTM1ZThiMC1hOTU4LTExZTMtYTVlMi0wODAwMjAwYzlhNjYi",
	 *			"projectedImages": "ImdhbWVJZCI6ICI0YTM1ZThiMC1hOTU4LTExZTMtYTVlMi0wODAwMjAwYzlhNjYiImdhbWVJZCI6ICI0YTM1ZThiMC1hOTU4LTExZTMtYTVlMi0wODAwMjAwYzlhNjYi"
	 *	 }
	 *
	 *	@return 
	 */
	@RequestMapping(method = RequestMethod.POST, value = "createGame")
	public @ResponseBody String startGame(
			@RequestParam(value = "gameId",required = true, defaultValue = "") final String gameId,
			@RequestParam(value = "meanImage", required = true, defaultValue = "") final String meanImage,
			@RequestParam(value = "covarEigen", required = true, defaultValue = "") final String covarEigen,
			@RequestParam(value = "workFunctEigen", required = true, defaultValue = "") final String workFunctEigen,
			@RequestParam(value = "projectedImages", required = true, defaultValue = "") final String projectedImages
			){
		
		GameData recognizerData = new GameData(meanImage, covarEigen, workFunctEigen, projectedImages);
		gameManager.startGame(gameId, recognizerData);
		return null;
		
	}
}
