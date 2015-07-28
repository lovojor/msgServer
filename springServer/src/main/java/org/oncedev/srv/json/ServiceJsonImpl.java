package org.oncedev.srv.json;

import java.util.Locale;

import org.oncedev.AppContext;
import org.oncedev.DefineErrors;
import org.oncedev.DefineRest;
import org.oncedev.DefineSpring;
import org.oncedev.beans.dto.MessageDTO;
import org.oncedev.beans.srv.BaseResponse;
import org.oncedev.beans.srv.SendMessageRQ;
import org.oncedev.bl.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The Class BolpMobileBancolombiaServiceImpl.
 */
@Controller
@RequestMapping(DefineRest.MSG_SERVICES)
public class ServiceJsonImpl {

	protected static final Logger logger = LoggerFactory
			.getLogger(ServiceJsonImpl.class);

	/**
	 * @param changeMenuServiceBeanRQ
	 * @return
	 */
	@RequestMapping(value = DefineRest.SEND_MSG, method = RequestMethod.POST)
	public @ResponseBody BaseResponse sendMessage(
			@RequestBody SendMessageRQ sendMessageRQ,
			@RequestHeader HttpHeaders headers) {

		logger.debug("Ingresa Servcio regDevice");
		BaseResponse rs = new BaseResponse();
		rs.setErrorCode(DefineErrors.OK_CODE);
		// Validations
		if (sendMessageRQ.getType() != 1) {
			sendMessageRQ.setType(1);
		}
		if (sendMessageRQ.getTempateCode() != null
				&& sendMessageRQ.getTempateCode().trim().length() == 0) {
			rs.setErrorCode(DefineErrors.INVALID_TEMPLATE_CODE);
		}

		// Ejecución BL
		if (rs.getErrorCode().equals(DefineErrors.OK_CODE)) {
			MessageSender sender = AppContext.getApplicationContext().getBean(
					DefineSpring.BL_MESSAGE_SENDER_EMAIL, MessageSender.class);
			
			MessageDTO dto = new MessageDTO();
			BeanUtils.copyProperties(sendMessageRQ, dto);
			
			rs.setErrorCode(sender.sendMessage(dto));
		}

		rs.setErrorMessage(getMessage(rs.getErrorCode()));

		return rs;
	}

	private String getMessage(String errorCode) {
		String result = null;
		if (!errorCode.equals(DefineErrors.OK_CODE)) {
			result = AppContext.getApplicationContext().getMessage(errorCode,
					null, Locale.getDefault());
		}
		return result;
	}

}