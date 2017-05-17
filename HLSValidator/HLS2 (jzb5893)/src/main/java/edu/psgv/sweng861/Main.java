package edu.psgv.sweng861;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.net.URL;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * HTTP Live Streaming Validator version 2.1.2
 * 
 * by Jan Balangue
 * SWENG 861
 * 
 */
public class Main {

	private static final Logger logger = LogManager.getLogger();

	/**
	 * The main class accepts batch input or console input.
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info(">>main()");
		System.out.println("HTTP Live Streaming Validator, version 2.1.2");
		System.out.println("--------------------------------------------");
		if (args.length > 0) {
			try {
				FileReader fileReader = new FileReader(args[0]);	
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				while ((args[0] = bufferedReader.readLine()) != null) {
					if (args[0].trim().endsWith(".m3u8")) {
						try {
							openConnection(args[0]);
						} catch (MalformedURLException e){
							logger.error("Malformed URL Exception.");
						} catch (IOException e) {
							logger.error("IO Exception.");
						}
					}
				}
				bufferedReader.close();
			} catch (FileNotFoundException ex) {
				logger.error("Unable to open file '" + args[0] + "'");
			} catch (IOException ex) {
				logger.error("Error reading file '" + args[0] + "'");
			}
		} else {		
			Scanner in = new Scanner(System.in);
			String strURL;
			System.out.print("Enter URL (Q to quit): ");
			strURL = in.nextLine();
			while (!strURL.contentEquals("Q")) {
				if (strURL.endsWith(".m3u8")) {
					try {
						openConnection(strURL);
					} catch (MalformedURLException e){
						logger.error("Malformed URL Exception.");
					} catch (IOException e) {
						logger.error("IO Exception.");
					}
				} else {
					logger.info(strURL + " is not a valid playlist.");	
				}
				System.out.print("Enter URL (Q to quit): ");
				strURL = in.nextLine();
			}
			System.out.println("\nExiting validator.\n");
			in.close();
			logger.info("<<main()");
		}
	}
	
	/**
	 * The openConnection method opens a connection if responseCode = 200.
	 * @param strURL
	 * @throws IOException
	 */
	public static void openConnection(String strURL) throws IOException {
		// attempts to open a connection if response code is 200
		logger.info(">>openConnection()");
		URL objURL = new URL(strURL);
		HttpURLConnection con = (HttpURLConnection) objURL.openConnection();		
		int responseCode  = con.getResponseCode();
		if (responseCode == 200) {
			logger.debug("Response code: " + responseCode);
			con.connect();
			System.out.println("\n\nPlaylist title:\n" + strURL.toString() + "\n");
			report(strURL, con.getInputStream());
			con.disconnect();
		}
		logger.info("<<openConnection()");
	}

	/**
	 * The report method creates either a MasterPlaylist or a MediaPlaylist.
	 * @param strURL
	 * @param inputStream
	 */
	private static void report(String strURL, InputStream inputStream) {
		// displays playlist including any errors
		logger.info(">>report()");
		ArrayList<String> lines = new ArrayList<String>();
		boolean isMasterPlaylist = false;
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = bufferedReader.readLine())!= null) {
				lines.add(line);
				System.out.println(line);
				if (line.endsWith(".m3u8")){
					isMasterPlaylist = true;
					logger.debug("isMasterPlaylist="+ isMasterPlaylist);
				}
			}
			FirstLineChecker flc = new FirstLineChecker();
			if (isMasterPlaylist == true){
				MasterPlaylist pl = new MasterPlaylist(strURL, lines); //do not edit this line
			} else {
				MediaPlaylist pl = new MediaPlaylist(strURL, lines);
				flc.check(pl);
			}
		bufferedReader.close();
		} catch (Exception e){
			logger.error("Exception");
		}
		
		logger.info("<<report()");
	}
}
