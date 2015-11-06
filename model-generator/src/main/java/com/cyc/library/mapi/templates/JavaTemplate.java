package com.cyc.library.mapi.templates;

import com.cyc.library.model.InterfaceObj;
import com.cyc.library.model.MethodObj;
import com.cyc.library.model.ModelObj;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author nwinant
 */
abstract public class JavaTemplate extends VelocityTemplate {
  
  public JavaTemplate(String baseTargetDir) {
    super( baseTargetDir);
    setDefaults();
  }
  
  public JavaTemplate() {
    super(JAVA_BASE_TARGET_DIR);
    setDefaults();
  }
  
  
  // Public
  
  public void setRawPackageName(String pkg) {
    setProperty(PACKAGE_PROP, pkg);
  }
  
  public void setPackageName(String pkg) {
    setRawPackageName(pkg);
  }
  
  public Set<String> getImports() {
    return this.imports;
  }
  
  public void addImports(Collection<String> imports) {
    this.imports.addAll(imports);
  }
  
  public void setVisibility(String visibility) {
    setProperty(VISIBILITY_PROP, visibility);
  }
  
  public void setRawName(String name) {
    setProperty(NAME_PROP, name);
  }
  
  public void setName(String name) {
    setRawName(name);
  }
  
  public List<MethodObj> getMethods() {
    return this.methods;
  }
  
  public void addMethods(List<MethodObj> methods) {
    this.methods.addAll(methods);
  }
  
  public void setDefaults() {
    setVisibility("public");
  }
  
  public void populateFromObj(ModelObj obj) {
    setRawName(obj.getName());
  }
  
  public void populateFromObj(InterfaceObj obj) {
    setPackageName(obj.getFullPackageName());
    addImports(obj.getImports());
    populateFromObj((ModelObj) obj);
    setVisibility(obj.getVisibility());
    addMethods(obj.getMethods());
    setProperty(CYC_NAME_PROP, obj.getCycName());
  }
  
  public File getTargetDir() {
    final String javaPath = getProperty(PACKAGE_PROP).toString().replace(".", FS);
    return new File(baseTargetDir + ((!javaPath.isEmpty()) ? FS + javaPath : ""));
  }
  
  @Override
  public boolean isSufficientlyConfigured() {
    return hasProperty(PACKAGE_PROP) 
            && !getImports().isEmpty()
            && hasProperty(VISIBILITY_PROP) 
            && hasProperty(NAME_PROP);
  }
  
  @Override
  public File getTargetFile() {
    return new File(getTargetDir() + FS + getProperty(NAME_PROP) + ".java");
  }
  
  @Override
  public List<String> getMacroTemplates() {
    final List<String> templates = super.getMacroTemplates();
    templates.add(JAVA_MACROS_FILE);
    return templates;
  }
  
  
  // Protected
  
  @Override
  protected void performPreMerge() {
    super.performPreMerge();
    final List importList = new ArrayList(getImports());
    Collections.sort(importList);
    setProperty(IMPORTS_PROP, importList);
    setProperty(METHODS_PROP, getMethods());
  }
  
  
  // Internal
  
  public static final String JAVA_BASE_TARGET_DIR = MAVEN_BASE_TARGET_DIR;
  public static final String DEFAULT_JAVA_TEMPLATE_DIR = "templates" + FS + "java";
  public static final String DEFAULT_JAVA_OBJ_TEMPLATE_DIR = "templates" + FS + "java" + FS + "obj";

  public static final String JAVA_MACROS_FILE = DEFAULT_JAVA_TEMPLATE_DIR + FS + "java_macros.vm";
  
  public static final String PACKAGE_PROP = "package";
  public static final String IMPORTS_PROP = "imports";
  public static final String VISIBILITY_PROP = "visibility";
  public static final String NAME_PROP = "name";
  public static final String CYC_NAME_PROP = "cyc_name";
  public static final String METHODS_PROP = "methods";
  
  final private Set<String> imports = new HashSet<String>();
  final private List<MethodObj> methods = new ArrayList<MethodObj>();
}
