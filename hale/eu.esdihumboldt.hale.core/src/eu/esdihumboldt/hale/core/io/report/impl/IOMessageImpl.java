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

package eu.esdihumboldt.hale.core.io.report.impl;

import net.jcip.annotations.Immutable;
import eu.esdihumboldt.hale.core.io.report.IOMessage;
import eu.esdihumboldt.hale.core.report.impl.MessageImpl;

/**
 * Default {@link IOMessage} implementation
 *
 * @author Simon Templer
 * @partner 01 / Fraunhofer Institute for Computer Graphics Research
 * @since 2.2
 */
@Immutable
public class IOMessageImpl extends MessageImpl implements IOMessage {

	private final int column;
	
	private final int lineNumber;

	/**
	 * Create a new message
	 * 
	 * @param message the message string
	 * @param throwable the associated throwable, may be <code>null</code>
	 */
	public IOMessageImpl(String message, Throwable throwable) {
		this(message, throwable, -1, -1);
	}

	/**
	 * Create a new message
	 * 
	 * @param message the message string
	 * @param throwable the associated throwable, may be <code>null</code>
	 * @param lineNumber the line number in the file, <code>-1</code> for none
	 * @param column the column in the line, <code>-1</code> for none
	 */
	public IOMessageImpl(String message, Throwable throwable, int lineNumber,
			int column) {
		super(message, throwable);
		this.column = column;
		this.lineNumber = lineNumber;
	}

	/**
	 * @see IOMessage#getColumn()
	 */
	@Override
	public int getColumn() {
		return column;
	}

	/**
	 * @see IOMessage#getLineNumber()
	 */
	@Override
	public int getLineNumber() {
		return lineNumber;
	}

}