package com.cyc.library.mapi.templates.web;

import com.cyc.library.model.InterfaceObj;
import com.cyc.library.model.PackageObj;
import com.cyc.library.model.ProjectObj;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author nwinant
 */
public class WebXmlTemplate extends WebTemplate {
  
  public WebXmlTemplate(ProjectObj obj) {
    super();
    populateFromObj(obj);
  }
  
  
  // Public
  
  public void populateFromObj(ProjectObj obj) {
    // FIXME: this needs something...
    
    List<ServletDesc> servlets = new ArrayList<ServletDesc>();
    for (PackageObj pkg : obj.getPackages()) {
      for (InterfaceObj iface : pkg.getInterfaces()) {
//        servlets.add(new ServletDesc(iface.getCycName(), iface.getFullPackageName()));
        servlets.add(new ServletDesc("ws", iface.getPackageName() + ".ws"));
      }
    }
    Collections.sort(servlets);
    setProperty(SERVLETS_PROP, servlets);
  }
  
  @Override
  public String getTemplateFileName() { return TEMPLATE_FILE_NAME; }
  
  @Override
  public File getTargetFile() {
    return new File(getTargetDir() + FS + "WEB-INF" + FS + "web.xml");
  }

  @Override
  public boolean isSufficientlyConfigured() {
    return this.hasProperty(SERVLETS_PROP);
  }
  
  
  // Internal
  
  public static class ServletDesc implements Comparable<ServletDesc> {
    final public String servletName;
    final public String servletPackage;
    
    public ServletDesc(String servletName, String servletPackage) {
      this.servletName = servletName.toLowerCase();
      this.servletPackage = servletPackage;
    }
    
    public String getName() { return this.servletName; }
    public String getPackage() { return this.servletPackage; }

    @Override
    public int compareTo(ServletDesc o) {
      int value = servletPackage.compareTo(o.servletPackage);
      if (value != 0) {
        return value;
      }
      return servletName.compareTo(servletPackage);
    }
  }
  
  public static final String TEMPLATE_FILE_NAME = DEFAULT_WEB_TEMPLATE_DIR + FS + "webxml.vsl";
  public static final String SERVLETS_PROP = "servlets";
}