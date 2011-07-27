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

package eu.esdihumboldt.hale.align.transformation.report.impl;

import net.jcip.annotations.Immutable;
import eu.esdihumboldt.hale.align.model.Cell;
import eu.esdihumboldt.hale.align.transformation.report.TransformationMessage;
import eu.esdihumboldt.hale.core.report.impl.MessageImpl;

/**
 * Default {@link TransformationMessage} implementation
 * @author Simon Templer
 */
@Immutable
public class TransformationMessageImpl extends MessageImpl implements
		TransformationMessage {

	private final Cell cell;

	/**
	 * Create a new transformation message
	 * 
	 * @param cell the cell the message is associated to
	 * @param message the message
	 * @param throwable the throwable associated to the message, may be <code>null</code>
	 */
	public TransformationMessageImpl(Cell cell, String message, Throwable throwable) {
		super(message, throwable);
		
		this.cell = cell;
	}

	/**
	 * @see TransformationMessage#getCell()
	 */
	@Override
	public Cell getCell() {
		return cell;
	}

}
