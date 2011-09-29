/*
 * HUMBOLDT: A Framework for Data Harmonisation and Service Integration.
 * EU Integrated Project #030962                 01.10.2006 - 30.09.2010
 * 
 * For more information on the project, please refer to the this web site:
 * http://www.esdi-humboldt.eu
 * 
 * LICENSE: For information on the license under which this program is 
 * available, please refer to http:/www.esdi-humboldt.eu/license.html#core
 * (c) the HUMBOLDT Consortium, 2007 to 2010.
 */

package eu.esdihumboldt.gmlhandler;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.deegree.commons.xml.XMLParsingException;
import org.deegree.cs.exceptions.UnknownCRSException;
import org.deegree.feature.Feature;
import org.deegree.feature.FeatureCollection;
import org.deegree.feature.property.Property;
import org.deegree.feature.types.ApplicationSchema;
import org.deegree.feature.types.FeatureType;
import org.deegree.feature.types.property.PropertyType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * Test-class for the GMLHandler functionality.
 * 
 * @author Anna Pitaev
 * @partner 04 / Logica
 * @version $Id$
 */
public class GmlHandlerTest {
	/** Logger for this class */
	private static final Logger LOG = Logger.getLogger(GmlHandlerTest.class);

	/** http-based URL for the application schema */
	private static final String SCHEMA_URL = "http://svn.esdi-humboldt.eu/repo/humboldt2/trunk/cst/eu.esdihumboldt.cst.corefunctions/src/test/resource/eu/esdihumboldt/cst/corefunctions/inspire/inspire_v3.0_xsd/" //$NON-NLS-1$
			+ "HydroPhysicalWaters.xsd"; //$NON-NLS-1$

	/** generated instance location */
	private static final String GML32_GENERATED_LOCATION = "src/test/resources/generated.gml"; //$NON-NLS-1$

	/** handler to proceed gmldata */
	private static GmlHandler gmlHandler;
	
	/** URL of XSD for tests **/
	private static final String xsdUrl = 	"file://" + (new GmlHandlerTest()).getClass() //$NON-NLS-1$
										  .getResource("./HydroPhysicalWaters.xsd").getFile(); //$NON-NLS-1$

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// pre-define namespaces
//		HashMap<String, String> namespaces = new HashMap<String, String>();
//		namespaces.put("gco", "http://www.isotc211.org/2005/gco");
//		namespaces.put("gmd", "http://www.isotc211.org/2005/gmd");
//		namespaces.put("gn",
//				"urn:x-inspire:specification:gmlas:GeographicalNames:3.0");
//		namespaces.put("hy-p",
//				"urn:x-inspire:specification:gmlas:HydroPhysicalWaters:3.0");
//		namespaces.put("hy", "urn:x-inspire:specification:gmlas:HydroBase:3.0");
//		namespaces.put("base",
//				"urn:x-inspire:specification:gmlas:BaseTypes:3.2");
//		namespaces.put("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		

		// set up GMLHandler with the test configuration

		gmlHandler = new GmlHandler(GMLVersions.gml3_2_1, xsdUrl);

		// set target gml destination
		gmlHandler.setTargetGmlUrl(GML32_GENERATED_LOCATION);				
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass	
	public static void tearDownAfterClass() throws Exception {
		new File(GML32_GENERATED_LOCATION).delete();
	}

	/**
	 * Test method for
	 * {@link eu.esdihumboldt.gmlhandler.GmlHandler#readSchema()}.
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws ClassCastException
	 * @throws MalformedURLException
	 */
	@Test
	public final void testReadSchema() throws MalformedURLException, 
			ClassCastException, ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		// read application schema stored locally
		
		gmlHandler.setSchemaUrl(xsdUrl);
		ApplicationSchema schema = gmlHandler.readSchema();

		// validate root FeatureTypes
		FeatureType[] rootFTypes = schema.getRootFeatureTypes();
		assertEquals(17, rootFTypes.length);

		for (FeatureType rootType : rootFTypes) {
			LOG.debug("Root Feature Type : " //$NON-NLS-1$
					+ rootType.getName().getNamespaceURI() + ":" //$NON-NLS-1$
					+ rootType.getName().getLocalPart());
		}

