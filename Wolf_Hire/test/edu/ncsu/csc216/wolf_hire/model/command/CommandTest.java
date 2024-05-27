package edu.ncsu.csc216.wolf_hire.model.command;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_hire.model.command.Command.CommandValue;
/**
 * This class is used to test its respective class
 * @author Abhignan Reddy Nyalamadugu
 *
 */
public class CommandTest {
	
	/** String constant */
	private static final String INFO = "some information";
	
	/** tests the constructor to check if its working appropriately */
	@Test
	public void testCommand() {
		Exception e = assertThrows(IllegalArgumentException.class,
				() -> new Command(null, ""));
				assertTrue(e.getMessage().equals("Invalid information."));
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Command(CommandValue.ASSIGN, ""));
					assertTrue(e1.getMessage().equals("Invalid information."));
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Command(CommandValue.RESUBMIT, "abc"));
		assertTrue(e2.getMessage().equals("Invalid information."));
		
		Command c = assertDoesNotThrow(
				() -> new Command( CommandValue.ASSIGN, "some information"),
				"Should not throw exception");
		assertEquals(CommandValue.ASSIGN, c.getCommand(), "Incorrect Command value");
		assertTrue(INFO.equals(c.getCommandInformation()));
	}
}
