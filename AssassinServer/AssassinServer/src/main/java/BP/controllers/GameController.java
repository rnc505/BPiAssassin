package BP.controllers;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import BP.domain.GameData;
import BP.events.GameManager;
import BP.events.GameManagerInterface;
import BP.events.objects.GameCreated;
import BP.events.objects.GameEnded;
import BP.events.objects.GameStarted;
import BP.events.objects.UserKilled;
import BP.events.objects.TargetInfo;
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
	 * createGame - Creates a game instance and passes user image data back to
	 * host for initializing the facial recognizer.
	 * 
	 * Expected format of JSON: { "playerIds":
	 * ["4a35e8b0-a958-11e3-a5e2-0800200c9a66",
	 * "b8757730-0b97-4c0b-81d1-c153a27684e3",
	 * "b4616bbd-6407-4227-86cf-04761764fd0a",
	 * "7ed41362-639c-461e-85f8-53ca2613c9f5"], "hostId":
	 * "4a35e8b0-a958-11e3-a5e2-0800200c9a66" }
	 * 
	 * @return GameCreated Object
	 */

	@RequestMapping(method = RequestMethod.GET, value = "/getUserStatus/{userId}")
	public @ResponseBody
	String getUserStatus(@PathVariable String userId) {
		return new JSONObject().put("status",this.gameManager.GetUserStatus(userId)).toString();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/createGame")
	public @ResponseBody
	GameCreated createGame(@RequestBody final String Body) {
		// @RequestParam(value = "hostId", required = true, defaultValue = "")
		// final String hostID,
		// @RequestParam(value = "playerIds", required = true, defaultValue =
		// "") final String[] playerIds) {
		JSONObject body = new JSONObject(Body);
		ArrayList<String> temp = jsonArrayToArrayList(
				body.getJSONArray("playerIds"), String.class);
		return gameManager.createGame(body.getString("hostId"), temp);
	}

	/**
	 * startGame - Starts a game with the initialized values from Facial
	 * Recognizer
	 * 
	 * The following four parameters are the base64-encoded binary data required
	 * by the recognizer to make the game work:
	 * 
	 * - meanImage - covarianceEigenvectors -
	 * largestEigenvectorsOfTheWorkFunction - projectedImages
	 * 
	 * Expected format of JSON: { "gameId":
	 * "4a35e8b0-a958-11e3-a5e2-0800200c9a66", "meanImage":
	 * "ImdhbWVJZCI6ICI0YTM1ZThiMC1hOTU4LTExZTMtYTVlMi0wODAwMjAwYzlhNjYiImdhbWVJZCI6ICI0YTM1ZThiMC1hOTU4LTExZTMtYTVlMi0wODAwMjAwYzlhNjYi"
	 * , "covarEigen":
	 * "ImdhbWVJZCI6ICI0YTM1ZThiMC1hOTU4LTExZTMtYTVlMi0wODAwMjAwYzlhNjYiImdhbWVJZCI6ICI0YTM1ZThiMC1hOTU4LTExZTMtYTVlMi0wODAwMjAwYzlhNjYi"
	 * , "workFunctEigen":
	 * "ImdhbWVJZCI6ICI0YTM1ZThiMC1hOTU4LTExZTMtYTVlMi0wODAwMjAwYzlhNjYiImdhbWVJZCI6ICI0YTM1ZThiMC1hOTU4LTExZTMtYTVlMi0wODAwMjAwYzlhNjYi"
	 * , "projectedImages":
	 * "ImdhbWVJZCI6ICI0YTM1ZThiMC1hOTU4LTExZTMtYTVlMi0wODAwMjAwYzlhNjYiImdhbWVJZCI6ICI0YTM1ZThiMC1hOTU4LTExZTMtYTVlMi0wODAwMjAwYzlhNjYi"
	 * }
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/startGame")
	public @ResponseBody
	String startGame(@RequestBody final String Body) {
		// @RequestParam(value = "gameId", required = true, defaultValue = "")
		// final String gameId,
		// @RequestParam(value = "meanImage", required = true, defaultValue =
		// "") final String meanImage,
		// @RequestParam(value = "covarEigen", required = true, defaultValue =
		// "") final String covarEigen,
		// @RequestParam(value = "workFunctEigen", required = true, defaultValue
		// = "") final String workFunctEigen,
		// @RequestParam(value = "projectedImages", required = true,
		// defaultValue = "") final String projectedImages) {
		final JSONObject body = new JSONObject(Body);
		GameStarted startedGame = gameManager.startGame(body
				.getString("gameId"), new GameData(body.getString("meanImage"),
				body.getString("covarEigen"), body.getString("workFunctEigen"),
				body.getString("projectedImages")));
		this.apnController.sendNotification(startedGame,
				"LET THE GAMES BEGIN! Come see who is your first target...");
		return new JSONObject().toString();

	}

	@RequestMapping(method = RequestMethod.GET, value = "/gamePlayData/{gameId}")
	public @ResponseBody
	GameData getGamePlayData(@PathVariable String gameId) {
		return gameManager.getGamePlayData(gameId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getTarget/{gameId}/{userId}")

	public @ResponseBody
	TargetInfo getTarget(@PathVariable String gameId, @PathVariable String userId) {
		return gameManager.getTarget(gameId, userId);
		//return new JSONObject().put("target", gameManager.getTarget(gameId, userId)).toString();

	}

	@RequestMapping(method = RequestMethod.POST, value = "/killUser")
	public @ResponseBody
	TargetInfo killUser(@RequestBody final String Body
	// @RequestParam(value = "gameId", required = true, defaultValue = "") final
	// String gameId,
	// @RequestParam(value = "assassinId", required = true, defaultValue = "")
	// final String assassinId,
	// @RequestParam(value = "victimId", required = true, defaultValue = "")
	// final String victimId
	) {
		final JSONObject body = new JSONObject(Body);
		UserKilled killed = gameManager.killUser(body.getString("gameId"),
				body.getString("assassinId"), body.getString("victimId"));
		TargetInfo ret;
		if (killed.getNextTarget().equals(body.getString("assassinId"))) {
			ret = this.gameManager.getTarget(body.getString("gameId"), body.getString("assassinId"));
			GameEnded ended = this.gameManager.endGame(
					body.getString("gameId"), body.getString("assassinId"));
			this.apnController.sendNotification(ended, ended.getWinner()
					+ " has won the game!");
		} else {
			UserKilled victim = killed.createUKObjectforVictim();
			this.apnController.sendNotification(killed,
					killed.getVictimCodeName() + " has been assassinated!!");
			this.apnController.sendNotification(victim,
					"You have been assassinated!");
			ret = this.gameManager.getTarget(body.getString("gameId"), body.getString("assassinId"));
		}
//		return new JSONObject().put("target",killed.getNextTarget()).toString();
//		return this.gameManager.getTarget(body.getString("gameId"), body.getString("assassinId"));
		return ret; 
	}

	@RequestMapping(method = RequestMethod.POST, value = "/registerUser")
	public @ResponseBody
	String registerUser(@RequestBody final String Body
	// @RequestParam(value = "username", required = true) final String username,
	// @RequestParam(value = "thumbnail", required = true) final GameUserImage
	// thumbnail,
	// @RequestParam(value = "faceImages", required = true) final String[]
	// faceImages,
	// @RequestParam(value = "apn", required = true) final String apn,
	// @RequestParam(value = "platformId", required = true) final String
	// platformId
	) {
		JSONObject body = new JSONObject(Body);
		ArrayList<GameUserImage> temp = jsonArrayToArrayList(
				body.getJSONArray("faceImages"), GameUserImage.class);
		String userUUID = gameManager.RegisterUser(body.getString("username"),
				new GameUserImage(body.getString("thumbnail")), temp,
				body.getString("apn"), body.getString("platformId"));

		return new JSONObject().put("userId", userUUID).toString();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/helloWorld")
	public @ResponseBody
	String helloWorld() {
		return "Hello World";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/exampleArray")
	public @ResponseBody
	ArrayList<String> retArray() {
		ArrayList<String> ret = new ArrayList<String>(3);
		ret.add("Hello");
		ret.add("World");
		ret.add("!");
		return ret;
	}

	static private <T> ArrayList<T> jsonArrayToArrayList(JSONArray array,
			Class<T> cls) {
		ArrayList<T> listdata = new ArrayList<T>();
		try {
			Constructor<T> ctor = cls.getConstructor(String.class);
			if (array != null) {
				for (int i = 0; i < array.length(); i++) {
					T temp = ctor.newInstance(array.getString(i));
					listdata.add(temp);
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage(), ex.getCause());
		}
		return listdata;
	}

}
