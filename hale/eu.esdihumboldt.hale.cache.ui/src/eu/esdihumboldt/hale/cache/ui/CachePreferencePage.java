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

package eu.esdihumboldt.hale.cache.ui;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.fhg.igd.osgi.util.OsgiUtils;
import de.fhg.igd.osgi.util.configuration.IConfigurationService;
import de.fhg.igd.osgi.util.configuration.JavaPreferencesConfigurationService;
import de.fhg.igd.osgi.util.configuration.NamespaceConfigurationServiceDecorator;
import eu.esdihumboldt.hale.cache.Request;

/**
 * The preference page for cache settings.
 * 
 * @author Andreas Burchert
 * @partner 01 / Fraunhofer Institute for Computer Graphics Research
 * @version $Id$
 */
public class CachePreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	private IConfigurationService org;
	private static final String DELIMITER = "/"; //$NON-NLS-1$
	private PreferenceStore prefs = new PreferenceStore();
	
	/**
	 * Constructor.
	 */
	public CachePreferencePage() {
		super(GRID);
		
		IConfigurationService org = OsgiUtils.getService(IConfigurationService.class);
		if (org == null) {
			// if no configuration service is present, fall back to new instance
			
			// 1. use user prefs, may not have rights to access system prefs
		    // 2. no default properties
		    // 3. don't default to system properties
			org = new JavaPreferencesConfigurationService(false, null, false);
		}
		
		this.org = new NamespaceConfigurationServiceDecorator(
				org, 
				Request.class.getPackage().getName().replace(".", DELIMITER),  //$NON-NLS-1$
				DELIMITER);
		
		// setup PreferenceStore save path
		prefs.setFilename(org.get("cache.path", Platform.getLocation().toString())+"/Cache.pref"); //$NON-NLS-1$
		setPreferenceStore(prefs);
	}
	
	/**
	 * @see IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
		//nothing to do here
	}

	@Override
	protected void createFieldEditors() {
		// restore data
		prefs.setValue("cache.enabled", org.getBoolean("cache.enabled", false)); //$NON-NLS-1$ //$NON-NLS-2$
		prefs.setValue("cache.path",	org.get("cache.path", Platform.getLocation().toString())); //$NON-NLS-1$ //$NON-NLS-2$
		prefs.setValue("cache.name",	org.get("cache.name", "HALE_WebRequest")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		
		// add fields
		addField(new BooleanFieldEditor("cache.enabled", Messages.CachePreferencePage_10, getFieldEditorParent())); //$NON-NLS-1$
		addField(new DirectoryFieldEditor("cache.path", Messages.CachePreferencePage_12, getFieldEditorParent())); //$NON-NLS-1$
		addField(new StringFieldEditor("cache.name", Messages.CachePreferencePage_14, 20, getFieldEditorParent())); //$NON-NLS-1$
		
		// placeholder
//		Composite ph = new Composite(getFieldEditorParent(), SWT.NONE);
//		ph.setLayoutData(GridDataFactory.swtDefaults().hint(0, 0).create());
		
		Button clearCache = new Button(getFieldEditorParent(), GRID);
		clearCache.setText(Messages.CachePreferencePage_15);
		clearCache.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				Request.getInstance().clear();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {/* not needed */}
		});
		clearCache.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 1, 1));
	}
	
	/**
	 * @see FieldEditorPreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		org.setBoolean("cache.enabled", prefs.getBoolean("cache.enabled")); //$NON-NLS-1$ //$NON-NLS-2$
		org.set("cache.path", prefs.getString("cache.path")); //$NON-NLS-1$ //$NON-NLS-2$
		org.set("cache.name", prefs.getString("cache.name")); //$NON-NLS-1$ //$NON-NLS-2$
		
		//
		Request.getInstance().setEnabled(prefs.getBoolean("cache.enabled")); //$NON-NLS-1$

		return super.performOk();
	}
}