/**
 * 
 */
package org.oncedev.bl;

import org.oncedev.beans.dto.MessageDTO;

/**
 * @author Lovojor
 *
 *	Message senders definition
 */
public interface MessageSender {
	
	/**
	 * 
	 * @param message object with message data
	 * @return a String ErrorCode
	 */
	public String sendMessage(MessageDTO message);

}