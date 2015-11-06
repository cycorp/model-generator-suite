package com.cyc.library.mapi.generator;

import com.cyc.library.mapi.templates.ClassObjProviderTemplate;
import com.cyc.library.mapi.templates.ClassObjTemplate;
import com.cyc.library.mapi.templates.InterfaceObjTemplate;
import com.cyc.library.model.ClassObj;
import com.cyc.library.model.InterfaceObj;
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
public class ModelGenerator extends TemplateGenerator {
  
  // Constructors
  
  public ModelGenerator(List<String> enabledDirectives) {
    super(enabledDirectives);
  }
  
  public ModelGenerator() {
    this(getDefaultDirectives());
  }
  
  public static List<String> getDefaultDirectives() {
    return new ArrayList(Arrays.asList(
            GENERATE_MODEL_INTERFACES_DIRECTIVE,
            GENERATE_MODEL_CLASSES_DIRECTIVE));
  }
  
  
  // Public
  
  @Override
  public void generate(InterfaceObj obj) throws IOException {
    if (hasDirective(GENERATE_MODEL_INTERFACES_DIRECTIVE)) {
      final InterfaceObjTemplate template = configTemplate(new InterfaceObjTemplate(obj));
      getLog().log(Level.INFO, "Generating interface {0}", template.getTargetFile());
      generate(template);
    }
  }
  
  @Override
  public void generate(ClassObj obj) throws IOException {
    if (hasDirective(GENERATE_MODEL_CLASSES_DIRECTIVE)) {
      final ClassObjTemplate template = configTemplate(new ClassObjTemplate(obj));
      getLog().log(Level.INFO, "Generating class {0}", template.getTargetFile());
      generate(template);
      final ClassObjProviderTemplate template2 = configTemplate(new ClassObjProviderTemplate(obj));
      getLog().log(Level.INFO, "Generating class {0}", template2.getTargetFile());
      generate(template2);
    }
  }
  
  @Override
  public void generate(ProjectObj project) throws IOException {
    super.generate(project);
  }
  
  
  // Protected
  
  @Override
  protected Logger getLog() {
    return LOGGER;
  }
  
  
  // Internal
  
  public static final String GENERATE_MODEL_INTERFACES_DIRECTIVE = "generateModelInterfaces";
  public static final String GENERATE_MODEL_CLASSES_DIRECTIVE = "generateModelClasses";
  
  private static final Logger LOGGER = Logger.getLogger(ModelGenerator.class.getSimpleName());
}
