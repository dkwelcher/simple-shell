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
	
	public void changeCurrentDir(String path) {
		File newDir = new File(path);
		osState.setCurrentDir(newDir);
	}
}
