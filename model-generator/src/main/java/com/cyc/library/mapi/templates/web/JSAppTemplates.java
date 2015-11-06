package com.cyc.library.mapi.templates.web;

import com.cyc.library.model.ClassObj;
import com.cyc.library.model.ProjectObj;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nwinant
 */
public class JSAppTemplates extends WebTemplateGroup {
  
  // Constructors
  
  public JSAppTemplates(ProjectObj projectObj, String appName, String appFolder) {
    super();
    this.projectObj = projectObj;
    this.appName = appName;
    this.appFolder = appFolder;
  }
  
  public JSAppTemplates(ProjectObj projectObj) {
    this(projectObj, projectObj.getName().replaceAll("/", ""), "app");
  }
  
  
  // Public
  
  public String getAppName() { return this.appName; }
  public String getAppFolder() { return this.appFolder; }
  
  public JSClassTemplates createClassTemplates(ClassObj classObj) {
    return new JSClassTemplates(classObj, getAppName(), getAppFolder());
  }
  
  
  // Protected
  
  @Override
  protected void addTemplates() {
    addTemplate(new HTMLFile("", getAppFolder()), new TemplateFile("", "app_html"));
    addTemplate(new JSFile("", getAppFolder()), new TemplateFile("", "app_js"));
  }
  
  @Override
  protected void populateProperties(Map<String, Object> properties) {
    properties.put("project_obj", projectObj);
    properties.put("app_name", getAppName());
    properties.put("app_folder", getAppFolder());
    List<ClassSummary> classSummaries = new ArrayList<ClassSummary>();
    for (ClassObj classObj : projectObj.getAllClasses()) {
      classSummaries.add(new ClassSummary(classObj.getPrimaryImpl().getName()));
    }
    properties.put("class_summaries", classSummaries);
  }
  
  
  // Internal
  
  public static class ClassSummary {
    final private String className;
    final private String classesName;
    public ClassSummary(String className, String classesName) {
      this.className = className;
      this.classesName = classesName;
    }
    public ClassSummary(String className) {
      this(className, pluralize(className));
    }
    public String getClassName() { return this.className; }
    public String getClassesName() { return this.classesName; }
  }
  
  final protected ProjectObj projectObj;
  final protected String appName;
  final protected String appFolder;
}
