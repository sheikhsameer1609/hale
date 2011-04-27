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

package eu.esdihumboldt.hale.core.report.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.cs3d.util.logging.ALogger;
import de.cs3d.util.logging.ALoggerFactory;
import eu.esdihumboldt.hale.core.report.Message;
import eu.esdihumboldt.hale.core.report.Report;
import eu.esdihumboldt.hale.core.report.Reporter;

/**
 * Default report implementation
 * @param <T> the message type 
 *
 * @author Simon Templer
 * @partner 01 / Fraunhofer Institute for Computer Graphics Research
 * @since 2.2
 */
public abstract class DefaultReporter<T extends Message> implements Reporter<T> {
	
	/**
	 * The logger
	 */
	public static final ALogger log = ALoggerFactory.getMaskingLogger(DefaultReporter.class, null);
	
	private boolean success = false;
	
	private final Class<T> messageType;
	
	private final List<T> errors = new ArrayList<T>();
	
	private final List<T> warnings = new ArrayList<T>();
	
	private Date timestamp;
	
	private final boolean doLog;
	
	private final String taskName;

	/**
	 * Create an empty report. It is set to not successful by default. But you
	 * should call {@link #setSuccess(boolean)} nonetheless to update the
	 * timestamp after the task has finished.
	 * 
	 * @param taskName the name of the task the report is related to 
	 * @param messageType the message type
	 * @param doLog if added messages shall also be logged using {@link ALogger} 
	 */
	public DefaultReporter(String taskName, Class<T> messageType, boolean doLog) {
		super();
		this.messageType = messageType;
		this.doLog = doLog;
		this.taskName = taskName;
		
		timestamp = new Date();
	}

	/**
	 * Adds a warning to the report. If configured accordingly a log
	 * message will also be created.
	 * 
	 * @param message the warning message
	 */
	@Override
	public void warn(T message) {
		warnings.add(message);
		
		if (doLog) {
			log.warn(message.getMessage(), message.getThrowable());
		}
	}
	
	/**
	 * Adds an error to the report. If configured accordingly a log
	 * message will also be created.
	 * 
	 * @param message the error message
	 */
	@Override
	public void error(T message) {
		errors.add(message);
		
		if (doLog) {
			log.error(message.getMessage(), message.getThrowable());
		}
	}
	
	/**
	 * @see Report#getTaskName()
	 */
	@Override
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @see Report#getErrors()
	 */
	@Override
	public Collection<T> getErrors() {
		return Collections.unmodifiableList(errors);
	}

	/**
	 * @see Report#getMessageType()
	 */
	@Override
	public Class<T> getMessageType() {
		return messageType;
	}

	/**
	 * @see Report#getSummary()
	 */
	@Override
	public String getSummary() {
		if (success) {
			return getSuccessSummary();
		}
		else {
			return getFailSummary();
		}
	}

	/**
	 * Get the report summary if it was not successful
	 *  
	 * @return the report summary 
	 */
	protected abstract String getFailSummary();

	/**
	 * Get the report summary if it was successful
	 *  
	 * @return the report summary 
	 */
	protected abstract String getSuccessSummary();

	/**
	 * @see Report#getTimestamp()
	 */
	@Override
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @see Report#getWarnings()
	 */
	@Override
	public Collection<T> getWarnings() {
		return Collections.unmodifiableList(warnings);
	}

	/**
	 * Set if the task was successful. Also updates the report timestamp.
	 * 
	 * @param success if the task was successful
	 */
	public void setSuccess(boolean success) {
		this.success = success;
		
		timestamp = new Date();
	}

	/**
	 * @see Report#isSuccess()
	 */
	@Override
	public boolean isSuccess() {
		return success;
	}

}
