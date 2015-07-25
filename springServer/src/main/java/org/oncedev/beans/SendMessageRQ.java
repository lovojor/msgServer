package org.oncedev.beans;

import java.util.Map;

/**
 * @author Lovojor
 *
 * It store all RQ data to send a message
 */
public class SendMessageRQ {
	
	private int type;
	
	private String tempateCode;
	
	private Map<String, String> params;

	/**
	 * @return the type of message
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type of message to set.
	 * 			Values: 1-> email
	 * 
	 * 
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the tempateCode
	 */
	public String getTempateCode() {
		return tempateCode;
	}

	/**
	 * @param tempateCode the Code of template to use
	 */
	public void setTempateCode(String tempateCode) {
		this.tempateCode = tempateCode;
	}

	/**
	 * @return the message parameters
	 */
	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * @param params the list of message parameters to set
	 */
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
}