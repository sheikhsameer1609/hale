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

package eu.esdihumboldt.hale.io.gml.writer.internal.geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.geotools.feature.NameImpl;
import org.opengis.feature.type.Name;

import com.vividsolutions.jts.geom.Geometry;

import de.cs3d.util.logging.ALogger;
import de.cs3d.util.logging.ALoggerFactory;
import eu.esdihumboldt.hale.io.gml.writer.internal.GmlWriterUtil;
import eu.esdihumboldt.hale.io.gml.writer.internal.geometry.GeometryConverterRegistry.ConversionLadder;
import eu.esdihumboldt.hale.io.gml.writer.internal.geometry.writers.CurveWriter;
import eu.esdihumboldt.hale.io.gml.writer.internal.geometry.writers.LegacyMultiPolygonWriter;
import eu.esdihumboldt.hale.io.gml.writer.internal.geometry.writers.LegacyPolygonWriter;
import eu.esdihumboldt.hale.io.gml.writer.internal.geometry.writers.LineStringWriter;
import eu.esdihumboldt.hale.io.gml.writer.internal.geometry.writers.MultiLineStringWriter;
import eu.esdihumboldt.hale.io.gml.writer.internal.geometry.writers.MultiPointWriter;
import eu.esdihumboldt.hale.io.gml.writer.internal.geometry.writers.MultiPolygonWriter;
import eu.esdihumboldt.hale.io.gml.writer.internal.geometry.writers.PointWriter;
import eu.esdihumboldt.hale.io.gml.writer.internal.geometry.writers.PolygonWriter;
import eu.esdihumboldt.hale.schemaprovider.model.AttributeDefinition;
import eu.esdihumboldt.hale.schemaprovider.model.TypeDefinition;

/**
 * Write geometries for a GML document
 *
 * @author Simon Templer
 * @partner 01 / Fraunhofer Institute for Computer Graphics Research
 * @version $Id$ 
 */
public class StreamGeometryWriter extends AbstractTypeMatcher<Class<? extends Geometry>> {
	
	private static final ALogger log = ALoggerFactory.getLogger(StreamGeometryWriter.class);
	
	/**
	 * Get a geometry writer instance with a default configuration
	 * 
	 * @param gmlNs the GML namespace 
	 * 
	 * @return the geometry writer
	 */
	public static StreamGeometryWriter getDefaultInstance(String gmlNs) {
		StreamGeometryWriter sgm = new StreamGeometryWriter(gmlNs);
		
		//TODO configure
		sgm.registerGeometryWriter(new CurveWriter());
		sgm.registerGeometryWriter(new PointWriter());
		sgm.registerGeometryWriter(new PolygonWriter());
		sgm.registerGeometryWriter(new LineStringWriter());
		sgm.registerGeometryWriter(new MultiPolygonWriter());
		sgm.registerGeometryWriter(new MultiPointWriter());
		sgm.registerGeometryWriter(new MultiLineStringWriter());
		sgm.registerGeometryWriter(new LegacyPolygonWriter());
		sgm.registerGeometryWriter(new LegacyMultiPolygonWriter());
		
		return sgm;
	}
	
	/**
	 * The GML namespace
	 */
	private final String gmlNs;
	
	/**
	 * Geometry types mapped to compatible writers
	 */
	private final Map<Class<? extends Geometry>, Set<GeometryWriter<?>>> geometryWriters =
		new HashMap<Class<? extends Geometry>, Set<GeometryWriter<?>>>();
	
	/**
	 * Types mapped to geometry types mapped to matched definition paths
	 */
	//XXX stored paths instead per attribute definition?
	private final Map<TypeDefinition, Map<Class<? extends Geometry>, DefinitionPath>> storedPaths = 
		new HashMap<TypeDefinition, Map<Class<? extends Geometry>,DefinitionPath>>(); 

	/**
	 * Constructor
	 * 
	 * @param gmlNs the GML namespace
	 */
	public StreamGeometryWriter(String gmlNs) {
		super();
		
		this.gmlNs = gmlNs;
	}
	
	/**
	 * Register a geometry writer
	 *  
	 * @param writer the geometry writer
	 */
	public void registerGeometryWriter(GeometryWriter<?> writer) {
		Class<? extends Geometry> geomType = writer.getGeometryType();
		Set<GeometryWriter<?>> writers = geometryWriters.get(geomType);
		if (writers == null) {
			writers = new HashSet<GeometryWriter<?>>();
			geometryWriters.put(geomType, writers);
		}
		
		writers.add(writer);
	}

