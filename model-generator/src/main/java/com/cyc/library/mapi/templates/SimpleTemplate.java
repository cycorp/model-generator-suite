package com.cyc.library.mapi.templates;

import java.io.File;

/**
 *
 * @author nwinant
 */
public class SimpleTemplate extends VelocityTemplate {
  
  // Constructors
  
  public SimpleTemplate(String baseTargetDir, String outfileName, String templateFileName) {
    super(baseTargetDir);
    this.outfileName = outfileName;
    this.templateFileName = templateFileName;
  }
  
  
  // Public
  
  public File getTargetDir() {
    return new File(baseTargetDir);
  }
  
  @Override
  public String getTemplateFileName() { return templateFileName; }
  
  @Override
  public File getTargetFile() {
    return new File(getTargetDir() + FS + outfileName);
  }
  
  @Override
  public boolean isSufficientlyConfigured() {
    return true;
  }
  
  
  // Internal
  
  private final String outfileName;
  private final String templateFileName;
}
