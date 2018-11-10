/**
 * ThreadComputing.java
 * Purpose: 
 *
 * HeighMapGenerationTools
 * @author 
 * @version 1.0 
 */

package controllers;

public class ThreadComputing implements Runnable{
	   private Thread thread;
	   private String threadName;

	   ThreadComputing(String name) {
		   threadName = name;
		   System.out.println("Creating " +  threadName );
	   }

	   public void run() {
		   System.out.println("Running " +  threadName );
		   try {
				   // Let the thread sleep for a while.
				   Thread.sleep(50);
			   }
		   catch (InterruptedException ie) {
			   System.out.println("Thread " +  threadName + " interrupted.");
			   ie.printStackTrace();
		   }
		   System.out.println("Thread " +  threadName + " exiting.");
	   }

	   public void start () {
		   System.out.println("Starting " +  threadName );
		   if (thread == null) {
			   thread = new Thread (this, threadName);
			   thread.start ();
		   }
	   }
}
