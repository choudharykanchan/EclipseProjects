package com.dealertire.RightTurnFramework.Models;

/**
 * Model for a staggered set. Includes broken staggereds.
 * @author bgreen
 */
public class StaggeredSet {

	/**Front size*/
	public Size front;
	
	/**Rear size*/
	public Size rear;
	
	/**
	 * Constructor for parsing a string into a staggered set.
	 * @param sizeDescription
	 */
	public StaggeredSet(String sizeDescription) {
		String[] sizeDescriptions = sizeDescription.split(":");
		if (!sizeDescriptions[0].isEmpty())
			front = new Size(sizeDescriptions[0]);
		if (!sizeDescriptions[1].isEmpty())
			rear = new Size(sizeDescriptions[1]);
	}
	
	/**
	 * Is this a broken staggered?
	 * @return True if either front or rear is missing.
	 */
	public boolean isBroken() {
		return front == null || rear == null;
	}
}
