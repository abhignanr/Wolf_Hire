package edu.ncsu.csc216.wolf_hire.model.manager;

import java.util.ArrayList;

import edu.ncsu.csc216.wolf_hire.model.application.Application;
import edu.ncsu.csc216.wolf_hire.model.command.Command;
import edu.ncsu.csc216.wolf_hire.model.io.PositionReader;
import edu.ncsu.csc216.wolf_hire.model.io.PositionWriter;

/**
 * This class is the main class which handles an array of positions and its functionality.
 * It puts all the poisitond and its application together and can handle any functions needed.
 * @author Abhignan Reddy Nyalamadugu
 */
public class WolfHire {
	/** list of Positions */
	private ArrayList<Position> positions = new ArrayList<Position>();
	/** this field maintains the position which is active */
	private Position activePosition;
	
	/** This is the only instance of WolfHire */
	private static WolfHire singleton;
	
	/**
	 * This method helps make sure that only one instance of WolfHire exists
	 * @return returns the one and only instance of WolfHire
	 */
	public static WolfHire getInstance() {
		if (singleton == null) {
			singleton = new WolfHire();
		}
		return singleton;
	}
	
	/**
	 * Private constructor for WolfHire to make sure only one instance is created
	 */
	private WolfHire() {
		positions = new ArrayList<Position>();
		activePosition = null;
	}
	
	/**
	 * This method adds a new position
	 * @param positionName name of position
	 * @param hoursPerWeek Work Hours per week
	 * @param payRate Rate of pay
	 * @throws IllegalArgumentException IllegalArgumentException is thrown if the positionName parameter is null,
	 *  empty string, or a duplicate of an existing position name(case-insensitive). The message is "Position
	 *   cannot be created.".
	 */
	public void addNewPosition(String positionName, int hoursPerWeek, int payRate) {
		if (positionName == null || "".equals(positionName)) {
			throw new IllegalArgumentException("Position cannot be created.");
		}
		if (positions != null) {
			for (int i = 0; i < positions.size(); i++) {
				if (positions.get(i).getPositionName().equals(positionName)) {
					throw new IllegalArgumentException("Position cannot be created.");
				}
			}
		}
		Position p = new Position(positionName, hoursPerWeek, payRate);
		if (p != null) {
			positions.add(p);
		}
		loadPosition(positionName);
	}
	
	/**
	 * This method Finds the Position with the given name in the list, makes it active or activePosition, and sets the
	 *  application id for that position so that any new Applications added to the position are created
	 *   with the next correct id
	 * @param positionName name of position
	 * @throws IllegalArgumentException IllegalArgumentException is thrown if there is no position with the
	 *  given name. The message is "Position not available.".
	 */
	public void loadPosition(String positionName) {
		if (positions.size() == 0) {
			throw new IllegalArgumentException("Position not available.");
		}
		for (int i = 0; i < positions.size(); i++) {
			if (positions.get(i).getPositionName().equals(positionName)) {
				activePosition = positions.get(i);
			}
		}
		if (!activePosition.getPositionName().equals(positionName)) {
			throw new IllegalArgumentException("Position not available.");
		}
		int temp = 1;
		for (int i = 0; i < activePosition.getApplications().size(); i++) {
			if (activePosition.getApplications().get(i).getId() >= temp) {
				temp = activePosition.getApplications().get(i).getId() + 1;
			}
		}
		Application.setCounter(temp);
	}
	
	/**
	 * Returns the activePosition or null if the is no activePosition
	 * @return returns  activePosition
	 */
	public Position getActivePosition() {
		return activePosition;
	}
	
	/**
	 *  Returns the position name for the activePosition. If the activePosition is null, then null is returned.
	 * @return returns position name
	 */
	public String getActivePositionName() {
		if (activePosition == null) {
			return null;
		}
		return activePosition.getPositionName();
	}
	
	/**
	 *  Returns a String array of position names in the order they are listed in the positions list. This is used by the GUI
	 *   to populate the position drop down. If there are no positions, the list is empty.
	 * @return Returns a String array of position names in the order they are listed in the positions list.
	 */
	public String[] getPositionList() {
		String[] positionList = new String [positions.size()];
		for (int i = 0; i < positions.size(); i++) {
			positionList[i] = positions.get(i).getPositionName();
		}
		return positionList;
	}
	
