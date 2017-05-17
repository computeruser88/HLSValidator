package edu.psgv.sweng861;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class FirstLineChecker implements Checker {
	/**
	 * The FirstLineChecker class checks the first line of the playlist to ensure it is #EXTM3U.
	 */
	final Logger logger = LogManager.getLogger();

	@Override	
	public void check(PlayList playlist) {
		/**
		 * This method checks the first line to make sure it is #EXTM3U.
		 */
		logger.info(">>FirstLineChecker.check");
		ArrayList<String> lines = playlist.getLines();
		CharSequence correctFirstLine = "#EXTM3U";
		if (lines.get(0).contentEquals(correctFirstLine)) {
			System.out.println("SUCCESS: First line validated as #EXTM3U.");
		} else {
			System.out.println("FAILURE: First line is " + lines.get(0) + ", not #EXTM3U.");
		}
		logger.info("<<FirstLineChecker.check");
	}
}
