package org.oncedev.beans;
import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */

/**
 * It is a Unit test over TemplateBean
 * 
 * @author Lovojor
 *
 */
public class TemplateBeanTest {
	private static final String strTemplate = "Templates/100.contacts.vm.htm|lovojor@hotmail.com|Formulario de Contacto";
	private static final String templateCode = "100";
	private static final String relativeURI = "Templates/100.contacts.vm.htm";
	private static final String contact = "lovojor@hotmail.com";
	private static final String subject = "Formulario de Contacto";
	
	@Test
	public void testConstructor() {
		TemplateBean bean = new TemplateBean(templateCode, strTemplate);
		
		Assert.assertEquals(templateCode, bean.getCode());
		Assert.assertEquals(relativeURI, bean.getRelativeFilePath());
		Assert.assertEquals(contact, bean.getContact());
		Assert.assertEquals(subject, bean.getSubject());
	}
	
	public static void main(String[] params) {
		String path = "C:/Users/lovoj_000/git/msgServer/springServer/Config\\";
		String file = "Templates/contacts.vm";
		char wrongSeparator = '/';
		if (File.separatorChar == wrongSeparator) {
			wrongSeparator = '\\';
		}
		path=path.replace(wrongSeparator, File.separatorChar);
		System.out.println(path);
		
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
		ve.setProperty("file.resource.loader.class",FileResourceLoader.class.getName());
		ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH,path);
		ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE,true);
		ve.init();
		
		Template template = ve.getTemplate(file);
		
		VelocityContext context = new VelocityContext();
		List<Map<String, String>> pr = new ArrayList<Map<String,String>>();
		
		Map<String, String> item = new HashMap<String, String>();
		item.put("name", "Nombre");
		item.put("value", "Jorge");
		pr.add(item);
		
		item = new HashMap<String, String>();
		item.put("name", "Apellido");
		item.put("value", "Lomas");
		pr.add(item);
		context.put("date", "2014/12/12");
		context.put("time", "12:12:12");
		context.put("dataList", pr);
		
		/*  now render the template into a Writer  */
        StringWriter writer = new StringWriter();
        template.merge( context, writer );
        
        System.out.println(writer.toString());
	}
}