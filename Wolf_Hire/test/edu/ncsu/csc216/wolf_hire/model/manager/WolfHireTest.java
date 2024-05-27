package edu.ncsu.csc216.wolf_hire.model.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_hire.model.command.Command;
import edu.ncsu.csc216.wolf_hire.model.command.Command.CommandValue;
/**
 * This class is used to test its respective class
 * @author abhig
 *
 */
public class WolfHireTest {
	/** this method tests if the addNewPosition test is working */
	@Test
	public void testAddNewPosition() {
		WolfHire.getInstance().resetManager();
		WolfHire.getInstance().addNewPosition("CSC 216", 10, 10);
		assertEquals("CSC 216", WolfHire.getInstance().getPositionList()[0]);
	}
	
	/** test for loadPositionsFromFile method */
	@Test
	public void testLoadPositionsFromFile() {
		WolfHire.getInstance().resetManager();
		assertNull(WolfHire.getInstance().getActivePositionName());
		ArrayList<Position> loadedPositions = WolfHire.getInstance().loadPositionsFromFile("test-files/positions1.txt");
		assertEquals(loadedPositions.get(0).getPositionName(), "CSC 216 PTF");
		assertEquals(WolfHire.getInstance().getActivePositionName(), "CSC 216 PTF");
	}
	
//	@Test
//	public void testGetApplicationsArray() {
//		String[][] test = WolfHire.getInstance().getApplicationsAsArray("All");
//		assertEquals(test[0][0], "CSC 216 PTF");
//	}
	/** test for getApplicationsById method */
	@Test
	public void testGetApplicationsById() {
		WolfHire.getInstance().resetManager();
//		assertNull(WolfHire.getInstance().getApplicationById(200));
		ArrayList<Position> loadedPositions = WolfHire.getInstance().loadPositionsFromFile("test-files/positions1.txt");
		assertEquals(loadedPositions.get(0).getPositionName(), "CSC 216 PTF");
		assertEquals(WolfHire.getInstance().getApplicationById(2).getState(), "Submitted");
	}
	/** test for executeCommand and addApplicationToPosition methods */
	@Test
	public void testExecuteCommandAndAddApplication() {
		WolfHire.getInstance().resetManager();
//		assertNull(WolfHire.getInstance().getApplicationById(200));
		ArrayList<Position> loadedPositions = WolfHire.getInstance().loadPositionsFromFile("test-files/positions1.txt");
		assertEquals(loadedPositions.get(0).getPositionName(), "CSC 216 PTF");
		assertEquals(WolfHire.getInstance().getApplicationById(2).getState(), "Submitted");
		Command c = new Command(CommandValue.ASSIGN, "Heckman");
		WolfHire.getInstance().executeCommand(2, c);
		assertEquals(WolfHire.getInstance().getActivePosition().getApplicationById(2).getState(), "Reviewing");
		WolfHire.getInstance().addApplicationToPosition("Abhi", "Reddy", "arnyalam");
		assertEquals("Abhi", WolfHire.getInstance().getApplicationById(12).getFirstName());
	}
	/** test for DeleteApplicationsById method */
	@Test
	public void testDeleteApplicationById() {
		WolfHire.getInstance().resetManager();
//		assertNull(WolfHire.getInstance().getApplicationById(200));
		ArrayList<Position> loadedPositions = WolfHire.getInstance().loadPositionsFromFile("test-files/positions1.txt");
		assertEquals(loadedPositions.get(0).getPositionName(), "CSC 216 PTF");
		assertEquals(WolfHire.getInstance().getApplicationById(2).getState(), "Submitted");
		WolfHire.getInstance().deleteApplicationById(2);
		assertEquals(null, WolfHire.getInstance().getApplicationById(2));
		
	}
	
	/** test for the method getApplicationsAsArray */
	@Test
	public void testGetApplicationsAsArray() {
		WolfHire.getInstance().resetManager();
		WolfHire.getInstance().loadPositionsFromFile("test-files/positions1.txt");
		String[][] applicationArrayAll = WolfHire.getInstance().getApplicationsAsArray("All");
		assertEquals(applicationArrayAll[0][0], "2");
		assertEquals(applicationArrayAll[0][1], "Submitted");
		assertEquals(applicationArrayAll[0][2], "cschmid");
		assertEquals(applicationArrayAll[0][3], "");
		
		String[][] applicationArrayProcessing = WolfHire.getInstance().getApplicationsAsArray("Processing");
		assertEquals(applicationArrayProcessing[0][0], "11");
		assertEquals(applicationArrayProcessing[0][1], "Processing");
		assertEquals(applicationArrayProcessing[0][2], "qmullen");
		assertEquals(applicationArrayProcessing[0][3], "sesmith5");
	}
}
