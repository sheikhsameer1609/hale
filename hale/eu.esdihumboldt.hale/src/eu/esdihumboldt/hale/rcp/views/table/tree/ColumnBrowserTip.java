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

package eu.esdihumboldt.hale.rcp.views.table.tree;

import org.eclipse.jface.viewers.ColumnViewer;

/**
 * Browser Tip for a certain column
 *
 * @author Simon Templer
 * @partner 01 / Fraunhofer Institute for Computer Graphics Research
 * @version $Id$ 
 */
public class ColumnBrowserTip extends BrowserColumnViewerTip {
	
	private final int column;
	
	private final TipProvider tipProvider;

	/**
	 * Constructor
	 * 
	 * @param viewer the column viewer
	 * @param width the tip width
	 * @param height the tip height
	 * @param plainText if plain text or HTML shall be displayed
	 * @param column the column index
	 * @param tipProvider the tool tip provider, if a <code>null</code> tip provider
	 *   is used, the cell's text will be used for the tool tip
	 */
	public ColumnBrowserTip(ColumnViewer viewer, int width, int height,
			boolean plainText, int column, TipProvider tipProvider) {
		super(viewer, width, height, plainText);
		this.column = column;
		this.tipProvider = tipProvider;
	}

	/**
	 * @see BrowserColumnViewerTip#getToolTip(Object, int, String)
	 */
	@Override
	protected String getToolTip(Object element, int col, String text) {
		if (col == column) {
			if (tipProvider != null) {
				return tipProvider.getToolTip(element);
			}
			else {
				return text;
			}
		}
		return null;
	}

}