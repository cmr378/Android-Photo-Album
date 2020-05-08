package model;

import java.io.Serializable;

public class Tag implements Serializable{

	private static final long serialVersionUID = 2572659596329551353L;
	private String type;
	private String value; 

	public Tag(String type, String value) {
		this.type = type;
		this.value = value; 
	}
	
	/**
	 * returns tag type
	 * @return
	 */
	
	public String getType() {
		return type; 
	}
	
	/**
	 * Returns tag value
	 * @return
	 */
	
	public String getValue() {
		return value; 
	}
	
	/**
	 * Set type value to the new type the user gives 
	 * @param newType
	 */
	
	public void setType(String newType) {
		type = newType; 
	}
	
	/**
	 * Set tag value to the new value the user gives 
	 * @param newValue
	 */
	
	public void setValue(String newValue) {
		value = newValue; 
	}
} 
