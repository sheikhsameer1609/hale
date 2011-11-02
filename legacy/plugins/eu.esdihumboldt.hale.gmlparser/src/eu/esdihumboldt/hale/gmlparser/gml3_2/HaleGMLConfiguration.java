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

package eu.esdihumboldt.hale.gmlparser.gml3_2;

import java.util.Map;

import javax.xml.namespace.QName;

import org.geotools.gml3.bindings.PolygonPatchTypeBinding;
import org.geotools.gml3.bindings.SurfaceArrayPropertyTypeBinding;
import org.geotools.gml3.v3_2.GML;
import org.geotools.xml.Configuration;

import eu.esdihumboldt.hale.gmlparser.binding.HaleReferenceTypeBinding;

/**
 * Extended GML 3.2 configuration
 *
 * @author Simon Templer
 * @partner 01 / Fraunhofer Institute for Computer Graphics Research
 * @version $Id$ 
 */
public class HaleGMLConfiguration extends org.geotools.gml3.v3_2.GMLConfiguration {

	/**
	 * GML 3.2 Qualified Name for PolygonPatchType
	 */
	public static final QName PolygonPatchType = new QName("http://www.opengis.net/gml/3.2", //$NON-NLS-1$
		"PolygonPatchType"); //$NON-NLS-1$
	
	/**
	 * GML 3.2 Qualified Name for AbstractRingType
	 */
	public static final QName AbstractRingType = new QName("http://www.opengis.net/gml/3.2", //$NON-NLS-1$
    	"AbstractRingType"); //$NON-NLS-1$
	
	/**
	 * GML 3.2 Qualified Name for SurfacePatchArrayPropertyType
	 */
	public static final QName SurfacePatchArrayPropertyType = new QName("http://www.opengis.net/gml/3.2", //$NON-NLS-1$
		"SurfacePatchArrayPropertyType"); //$NON-NLS-1$
	
	/**
	 * GML 3.2 Qualified Name for SurfaceType
	 */
	public static final QName SurfaceType = new QName("http://www.opengis.net/gml/3.2", //$NON-NLS-1$
		"SurfaceType"); //$NON-NLS-1$
	
	/**
	 * GML 3.2 Qualified Name for MultiPolygonType
	 */
	public static final QName MultiPolygonType = new QName("http://www.opengis.net/gml/3.2", //$NON-NLS-1$
		"MultiPolygonType"); //$NON-NLS-1$
	
	/**
	 * @see Configuration#configureBindings(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void configureBindings(Map bindings) {
		super.configureBindings(bindings);
		
		HaleDoubleListBinding doubleListBinding = new HaleDoubleListBinding();
		bindings.put(doubleListBinding.getTarget(), doubleListBinding);
		
		bindings.put(PolygonPatchType, PolygonPatchTypeBinding.class);
		
		bindings.put(SurfacePatchArrayPropertyType, SurfaceArrayPropertyTypeBinding.class);
		
		bindings.put(SurfaceType, HaleSurfaceTypeBinding.class);
		
		bindings.put(MultiPolygonType, HaleMultiPolygonTypeBinding.class);
		
		bindings.put(GML.ReferenceType, HaleReferenceTypeBinding.class);
		
//obsolete		bindings.put(GML.PolygonType, HalePolygonTypeBinding.class);
		
		bindings.put(GML.AbstractGeometryType, HaleAbstractGeometryTypeBinding.class);
		
		HaleCurvePropertyTypeBinding curvePropertyTypeBinding = new HaleCurvePropertyTypeBinding();
		bindings.put(curvePropertyTypeBinding.getTarget(), curvePropertyTypeBinding);
		
		bindings.put(new QName("http://www.opengis.net/gml/3.2","AbstractRingPropertyType"), HaleAbstractRingPropertyTypeBinding.class); //$NON-NLS-1$ //$NON-NLS-2$
	}

}