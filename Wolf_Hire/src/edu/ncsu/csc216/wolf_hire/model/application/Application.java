/**
 * 
 */
package edu.ncsu.csc216.wolf_hire.model.application;

import edu.ncsu.csc216.wolf_hire.model.command.Command;
import edu.ncsu.csc216.wolf_hire.model.command.Command.CommandValue;

/**
 * This class handles the Application functionality and also implements an FSM to control the state of an application and 
 *  maintains the state of an application
 * @author Abhignan Reddy Nyalamadugu
 *
 */
public class Application {

	/** A constant string for the submitted state’s name with the value "Submitted" */
	public static final String SUBMITTED_NAME = "Submitted";
	/** A constant string for the rejected state’s name with the value "Rejected" */
	public static final String REJECTED_NAME = "Rejected";
	/** A constant string for the reviewing state’s name with the value "Reviewing" */
	public static final String REVIEWING_NAME = "Reviewing";
	/** A constant string for the interviewing state’s name with the value "Interviewing". */
	public static final String INTERVIEWING_NAME = "Interviewing";
	/** A constant string for the processing state’s name with the value "Processing". */
	public static final String PROCESSING_NAME = "Processing";
	/** A constant string for the hired state’s name with the value "Hired". */
	public static final String HIRED_NAME = "Hired";
	/** A constant string for the inactive state’s name with the value "Inactive". */
	public static final String INACTIVE_NAME = "Inactive";
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
	
	/** Unique id for an application. */
	public int applicationId;
	//Current state for the application */
	//public ApplicationState state;
	/** First name of the applicant. */
	public String firstname;
	/** Surname of the applicant. */
	public String surname;
	/** Unity id of the applicant. */
	public String unityId;
	/** Reviewer assigned to review the application. */
	public String reviewer;
	/**  Contains the rejection reason OR termination reason for the application, if appropriate. */
	public String note;
	/** counter for number of applications*/
	public static int counter;
	
	/** instance of ApplicationState to maintain state of Application */
	private ApplicationState currentState;
	/** instance of SubmittedState */
	private ApplicationState submittedState = new SubmittedState();
	/** instance of RejectedState */
	private ApplicationState rejectedState = new RejectedState();
	/** instance of ReviewingState */
	private ApplicationState reviewingState = new ReviewingState();
	/** instance of InterviewingState */
	private ApplicationState interviewingState = new InterviewingState();
	/** instance of ProcessingState */
	private ApplicationState processingState = new ProcessingState();
	/** instance of HiredState */
	private ApplicationState hiredState = new HiredState();
	/** instance of InactiveState */
	private ApplicationState inactiveState = new InactiveState();
	
	/**
	 * This is the constructor for Application
	 * @param firstName the first name of applicant
	 * @param surname the surname of applicant
	 * @param unityId the unityid of applicant
	 * @throws IllegalArgumentException  If any of the parameters are null or empty strings,
	 *  then an IllegalArgumentException is thrown
	 */
	public Application(String firstName, String surname, String unityId) {
		if ("".equals(firstName) || firstName == null || "".equals(surname) || surname == null || "".equals(unityId) || unityId == null) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		setFirstName(firstName);
		setSurname(surname);
		setUnityId(unityId);
		setId(counter);
		incrementCounter();
		setState(SUBMITTED_NAME);
	}
	
