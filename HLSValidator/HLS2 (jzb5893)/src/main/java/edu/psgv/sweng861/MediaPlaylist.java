package edu.psgv.sweng861;

import java.util.ArrayList;
/**
 * The MediaPlaylist class is a single playlist.
 * @author janantoniofavisbalangue
 *
 */
public class MediaPlaylist extends PlayList {
	/**
	 * The MediaPlaylist constructor calls the Playlist constructor.
	 * @param sURL
	 * @param lines
	 */
	MediaPlaylist(String sURL, ArrayList<String> lines) {
		super(sURL, lines);
	}

}
