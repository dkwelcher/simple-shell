package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandReader {

	private static final String WINDOWS_CMDS_FILE_PATH = "./res/syscmds/window-commands.txt";
	private static final String UNIX_CMDS_FILE_PATH = "./res/syscmds/unix-commands.txt";
	
	private String os;
	
	public CommandReader(String os) {
		this.os = os;
	}
	
	public Map<String, List<String>> readCommands() throws IOException {
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
}
