package com.cyc.model.templates.web;

import com.cyc.model.templates.VelocityTemplateGroup;

/**
 *
 * @author nwinant
 */
abstract public class WebTemplateGroup extends VelocityTemplateGroup {
  
  public WebTemplateGroup(String baseTargetDir, String baseTemplateDir) {
    super(baseTargetDir, baseTemplateDir);
  }
  
  public WebTemplateGroup() {
    this(WebTemplate.WEB_BASE_TARGET_DIR, WebTemplate.DEFAULT_WEB_TEMPLATE_DIR);
  }
  
  
  // Protected
  
  static protected String pluralize(String word) {
    if (word.endsWith("s")) {
      return word + "es";
    } 
    return word + "s";
  }
  
  
  // Internal
  
  protected static class JSFile extends LocalFile {
    public JSFile(String dir, String filename) {
      super(dir, filename);
    }
    @Override public String getFileName() { return this.filename + ".js"; }
  }
  
  protected static class HTMLFile extends LocalFile {
    public HTMLFile(String dir, String filename) {
      super(dir, filename);
    }
    @Override public String getFileName() { return this.filename + ".html"; }
  }
  
  protected static class CSSFile extends LocalFile {
    public CSSFile(String dir, String filename) {
      super(dir, filename);
    }
    @Override public String getFileName() { return this.filename + ".css"; }
  }
}
