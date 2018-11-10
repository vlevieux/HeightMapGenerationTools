package controllers;

public class TestMultiThreading {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ThreadView view = new ThreadView("view");
		ThreadComputing computationProcess = new ThreadComputing("computationProcess");

		view.start();
		computationProcess.start();
		
	}

}