	/**
	 * Write a geometry to a stream for a GML document
	 * 
	 * @param writer the XML stream writer
	 * @param geometry the geometry
	 * @param property the geometry property
	 * @param srsName the SRS name of a common SRS for the whole 
	 *   document, may be <code>null</code>
	 * @throws XMLStreamException if any error occurs writing the geometry
	 */
	public void write(XMLStreamWriter writer, Geometry geometry,
			AttributeDefinition property, String srsName) throws XMLStreamException {
		// write any srsName attribute on the parent element
		writeSrsName(writer, property.getAttributeType(), geometry, srsName);
		
		Class<? extends Geometry> geomType = geometry.getClass();
		
		// remember if we already found a solution to this problem
		DefinitionPath path = restoreCandidate(property.getAttributeType(), geomType);
		
		if (path == null) {
			// find candidates
			List<DefinitionPath> candidates = findCandidates(property, geomType);
			
			// if no candidate found, try with compatible geometries
			Class<? extends Geometry> originalType = geomType;
			Geometry originalGeometry = geometry;
			ConversionLadder ladder = GeometryConverterRegistry.getInstance().createLadder(geometry);
			while (candidates.isEmpty() && ladder.hasNext()) {
				geometry = ladder.next();
				geomType = geometry.getClass();
				
				log.info("Possible structure for writing " + originalType.getSimpleName() +  //$NON-NLS-1$
						" not found, trying " + geomType.getSimpleName() + " instead"); //$NON-NLS-1$ //$NON-NLS-2$
				
				DefinitionPath candPath = restoreCandidate(property.getAttributeType(), geomType);
				if (candPath != null) {
					// use stored candidate
					candidates = Collections.singletonList(candPath);
				}
				else {
					candidates = findCandidates(property, geomType);
				}
			}
			
			for (DefinitionPath candidate : candidates) {
				log.info("Geometry structure match: " + geomType.getSimpleName() + " - " + candidate); //$NON-NLS-1$ //$NON-NLS-2$
			}
			
			if (candidates.isEmpty()) {
				log.error("No geometry structure match for " +  //$NON-NLS-1$
						originalType.getSimpleName() + " found, writing WKT " + //$NON-NLS-1$
						"representation instead"); //$NON-NLS-1$
				
				writer.writeCharacters(originalGeometry.toText());
				return;
			}
			
			// determine preferred candidate
			//XXX for now: first one
			path = candidates.get(0);
			
			// remember for later
			storeCandidate(property.getAttributeType(), geomType, path);
		}
		
		// write geometry
		writeGeometry(writer, geometry, path, srsName);
	}

	/**
	 * Find candidates for a possible path to use for writing the geometry
	 * 
	 * @param property the start property 
	 * @param geomType the geometry type
	 * 
	 * @return the path candidates
	 */
	public List<DefinitionPath> findCandidates(AttributeDefinition property,
			Class<? extends Geometry> geomType) {
		Set<GeometryWriter<?>> writers = geometryWriters.get(geomType);
		if (writers == null || writers.isEmpty()) {
			// if no writer is present, we can cancel right here
			return new ArrayList<DefinitionPath>();
		}
		
		return super.findCandidates(property.getAttributeType(),
				new NameImpl(property.getNamespace(), property.getName()), 
				property.getMaxOccurs() <= 1, geomType);
	}

	/**
	 * Write the geometry using the given path
	 * 
	 * @param writer the XML stream writer
	 * @param geometry the geometry
	 * @param path the definition path to use
	 * @param srsName the SRS name of a common SRS for the whole 
	 *   document, may be <code>null</code>
	 * @throws XMLStreamException if writing the geometry fails
	 */
	@SuppressWarnings("unchecked")
	private void writeGeometry(XMLStreamWriter writer, Geometry geometry,
			DefinitionPath path, String srsName) throws XMLStreamException {
		@SuppressWarnings("rawtypes")
		GeometryWriter geomWriter = path.getGeometryWriter();
		
		Name name = path.getLastName();
		
		if (path.isEmpty()) {
			// directly write geometry
			geomWriter.write(writer, geometry, path.getLastType(), name, gmlNs); 
		}
		else {
			for (PathElement step : path.getSteps()) {
				// start elements
				name = step.getName();
				GmlWriterUtil.writeStartPathElement(writer, step, false);
				// write eventual required ID
				GmlWriterUtil.writeRequiredID(writer, step.getType(), null, false);
				// write eventual srsName
				writeSrsName(writer, step.getType(), geometry, srsName);
			}
			
			// write geometry
			geomWriter.write(writer, geometry, path.getLastType(), name, gmlNs);
			
			for (int i = 0; i < path.getSteps().size(); i++) {
				// end elements
				writer.writeEndElement();
			}
		}
	}