		// Compare the count of the FeatureTypes
		FeatureType[] ftypes = schema.getFeatureTypes();
		for (FeatureType ftype : ftypes) {
			LOG.debug("Application Schema Type : " //$NON-NLS-1$
					+ ftype.getName().getNamespaceURI() + ":" //$NON-NLS-1$
					+ ftype.getName().getLocalPart());
			// validate a single Feature Type
			if (ftype.getName().getLocalPart().equals("Rapids")) { //$NON-NLS-1$
				assertEquals("hy-p", ftype.getName().getPrefix()); //$NON-NLS-1$
				assertEquals(
						"urn:x-inspire:specification:gmlas:HydroPhysicalWaters:3.0", //$NON-NLS-1$
						ftype.getName().getNamespaceURI());
				// check parent type
				FeatureType pType = schema.getParentFt(ftype);
				LOG.debug("Parent Typy of hy-p:Rapids is " //$NON-NLS-1$
						+ pType.getName().toString());
				assertEquals("FluvialPoint", pType.getName().getLocalPart()); //$NON-NLS-1$
				// check property declarations list
				List<PropertyType> pDeclarations = ftype
						.getPropertyDeclarations();
				assertEquals(8, pDeclarations.size());
				for (PropertyType propType : pDeclarations) {
					LOG.debug("Property List of hy-p:Rapids contains : " //$NON-NLS-1$
							+ propType.getName().toString());
				}
			}
		}
		assertEquals(49, ftypes.length);
	}

	/**
	 * Little test to see if we can read in a local schema, i.e. file protocol.
	 * 
	 * @throws MalformedURLException
	 * @throws ClassCastException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@Test
	public final void testReadLocalSchema() throws MalformedURLException,
			ClassCastException, ClassNotFoundException, InstantiationException,
			IllegalAccessException {		
		gmlHandler.setSchemaUrl(xsdUrl);

		// read application schema
		gmlHandler.readSchema();
	}

	/**
	 * Test method for {@link eu.esdihumboldt.gmlhandler.GmlHandler#readFC()}.
	 * 
	 * @throws UnknownCRSException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws FactoryConfigurationError
	 * @throws XMLStreamException
	 * @throws ClassCastException
	 * @throws XMLParsingException
	 */
	@Test
	public final void testReadFC() throws XMLParsingException,
			ClassCastException, XMLStreamException, FactoryConfigurationError,
			IOException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnknownCRSException {
		LOG.info("Reading of source Feature Collection.."); //$NON-NLS-1$
		
		gmlHandler.setSchemaUrl(xsdUrl);
		String urlPath = "file://" //$NON-NLS-1$
				+ this.getClass().getResource("./va_target_v3.gml").getFile(); //$NON-NLS-1$
		gmlHandler.setGmlUrl(urlPath);
		// read FeatureCollection
		FeatureCollection fc = null;
		fc = gmlHandler.readFC();

		// check feature collection size
		assertEquals(998, fc.size());
		LOG.info("Feature Collection made up of " + fc.size()); //$NON-NLS-1$
		// validate a single feature with id=Watercourses=VA.942

		Iterator<Feature> fcIterator = fc.iterator();
		LOG.info("Verifying of the single features"); //$NON-NLS-1$
		while (fcIterator.hasNext()) {
			Feature feature = fcIterator.next();
			LOG.debug("Found feature with ID = " + feature.getId()); //$NON-NLS-1$
			if (feature.getId().equals("Watercourses_VA.942")) { //$NON-NLS-1$
				LOG.info("Verifying feature with feature id = Watercourses_VA.942"); //$NON-NLS-1$
				// check feature name
				assertEquals("Watercourse", feature.getName().getLocalPart()); //$NON-NLS-1$
				// check feature property list
				Property[] props = feature.getProperties();
				assertEquals(18, props.length);
				// check property localType
				for (Property prop : props) {
					LOG.debug("Found property " + prop.getName().toString()); //$NON-NLS-1$
					if (prop.getName().getLocalPart().equals("localType")) { //$NON-NLS-1$
						LOG.info("Verifying of  Property Watercourse:localType"); //$NON-NLS-1$
						assertEquals("Fluss, Bach", prop.getValue().toString()); //$NON-NLS-1$
					}
				}
			}
		}
	}

	/**
	 * Test method for
	 * {@link eu.esdihumboldt.gmlhandler.GmlHandler#writeFC(org.deegree.feature.FeatureCollection)}
	 * .
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws FactoryConfigurationError
	 * @throws TransformationException
	 * @throws UnknownCRSException
	 * @throws XMLStreamException
	 * @throws ClassCastException
	 * @throws FileNotFoundException
	 * @throws XMLParsingException
	 */
//	@Test
//	public final void testWriteFC() throws XMLParsingException,
//			FileNotFoundException, ClassCastException, XMLStreamException,
//			UnknownCRSException, TransformationException,
//			FactoryConfigurationError, IOException, ClassNotFoundException,
//			InstantiationException, IllegalAccessException {
//		// TODO provide XUNIT-based testing
//		
//		
//		gmlHandler.setSchemaUrl(xsdUrl);
//		String urlPath = "file://"
//				+ this.getClass().getResource("./va_target_v3.gml").getFile();
//		gmlHandler.setGmlUrl(urlPath);
//		gmlHandler.writeFC(gmlHandler.readFC(), "urn:x-inspire:specification:gmlas:HydroPhysicalWaters:3.0");
//	}
	
}