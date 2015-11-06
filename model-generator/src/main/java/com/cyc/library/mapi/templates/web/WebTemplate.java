package com.cyc.library.mapi.templates.web;

import com.cyc.library.mapi.templates.VelocityTemplate;
import java.io.File;

/**
 *
 * @author nwinant
 */
abstract public class WebTemplate extends VelocityTemplate {
  
  public WebTemplate(String baseTargetDir) {
    super(baseTargetDir);
  }
  
  public WebTemplate() {
    super(WEB_BASE_TARGET_DIR);
  }
  
  
  // Public
  
  public File getTargetDir() {
    return new File(baseTargetDir);
  }
  
  
  // Internal
  
//  public static final String WEB_BASE_TARGET_DIR = "target/generated-sources/webapp";
  public static final String WEB_BASE_TARGET_DIR = "src/main/webapp";
  public static final String DEFAULT_WEB_TEMPLATE_DIR = "templates/web";
}
