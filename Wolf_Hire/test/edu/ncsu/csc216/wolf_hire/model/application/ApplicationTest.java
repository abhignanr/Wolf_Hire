package edu.ncsu.csc216.wolf_hire.model.application;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_hire.model.command.Command;
import edu.ncsu.csc216.wolf_hire.model.command.Command.CommandValue;

/**
 * This class is used to test its respective class
 * @author abhig
 *
 */
public class ApplicationTest {
	
	/**  A constant string for the rejection reason of "Qualifications". */
	public static final String QUALIFICATIONS_REJECTION = "Qualifications";
	/** A constant string for the rejection reason of "Incomplete". */
	public static final String INCOMPLETE_REJECTION = "Incomplete";
	/** A constant string for the rejection reason of "Positions". */
	public static final String POSITIONS_REJECTION = "Positions";
	/**  A constant string for the rejection reason of "Duplicate" */
	public static final String DUPLICATE_REJECTION = "Duplicate";
	/** A constant string for the priority of "Completed". */
	public static final String COMPLETED_TERMINATION = "Completed";
	/**  A constant string for the priority of "Resigned". */
	public static final String RESIGNED_TERMINATION = "Resigned";
	/** A constant string for the priority of "Fired". */
	public static final String FIRED_TERMINATION = "Fired";

	/** tests the constructor to check if its working appropriately */
	@Test
	public void testApplication() {
		Exception e = assertThrows(IllegalArgumentException.class,
				() -> new Application(null, "Abhi", "Nyalamadugu"));
				assertTrue(e.getMessage().equals("Application cannot be created."));
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Application(1, null, "abc", "def", "ghi", "jkl", "mno"));
					assertTrue(e1.getMessage().equals("Application cannot be created."));
					
		Application a = assertDoesNotThrow(
				() -> new Application("Abhignan", "Nyalamadugu", "arnyalam"),
				"Should not throw exception");
		assertEquals("Abhignan", a.getFirstName(), "Incorrect value");
		assertEquals("Nyalamadugu", a.getSurname(), "Incorrect value");
		assertEquals("arnyalam", a.getUnityId(), "Incorrect value");
		
		Application a1 = assertDoesNotThrow(
				() -> new Application(1, "Reviewing", "Abhignan", "Nyalamadugu", "arnyalam", "Heckman", null),
				"Should not throw exception");
		assertEquals(1, a1.getId(), "Incorrect value");
		assertEquals("Reviewing", a1.getState(), "Incorrect value");
		assertEquals("Abhignan", a1.getFirstName(), "Incorrect value");
		assertEquals("Nyalamadugu", a1.getSurname(), "Incorrect value");
		assertEquals("arnyalam", a1.getUnityId(), "Incorrect value");
		assertEquals("Heckman", a1.getReviewer(), "Incorrect value");
		assertEquals("", a1.getNote(), "Incorrect value");
		
		assertThrows(IllegalArgumentException.class,
				() -> new Application(1, "Submitted", "Abhignan", "Nyalamadugu", "arnyalam", "Heckman", "abc"),
				"Should throw exception");
		assertThrows(IllegalArgumentException.class,
				() -> new Application(1, "Reviewing", "Abhignan", "Nyalamadugu", "arnyalam", null, "abc"),
				"Should throw exception");
		assertThrows(IllegalArgumentException.class,
				() -> new Application(1, "Reviewing", "Abhignan", "Nyalamadugu", "arnyalam", "abc", "abc"),
				"Should  throw exception");
		assertThrows(IllegalArgumentException.class,
				() -> new Application(1, "Rejected", "Abhignan", "Nyalamadugu", "arnyalam", null, null),
				"Should throw exception");
		
