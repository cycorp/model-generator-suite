package com.cyc.library.mapi.templates;

import com.cyc.base.CycApiException;
import com.cyc.base.cycobject.CycObject;
import com.cyc.base.inference.InferenceResultSet;
import com.cyc.baseclient.cycobject.CycVariableImpl;
import com.cyc.library.mapi.AbstractCycModelObjectProvider;
import com.cyc.library.mapi.exceptions.ModelAPIException;
import com.cyc.library.mapi.exceptions.ModelAPIRuntimeException;
import com.cyc.library.model.ClassObj;
import com.cyc.query.QueryResultSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author nwinant
 */
public class ClassObjProviderTemplate extends ClassTemplate {
  
  public ClassObjProviderTemplate(ClassObj obj) {
    super();
    this.populateFromObj(obj);
  }
  
  
  // Public
  
  public void populateFromObj(ClassObj obj) {
    super.populateFromObj(obj);
    final String implName = obj.getFullPackageName() + "." + obj.getName();
    setProperty(CLASS_IMPL_PROP, obj.getName());
    setPackageName(obj. getPrimaryImpl().getPackageName() + "." + SUB_PACKAGE);
    setSuperClass("AbstractCycModelObjectProvider");
    addImports(Arrays.asList(
            implName,
            AbstractCycModelObjectProvider.class.getCanonicalName(),
            ModelAPIException.class.getCanonicalName(),
            ModelAPIRuntimeException.class.getCanonicalName(),
            "java.io.IOException",
            "java.util.ArrayList",
            "java.util.Collections",
            "java.util.Comparator",
            "java.util.HashSet",
            "java.util.List",
            "java.util.Set",
            CycApiException.class.getCanonicalName(),
            CycObject.class.getCanonicalName(),
            CycVariableImpl.class.getCanonicalName(),
            QueryResultSet.class.getCanonicalName(),
            InferenceResultSet.class.getCanonicalName()//,
            // TimeOutException.class.getCanonicalName()
            ));
    setName(obj.getPrimaryImpl().getName());
    setInterfaceObjs(new ArrayList());
  }
  
  @Override
  public void setName(String name) {
    setRawName(name + "Provider");
  }
    
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
  
  public static final String TEMPLATE_FILE_NAME = DEFAULT_JAVA_OBJ_TEMPLATE_DIR + FS + "classobjprovider.vm";
  public static final String MACROS_FILE_NAME_1 = DEFAULT_JAVA_OBJ_TEMPLATE_DIR + FS + "mapiobj_macros.vm";
  public static final String MACROS_FILE_NAME_2 = DEFAULT_JAVA_OBJ_TEMPLATE_DIR + FS + "classobj_macros.vm";
  public static final String SUB_PACKAGE = "provider";
  public static final String CLASS_IMPL_PROP = "class_impl";
}
