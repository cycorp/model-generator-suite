package com.cyc.model.templates;

import com.cyc.model.objects.ClassObj;
import com.cyc.model.objects.InterfaceObj;
import com.cyc.model.objects.ModelObj;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author nwinant
 */
public class ClassTemplate extends JavaTemplate {
  
  public ClassTemplate(String baseTargetDir) {
    super(baseTargetDir);
  }
  
  public ClassTemplate() {
    super();
  }
  
  
  // Public
  
  public void populateFromObj(ClassObj obj) {
    for (InterfaceObj iobj : obj.getImpls()) {
      addInterfaceObj(iobj);
    }
    addInterfaceObj(obj.getPrimaryImpl());
    addImports(obj.getImports());
    setPackageName(obj.getFullPackageName());
    setProperty(CLASS_OBJ_PROP, obj);
    if (obj.getSuperClass() != null) {
      setSuperClass(obj.getSuperClass());
    }
    populateFromObj((ModelObj) obj);
  }
  
  @Override
  public void setName(String name) {
    setRawName(name + "Impl");
  }
  
  @Override
  public String getTemplateFileName() { return TEMPLATE_FILE_NAME; }
  
  @Override
  public List<String> getMacroTemplates() {
    final List<String> templates = super.getMacroTemplates();
    templates.add(MACROS_FILE_NAME);
    return templates;
  }
  
  public void setSuperClass(String classname) {
    setProperty(SUPERCLASS_PROP, classname);
  }
  
  
  // Protected
  
  @Override
  protected void performPreMerge() {
    super.performPreMerge();
    setProperty(INTERFACES_PROP, this.interfaces);
  }
  
  protected void addInterfaceObj(InterfaceObj obj) {
    super.populateFromObj(obj);
    interfaces.add(obj.getName());
  }
  
  protected void setInterfaceObjs(List<InterfaceObj> objs) {
    interfaces.clear();
    for (InterfaceObj obj : objs) {
      addInterfaceObj(obj);
    }
  }
  
  
  // Internal
  
  public static final String TEMPLATE_FILE_NAME = DEFAULT_JAVA_TEMPLATE_DIR + FS + "class.vm";
  public static final String MACROS_FILE_NAME = DEFAULT_JAVA_TEMPLATE_DIR + FS + "class_macros.vm";
  public static final String SUPERCLASS_PROP = "superclass";
  public static final String INTERFACES_PROP = "interfaces";
  public static final String CLASS_OBJ_PROP = "class_obj";

  private Set<String> interfaces = new HashSet<String>();
}