		assertDoesNotThrow(
				() -> new Application(1, "Inactive", "Abhignan", "Nyalamadugu", "arnyalam", "asdsa", FIRED_TERMINATION),
				"Should not throw exception");
		assertDoesNotThrow(
				() -> new Application(1, "Interviewing", "Abhignan", "Nyalamadugu", "arnyalam", "Heckman", null),
				"Should not throw exception");
		assertDoesNotThrow(
				() -> new Application(1, "Processing", "Abhignan", "Nyalamadugu", "arnyalam", "Heckman", null),
				"Should not throw exception");
		assertDoesNotThrow(
				() -> new Application(1, "Hired", "Abhignan", "Nyalamadugu", "arnyalam", "Heckman", null),
				"Should not throw exception");
		Application a2 = assertDoesNotThrow(
				() -> new Application(1, "Rejected", "Abhignan", "Nyalamadugu", "arnyalam", null, DUPLICATE_REJECTION),
				"Should not throw exception");
		assertEquals("", a2.getReviewer());
		assertEquals(DUPLICATE_REJECTION, a2.getNote());
	}
	
	/** tests the toString method to check if it is working properly */
	@Test
	public void testToString() {
		Application a1 = assertDoesNotThrow(
				() -> new Application(1, "Reviewing", "Abhignan", "Nyalamadugu", "arnyalam", "Heckman", null),
				"Should not throw exception");
		assertEquals("* 1,Reviewing,Abhignan,Nyalamadugu,arnyalam,Heckman,", a1.toString(), "Incorrect value");
	}
	
	/** tests the update method to check if the state transition is happening correctly */
	@Test
	public void testUpdate() {
		Application a1 = assertDoesNotThrow(
				() -> new Application(1, "Reviewing", "Abhignan", "Nyalamadugu", "arnyalam", "Heckman", null),
				"Should not throw exception");
		Command c = new Command(CommandValue.SCHEDULE, null);
		a1.update(c);
		assertEquals("Interviewing", a1.getState());
		
		Application a2 = assertDoesNotThrow(
				() -> new Application(1, "Reviewing", "Abhignan", "Nyalamadugu", "arnyalam", "Heckman", null),
				"Should not throw exception");
		Command c1 = new Command(CommandValue.RETURN, null);
		a2.update(c1);
		assertEquals("Submitted", a2.getState());
		
		Application a3 = assertDoesNotThrow(
				() -> new Application(1, "Reviewing", "Abhignan", "Nyalamadugu", "arnyalam", "Heckman", null),
				"Should not throw exception");
		Command c2 = new Command(CommandValue.REJECT, DUPLICATE_REJECTION);
		a3.update(c2);
		assertEquals("Rejected", a3.getState());
	}
	
	/** tests the update method to check if the state transition is happening correctly */
	@Test
	public void testUpdateRejected() {
		Application a1 = assertDoesNotThrow(
				() -> new Application(1, "Rejected", "Abhignan", "Nyalamadugu", "arnyalam", null, POSITIONS_REJECTION),
				"Should not throw exception");
		Command c = new Command(CommandValue.RESUBMIT, null);
		a1.update(c);
		assertEquals("Submitted", a1.getState());
	}
	
	/** tests the update method to check if the state transition is happening correctly */
	@Test
	public void testUpdateSubmitted() {
		Application a1 = assertDoesNotThrow(
				() -> new Application(1, "Submitted", "Abhignan", "Nyalamadugu", "arnyalam", null, null),
				"Should not throw exception");
		Command c = new Command(CommandValue.ASSIGN, "abc");
		a1.update(c);
		assertEquals("Reviewing", a1.getState());
		
		Application a2 = assertDoesNotThrow(
				() -> new Application(1, "Submitted", "Abhignan", "Nyalamadugu", "arnyalam", null, null),
				"Should not throw exception");
		Command c1 = new Command(CommandValue.REJECT, POSITIONS_REJECTION);
		a2.update(c1);
		assertEquals("Rejected", a2.getState());
	}
	
	/** tests the update method to check if the state transition is happening correctly */
	@Test
	public void testUpdateInterviewing() {
		Application a1 = assertDoesNotThrow(
				() -> new Application(1, "Interviewing", "Abhignan", "Nyalamadugu", "arnyalam", "Heckman", null),
				"Should not throw exception");
		Command c = new Command(CommandValue.SCHEDULE, null);
		a1.update(c);
		assertEquals("Interviewing", a1.getState());
		
		Application a2 = assertDoesNotThrow(
				() -> new Application(1, "Interviewing", "Abhignan", "Nyalamadugu", "arnyalam", "Heckman", null),
				"Should not throw exception");
		Command c1 = new Command(CommandValue.ASSIGN, "abc");
		a2.update(c1);
		assertEquals("Reviewing", a2.getState());
		
		Application a3 = assertDoesNotThrow(
				() -> new Application(1, "Interviewing", "Abhignan", "Nyalamadugu", "arnyalam", "Heckman", null),
				"Should not throw exception");
		Command c2 = new Command(CommandValue.REJECT, DUPLICATE_REJECTION);
		a3.update(c2);
		assertEquals("Rejected", a3.getState());
		
		Application a4 = assertDoesNotThrow(
				() -> new Application(1, "Interviewing", "Abhignan", "Nyalamadugu", "arnyalam", "Heckman", null),
				"Should not throw exception");
		Command c3 = new Command(CommandValue.RESUBMIT, null);
		assertThrows(UnsupportedOperationException.class,
				() -> a4.update(c3),
				"Should throw exception");
		
	}
	
	/** tests the update method to check if the state transition is happening correctly */
	@Test
	public void testUpdateProcessing() {
		
		Application a2 = assertDoesNotThrow(
				() -> new Application(1, "Processing", "Abhignan", "Nyalamadugu", "arnyalam", "Heckman", null),
				"Should not throw exception");
		Command c1 = new Command(CommandValue.HIRE, null);
		a2.update(c1);
		assertEquals("Hired", a2.getState());
		
		Application a3 = assertDoesNotThrow(
				() -> new Application(1, "Processing", "Abhignan", "Nyalamadugu", "arnyalam", "Heckman", null),
				"Should not throw exception");
		Command c2 = new Command(CommandValue.REJECT, DUPLICATE_REJECTION);
		a3.update(c2);
		assertEquals("Rejected", a3.getState());
	}
	
	/** tests the update method to check if the state transition is happening correctly */
	@Test
	public void testUpdateHired() {
		Application a1 = assertDoesNotThrow(
				() -> new Application(1, "Hired", "Abhignan", "Nyalamadugu", "arnyalam", "adsa", null),
				"Should not throw exception");
		Command c = new Command(CommandValue.TERMINATE, FIRED_TERMINATION);
		a1.update(c);
		assertEquals("Inactive", a1.getState());
	}
	
	/** tests the update method to check if the state transition is happening correctly */
	@Test
	public void testUpdateInactive() {
		Application a1 = assertDoesNotThrow(
				() -> new Application(1, "Inactive", "Abhignan", "Nyalamadugu", "arnyalam", "abc", COMPLETED_TERMINATION),
				"Should not throw exception");
		Command c = new Command(CommandValue.TERMINATE, "asdasd");
		assertThrows(UnsupportedOperationException.class,
				() -> a1.update(c));
	}
		
}

