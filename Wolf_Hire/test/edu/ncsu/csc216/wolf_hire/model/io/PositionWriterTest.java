package edu.ncsu.csc216.wolf_hire.model.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_hire.model.application.Application;
import edu.ncsu.csc216.wolf_hire.model.manager.Position;
/**
 * This class is used to test its respective class
 * @author abhig
 *
 */
public class PositionWriterTest {
	/** failing test */
	@Test
	public void testWritePositionsToFile() {
		Application.setCounter(0);
	    ArrayList<Position> positions = new ArrayList<Position>();
	    positions.add(new Position("test", 10, 15));
	    positions.get(0).addApplication("Abhignan", "Nyalamadugu", "arnyalam");
	    
	    try {
	    	PositionWriter.writePositionsToFile("test-files/write_positions.txt", positions);
	    } catch (IllegalArgumentException e) {
	    	fail("Cannot write to Position records file");
	    }
	    checkFiles("test-files/write_expected_positions.txt", "test-files/write_positions.txt");
	}
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new File(expFile));
			 Scanner actScanner = new Scanner(new File(actFile));) {
			
			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
	
	
}
