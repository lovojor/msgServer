package org.oncedev.beans.dto;

import java.util.Map;

/**
 * @author Lovojor
 *
 * It store all RQ data to send a message
 */
public class MessageDTO {
	
	private String tempateCode;
	
	private Map<String, String> params;

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