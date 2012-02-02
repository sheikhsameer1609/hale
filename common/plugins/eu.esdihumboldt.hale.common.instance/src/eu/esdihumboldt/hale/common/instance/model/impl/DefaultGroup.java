/*
 * HUMBOLDT: A Framework for Data Harmonisation and Service Integration.
 * EU Integrated Project #030962                 01.10.2006 - 30.09.2010
 * 
 * For more information on the project, please refer to the this web site:
 * http://www.esdi-humboldt.eu
 * 
 * LICENSE: For information on the license under which this program is 
 * available, please refer to http:/www.esdi-humboldt.eu/license.html#core
 * (c) the HUMBOLDT Consortium, 2007 to 2011.
 */

package eu.esdihumboldt.hale.common.instance.model.impl;

import java.util.Arrays;

import javax.xml.namespace.QName;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import eu.esdihumboldt.hale.common.instance.model.Group;
import eu.esdihumboldt.hale.common.instance.model.MutableGroup;
import eu.esdihumboldt.hale.common.schema.model.DefinitionGroup;

/**
 * Group implementation backed by a {@link ListMultimap}.
 * @author Simon Templer
 */
public class DefaultGroup implements MutableGroup {

	private final ListMultimap<QName, Object> properties = ArrayListMultimap.create();
	
	private final DefinitionGroup definition;
	
	/**
	 * Create an empty group.
	 * @param definition the associated definition
	 */
	public DefaultGroup(DefinitionGroup definition) {
		super();
		this.definition = definition;
	}

	/**
	 * @see Group#getProperty(QName)
	 */
	@Override
	public Object[] getProperty(QName propertyName) {
		return properties.get(propertyName).toArray();
	}

	/**
	 * @see Group#getPropertyNames()
	 */
	@Override
	public Iterable<QName> getPropertyNames() {
		return properties.keySet();
	}

	/**
	 * @see Group#getDefinition()
	 */
	@Override
	public DefinitionGroup getDefinition() {
		return definition;
	}

	/**
	 * @see MutableGroup#addProperty(QName, Object)
	 */
	@Override
	public void addProperty(QName propertyName, Object value) {
		properties.put(propertyName, value);
	}

	/**
	 * @see MutableGroup#setProperty(QName, Object[])
	 */
	@Override
	public void setProperty(QName propertyName, Object... values) {
		properties.replaceValues(propertyName, Arrays.asList(values));
	}

}