	/**
	 * Uses the PositionReader to read the given fileName. The returned list of Positions are added to the positions field.
	 *  The first position in the list returned from PositionReader is made the activePosition.
	 * @param fileName name of file to read from
	 * @return returns list of positions
	 */
	public ArrayList<Position> loadPositionsFromFile(String fileName) {
			ArrayList<Position> loadedPositions = PositionReader.readPositionFile(fileName);
			for (int i = 0; i < loadedPositions.size(); i++) {
				if (i == 0) {
					activePosition = loadedPositions.get(i);
				}
				positions.add(loadedPositions.get(i));
			}
			return loadedPositions;
	}
	
	/**
	 * This method saves the positions to the given file name
	 * @param fileName name of file to write to
	 * @throws IllegalArgumentException If the activePosition is null an IllegalArgumentException with message
	 *  "Unable to save file." should be thrown.
	 */
	public void savePositionsToFile(String fileName) {
		if (activePosition == null) {
			throw new IllegalArgumentException("Unable to save file.");
		}
		PositionWriter.writePositionsToFile(fileName, positions);
	}
	
	/**
	 * This method returns a 2D Object array that is used by the GUI
	 * @param filter used to filter Applications
	 * @return 2D object array to be used by GUI
	 */
	public String[][] getApplicationsAsArray(String filter) {
		if (activePosition != null) {
			String[][] applicationArray = new String[activePosition.getApplications().size()][4];
			if ("All".equals(filter)) {
				for (int i = 0; i < activePosition.getApplications().size(); i++) {
					applicationArray[i][0] = "" + activePosition.getApplications().get(i).getId();
					applicationArray[i][1] = activePosition.getApplications().get(i).getState();
					applicationArray[i][2] = activePosition.getApplications().get(i).getUnityId();
					applicationArray[i][3] = activePosition.getApplications().get(i).getReviewer();
				}
			}
			else {
//				int applicationCounter = 0;
//				for (int i = 0; i < activePosition.getApplications().size(); i++) {
//					if (filter.equals(activePosition.getApplications().get(i).getState())) {
//						++applicationCounter;
//					}
//				}
//				String[][] applicationArrayFiltered = new String[applicationCounter][4];
				int j = 0;
				for (int i = 0; i < activePosition.getApplications().size(); i++) {
					if (filter.equals(activePosition.getApplications().get(i).getState())) {
						applicationArray[j][0] = "" + activePosition.getApplications().get(i).getId();
						applicationArray[j][1] = activePosition.getApplications().get(i).getState();
						applicationArray[j][2] = activePosition.getApplications().get(i).getUnityId();
						applicationArray[j][3] = activePosition.getApplications().get(i).getReviewer();
						++j;
					}	
				}
			}
			return applicationArray;
		}
		return null;
	}
	
	/**
	 * This method  returns the given application or null if there is no application with the given
	 *  id in the activePosition
	 *  @param id of application to be returned
	 * @return Application instance in ActivePosition
	 */
	public Application getApplicationById(int id) {
		if (activePosition != null) {
			for(int i = 0; i < activePosition.getApplications().size(); i++) {
				if (id == activePosition.getApplications().get(i).getId()) {
					return activePosition.getApplications().get(i);
				}
			}
		}
		return null;
	}
	
//	/**
//	 * This method returns applications as a string array for the GUI to use
//	 * @return returns applications as a string array
//	 */
//	public String[][] getApplicationsAsArray() {
//		return null;
//	}
	
	/**
	 * will find the Application with the given id and update it by passing in the given
	 *  Command. If there is no Application with that id, the list doesn’t change.
	 * @param id Id of Application
	 * @param c Command value
	 */
	public void executeCommand(int id, Command c) {
		if (activePosition != null) {
			for(int i = 0; i < activePosition.getApplications().size(); i++) {
				if (id == activePosition.getApplications().get(i).getId()) {
					activePosition.getApplications().get(i).update(c);
				}
			}
		}
	}
	
	/**
	 * This method adds an application to a given position
	 * @param firstname first name of applicant
	 * @param surname surname of applicant
	 * @param unityId unityId of applicant
	 */
	public void addApplicationToPosition(String firstname, String surname, String unityId) {
		if (activePosition != null) {
			activePosition.addApplication(firstname, surname, unityId);
		}
	}
	
	/**
	 * This method deletes Application with given id
	 * @param id Id of Application
	 */
	public void deleteApplicationById(int id) {
		if (activePosition != null) {
			for(int i = 0; i < activePosition.getApplications().size(); i++) {
				if (id == activePosition.getApplications().get(i).getId()) {
					activePosition.getApplications().remove(i);
				}
			}
		}
	}
	
	/**
	 * This method is used to reset the WolfHire class
	 */
	protected void resetManager() {
		singleton = null;
	}
	
}
