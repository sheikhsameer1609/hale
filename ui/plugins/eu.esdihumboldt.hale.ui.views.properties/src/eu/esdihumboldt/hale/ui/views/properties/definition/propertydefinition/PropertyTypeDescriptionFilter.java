/*
 * Copyright (c) 2012 Data Harmonisation Panel
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     HUMBOLDT EU Integrated Project #030962
 *     Data Harmonisation Panel <http://www.dhpanel.eu>
 */

package eu.esdihumboldt.hale.ui.views.properties.definition.propertydefinition;

import eu.esdihumboldt.hale.common.schema.model.Definition;
import eu.esdihumboldt.hale.common.schema.model.PropertyDefinition;
import eu.esdihumboldt.hale.ui.views.properties.definition.DefaultDefinitionFilter;

/**
 * Filter that lets only {@link PropertyDefinition}s with a description that is
 * not <code>null</code> pass.
 * 
 * @author Patrick Lieb
 */
public class PropertyTypeDescriptionFilter extends DefaultDefinitionFilter {

	/**
	 * @see eu.esdihumboldt.hale.ui.views.properties.definition.DefaultDefinitionFilter#isFiltered(eu.esdihumboldt.hale.common.schema.model.Definition)
	 */
	@Override
	public boolean isFiltered(Definition<?> input) {
		if (input instanceof PropertyDefinition) {
			return ((PropertyDefinition) input).getPropertyType().getDescription() == null;
		}
		return true;
	}

}
