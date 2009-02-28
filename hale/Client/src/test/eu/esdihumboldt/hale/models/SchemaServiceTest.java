package test.eu.esdihumboldt.hale.models;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.xml.SchemaFactory;
import org.geotools.xml.schema.Attribute;
import org.geotools.xml.schema.ComplexType;
import org.geotools.xml.schema.Element;
import org.geotools.xml.schema.Schema;
import org.junit.Test;
import org.xml.sax.SAXException;

import eu.esdihumboldt.hale.models.impl.SchemaServiceImpl;

/**
 * Unit tests which covers the SchemaService implementation class.
 */
public class SchemaServiceTest {

//	private Element[] printElements(Element element, int level) {
//		
//		for (int i = 0; i < 0; i++) {
//			System.out.print("\t");
//		}
//		System.out.println(element.getName());
//		if (element.get)
//	}
	
	@Test
	public void testLoadSourceSchema() throws URISyntaxException, SAXException, FileNotFoundException {
		
		InputStream is = new FileInputStream("schema/source/roadsGermany212.xsd");
		Schema schema = SchemaFactory.getInstance(null, is);
		for (Element element : schema.getElements())
		{
			System.out.println(element.getName());
			
		
			SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
			builder.setName(element.getName());
			builder.setNamespaceURI(element.getNamespace());
			
		}
		for (ComplexType type : schema.getComplexTypes() )
		{
			System.out.println(type.getName() + " " + type.getChildElements().length );
			for (Element element : type.getChildElements()) {
				System.out.println("\te " + element.getName());
			}
			for (Attribute attribue : type.getAttributes()) {
				System.out.println("\ta " + attribue.getName());
			}
		}
	
		
		
		SchemaServiceImpl service = (SchemaServiceImpl) SchemaServiceImpl.getInstance();
//		URI file = new URI("schema/target/Roadlink.xsd");
		URI file = new URI("resources/schema/inheritance/rise_hydrography.xsd");
//		URI file = new URI("schema/source/roadsGermany212.xsd");
		service.loadSourceSchema(file);
	}

	@Test
	public void testLoadTargetSchema() {
	}

}