	/**
	 * This is the constructor for Application
	 * @param id the id of applicant
	 * @param state state of the Application instance
	 * @param firstName the first name of applicant
	 * @param surname the surname of applicant
	 * @param unityId the unityid of applicant
	 * @param reviewer the reviewer of application
	 * @param note note maintaing string information
	 * @throws IllegalArgumentException if invalid  parameters are given
	 */
	public Application(int id, String state, String firstName, String surname, String unityId, String reviewer, String note) {
		if ("".equals(firstName) || firstName == null || "".equals(surname) || surname == null || "".equals(unityId) || unityId == null || "".equals(state) || state == null || id < 1) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		
		setFirstName(firstName);
		setSurname(surname);
		setUnityId(unityId);
		setReviewer(reviewer);
		if ((state.equals(SUBMITTED_NAME) || state.equals(REJECTED_NAME))  && reviewer != null && !"".equals(reviewer)) {
//			if (reviewer != null) {
			throw new IllegalArgumentException("Application cannot be created.");
//			}
		}
		if ((state.equals(REVIEWING_NAME) || state.equals(INTERVIEWING_NAME) || state.equals(PROCESSING_NAME) || state.equals(HIRED_NAME) || state.equals(INACTIVE_NAME)) && (reviewer == null || "".equals(reviewer))) {
//			if (reviewer == null) {
				throw new IllegalArgumentException("Application cannot be created.");
//			}
		}
		setState(state);
		if ((state.equals(SUBMITTED_NAME) || state.equals(REVIEWING_NAME) || state.equals(INTERVIEWING_NAME) || state.equals(PROCESSING_NAME) || state.equals(HIRED_NAME)) && note != null) {
//			if (note != null) {
			throw new IllegalArgumentException("Application cannot be created.");
//			}
		}
//		else if ((state == REJECTED_NAME && note == null) || (state == REJECTED_NAME && !note.equals(QUALIFICATIONS_REJECTION)) && (state == REJECTED_NAME && !note.equals(POSITIONS_REJECTION)) && (state == REJECTED_NAME && !note.equals(DUPLICATE_REJECTION)) && (state == REJECTED_NAME && !note.equals(INCOMPLETE_REJECTION)) && (state == REJECTED_NAME && !note.equals(POSITIONS_REJECTION))) {
		if (state.equals(REJECTED_NAME)) {
			if (note == null) {
			throw new IllegalArgumentException("Application cannot be created.");
			}
			else if (!(note.equals(DUPLICATE_REJECTION) || note.equals(POSITIONS_REJECTION) || note.equals(INCOMPLETE_REJECTION) || note.equals(QUALIFICATIONS_REJECTION))) {
				throw new IllegalArgumentException("Application cannot be created.");
			}
		}
		if (state.equals(INACTIVE_NAME)) {
			if (note == null) {
			throw new IllegalArgumentException("Application cannot be created.");
			}
			else if (!(note.equals(COMPLETED_TERMINATION) || note.equals(FIRED_TERMINATION) || note.equals(RESIGNED_TERMINATION) )) {
				throw new IllegalArgumentException("Application cannot be created.");
			}
		}
		setNote(note);
		if (id < counter) {
			setId(id);
		}
		else if (id > applicationId) {
			setCounter(id + 1);
		}
		else {
			incrementCounter();
		}
		setId(id);
//		incrementCounter();
	}

	/**
	 * Getter for applicationId
	 * @return the applicationId
	 */
	public int getId() {
		return applicationId;
	}

