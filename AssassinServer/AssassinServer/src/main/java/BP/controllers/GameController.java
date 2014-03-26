package BP.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import BP.domain.GameData;
import BP.events.GameManager;
import BP.events.GameManagerInterface;
import BP.events.objects.GameCreated;
import BP.events.objects.GameEnded;
import BP.events.objects.GameStarted;
import BP.events.objects.UserKilled;
import BP.users.GameUserImage;


@Controller
@RequestMapping("/game")
public class GameController {
	
	GameManagerInterface gameManager;
	APNController apnController;
	public GameController() {
		this.gameManager = new GameManager();
		this.apnController = new APNController();
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
	
	@RequestMapping(method = RequestMethod.POST, value = "/createGame")
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
	@RequestMapping(method = RequestMethod.POST, value = "/startGame")
	public @ResponseBody String startGame(
			@RequestParam(value = "gameId", required = true, defaultValue = "") final String gameId,
			@RequestParam(value = "gameData",required = true, defaultValue = "") final GameData recognizerData)
	{
		GameStarted startedGame = gameManager.startGame(gameId, recognizerData);
		this.apnController.sendNotification(startedGame, "LET THE GAMES BEGIN! Come see who's your first target...");
		return "";
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/gamePlayData/{gameId}")
	public @ResponseBody GameData getGamePlayData(@PathVariable String gameId) {
		return gameManager.getGamePlayData(gameId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getTarget/{gameId}/{userId}")
	public @ResponseBody String getTarget(@PathVariable String gameId, @PathVariable String userId) {
		return gameManager.getTarget(gameId, userId);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/killUser")
	public @ResponseBody String killUser(
			@RequestParam(value = "gameId", required = true, defaultValue = "") final String gameId,
			@RequestParam(value = "assassinId", required = true, defaultValue = "") final String assassinId,
			@RequestParam(value = "victimId", required = true, defaultValue = "") final String victimId) 
	{
		UserKilled killed = gameManager.killUser(gameId, assassinId, victimId);
		if(killed.getNextTarget().equals(assassinId)) {
			GameEnded ended =  this.gameManager.endGame(gameId, assassinId);
			this.apnController.sendNotification(ended, ended.getWinner(assassinId) + " has won the game!");
		} else {
			UserKilled everyoneElse = killed.getEveryoneExceptAssassinAndVictim(assassinId, victimId);
			this.apnController.sendNotification(everyoneElse, everyoneElse.getNextTarget() + " has been assassinated!!");
			this.apnController.sendNotification(killed.getVictim(victimId), "You have been assassinated!");
		}
		this.apnController.sendNotification(killed, ""); // 
		return killed.getNextTarget(); 
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/registerUser")
	public @ResponseBody String registerUser(
			@RequestParam(value = "username", required = true, defaultValue = "") final String username,
			@RequestParam(value = "thumbnail", required = true, defaultValue = "") final GameUserImage thumbnail,
			@RequestParam(value = "faceImages", required = true, defaultValue = "") final ArrayList<GameUserImage> faceImages,
			@RequestParam(value = "apn", required = true, defaultValue = "") final String apn,
			@RequestParam(value = "platformId", required = true, defaultValue = "") final String platformId) 
	{
		return gameManager.RegisterUser(username, thumbnail, faceImages, apn, platformId);
	}
			
	@RequestMapping(method = RequestMethod.GET, value = "/helloWorld")
	public @ResponseBody String helloWorld() {
		return "Hello World";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/exampleArray")
	public @ResponseBody ArrayList<String> retArray() {
		ArrayList<String> ret = new ArrayList<String>(3);
		ret.add("Hello");
		ret.add("World");
		ret.add("!");
		return ret;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/exampleMap")
	public @ResponseBody Map<String,UserKilled> exampleMap() {
		Map<String,UserKilled> ret = new HashMap<String,UserKilled>(3);
		ret.put("user1", new UserKilled("ABCD"));
		ret.put("user2", new UserKilled("EFGH"));
		ret.put("user3", new UserKilled("IJKL"));
		return ret;
	}
	
	
}
