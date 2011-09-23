package eu.esdihumboldt.cst.transformer.service;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Utility class that adds corefucntions jar to class path in the runtime. 
 * 
 * @author jezekjan
 *
 */
public class AddFunctionsToPathUtility {
	
	public static AddFunctionsToPathUtility getInstance(){
		return new AddFunctionsToPathUtility();
	}
	public void add() {
		Class[] parameters = new Class[]{URL.class};
		URL functions = getClass().getResource("/corefunctions-1.0.4.jar");		 //$NON-NLS-1$
		URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
	      Class sysclass = URLClassLoader.class;

	      try {
	         Method method = sysclass.getDeclaredMethod("addURL", parameters); //$NON-NLS-1$
	         method.setAccessible(true);
	         method.invoke(sysloader, new Object[]{functions});
	      } catch (Throwable t) {
	         t.printStackTrace();
	         //throw new IOException("Error, could not add URL to system classloader");
	      }

	}
}