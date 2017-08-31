package com.cyc.model.templates;

import com.cyc.model.objects.InterfaceObj;
import java.util.List;

/**
 *
 * @author nwinant
 */
public class InterfaceTemplate extends JavaTemplate {
  
  public InterfaceTemplate(String baseTargetDir) {
    super(baseTargetDir);
  }
  
  public InterfaceTemplate() {
    super();
  }
  
  
  // Public
  
  @Override
  public String getTemplateFileName() { return TEMPLATE_FILE_NAME; }
    
  @Override
  public List<String> getMacroTemplates() {
    final List<String> templates = super.getMacroTemplates();
    templates.add(CLASS_MACROS_FILE);
    return templates;
  }
  
    public void populateFromObj(InterfaceObj obj) {
    super.populateFromObj(obj);
    setProperty(IFACE_OBJ_PROP, obj);
  }
  
  
  // Internal
  
  public static final String TEMPLATE_FILE_NAME = DEFAULT_JAVA_TEMPLATE_DIR + FS + "interface.vm";
  public static final String CLASS_MACROS_FILE = DEFAULT_JAVA_TEMPLATE_DIR + FS + "interface_macros.vm";
  public static final String IFACE_OBJ_PROP = "iface_obj";

}
