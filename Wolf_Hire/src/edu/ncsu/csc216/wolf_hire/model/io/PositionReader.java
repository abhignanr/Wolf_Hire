/**
 * 
 */
package edu.ncsu.csc216.wolf_hire.model.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.wolf_hire.model.application.Application;
import edu.ncsu.csc216.wolf_hire.model.manager.Position;

/**
 * This class handles the Position IO, specifically the read part
 * @author Abhignan Reddy Nyalamadugu
 */
public class PositionReader {
	/**
	 * This method reads the filename from the parameter and returns a list 
	 * of positions
	 * @param fileName name of file to be read
	 * @return Arraylist of positions
	 * @throws IllegalArgumentException If the file cannot be loaded because it doesn’t exist, the 
	 * method will throw an IllegalArgumentException with the message “Unable to load file ."
	 */
	public static ArrayList<Position> readPositionFile(String fileName) {
		ArrayList<Position> positions = new ArrayList<Position>();
		String file = "";
		try {
			Scanner fileReader = new Scanner(new FileInputStream(fileName));
			while (fileReader.hasNextLine()) {
				file = file + fileReader.nextLine();
				if (fileReader.hasNextLine()) {
					file += "\n";
				}
			}
//			if (!"#".equals(file.charAt(0))) {
//				return positions;
//			}
			Scanner scnr = new Scanner(file);
			scnr.useDelimiter("\\r?\\n?[#]");
			while (scnr.hasNext()) {
				try {
				positions.add(processPosition(scnr.next()));
				} catch (IllegalArgumentException e) {
					// should ignore exception so that it can proceed with the next position
				}
			}
		} catch (FileNotFoundException f) {
			throw new IllegalArgumentException("Unable to load file .");
		}
		
		
		return positions;
 }

	/** 
	 * helper method for readPositionFile
	 * @param position Position to be processed
	 * @return positionobject which is processed from string parameter
	 */
	private static Position processPosition(String position) {
		Scanner scnr = new Scanner(position).useDelimiter(",");
		Position p = processPositionLine(scnr.nextLine());
		while(scnr.hasNextLine()) {
			try {
				p.addApplication(processApplication(scnr.nextLine()));
			} catch (IllegalArgumentException e) {
				//should ignore exception so that it can proceed with the next application
			}
		}
		scnr.close();
		if (p.getApplications().size() == 0) {
			throw new IllegalArgumentException();
		}
		return p;
	}
	
	/**
	 * Helper method for processPosition method
	 * @param positionLine Processes just the position line
	 * @return position object
	 */
	private static Position processPositionLine(String positionLine) {
		String positionLineUpdated = positionLine.substring(1, positionLine.length());
		positionLineUpdated = positionLineUpdated.trim();
		Scanner scnr = new Scanner(positionLineUpdated).useDelimiter(",");
		String name = "";
		int hrs = 0;
		int pay = 0;
		try {
		name = scnr.next();
		hrs =  Integer.parseInt(scnr.next());
		pay = Integer.parseInt(scnr.next());
		} catch (NoSuchElementException n) {
			//do nothing
		}
		Position position = new Position(name, hrs, pay);
		scnr.close();
		return position;
	}
	
	/**
	 * helper method for process position
	 * @param applicationLine application string to be processed into application object
	 * @return application object
	 */
	private static Application processApplication(String applicationLine) {
		String applicationLineUpdated = applicationLine.substring(2, applicationLine.length());
		applicationLineUpdated = applicationLineUpdated.trim();
		Scanner scnr = new Scanner(applicationLineUpdated).useDelimiter(",");
		int id =  Integer.parseInt(scnr.next());
		String state = scnr.next();
		String firstName = scnr.next();
		String surname = scnr.next();
		String unityId = scnr.next();
		String reviewer = scnr.next();
		String note = null;
		try {
			note = scnr.next();
		} catch (NoSuchElementException n) {
			note = null;
		}
		scnr.close();
		Application application = new Application(id, state, firstName, surname, unityId, reviewer, note);
		return application;	
	}
	
}
