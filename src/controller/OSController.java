package controller;

import java.io.File;

import model.OSState;

public class OSController {

	private OSState osState;
	
	public OSController() {
		osState = new OSState();
	}
	
	public String getOsName() {
		return osState.getOsName();
	}
	
	public File getCurrentDir() {
		return osState.getCurrentDir();
	}
	
	public String getCurrentDirAbsPath() {
		return osState.getCurrentDir().getAbsolutePath();
	}
	
	public String changeCurrentDir(String path) {
		try {
			File newDir = new File(path);
			osState.setCurrentDir(newDir);
			return "Directory changed to: " + newDir.getAbsolutePath();
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
