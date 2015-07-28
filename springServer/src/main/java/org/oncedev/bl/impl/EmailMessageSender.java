/**
 * 
 */
package org.oncedev.bl.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;
import org.oncedev.DefineErrors;
import org.oncedev.DefineSpring;
import org.oncedev.Defines;
import org.oncedev.beans.TemplateBean;
import org.oncedev.beans.dto.MessageDTO;
import org.oncedev.bl.MessageSender;
import org.springframework.stereotype.Service;

/**
 * <b>OnceDev</b>
 * 
 * <p>
 * <b>Description: </b>
 * <p>
 * It sends a message by E-mail
 * 
 * @author Lovojor
 * 
 * @version 1.0, 24/July/2015
 * 
 */
@Service(DefineSpring.BL_MESSAGE_SENDER_EMAIL)
public class EmailMessageSender implements MessageSender {

	private VelocityEngine engine = null;

	private VelocityContext contextv = null;

	private Properties templateConfig;

	private Map<String, TemplateBean> templateCache;
	
	private String configPath;
	
	public EmailMessageSender() throws Exception {

		configPath = System.getProperty(Defines.CONFIG_PATH);

		if (configPath == null || configPath.trim().length() == 0) {
			System.out.println(
					"No se ha asignado la ruta de configuración externa" + configPath);
			configPath = "/var/bakno/Config";
		}

		char wrongSeparator = '/';
		if (File.separatorChar == wrongSeparator) {
			wrongSeparator = '\\';
		}		
		configPath = configPath.trim().replace(wrongSeparator, File.separatorChar);
		if (!configPath.endsWith(File.separator)) {
			configPath = configPath + File.separator;
		}
		

		templateConfig = new Properties();
		InputStream input;
		try {
			input = new FileInputStream(configPath + "templates.properties");
					System.out.println("Template: " + System.getProperty(Defines.CONFIG_TEMPLATE));
			templateConfig.load(input);
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new Exception("Archivo de configuración "
					+ Defines.CONFIG_TEMPLATE + " no existe.", e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("Error al leer archivo de configuración "
					+ Defines.CONFIG_TEMPLATE, e);
		}

		System.out.println("Ruta Plantilla Correo " + configPath);

		engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
		engine.setProperty("file.resource.loader.class",FileResourceLoader.class.getName());
		engine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH,configPath);
		engine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE,true);
		engine.init();
		templateCache = new HashMap<String, TemplateBean>();
	}

	public String sendMessage(MessageDTO data) {
		
		TemplateBean bean = getTemplate(data.getTempateCode());
		
		if (bean==null) {
			return DefineErrors.INVALID_TEMPLATE_CODE;
		}
		
		contextv = new VelocityContext();
		contextv.put("date", getCurrentDateTime("yyyy/MM/dd"));
		contextv.put("time", getCurrentDateTime("HH:mm:ss"));
		contextv.put("dataList", buildParams(data.getParams()) );

		Template template = engine.getTemplate(bean.getRelativeFilePath());
		StringWriter writer = new StringWriter();
		template.merge(contextv, writer);

		String content = writer.toString();

		try {
			Context context = new javax.naming.InitialContext();
			Session mailSession = (Session) context.lookup("mail/rbmovil");
			//mailSession.getProperties().put("mail.smtp.starttls.enable", true);
			Message msg = new MimeMessage(mailSession);
			//msg.setFrom(InternetAddress.parse("jorge.lomas@oncedev.com.ec")[0]);
			msg.setRecipients(javax.mail.Message.RecipientType.TO,
					javax.mail.internet.InternetAddress.parse(bean.getContact()));
			msg.setSubject(bean.getSubject());
			msg.setContent(content, "text/html");
			Transport.send(msg);

		} catch (AddressException e) {
			e.printStackTrace();
			return DefineErrors.INVALID_ADRESS_CODE;
		} catch (MessagingException e) {
			e.printStackTrace();
			return DefineErrors.INVALID_MESSAGE_CODE;
		} catch (NamingException e) {
			e.printStackTrace();
			return DefineErrors.INVALID_NAME_CODE;
		}
		
		return DefineErrors.OK_CODE;
	}

	private TemplateBean getTemplate(String code) {

		TemplateBean bean = templateCache.get(code);

		if (bean == null) {
			String strTemplate = templateConfig.getProperty(code);
			if (strTemplate != null) {
				bean = new TemplateBean(code, strTemplate);
				templateCache.put(code, bean);
			}
		}

		return bean;
	}

	private List<Map<String, String>> buildParams(Map<String, String> params) {

		List<Map<String, String>> listParams = new ArrayList<Map<String, String>>();

		for (Entry<String, String> entry : params.entrySet()) {
			Map<String, String> item = new HashMap<String, String>();
			item.put("name", entry.getKey());
			item.put("value", entry.getValue());
			listParams.add(item);
		}

		return listParams;
	}
	
	private String getCurrentDateTime(String format) {
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(new Date());
	}
}