	/**
	 * setter for applicationId
	 * @param applicationId the applicationId to set
	 */
	private void setId(int applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * getter for state
	 * @return the state
	 */
	public String getState() {
		return currentState.getStateName();
	}

	/**
	 * setter for state
	 * @param stateValue the state to set
	 */
	private void setState(String stateValue) {
		switch (stateValue) {
			case SUBMITTED_NAME :
				currentState = submittedState;
				break;
			case REJECTED_NAME :
				currentState = rejectedState;
				break;
			case REVIEWING_NAME :
				currentState = reviewingState;
				break;
			case INTERVIEWING_NAME :
				currentState = interviewingState;
				break;
			case PROCESSING_NAME :
				currentState = processingState;
				break;
			case HIRED_NAME :
				currentState = hiredState;
				break;
			case INACTIVE_NAME :
				currentState = inactiveState;
				break;
			default:
				break;
		}
	}

	/**
	 * getter for first name
	 * @return the first name
	 */
	public String getFirstName() {
		return firstname;
	}

	/**
	 * setter for first name
	 * @param firstname the first name to set
	 */
	private void setFirstName(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * getter for surname
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * setter for surname
	 * @param surname the surname to set
	 */
	private void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * getter for unityId
	 * @return the unityId
	 */
	public String getUnityId() {
		return unityId;
	}

	/**
	 * setter for unityId
	 * @param unityId the unityId to set
	 */
	private void setUnityId(String unityId) {
		this.unityId = unityId;
	}

	/**
	 * getter for reviewer
	 * @return the reviewer
	 */
	public String getReviewer() {
		if (reviewer == null) {
			return "";
		}
		return reviewer;
	}

	/**
	 * setter for reviewer
	 * @param reviewer the reviewer to set
	 */
	private void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	/**
	 * getter for note
	 * @return the note
	 */
	public String getNote() {
		if (note == null) {
			return "";
		}
		return note;
	}

	/**
	 * setter for note
	 * @param note the note to set
	 */
	private void setNote(String note) {
		this.note = note;
	}
	
	/**
	 * This method increments the counter field
	 */
	public static void incrementCounter() {
		++counter;
	}
	
	/**
	 * This method sets the counter field to the parameter value
	 * @param newCount new number to set counter field to
	 */
	public static void setCounter(int newCount) {
		counter = newCount;
	}
	
	@Override
	public String toString() {
		return "* " + applicationId + "," + currentState.getStateName() + "," + firstname
				+ "," + surname + "," + unityId + "," + getReviewer() + "," + getNote();
	}
	
	/**
	 * This method updates the state with given command
	 * @param c command to be updated with
	 * @throws UnsupportedOperationException if state is not appropriate for FSM
	 */
	public void update(Command c) {
		currentState.updateState(c);
	}
	
	/**
	 * Interface for states in the Application State Pattern.  All 
	 * concrete Application states must implement the ApplicationState interface.
	 * The ApplicationState interface should be a private interface of the 
	 * Application class.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu) 
	 */
	public interface ApplicationState {
		
		/**
		 * Update the Application from the given Command.
		 * An UnsupportedOperationException is thrown if the Command
		 * is not a valid action for the given state.  
		 * @param command Command describing the action that will update the Application's
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state with message "Invalid command.".
		 */
		void updateState(Command command);
		
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		String getStateName();

	}
	
	/**
	 * This is an inner class of the class Application. This class
	 * maintains one of the seven states.
	 * @author abhig
	 *
	 */
	private class SubmittedState implements ApplicationState {
		
		/**
		 * This method is used to update the state field of the Application class
		 * @param command This is the command to be implemented
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state with message "Invalid command.".
		 */
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.ASSIGN) { //&& command.getCommandInformation() != null) {
				currentState = reviewingState;
				reviewer = command.getCommandInformation();
			}
			else if (command.getCommand() == CommandValue.REJECT) {
				if (!(command.getCommandInformation().equals(DUPLICATE_REJECTION) || command.getCommandInformation().equals(POSITIONS_REJECTION) || command.getCommandInformation().equals(INCOMPLETE_REJECTION) || command.getCommandInformation().equals(QUALIFICATIONS_REJECTION))) {
					throw new UnsupportedOperationException("Invalid command.");
				}
				currentState = rejectedState;
				note = command.getCommandInformation();
			}
			else {
				throw new UnsupportedOperationException("Invalid command.");
			}
		}
		
		/**
		 * This method is used to find the state of an instance of the application class
		 * @return returns name of state application is on
		 */
		public String getStateName() {
			return "Submitted";
			
		}
	}
	
	/**
	 * This is an inner class of the class Application. This class
	 * maintains one of the seven states.
	 * @author abhig
	 *
	 */
	private class RejectedState implements ApplicationState {
		
