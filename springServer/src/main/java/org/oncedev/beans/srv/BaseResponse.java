/**
 * 
 */
package org.oncedev.beans.srv;

/**
 * @author Lovojor
 * 
 * Base response object
 *
 */
public class BaseResponse {

	private String errorCode;
	private String errorMessage;
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}