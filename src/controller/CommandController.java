package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CommandController {
	
	private static final String WINDOWS_CMDS_FILE_PATH = "./res/syscmds/window-commands.txt";
	private static final String UNIX_CMDS_FILE_PATH = "./res/syscmds/unix-commands.txt";

	private OSController osController;
	private String os;
	private Map<String, List<String>> commands;
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
	
	public Map<String, List<String>> getCommands() {
		return commands;
	}
	
	private Map<String, List<String>> readCommands(String os) throws IOException {
		Map<String, List<String>> commandToArgs = new HashMap<>();
		String filePath = "";
		
		if(os.equals("windows")) {
			filePath = WINDOWS_CMDS_FILE_PATH;
		} else if(os.equals("unix")) {
			filePath = UNIX_CMDS_FILE_PATH;
		}
		
		try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String command;
			
			while((command = reader.readLine()) != null) {
				String[] parts = command.split(",");
				String key = parts[0];
				List<String> valueList = Arrays.asList(Arrays.copyOfRange(parts, 1, parts.length));
				commandToArgs.put(key, valueList);
			}
			
			return commandToArgs;
		} catch(IOException e) {
			throw new IOException("Problem with reading the command file");
		}
		
	}
	
	public String executeCommand(String input) {

		String[] parts = input.split(" ");
		String command = parts[0];
		
		if("exit".equals(command)) {
			System.exit(0);
		}
		
		if(!isValidCommand(command)) {
			return "Invalid command";
		}
		
		List<String> commandArgs = new ArrayList<>(commands.get(command));
		
		if(parts.length > 1) {
			commandArgs.addAll(Arrays.asList(Arrays.copyOfRange(parts, 1, parts.length)));
		}
		
		ProcessBuilder processBuilder = new ProcessBuilder(commandArgs);	
		String output;
			
		try {
			Process process = processBuilder.start();
			output = readOutput(process);
		} catch(Exception e) {
			output = e.getMessage();
		}
			
		commandHistory.add(command);
		
		if("del".equals(command)) {
			return "Deleted file";
		}
		
		return output;
	}
	
	private boolean isValidCommand(String command) {
		return command != null &&
			   !command.isBlank() &&
			   commands.containsKey(command);
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
