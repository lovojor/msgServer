package org.oncedev.beans;
import org.oncedev.Defines;

/**
 * 
 */

/**
 * It has a template representation
 * 
 * @author Lovojor
 *
 */
public class TemplateBean {

	private String code;
	private String relativeFilePath;
	private String contact;
	private String subject;

	/**
	 * Default Constructor
	 */
	public TemplateBean() {
		super();
	}

	/**
	 * @param code
	 *            - a code to identify a template and code key in config file
	 * @param template
	 *            - a string template representation
	 */
	public TemplateBean(String code, String template) {
		if (template != null && template.trim().length() > 0) {
			String[] fields = template.trim().split(Defines.STR_SEPARATOR);
			setCode(code);
			setRelativeFilePath(fields[0]);
			if (fields.length > 1) {
				setContact(fields[1]);
			}
			if (fields.length > 2) {
				setSubject(fields[2]);
			}
		}
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the relativeFilePath
	 */
	public String getRelativeFilePath() {
		return relativeFilePath;
	}

	/**
	 * @param relativeFilePath
	 *            the relativeFilePath to set
	 */
	public void setRelativeFilePath(String relativeFilePath) {
		this.relativeFilePath = relativeFilePath;
	}

	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * @param contact
	 *            the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

}