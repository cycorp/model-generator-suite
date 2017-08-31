package com.cyc.model.templates.web;

import com.cyc.model.objects.ProjectObj;
import java.io.File;

/**
 *
 * @author nwinant
 */
public class IndexTemplate extends WebTemplate {
  
  public IndexTemplate(ProjectObj obj) {
    super();
    populateFromObj(obj);
  }
  
  
  // Public
  
  public void populateFromObj(ProjectObj obj) {
    setProperty(PROJECT_PROP, obj);
  }
  
  @Override
  public String getTemplateFileName() { return TEMPLATE_FILE_NAME; }
  
  @Override
  public File getTargetFile() {
    return new File(getTargetDir() + FS + "index.jsp");
  }

  @Override
  public boolean isSufficientlyConfigured() {
    return this.hasProperty(PROJECT_PROP);
  }
  
  
  // Internal
  public static final String TEMPLATE_FILE_NAME = DEFAULT_WEB_TEMPLATE_DIR + FS + "index.vsl";
  public static final String PROJECT_PROP = "project";
}
