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

package eu.esdihumboldt.hale.models;

import java.util.HashMap;
import java.util.List;

import eu.esdihumboldt.hale.models.project.generated.ConfigSection;

/**
 * 
 * @author Andreas Burchert
 * @partner 01 / Fraunhofer Institute for Computer Graphics Research
 * @version $Id$
 */
public interface ConfigSchemaService extends UpdateService {

	public void addSection(String name);
	public void addSection(String name, HashMap<String, String> data);
	public void removeSection(String name);
	
	public void addItem(String section, String key, String value);
	
	public HashMap<String, String> getSectionData(String name);
	
	public void parseConfig(List<ConfigSection> list);
	public List<ConfigSection> generateConfig();
}
