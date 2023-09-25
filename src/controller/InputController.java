package controller;

public class InputController {

	private CommandController commandController;
	
	public InputController() {
		this.commandController = new CommandController();
	}
	
	public String validateInput(String input) {
		String trimmedInput = input.trim();
		
		if(isValidCommand(trimmedInput)) {
			return commandController.executeCommand(trimmedInput);
		} else {
			return "Invalid command";
		}
	}
	
	private boolean isValidCommand(String input) {
		return true;
	}
}
