package com.cyc.model.generator;

import com.cyc.model.templates.VelocityTemplate;
import com.cyc.model.templates.VelocityTemplateGroup;
import com.cyc.model.objects.ClassObj;
import com.cyc.model.objects.InterfaceObj;
import com.cyc.model.objects.PackageObj;
import com.cyc.model.objects.ProjectObj;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nwinant
 */
abstract public class TemplateGenerator {
  
  // Constructors
  
  public TemplateGenerator(List<String> enabledDirectives) {
    this.directives = enabledDirectives;
    getLog().log(Level.INFO, "Directives: {0}", directives);
  }
  
  
  // Public
  
  public void generate(VelocityTemplate template) throws IOException {
    if (hasDirective(GENERATE_STRINGS_ONLY_DIRECTIVE)) {
      System.out.println(template.generateString());
    } else {
      getLog().log(Level.INFO, "Generating {0}", template.getTargetFile());
      File file = template.generateFile();
      getLog().log(Level.INFO, "    ... at {0}", file.getAbsolutePath());
    }
  }
  
  public void generate(VelocityTemplateGroup templates) throws IOException {
    templates.initializeTemplates();
    for (VelocityTemplate template : templates.getTemplates()) {
      generate(template);
    }
  }
  
  public void generate(InterfaceObj obj) throws IOException {}
  
  public void generate(ClassObj obj) throws IOException {}
  
  public void generate(PackageObj pkg) throws IOException {
    for (InterfaceObj iface : pkg.getInterfaces()) {
      generate(iface);
    }
    for (ClassObj clazz : pkg.getClasses()) {
      generate(clazz);
    }
  }
  
  public void generate(ProjectObj project) throws IOException {
    for (PackageObj pkg : project.getPackages()) {
      this.generate(pkg);
    }
  }
  
  public List<String> getDirectives() {
    return this.directives;
  }
  
  public boolean hasDirective(String key) {
    return getDirectives().contains(key);
  }
  
  
  // Protected
  
  protected <T extends VelocityTemplate> T configTemplate(T template) {
    // FIXME: this would be cool if it worked, but it doesn't, because VelocityEngine 
    //        props are currently set during template instantiation... 
    //        See VelocityTemplate#initVelocity.
    template.setLoggingEnabled(true);
    return template;
  }
  
  protected Logger getLog() {
    return LOGGER;
  }
  
  
  // Internal
  
  public static final String GENERATE_STRINGS_ONLY_DIRECTIVE = "generateStringsOnly";
  private static final Logger LOGGER = Logger.getLogger(TemplateGenerator.class.getSimpleName());
  
  final private List<String> directives;
}