		/**
		 * This method is used to update the state field of the Application class
		 * @param command This is the command to be implemented
		 */
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.RESUBMIT) {
				note = null;
				currentState = submittedState;
			}
			else {
				throw new UnsupportedOperationException("Invalid command.");
			}
		}
		
		/**
		 * This method is used to find the state of an instance of the application class
		 * @return returns name of state application is on
		 */
		public String getStateName() {
			return "Rejected";
			
		}
	}
	
	/**
	 * This is an inner class of the class Application. This class
	 * maintains one of the seven states.
	 * @author abhig
	 *
	 */
	private class ReviewingState implements ApplicationState {
		
		/**
		 * This method is used to update the state field of the Application class
		 * @param command This is the command to be implemented
		 */
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.SCHEDULE) { 
				currentState = interviewingState;
			}
			else if (command.getCommand() == CommandValue.RETURN) {
				reviewer = null;
				currentState = submittedState;
			}
			else if (command.getCommand() == CommandValue.REJECT) {
				if (!(command.getCommandInformation().equals(DUPLICATE_REJECTION) || command.getCommandInformation().equals(POSITIONS_REJECTION) || command.getCommandInformation().equals(INCOMPLETE_REJECTION) || command.getCommandInformation().equals(QUALIFICATIONS_REJECTION))) {
					throw new UnsupportedOperationException("Invalid command.");
				}
				reviewer = null;
				currentState = rejectedState;
				note = command.getCommandInformation();
			}
			else {
				throw new UnsupportedOperationException("Invalid command.");
			}
		}
		
		/**
		 * This method is used to find the state of an instance of the application class
		 * @return returns name of state application is on
		 */
		public String getStateName() {
			return "Reviewing";
			
		}
	}
	
	/**
	 * This is an inner class of the class Application. This class
	 * maintains one of the seven states.
	 * @author abhig
	 *
	 */
	private class InterviewingState implements ApplicationState {
		
		/**
		 * This method is used to update the state field of the Application class
		 * @param command This is the command to be implemented
		 */
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.PROCESS) { 
				currentState = processingState;
			}
			else if (command.getCommand() == CommandValue.ASSIGN) { 
				currentState = reviewingState;
				reviewer = command.getCommandInformation();
			}
			else if (command.getCommand() == CommandValue.SCHEDULE) {
				currentState = interviewingState;
			}
			else if (command.getCommand() == CommandValue.REJECT) {
				if (!(command.getCommandInformation().equals(DUPLICATE_REJECTION) || command.getCommandInformation().equals(POSITIONS_REJECTION) || command.getCommandInformation().equals(INCOMPLETE_REJECTION) || command.getCommandInformation().equals(QUALIFICATIONS_REJECTION))) {
					throw new UnsupportedOperationException("Invalid command.");
				}
				reviewer = null;
				currentState = rejectedState;
				note = command.getCommandInformation();
			}
			else {
				throw new UnsupportedOperationException("Invalid command.");
			}
		}
		
		/**
		 * This method is used to find the state of an instance of the application class
		 * @return returns name of state application is on
		 */
		public String getStateName() {
			return "Interviewing";
			
		}
	}
	
	/**
	 * This is an inner class of the class Application. This class
	 * maintains one of the seven states.
	 * @author abhig
	 *
	 */
	private class ProcessingState implements ApplicationState {
		
		/**
		 * This method is used to update the state field of the Application class
		 * @param command This is the command to be implemented
		 */
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.HIRE) {
				currentState = hiredState;
			}
			else if (command.getCommand() == CommandValue.REJECT) {
				if (!(command.getCommandInformation().equals(DUPLICATE_REJECTION) || command.getCommandInformation().equals(POSITIONS_REJECTION) || command.getCommandInformation().equals(INCOMPLETE_REJECTION) || command.getCommandInformation().equals(QUALIFICATIONS_REJECTION))) {
					throw new UnsupportedOperationException("Invalid command.");
				}
				reviewer = null;
				currentState = rejectedState;
				note = command.getCommandInformation();
			}
			else {
				throw new UnsupportedOperationException("Invalid command.");
			}
		}
		
		/**
		 * This method is used to find the state of an instance of the application class
		 * @return returns name of state application is on
		 */
		public String getStateName() {
			return "Processing";
			
		}
	}
	
	/**
	 * This is an inner class of the class Application. This class
	 * maintains one of the seven states.
	 * @author abhig
	 *
	 */
	private class HiredState implements ApplicationState {
		
		/**
		 * This method is used to update the state field of the Application class
		 * @param command This is the command to be implemented
		 */
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.TERMINATE && (command.getCommandInformation().equals(COMPLETED_TERMINATION) || command.getCommandInformation().equals(FIRED_TERMINATION) || command.getCommandInformation().equals(RESIGNED_TERMINATION))) { 
				currentState = inactiveState;
				note = command.getCommandInformation();
			}
			else {
				throw new UnsupportedOperationException("Invalid command.");
			}
		}
		
		/**
		 * This method is used to find the state of an instance of the application class
		 * @return returns name of state application is on
		 */
		public String getStateName() {
			return "Hired";
			
		}
	}
	
	/**
	 * This is an inner class of the class Application. This class
	 * maintains one of the seven states.
	 * @author abhig
	 *
	 */
	private class InactiveState implements ApplicationState {
		
		/**
		 * This method is used to update the state field of the Application class
		 * @param command This is the command to be implemented
		 */
		public void updateState(Command command) {
			throw new UnsupportedOperationException("Invalid command.");
		}
		
		/**
		 * This method is used to find the state of an instance of the application class
		 * @return returns name of state application is on
		 */
		public String getStateName() {
			String inactive = "Inactive";
			return inactive;
			
		}
	}
	
}
