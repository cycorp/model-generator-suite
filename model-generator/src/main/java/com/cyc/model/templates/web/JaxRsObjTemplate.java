package com.cyc.model.templates.web;

import com.cyc.model.templates.ClassTemplate;
import com.cyc.model.objects.ClassObj;
import com.cyc.model.objects.MethodObj;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author nwinant
 */
public class JaxRsObjTemplate extends ClassTemplate {
  
  public JaxRsObjTemplate(ClassObj obj) {
    super();
    populateFromObj(obj);
    setup();
  }
  
  
  // Public
  
  @Override
  public void setPackageName(String pkg) {
    super.setPackageName(pkg + "." + SUB_PACKAGE);
  }
  
  @Override
  public void setName(String name) {
    setRawName(name + "WS");
  }
    
  @Override
  public String getTemplateFileName() { return TEMPLATE_FILE_NAME; }
  
  @Override
  public List<String> getMacroTemplates() {
    final List<String> templates = super.getMacroTemplates();
    templates.add(MACROS_FILE_NAME);
    return templates;
  }
  
  @Override
  public void populateFromObj(ClassObj obj) {
    super.populateFromObj(obj);
    setProperty(JAX_PATH_PROP, "/" + obj.getPrimaryImpl().getName().toLowerCase());
    setPackageName(obj.getPrimaryImpl().getPackageName());
    addImports(Arrays.asList(obj.getFullPackageName() + "." + obj.getName()));
    setName(obj.getPrimaryImpl().getName());
    wrapMethods(obj);
  }
  
  
  // Protected
  
  protected void setup() {
    addImports(Arrays.asList(
            "com.cyc.model.SimpleContextMap",
            "com.cyc.kb.exception.KbException",
            "com.cyc.library.restfulserviceutil.param.DateParam",
            "com.cyc.library.restfulserviceutil.service.CycRestfulWS",
            "javax.ws.rs.DefaultValue",
            "javax.ws.rs.GET",
            "javax.ws.rs.POST",
            "javax.ws.rs.Path",
            "javax.ws.rs.PathParam",
            "javax.ws.rs.Produces",
            "javax.ws.rs.QueryParam",
            "javax.ws.rs.core.MediaType",
            "javax.ws.rs.core.Response"
            ));
    setSuperClass("CycRestfulWS");
  }
  
  protected void wrapMethods(ClassObj obj) {
    final List<WsMethod> methods = new ArrayList<WsMethod>();
    for (MethodObj method : this.getMethods()) {
      methods.add(new WsMethod(method));
    }
    setProperty(WS_METHODS_OBJ, methods);
  }
  
  
  // Internal
  
  public static final String TEMPLATE_FILE_NAME = DEFAULT_JAVA_OBJ_TEMPLATE_DIR + FS + "jaxrsobj.vm";
  public static final String MACROS_FILE_NAME = DEFAULT_JAVA_OBJ_TEMPLATE_DIR + FS + "jaxrs_macros.vm";
  public static final String JAX_PATH_PROP = "jaxrs_path";
  public static final String SUB_PACKAGE = "ws";
  public static final String CLASS_OBJ_PROP = "class_obj";
  public static final String WS_METHODS_OBJ = "ws_methods";
}
