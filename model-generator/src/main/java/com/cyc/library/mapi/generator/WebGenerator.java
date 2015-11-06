package com.cyc.library.mapi.generator;

import com.cyc.library.mapi.templates.VelocityTemplate;
import com.cyc.library.mapi.templates.web.IndexTemplate;
import com.cyc.library.mapi.templates.web.JSAppTemplates;
import com.cyc.library.mapi.templates.web.JSClassTemplates;
import com.cyc.library.mapi.templates.web.JaxRSObjTemplate;
import com.cyc.library.mapi.templates.web.WebXmlTemplate;
import com.cyc.library.model.ClassObj;
import com.cyc.library.model.ProjectObj;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nwinant
 */
public class WebGenerator extends TemplateGenerator {
  
  // Constructors
  
  public WebGenerator(List<String> enabledDirectives) {
    super(enabledDirectives);
  }
  
  public WebGenerator() {
    this(getDefaultDirectives());
  }
  
  public static List<String> getDefaultDirectives() {
    return new ArrayList(Arrays.asList(
            GENERATE_WS_DIRECTIVE,
            GENERATE_UI_DIRECTIVE
            ));
  }
  
  
  // Public
  
  public void generateWebApp(ProjectObj project) throws IOException {
    final WebXmlTemplate webxml = configTemplate(new WebXmlTemplate(project));
    generate(webxml);
//    final IndexTemplate index = configTemplate(new IndexTemplate(project));
//    generate(index);
    this.appTemplates = new JSAppTemplates(project);
    generate(appTemplates);
  }
  
    public void generateWS(ClassObj obj) throws IOException {
    final JaxRSObjTemplate template = configTemplate(new JaxRSObjTemplate(obj));
    getLog().log(Level.INFO, "Generating JAX-RS service {0}", template.getTargetFile());
    generate(template);
  }
  
  public void generateUI(ClassObj obj) throws IOException {
    JSClassTemplates templates = appTemplates.createClassTemplates(obj);
    for (VelocityTemplate template : templates.getTemplates()) {
      getLog().log(Level.INFO, "Generating UI component {0}", template.getTargetFile());
    }
    generate(templates);
  }
  
  @Override
  public void generate(ClassObj clazz) throws IOException {
    if (hasDirective(GENERATE_WS_DIRECTIVE)) {
      generateWS(clazz);
    }
    if (hasDirective(GENERATE_UI_DIRECTIVE)) {
      generateUI(clazz);
    }
  }
  
  @Override
  public void generate(ProjectObj project) throws IOException {
    if (hasDirective(GENERATE_WS_DIRECTIVE) || hasDirective(GENERATE_UI_DIRECTIVE)) {
      generateWebApp(project);
    }
    super.generate(project);
  }
  
  
  // Protected
  
  @Override
  protected Logger getLog() {
    return LOGGER;
  }
  
  
  // Internal
  
  public static final String GENERATE_WS_DIRECTIVE = "generateWS";
  public static final String GENERATE_UI_DIRECTIVE = "generateUI";

  private static final Logger LOGGER = Logger.getLogger(WebGenerator.class.getSimpleName());
  
  private JSAppTemplates appTemplates;
}
