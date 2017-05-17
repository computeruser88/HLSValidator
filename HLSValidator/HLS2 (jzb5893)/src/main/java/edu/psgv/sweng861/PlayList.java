package edu.psgv.sweng861;

import java.util.ArrayList;
/**
 * The PlayList class is inherited by MediaPlaylist and MasterPlaylist.
 * @author janantoniofavisbalangue
 *
 */
public class PlayList {
	protected String strURL;
	protected ArrayList<String> lines;
	/**
	 * The constructor takes a URL and an arraylist containing the lines of the playlist as parameters.
	 * @param sURL
	 * @param lines
	 */
	PlayList(String sURL, ArrayList<String> lines) {
		this.strURL = sURL;
		this.lines = lines;
	}
	/**
	 * The getstrURL() method returns the URL of a playlist.
	 * @return
	 */
	public String getstrURL() {
		return this.strURL;
	}
	/**
	 * The setstrURL() method sets the URL of a playlist.
	 * @param sURL
	 */
	public void setstrURL(String sURL){
		this.strURL = sURL;
	}
	/**
	 * The getLines() method returns the lines of a playlist.
	 * @return
	 */
	public ArrayList<String> getLines() {
		return this.lines;
	}
	/**
	 * This was originally written to be an implementation of the Visitor pattern. The accept method is not actually used.
	 * @param c
	 */
	public void accept(Checker c) {
		c.check(this);
	}
}
