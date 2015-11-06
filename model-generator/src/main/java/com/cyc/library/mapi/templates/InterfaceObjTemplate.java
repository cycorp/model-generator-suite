package com.cyc.library.mapi.templates;

import com.cyc.library.model.InterfaceObj;
import java.util.List;

/**
 *
 * @author nwinant
 */
public class InterfaceObjTemplate extends InterfaceTemplate {
  
  public InterfaceObjTemplate(InterfaceObj obj) {
    super();
    this.populateFromObj(obj);
  }
  
  
  // Public
  
  @Override
  public String getTemplateFileName() { return TEMPLATE_FILE_NAME; }
  
  @Override
  public List<String> getMacroTemplates() {
    final List<String> templates = super.getMacroTemplates();
    templates.add(MACROS_FILE_NAME_1);
    templates.add(MACROS_FILE_NAME_2);
    return templates;
  }
  
  
  // Internal
  
  public static final String TEMPLATE_FILE_NAME = DEFAULT_JAVA_OBJ_TEMPLATE_DIR + FS + "interfaceobj.vm";
  public static final String MACROS_FILE_NAME_1 = DEFAULT_JAVA_OBJ_TEMPLATE_DIR + FS + "mapiobj_macros.vm";
  public static final String MACROS_FILE_NAME_2 = DEFAULT_JAVA_OBJ_TEMPLATE_DIR + FS + "interfaceobj_macros.vm";
  public static final String SUB_PACKAGE = "iface";
}
