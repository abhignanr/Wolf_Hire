package edu.ncsu.csc216.wolf_hire.model.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import edu.ncsu.csc216.wolf_hire.model.manager.Position;

/**
 * This class handles the Position IO, specifically the write part
 * @author Abhignan Reddy Nyalamadugu
 *
 */
public class PositionWriter {
	/**
	 * Writes poistions and its applications to the given filename
	 * using the toString functionality of each of them
	 * @param filename name of file to be read
	 * @param list Array list to be written to file
	 * @throws IllegalArgumentException  If there are any errors or exceptions, an 
	 * IllegalArgumentException is thrown with the message "Unable to save file."
	 */
	public static void writePositionsToFile(String filename, ArrayList<Position> list) {
		try {
		PrintStream fileWriter = new PrintStream(new File(filename));
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getApplications().size() != 0) {
			    fileWriter.println(list.get(i).toString());
			    for (int j = 0; j < list.get(i).getApplications().size(); j++) {
			    	fileWriter.println(list.get(i).getApplications().get(j).toString());
			    }
			}
			else {
				continue;
			}
		}
	
		fileWriter.close();
		} catch (FileNotFoundException f){
			throw new IllegalArgumentException("Unable to save file.");
		}
		
	}
}
