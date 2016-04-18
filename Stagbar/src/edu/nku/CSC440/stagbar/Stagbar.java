package edu.nku.CSC440.stagbar;

import java.awt.*;

 class Splash {

	 Splash() {
		final SplashScreen splash = SplashScreen.getSplashScreen();
		if (splash == null) {
			System.out.println("SplashScreen.getSplashScreen() returned null");
			return;
		}

		pause();
		splash.close();
	}

	 private synchronized void pause(){
		 try {
			 wait(4500);
		 } catch(InterruptedException e) {
			 e.printStackTrace();
		 }
	 }
 }

/** Using this class to launch application. Subject to change. */
public class Stagbar {

	public static void main(String[] args) {
		new Splash();
		Application.getInstance().getApplicationUI();
	}

}