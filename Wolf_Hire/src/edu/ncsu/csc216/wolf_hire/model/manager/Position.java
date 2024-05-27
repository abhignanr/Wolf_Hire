/**
 * 
 */
package edu.ncsu.csc216.wolf_hire.model.manager;
import java.util.ArrayList;

import edu.ncsu.csc216.wolf_hire.model.application.Application;
import edu.ncsu.csc216.wolf_hire.model.command.Command;
/**
 * This class handles all the functionality related to the Positions and also maintains a list of applications along with some functionality
 * @author Abhignan Reddy Nyalamadugu
 *
 */
public class Position {
	/** This field maintains the name of the position */
	private String positionName;
	/** this field maintains the hours work per week */
	private int hoursPerWeek;
	/** This field maintains the rate of pay */
	private int payRate;
	/** This field maintains a list of Applications in an ArrayList */
	public ArrayList<Application> list;
	
	/**
	 * Constructor for Position class, also instantiates List of Applications
	 * @param positionName name of position
	 * @param hrsPerWeek Work Hours per week
	 * @param payRate Rate of pay
	 * @throws IllegalArgumentException if invalid parameters are given
	 */
	public Position(String positionName, int hrsPerWeek, int payRate) {
		if (positionName == null || "".equals(positionName) || hrsPerWeek < 5 || hrsPerWeek > 20 || payRate < 7 || payRate > 35) {
			throw new IllegalArgumentException("Position cannot be created.");
		}
		setPositionName(positionName);
		setHoursPerWeek(hrsPerWeek);
		setPayRate(payRate);
		list = new ArrayList<Application>(100);
	}
	
	/**
	 * Private methood to set field hoursPerWeek
	 * @param hoursPerWeek hours work per week
	 */
	private void setHoursPerWeek(int hoursPerWeek) {
		this.hoursPerWeek = hoursPerWeek;
	}
	
	/**
	 * This method returns the field hoursPerWeek
	 * @return returns the field hoursPerWeek
	 */
	public int getHoursPerWeek() {
		return hoursPerWeek;
	}	
	
	/**
	 * Private methood to set field payRate
	 * @param payRate rate of pay
	 */
	private void setPayRate(int payRate) {
		this.payRate = payRate;
	}
	
	/**
	 * This method returns the field payRate
	 * @return returns the field payRate
	 */
	public int getPayRate() {
		return payRate;
	}
	
	/**
	 * Private methood to set field positionName
	 * @param positionName name of position
	 */
	private void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	
	/**
	 * Returns position name
	 * @return name of position
	 */
	public String getPositionName() {
		return positionName;
	}

	/**
	 * Sets Application Id
	 */
	public void setApplicationId() {
		int temp = 0;
		if (list.size() == 0) {
			Application.setCounter(1);
		}
		for (int i = 0; i < list.size(); i++) {
			if (temp < list.get(i).getId()) {
				temp = list.get(i).getId();
			}
		}
		Application.setCounter(temp + 1);
	}
	
	/**
	 * This method creates and adds application to list in sorted order.
	 * @param firstName first name
	 * @param surname surname
	 * @param unityId unity Id
	 * @return returns id of new addition
	 */
	public int addApplication(String firstName, String surname, String unityId) {
		Application a = new Application(firstName, surname, unityId);
		int temp = 0;
		for (int i = 0; i < list.size(); i++) {
			if (a.getId() > list.get(i).getId()) {
				temp = i + 1;
			}
		}
//		for (int i = 0; i < list.size(); i++) {
//			if (temp > list.get(i).getId()) {
//				temp = list.get(i).getId() + 1;
//			}
//		}
		if (temp > list.size()) {
			list.add(a);
		}
		else {
		list.add(temp, a);
		}
		return a.getId();
	}
	
	/**
	 * This method adds the application directly in sorted order
	 * @param application Application object
	 * @return id of new addition
	 * @throws IllegalArgumentException  If an application already exists with the given
	 *  id, an IllegalArgumentException will be thrown with the message “Application cannot be created.
	 */
	public int addApplication(Application application) {
		for(int i = 0; i < list.size(); i++) {
			if (application.getId() == list.get(i).getId()) {
				throw new IllegalArgumentException("Application cannot be created.");
			}
		}
		int temp = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.size() == 0) {
				temp = 0;
				break;
			}
			if (application.getId() > list.get(i).getId()) {
				temp = i + 1;
			}
		}
//		for (int i = 0; i < list.size(); i++) {
//			if (temp > list.get(i).getId()) {
//				temp = list.get(i).getId() + 1;
//			}
//		}
//		list.add(application);
//		list.add(application);
		list.add(temp, application);
//		list.remove(list.size() - 1);
//		list.remove(list.size() - 1);
		return application.getId();
	}
	
	/**
	 * This method returns an Array List of Applications
	 * @return Returns list of applications
	 */
	public ArrayList<Application> getApplications() {
		return list;
	}
	
	/**
	 * This method finds the Application with same Id as parameter
	 * and returns that application
	 * @param id Id of Application
	 * @return Application instance with same id
	 */
	public Application getApplicationById(int id) {
		for(int i = 0; i < list.size(); i++) {
			if (id == list.get(i).getId()) {
				return list.get(i);
			}
		}
		return null;
	}
	
	/**
	 * This method deletes Application with given id
	 * @param id Id of Application
	 */
	public void deleteApplicationById(int id) {
		for(int i = 0; i < list.size(); i++) {
			if (id == list.get(i).getId()) {
				list.remove(i);
			}
		}
	}
	
	/**
	 * will find the Application with the given id and update it by passing in the given
	 *  Command. If there is no Application with that id, the list doesn’t change.
	 * @param id Id of Application
	 * @param c Command value
	 */
	public void executeCommand(int id, Command c) {
		for(int i = 0; i < list.size(); i++) {
			if (id == list.get(i).getId()) {
				list.get(i).update(c);
			}
		}
	}
	
	@Override
	public String toString() {
		return "# " + positionName + "," + hoursPerWeek + "," + payRate;
	}
}
