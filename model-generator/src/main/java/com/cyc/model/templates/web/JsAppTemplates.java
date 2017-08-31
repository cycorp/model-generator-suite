package com.cyc.model.templates.web;

import com.cyc.model.objects.ClassObj;
import com.cyc.model.objects.ProjectObj;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nwinant
 */
public class JsAppTemplates extends WebTemplateGroup {
  
  // Constructors
  
  public JsAppTemplates(ProjectObj projectObj, String appName, String appFolder) {
    super();
    this.projectObj = projectObj;
    this.appName = appName;
    this.appFolder = appFolder;
  }
  
  public JsAppTemplates(ProjectObj projectObj) {
    this(projectObj, projectObj.getName().replaceAll("/", ""), "app");
  }
  
  
  // Public
  
  public String getAppName() { return this.appName; }
  public String getAppFolder() { return this.appFolder; }
  
  public JsClassTemplates createClassTemplates(ClassObj classObj) {
    return new JsClassTemplates(classObj, getAppName(), getAppFolder());
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
