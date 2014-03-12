package BP.controllers;

import java.util.ArrayList;
import java.util.List;

import BP.users.*;
import BP.repository.*;
import BP.game.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@RequestMapping("/game")
public class GameController {
	
	public GameController() {
	}
	
	
	/**
	 * createGame - Creates a game instance and passes user image data back to host for initializing the 
	 * facial recognizer.
	 * @param json - String containing the JSON of the request
	 * 
	 * Expected format of JSON:
	 *	 {
	 *			"playerIds": ["4a35e8b0-a958-11e3-a5e2-0800200c9a66",
	 *					"b8757730-0b97-4c0b-81d1-c153a27684e3",
	 *					"b4616bbd-6407-4227-86cf-04761764fd0a",
	 *					"7ed41362-639c-461e-85f8-53ca2613c9f5"],
	 *			"hostId": "4a35e8b0-a958-11e3-a5e2-0800200c9a66"
	 *	 }
	 */
	
	@RequestMapping(method = RequestMethod.POST, value = "createGame")
	public @ResponseBody List<GameUserImage> createGame(
			@RequestParam(value = "hostId",required = true, defaultValue = "") final String hostID,
			@RequestParam(value = "playerIds", required = true, defaultValue = "") final ArrayList<String> playerIds
			){
	
		return null;
		
	}
	
	
	
}
