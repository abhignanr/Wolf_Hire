package edu.ncsu.csc216.wolf_hire.model.io;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_hire.model.application.Application;
import edu.ncsu.csc216.wolf_hire.model.manager.Position;
/**
 * This class is used to test its respective class
 * @author abhig
 *
 */
public class PositionReaderTest {
	/** constant to store path of valid test file */
	private final String validTestFile = "test-files/positions2.txt";

	/** failing test */
	@Test
	public void testReadPositionFile() {
		Application.setCounter(0);
		try {
			ArrayList<Position> positions = PositionReader.readPositionFile(validTestFile);
			assertEquals(4, positions.size());
			assertEquals(3, positions.get(2).getApplications().size());
			
		} catch (IllegalArgumentException e) {
			System.out.println("fail");
		}
	}
}
