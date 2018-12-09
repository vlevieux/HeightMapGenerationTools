package controllers;

import javafx.concurrent.Task;

public class InitializingTask extends Task<Boolean> {

	@Override
	protected Boolean call() throws Exception {
		Main.checkTable();
		return true;
	}

}
