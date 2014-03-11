package BP.controllers;

import java.util.List;

import BP.users.*;
import BP.repository.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@Controller
@RequestMapping("/game/")
public class GameController {

	GameDataStorage store;
	public GameController() {
		this.store = new GameDataStorage();
	}
	
	public GameController(GameDataStorage store) {
		this.store = store;
	}
	
	/**
	 * createGame - Creates a game instance and passes user image data back to host for initializing the 
	 * facial recognizer.
	 * @param
	 * 
	 */
	
	/*@RequestMapping(method = RequestMethod.POST, value = "createGame")
	public List<GameUserImage> createGame() {
		return this.store.getAllUserImagesSorted();
	}*/
	
}
