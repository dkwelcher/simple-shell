package controller;

public class CommandController {

	private OSController osController;
	
	public CommandController() {
		this.osController = new OSController();
	}
	
	public String executeCommand(String command) {
		return "Command executed: " + command;
	}
}
