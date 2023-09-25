package model;

import java.io.File;

public class OSState {

	private String osName;
	private File currentDir;
	
	public OSState() {
		detectOS();
		initCurrentDir();
	}
	
	private void detectOS() {
		this.osName = System.getProperty("os.name").toLowerCase();
		
		if(osName.contains("win")) {
			osName = "windows";
		} else if(osName.contains("nix") ||
				  osName.contains("nux") ||
				  osName.contains("mac")) {
			osName = "unix";
		} else {
			osName = "unsupported";
		}
	}
	
	private void initCurrentDir() {
		currentDir = new File(System.getProperty("user.dir"));
	}
	
	public String getOsName() {
		return osName;
	}
	
	public File getCurrentDir() {
		return currentDir;
	}
	
	public void setCurrentDir(File newDir) {
		if(newDir != null && newDir.isDirectory()) {
			currentDir = newDir;
		} else {
			throw new IllegalArgumentException("Provided path does not exist");
		}
	}
	
}
