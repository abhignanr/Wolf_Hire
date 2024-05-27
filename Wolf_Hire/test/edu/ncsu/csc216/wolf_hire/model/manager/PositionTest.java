package edu.ncsu.csc216.wolf_hire.model.manager;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_hire.model.application.Application;
import edu.ncsu.csc216.wolf_hire.model.command.Command;
import edu.ncsu.csc216.wolf_hire.model.command.Command.CommandValue;

/**
 * This class is used to test its respective class
 * @author abhig
 *
 */
public class PositionTest {
	/** tests the constructor to check if its working appropriately */
	@Test
	public void testApplication() {
		Exception e = assertThrows(IllegalArgumentException.class,
				() -> new Position(null, 2, 3));
				assertTrue(e.getMessage().equals("Position cannot be created."));
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Position("abc", 2, 3));
				assertTrue(e1.getMessage().equals("Position cannot be created."));
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Position("abc", 6, 3));
				assertTrue(e2.getMessage().equals("Position cannot be created."));
					
		Position p = assertDoesNotThrow(
				() -> new Position("CSC 216", 6, 8),
				"Should not throw exception");
		assertEquals("CSC 216", p.getPositionName(), "Incorrect value");
		assertEquals(6, p.getHoursPerWeek(), "Incorrect value");
		assertEquals(8, p.getPayRate(), "Incorrect value");
//		Application.setCounter(0);
	}
	
	/** tests the setApplicationId method */
	@Test
	public void testGetApplicationId() {
		Application.setCounter(0);
		Position p1 = new Position("CSC 216", 6, 8);
		p1.addApplication("Abhi", "nyalamadugu", "arnyalam");
		p1.setApplicationId();
		assertEquals(Application.counter, 1);
		Application a = new Application ("Abhig", "nyalamadugua", "aarnyalam");
		p1.addApplication(a);
		p1.setApplicationId();
		assertEquals(Application.counter, 2);
//		Application.setCounter(0);
	}
	
	/** tests the DeleteApplicationById method */
	@Test
	public void testDeleteApplicationById() {
//		Application.setCounter(0);
//		Position q = new Position("CSC 217", 6, 8);
//		q.addApplication("Carol", "Schmidt", "cschmid");
//		q.addApplication(new Application(2, "Rejected", "Clinton", "Armstrong", "carmstr", null, "Qualifications"));
//		q.addApplication(new Application(7, "Inactive", "Audrey", "Kemp", "akemp", "tnmacnei", "Fired"));
//		q.addApplication(new Application(5, "Hired", "Craig", "Armstrong", "carmstr", "tnmacnei", null));
//		q.addApplication("Cailin", "Roach", "cvroach");
//		System.out.println(q.list);
		Application.setCounter(0);
		Position p = new Position("CSC 216", 6, 8);
		p.addApplication("Abhi", "nyalamadugu", "arnyalam");
		p.setApplicationId();
		assertEquals(Application.counter, 1);
		Application a = new Application ("Abhig", "nyalamadugua", "aarnyalam");
		p.addApplication(a);
		p.setApplicationId();
		assertEquals(Application.counter, 2);
		p.deleteApplicationById(0);
		assertEquals(p.list.size(), 1);
		Application.setCounter(0);
	}
	
	/** tests the executeCommand method */
	@Test
	public void testExecuteCommand() {
		Application.setCounter(0);
		Position p = new Position("CSC 216", 6, 8);
		Command c = new Command(CommandValue.ASSIGN, "Heckman");
		p.addApplication("Abhign", "nyalamadugua", "aarnyalam");
		p.executeCommand(0, c);
		assertEquals(p.list.get(0).getState(), "Reviewing");
//		Application.setCounter(0);
	}
	
}
