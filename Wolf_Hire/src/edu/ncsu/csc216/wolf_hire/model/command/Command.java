/**
 * 
 */
package edu.ncsu.csc216.wolf_hire.model.command;

/**
 * The Command class creates objects that encapsulate user actions (or transitions) that cause the
 *  state of a Application to update. 
 * @author Abhignan Reddy Nyalamadugu
 *
 */
public class Command {
	/** This is an enumeration containing the 7 states of the FSM */
	public enum CommandValue { /** Assign */ASSIGN, /** Reject */REJECT, /** Resubmit */RESUBMIT, /** Return */RETURN, /** Schedule */SCHEDULE, /** Process */PROCESS, /** Hire */HIRE, /** Terminate */TERMINATE }

	/** field for command information */
	private String commandInformation;
	
	/** field for CommandValue */
	private CommandValue command;
	
	/**
	 * This is the constructor for the Command class
	 * @param command state to be updated
	 * @param commandImformation information of command
	 * @throws IllegalArgumentException with message "Invalid information.".
	 */
	public Command(CommandValue command, String commandImformation) {
		if (command == null) {
			throw new IllegalArgumentException("Invalid information.");
		}
		else if ((command == CommandValue.ASSIGN || command == CommandValue.REJECT || command == CommandValue.TERMINATE) && (commandImformation == null || "".equals(commandImformation))) {
//			if (commandImformation == null || "".equals(commandImformation)) {
			throw new IllegalArgumentException("Invalid information.");
//			}
		}
		else if ((command == CommandValue.RESUBMIT || command == CommandValue.RETURN || command == CommandValue.SCHEDULE || command == CommandValue.PROCESS || command == CommandValue.HIRE) && commandImformation != null) {
//			if (commandImformation != null) {
			throw new IllegalArgumentException("Invalid information.");
//			}
		}
		
		this.command = command;
		this.commandInformation = commandImformation;
		
	}

	/**
	 * getter for commandInformation
	 * @return the commandInformation
	 */
	public String getCommandInformation() {
		return commandInformation;
	}
	
	/**
	 * getter for command
	 * @return the command
	 */
	public CommandValue getCommand() {
		return command;
	}
	
}
