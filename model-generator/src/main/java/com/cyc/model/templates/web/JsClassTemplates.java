package com.cyc.model.templates.web;

import com.cyc.model.objects.ClassObj;
import com.cyc.model.objects.MethodObj;
import com.cyc.model.objects.MethodObj.FuncType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nwinant
 */
public class JsClassTemplates extends WebTemplateGroup {
  public JsClassTemplates(ClassObj classObj, String appName, String appFolder) {
    super();
    this.classObj = classObj;
    this.appName = appName;
    this.appFolder = appFolder;
  }
  
  
  // Protected
  
  @Override
  protected void addTemplates() {
    final String outname = classObj.getPrimaryImpl().getName();
    addTemplate(new JSFile(appFolder + "/controller", pluralize(outname)), new TemplateFile("app", "controller"));
    addTemplate(new JSFile(appFolder + "/model", outname), new TemplateFile("app", "model"));
    addTemplate(new JSFile(appFolder + "/store", pluralize(outname)), new TemplateFile("app", "store"));
    addTemplate(new JSFile(appFolder + "/view/" + outname, "Edit"), new TemplateFile("app/classname", "Edit"));
    addTemplate(new JSFile(appFolder + "/view/" + outname, "List"), new TemplateFile("app/classname", "List"));
  }
  
  @Override
  protected void populateProperties(Map<String, Object> properties) {
    properties.put("class_obj", classObj);
    properties.put("app_name", appName);
    properties.put("app_folder", appFolder);
    properties.put("class", classObj.getPrimaryImpl().getName());
    properties.put("classes", pluralize(classObj.getPrimaryImpl().getName()));
    final List<ModelField> fields = new ArrayList<ModelField>();
    for (MethodObj method : classObj.getMethods()) {
      if (FuncType.GET.equals(method.getfType())) {
        fields.add(encapsulateMethod(method));
      }
    }
    properties.put("model_fields", fields);
  }
  
  protected ModelField encapsulateMethod(MethodObj method) {
    String dataType;
    if ("java.util.Date".equals(method.getReturnValue().getType())) {
      dataType = "date"; 
    } else {
      dataType = "string";
    }
    return new ModelField(method.getPredicate().toString(), dataType);
  }
  
  
  // Inner classes
  
  public static class ModelField {
    final private String predicate;
    final private String dataType;
    public ModelField(String predicate, String dataType) {
      this.predicate = predicate;
      this.dataType = dataType;
    }
    public String getPredicate() { return this.predicate; }
    public String getDataType() { return this.dataType; }
  }
  
  
  // Internal
  
  final protected ClassObj classObj;
  final protected String appName;
  final protected String appFolder;
}