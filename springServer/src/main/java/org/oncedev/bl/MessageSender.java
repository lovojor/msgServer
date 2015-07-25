/**
 * 
 */
package org.oncedev.bl;

import org.oncedev.beans.SendMessageRQ;

/**
 * @author Lovojor
 *
 *	Message senders definition
 */
public interface MessageSender {
	
	/**
	 * 
	 * @param rq object with message data
	 * @return a String ErrorCode
	 */
	public String sendMessage(SendMessageRQ rq);

}