	/**
	 * Write the SRS name if a corresponding attribute is present
	 * 
	 * @param writer the XML stream writer
	 * @param type the element type definition
	 * @param geometry the geometry
	 * @param srsName the common SRS name, may be <code>null</code>
	 * @throws XMLStreamException if writing the SRS name fails
	 */
	private void writeSrsName(XMLStreamWriter writer, TypeDefinition type,
			Geometry geometry, String srsName) throws XMLStreamException {
		//TODO can SRS be extracted from geometry?
		
		if (srsName != null) {
			AttributeDefinition srsAtt = null;
			for (AttributeDefinition att : type.getAttributes()) {
				if (att.getName().equals("srsName") //TODO improve condition? //$NON-NLS-1$
						&& (att.getNamespace() == null || 
								att.getNamespace().equals(gmlNs) || 
								att.getNamespace().isEmpty())) {
					srsAtt = att;
					break;
				}
			}
			
			if (srsAtt != null) {
				GmlWriterUtil.writeAttribute(writer, srsName, srsAtt);
			}
		}
	}

	/**
	 * Store the candidate for later use
	 * 
	 * @param type the attribute type definition
	 * @param geomType the geometry type
	 * @param path the definition path
	 */
	private void storeCandidate(TypeDefinition type,
			Class<? extends Geometry> geomType, DefinitionPath path) {
		Map<Class<? extends Geometry>, DefinitionPath> paths = storedPaths.get(type);
		if (paths == null) {
			paths = new HashMap<Class<? extends Geometry>, DefinitionPath>();
			storedPaths.put(type, paths);
		}
		paths.put(geomType, path);
	}
	
	/**
	 * Restore the candidate matching the given types
	 * 
	 * @param type the attribute type definition
	 * @param geomType the geometry type
	 * 
	 * @return a previously found path or <code>null</code> 
	 */
	private DefinitionPath restoreCandidate(TypeDefinition type,
			Class<? extends Geometry> geomType) {
		Map<Class<? extends Geometry>, DefinitionPath> paths = storedPaths.get(type);
		if (paths != null) {
			return paths.get(geomType);
		}
		return null;
	}

	/**
	 * Determines if a type definition is compatible to a geometry type
	 *  
	 * @param type the type definition
	 * @param geomType the geometry type
	 * @param path the current definition path
	 * 
	 * @return the (eventually updated) definition path if a match is found,
	 * otherwise <code>null</code>
	 */
	@Override
	protected DefinitionPath matchPath(TypeDefinition type, 
			Class<? extends Geometry> geomType, DefinitionPath path) {
		
		// check compatibility list
		Set<GeometryWriter<?>> writers = geometryWriters.get(geomType);
		if (writers != null) {
			for (GeometryWriter<?> writer : writers) {
				boolean compatible = false;
				Set<Name> names = writer.getCompatibleTypes();
				if (names != null) {
					if (names.contains(type.getName())) {
						// check type name
						compatible = true;
					}
					
					if (!compatible && type.getName().getNamespaceURI().equals(gmlNs)) {
						// check GML type name
						compatible = names.contains(new NameImpl(null, type.getName().getLocalPart()));
					}
					
					if (compatible) {
						// check structure / match writer
						DefinitionPath candidate = writer.match(type, path, gmlNs);
						if (candidate != null) {
							// set appropriate writer for path and return it
							candidate.setGeometryWriter(writer);
							return candidate;
						}
					}
				}
			}
		}
		
		// fall back to binding test
		// check for equality because we don't want a match for the property types
		boolean compatible = type.getType(null).getBinding().equals(geomType);
		
		if (compatible) {
			// check structure / match writers
			if (writers != null) {
				for (GeometryWriter<?> writer : writers) {
					DefinitionPath candidate = writer.match(type, path, gmlNs);
					if (candidate != null) {
						// set appropriate writer for path and return it
						candidate.setGeometryWriter(writer);
						return candidate;
					}
				}
			}
		}
		
		return null;
	}

}