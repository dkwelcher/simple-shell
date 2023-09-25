package controller;

import java.io.File;

import model.OSState;

public class OSController {

	private OSState osState;
	
	public OSController() {
		osState = new OSState();
	}
	
	public String getCurrentDirAbsPath() {
		return this.osState.getCurrentDir().getAbsolutePath();
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
