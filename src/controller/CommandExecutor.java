package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.CommandReader;

public class CommandExecutor {

	private OSController osController;
	private CommandReader commandReader;
	private String os;
	private Map<String, List<String>> commands;
	private Map<String, Integer> commandHistory;
	
	public CommandExecutor(OSController osController) {
		this.osController = osController;
		os = osController.getOsName();
		commandReader = new CommandReader(os);
		
		try {
			commands = commandReader.readCommands();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		commandHistory = new HashMap<>();
	}
	
	public Map<String, List<String>> getCommands() {
		return commands;
	}
	
	public String executeCommand(String input) {

		String[] parts = input.split(" ");
		String command = parts[0];
		
		if(!isValidCommand(command)) {
			return "Invalid command";
		}
		
		if("exit".equals(command)) {
			System.exit(0);
		}
		
		if("cd".equals(command) || "pwd".equals(command)) {
			return executeChangeDirCommand(command, parts);
		}
		
		if("history".equals(command)) {
			return executeHistoryCommand(command);
		}
		
		List<String> commandArgs = new ArrayList<>(commands.get(command));
		
		if(parts.length > 1) {
			commandArgs.addAll(Arrays.asList(Arrays.copyOfRange(parts, 1, parts.length)));
			if("find".equals(command) || "grep".equals(command)) {
				commandArgs.add("\t");
			}
		}
		
		ProcessBuilder processBuilder = new ProcessBuilder(commandArgs);	
		
		processBuilder.directory(osController.getCurrentDir());
		
		String output;
			
		try {
			Process process = processBuilder.start();
			output = readOutput(process);
		} catch(Exception e) {
			output = e.getMessage();
		}
			
		recordCommandHistory(command);
		
		if("del".equals(command) || "rm".equals(command)) {
			return "Deleted file";
		}
		
		if(output.isEmpty()) {
			return "Incomplete command arguments";
		}
		
		return output;
	}
	
	private boolean isValidCommand(String command) {
		return command != null &&
			   !command.isBlank() &&
			   commands.containsKey(command);
	}
	
	private String executeChangeDirCommand(String command, String[] parts) {
		if(parts.length > 1) {
			recordCommandHistory(command);
			return osController.changeCurrentDir(parts[1]);
		} else {
			return "Incomplete command arguments";
		}
	}
	
	private String executeHistoryCommand(String command) {
		StringBuilder sb = new StringBuilder();
		
		for(Map.Entry<String, Integer> usedCommand : commandHistory.entrySet()) {
			if(sb.length() == 0) {
				sb.append(usedCommand.getKey() + "(" + usedCommand.getValue() + ")");
			} else {
				sb.append(", " + usedCommand.getKey() + "(" + usedCommand.getValue() + ")");
			}
		}
		
		if(sb.length() == 0) {
			return "No command history exists";
		}
		
		return sb.toString();
	}
	
	private void recordCommandHistory(String command) {
		if(commandHistory.containsKey(command)) {
			commandHistory.put(command, commandHistory.get(command) + 1);
		} else {
			commandHistory.put(command, 1);
		}
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
