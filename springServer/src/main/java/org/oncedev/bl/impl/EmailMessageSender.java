/**
 * 
 */
package org.oncedev.bl.impl;

import org.oncedev.DefineErrors;
import org.oncedev.DefineSpring;
import org.oncedev.beans.SendMessageRQ;
import org.oncedev.bl.MessageSender;
import org.springframework.stereotype.Service;

/**
 * @author Lovojor
 * 
 * It sends a message by E-mail
 */
@Service(DefineSpring.BL_MESSAGE_SENDER_EMAIL)
public class EmailMessageSender implements MessageSender {

	public String sendMessage(SendMessageRQ rq) {
		// TODO Auto-generated method stub
		return DefineErrors.OK_CODE;
	}

}