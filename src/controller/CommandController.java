package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CommandController {
	
	private static final String WINDOWS_CMDS_FILE_PATH = "./res/syscmds/window-commands.txt";
	private static final String UNIX_CMDS_FILE_PATH = "./res/syscmds/unix-commands.txt";

	private OSController osController;
	private String os;
	private List<String> commands;
	
	public CommandController() {
		osController = new OSController();
		os = osController.getOsName();
		commands = readCommands(os);
	}
	
	private List<String> readCommands(String os) {
		List<String> commands = new LinkedList<>();
		String filePath = "";
		
		if(os.equals("windows")) {
			filePath = WINDOWS_CMDS_FILE_PATH;
		} else if(os.equals("unix")) {
			filePath = UNIX_CMDS_FILE_PATH;
		}
		
		try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String command;
			
			while((command = reader.readLine()) != null) {
				commands.add(command);
			}
			
			return commands;
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String executeCommand(String command) {
		return "Command executed: " + command;
	}
	
	private boolean isValidCommand(String command) {
		return true;
	}
}
