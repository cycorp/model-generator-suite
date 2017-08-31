package com.cyc.model.templates;

import com.cyc.model.objects.ClassObj;
import java.util.List;

/**
 *
 * @author nwinant
 */
public class ClassObjTemplate extends ClassTemplate {
  
  public ClassObjTemplate(ClassObj obj) {
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
  
  public static final String TEMPLATE_FILE_NAME = DEFAULT_JAVA_OBJ_TEMPLATE_DIR + FS + "classobj.vm";
  public static final String MACROS_FILE_NAME_1 = DEFAULT_JAVA_OBJ_TEMPLATE_DIR + FS + "mapiobj_macros.vm";
  public static final String MACROS_FILE_NAME_2 = DEFAULT_JAVA_OBJ_TEMPLATE_DIR + FS + "classobj_macros.vm";
  public static final String SUB_PACKAGE = "cycImpl";
}
