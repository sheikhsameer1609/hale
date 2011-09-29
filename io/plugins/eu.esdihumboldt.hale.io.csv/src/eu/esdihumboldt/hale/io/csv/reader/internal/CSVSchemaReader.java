/*
 * HUMBOLDT: A Framework for Data Harmonization and Service Integration.
 * EU Integrated Project #030962                 01.10.2006 - 30.09.2010
 * 
 * For more information on the project, please refer to the this web site:
 * http://www.esdi-humboldt.eu
 * 
 * LICENSE: For information on the license under which this program is 
 * available, please refer to http:/www.esdi-humboldt.eu/license.html#core
 * (c) the HUMBOLDT Consortium, 2007 to 2010.
 */
package eu.esdihumboldt.hale.io.csv.reader.internal;

import java.io.IOException;
import java.util.Arrays;

import javax.xml.namespace.QName;

import au.com.bytecode.opencsv.CSVReader;
import eu.esdihumboldt.hale.common.core.io.ContentType;
import eu.esdihumboldt.hale.common.core.io.IOProvider;
import eu.esdihumboldt.hale.common.core.io.IOProviderConfigurationException;
import eu.esdihumboldt.hale.common.core.io.ProgressIndicator;
import eu.esdihumboldt.hale.common.core.io.impl.AbstractIOProvider;
import eu.esdihumboldt.hale.common.core.io.report.IOReport;
import eu.esdihumboldt.hale.common.core.io.report.IOReporter;
import eu.esdihumboldt.hale.common.schema.io.SchemaReader;
import eu.esdihumboldt.hale.common.schema.io.impl.AbstractSchemaReader;
import eu.esdihumboldt.hale.common.schema.model.Schema;
import eu.esdihumboldt.hale.common.schema.model.constraint.property.Cardinality;
import eu.esdihumboldt.hale.common.schema.model.constraint.property.NillableFlag;
import eu.esdihumboldt.hale.common.schema.model.constraint.type.AbstractFlag;
import eu.esdihumboldt.hale.common.schema.model.constraint.type.HasValueFlag;
import eu.esdihumboldt.hale.common.schema.model.constraint.type.MappableFlag;
import eu.esdihumboldt.hale.common.schema.model.impl.DefaultPropertyDefinition;
import eu.esdihumboldt.hale.common.schema.model.impl.DefaultSchema;
import eu.esdihumboldt.hale.common.schema.model.impl.DefaultTypeDefinition;
import eu.esdihumboldt.hale.io.csv.CSVFileIO;

/**
 * Reads a schema from a CSV file.
 * 
 * @author Thorsten Reitz
 * @author Simon Templer
 */
public class CSVSchemaReader extends AbstractSchemaReader implements
		CSVConstants {

	/**
	 * The first line of the CSV file
	 */
	public static String[] firstLine;

	private DefaultSchema schema;

	/**
	 * Name of the parameter specifying the property name
	 */
	public static final String PARAM_PROPERTY = "properties";
	
	/**
	 * Name of the parameter specifying the property type
	 */
	public static final String PARAM_PROPERTYTYPE = "comboviewer";

	/**
	 * @see IOProvider#isCancelable()
	 */
	@Override
	public boolean isCancelable() {
		return false;
	}

	/**
	 * @see SchemaReader#getSchema()
	 */
	@Override
	public Schema getSchema() {
		return schema;
	}

	/**
	 * @see AbstractIOProvider#execute(ProgressIndicator, IOReporter)
	 */
	@Override
	protected IOReport execute(ProgressIndicator progress, IOReporter reporter)
			throws IOProviderConfigurationException, IOException {
		progress.begin("Load CSV schema", ProgressIndicator.UNKNOWN); //$NON-NLS-1$

		String namespace = CSVFileIO.CSVFILE_NS;
		schema = new DefaultSchema(namespace, getSource().getLocation());

		CSVReader reader = CSVUtil.readFirst(this);

		try {
			// create type definition
			String typename = getParameter(PARAM_TYPENAME);
			DefaultTypeDefinition type = new DefaultTypeDefinition(new QName(
					typename));

			// constraints on main type
			type.setConstraint(MappableFlag.ENABLED);
			type.setConstraint(HasValueFlag.DISABLED);
			type.setConstraint(AbstractFlag.DISABLED);

			// set metadata for main type
			type.setLocation(getSource().getLocation());

			String[] comboSelections = getParameter(PARAM_PROPERTYTYPE).split(",");
			String[] properties = getParameter(PARAM_PROPERTY).split(",");
			
			for (int i=0; i < comboSelections.length; i++) {
				PropertyType propertyType;
				propertyType = PropertyTypeExtension.getInstance().get(comboSelections[i]);
				
				DefaultPropertyDefinition property = new DefaultPropertyDefinition(
						new QName(properties[i]), type, propertyType.getTypeDefinition() );

				// set constraints on property
				property.setConstraint(NillableFlag.DISABLED); // nillable
				property.setConstraint(Cardinality.CC_EXACTLY_ONCE); // cardinality

				// set metadata for property
				property.setLocation(getSource().getLocation());
			}

			// initializes the first line of the table (names of the columns)
			firstLine = reader.readNext();

			//String[] properties = getParameter(PARAM_PROPERTY).split(",");

			boolean skip = Arrays.equals(properties, firstLine);

			type.setConstraint(new CSVConfiguration(CSVUtil.getSep(this),
					CSVUtil.getQuote(this), CSVUtil.getEscape(this), skip));

			schema.addType(type);

		} catch (Exception ex) {
			throw new RuntimeException(ex);

		}

		reporter.setSuccess(true);
		return reporter;
	}

	/**
	 * @see AbstractIOProvider#getDefaultContentType()
	 */
	@Override
	protected ContentType getDefaultContentType() {
		return CSVFileIO.CSVFILE_CT;
	}

}