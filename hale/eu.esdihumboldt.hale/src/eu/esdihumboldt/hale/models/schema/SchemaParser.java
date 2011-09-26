package eu.esdihumboldt.hale.models.schema;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

//import org.apache.xerces.jaxp.SAXParserFactoryImpl;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import eu.esdihumboldt.hale.Messages;

/**
 * SchemaParser
 *
 * @author ?
 * @partner ?
 * @version $Id$
 */
public class SchemaParser extends DefaultHandler {

	private String file;
	
	private Map<String, String> schemas = new HashMap<String, String>();
	
	@Override
	public void startElement(String namespaceURI, String localName,
            String qName, Attributes atts) throws SAXException {
//		System.out.println(namespaceURI + ", " + localName + ", " + qName);
//		for ( int i = 0; i < atts.getLength(); i++ ) System.out.println(atts.getQName(i));
		
		
		if ((qName.indexOf("import") > -1 || qName.indexOf("include") > -1) && atts.getIndex("schemaLocation") > -1) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
//			System.out.println("schemaLocation: " + atts.getValue("schemaLocation"));
			SchemaParser parser = new SchemaParser();
			String path = file.substring(0, file.lastIndexOf("/") ); //$NON-NLS-1$
			
			schemas.put(atts.getValue("schemaLocation"), path + "/" + atts.getValue("schemaLocation")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

			Map<String, String> map = parser.parse( path + "/" + atts.getValue("schemaLocation") ); //$NON-NLS-1$ //$NON-NLS-2$
			System.out.println("***************************" + path + "/"  + atts.getValue("schemaLocation")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			schemas.putAll(map);
		}

	}

	/**
	 * Returns a list of schemas which are in the import/include tag.
	 * 
	 * @param file the file name
	 * 
	 * @return schema map
	 */
	public Map<String, String> parse(String file) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		
		// Extracts the path without the filename
		this.file = file;
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(file, this);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("File " + file + " does not exist!"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		return schemas;
	}
}