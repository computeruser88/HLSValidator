package edu.psgv.sweng861;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * The MasterPlaylist class extends Playlist and aggregates MediaPlaylist.
 * @author janantoniofavisbalangue
 *
 */
public class MasterPlaylist extends PlayList {
	final Logger logger = LogManager.getLogger();
	private ArrayList<MediaPlaylist> mp = new ArrayList<MediaPlaylist>();
	private ArrayList<String> lop = new ArrayList<String>(); // lop = list of playlists
	
	/**
	 * This constructor gets the list of media playlists from the master playlist.
	 * @param sURL
	 * @param lines
	 */
	MasterPlaylist(String sURL, ArrayList<String> lines) {
		super(sURL, lines);
		lop = this.getListOfPlaylists(sURL, lines);
		for (String playlist: lop) {
			try {
				URL objURL = new URL(playlist);
				HttpURLConnection con = (HttpURLConnection) objURL.openConnection();		
				int responseCode  = con.getResponseCode();
				if (responseCode == 200) {
					con.connect();
					System.out.println("\n\nPlaylist title:\n" + playlist.toString() + "\n");
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String line;
					ArrayList<String> linelist = new ArrayList<String>();
					while ((line = bufferedReader.readLine())!= null) {
						linelist.add(line);
						System.out.println(line);
						}
					MediaPlaylist e = new MediaPlaylist(playlist, linelist);
					mp.add(e);
					FirstLineChecker flc = new FirstLineChecker();
					flc.check(e);
					bufferedReader.close();
					}
				con.disconnect();
			} catch (MalformedURLException e) {
				System.out.println("Malformed URL Exception.");
			} catch (IOException e) {
				System.out.println("IO Exception.");
			} catch (Exception e) {
				System.out.println("Exception.");
		
			}
		}
	}
	/**
	 * The getListOfPlaylists method concatenates/adds URLs from the MasterPlaylist to an arraylist and returns it.
	 * @param sURL
	 * @param lines
	 * @return
	 */
	public ArrayList<String> getListOfPlaylists(String sURL, ArrayList<String> lines) {
		ArrayList<String> listOfPlaylists = new ArrayList<String>();
		for (String line: this.lines) {
			if (line.endsWith(".m3u8"))
			{
				String newURL = sURL.substring(0,sURL.lastIndexOf("/")).concat("/").concat(line);
				listOfPlaylists.add(newURL);
				logger.debug(newURL);
			}
		}
		return listOfPlaylists;
	}
}
