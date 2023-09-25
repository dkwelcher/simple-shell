package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CommandController {
	
	private static final String WINDOWS_CMDS_FILE_PATH = "./res/syscmds/window-commands.txt";
	private static final String UNIX_CMDS_FILE_PATH = "./res/syscmds/unix-commands.txt";

	private OSController osController;
	private String os;
	private List<String> commands;
	private List<String> commandHistory;
	
	public CommandController() {
		osController = new OSController();
		os = osController.getOsName();
		
		try {
			commands = readCommands(os);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		commandHistory = new LinkedList<>();
	}
	
	public List<String> getCommands() {
		return commands;
	}
	
	private List<String> readCommands(String os) throws IOException {
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
			throw new IOException("Problem with reading the command file");
		}
		
	}
	
	public String executeCommand(String input) {

		String[] parts = input.split(" ");
		String command = parts[0];
		
		if(!isValidCommand(command)) {
			return "Invalid command";
		}
		
		String[] args = Arrays.copyOfRange(parts, 1, parts.length);
			
		ProcessBuilder processBuilder;
			
		if(args.length > 0) {
			List<String> multiCommand = new ArrayList<>();
			multiCommand.add(command);
			multiCommand.addAll(Arrays.asList(args));
			processBuilder = new ProcessBuilder(multiCommand);
		} else {
			processBuilder = new ProcessBuilder(command);
		}
			
		String output;
			
		try {
			Process process = processBuilder.start();
			output = readOutput(process);
		} catch(Exception e) {
			output = e.getMessage();
		}
			
		commandHistory.add(command);
		return output;
	}
	
	private boolean isValidCommand(String command) {
		return command != null &&
			   !command.isBlank() &&
			   commands.contains(command);
	}
	
	private String readOutput(Process process) {
		String line;
		StringBuilder sb = new StringBuilder();
		
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			while((line = reader.readLine()) != null) {
				sb.append("\n").append(line);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
}
