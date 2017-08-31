package com.cyc.model.templates;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nwinant
 */
abstract public class VelocityTemplateGroup {
  
  // Constructors
  
  public VelocityTemplateGroup(String baseTargetDir, String baseTemplateDir) {
    this.baseTargetDir = baseTargetDir;
    this.baseTemplateDir = baseTemplateDir;
    this.templates = new ArrayList<VelocityTemplate>();
    this.properties = new HashMap<String,Object>();
  }
  
  
  // Public
  
  public VelocityTemplate addTemplate(VelocityTemplate template) {
    addTemplateProperties(template);
    this.templates.add(template);
    return template;
  }
  
  public VelocityTemplate addLocalSimpleTemplate(String localTargetDir, String targetFileName, String localTemplateDir, String templateFileName) {
    final String targetDir;
    if ((localTargetDir != null) && !localTargetDir.isEmpty()) {
      targetDir = baseTargetDir + FS + localTargetDir;
    } else {
      targetDir = baseTargetDir;
    }
    final String templatePath;
    if ((localTemplateDir != null) && !localTemplateDir.isEmpty()) {
      templatePath = baseTemplateDir + FS + localTemplateDir + FS + templateFileName;
    } else {
      templatePath = baseTemplateDir + FS + templateFileName;
    }
    return addTemplate(new SimpleTemplate(
            targetDir, 
            targetFileName, 
            templatePath));
  }
  
  public VelocityTemplate addTemplate(LocalFile target, TemplateFile template) {
    return addLocalSimpleTemplate(
            target.getDir(), target.getFileName(),
            template.getDir(), template.getFileName());
  }
  
  public Map<String,Object> getProperties() {
    
    // FIXME: should we add a map of templates & names
    
    return this.properties;
  };
  
  public void generate(Writer writer) throws IOException {
    initializeTemplates();
    for (VelocityTemplate template : templates) {
      template.generate(writer);
    }
  }
  
  public List<File> generateFile() throws IOException {
    initializeTemplates();
    final List<File> results = new ArrayList<File>();
    for (VelocityTemplate template : templates) {
      results.add(template.generateFile());
    }
    return results;
  }
  
  public  List<String> generateString() throws IOException {
    initializeTemplates();
    final List<String> results = new ArrayList<String>();
    for (VelocityTemplate template : templates) {
      results.add(template.generateString());
    }
    return results;
  }
  
  public void printSummary(PrintStream out) {
    initializeTemplates();
    out.println("------------------------------");
    for (VelocityTemplate template : templates) {
      out.println("  " + template.getTemplateFileName());
      out.println("  -> " + template.getTargetFile());
    }
    out.println("------------------------------");
  }
  
  public void printSummary() {
    this.printSummary(System.out);
  }
  
  public List<VelocityTemplate> getTemplates() {
    return this.templates;
  }
  
  public void initializeTemplates() {
    if (this.templates.isEmpty()) {
      populateProperties(this.properties);
      addTemplates();
    }
  }
  
  
  // Protected
  
  abstract protected void populateProperties(Map<String,Object> properties);
  
  abstract protected void addTemplates();
  
  protected void addTemplateProperties(VelocityTemplate template) {
    final Map<String,Object> props = getProperties();
    for (String key : props.keySet()) {
      template.setProperty(key, props.get(key));
    }
  }
  
  
  // Internal
  
  protected static class LocalFile {
    final protected String dir;
    final protected String filename;
    public LocalFile(String dir, String filename) {
      this.dir = dir;
      this.filename = filename;
    }
    public String getDir() { return this.dir; }
    public String getFileName() { return this.filename; }
  }
  
  protected static class TemplateFile extends LocalFile {
    public TemplateFile(String dir, String filename) {
      super(dir, filename);
    }
    public String getFileName() { return this.filename + ".vsl"; }
  }
  
  protected static final String FS = System.getProperty("file.separator");
  final protected String baseTargetDir;
  final protected String baseTemplateDir;
  protected List<VelocityTemplate> templates;
  protected Map<String,Object> properties;
}