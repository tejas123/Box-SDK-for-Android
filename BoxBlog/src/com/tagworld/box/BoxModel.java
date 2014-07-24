package com.tagworld.box;

import java.io.Serializable;
import java.util.ArrayList;

import com.box.boxjavalibv2.dao.BoxTypedObject;

public class BoxModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String emailAddress;//, type;
	private ArrayList<BoxTypedObject> boxObjects;

	public ArrayList<BoxTypedObject> getBoxObjects() {
		return boxObjects;
	}

	public void setBoxObjects(ArrayList<BoxTypedObject> boxObjects) {
		this.boxObjects = boxObjects;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	
}
