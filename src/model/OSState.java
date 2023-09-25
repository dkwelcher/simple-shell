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
			this.osName = "unix";
		} else {
			this.osName = "unsupported";
		}
	}
	
	private void initCurrentDir() {
		this.currentDir = new File(System.getProperty("user.dir"));
	}
	
	public String getOsName() {
		return this.osName;
	}
	
	public File getCurrentDir() {
		return this.currentDir;
	}
	
	public void setCurrentDir(File newDir) {
		if(newDir != null && newDir.isDirectory()) {
			this.currentDir = newDir;
		} else {
			throw new IllegalArgumentException("Provided path does not exist");
		}
	}
	
}
