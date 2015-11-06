package com.cyc.library.mapi.templates;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 *
 * @author nwinant
 */
abstract public class VelocityTemplate {
  
  public VelocityTemplate(String baseTargetDir) {
    this.baseTargetDir = baseTargetDir;
    initVelocity();
    this.context = new VelocityContext();
  }
  
  public VelocityTemplate() {
    this(MAVEN_BASE_TARGET_DIR);
  }
  
  
  // Abstract
    
  abstract public String getTemplateFileName();
  abstract public File getTargetFile();
  
  /**
   * Template has a sufficient set of properties defined such that the merge should not fail.
   * @return 
   */
  abstract public boolean isSufficientlyConfigured();
  
  
  // Public
  
  public void generate(Writer writer) throws IOException {
    final String templatePath = getTemplateFileName();
    InputStream input = this.getClass().getClassLoader().getResourceAsStream(templatePath);
    if (input == null) {
      throw new IOException("Could not find template file " + templatePath);
    }
    InputStreamReader reader = new InputStreamReader(input);
    performPreMerge();
    Template template = engine.getTemplate(templatePath, "UTF-8");
    template.merge(context, writer, getMacroTemplates());
    writer.flush();
    writer.close();
  }
  
  public File generateFile(File targetName) throws IOException {
    File parent = targetName.getParentFile();
    /*
    if (parent.exists()) {
      FileUtils.deleteDirectory(parent);
    }
    */
    if (!parent.exists()) {
      parent.mkdirs();
    }
    final File filename = getTargetFile();
    final BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
    generate(writer);
    return filename;
  }
  
  public File generateFile() throws IOException {
    return generateFile(getTargetFile());
  }
  
  public String generateString() throws IOException {
    final StringWriter writer = new StringWriter();
    generate(writer);
    return writer.toString();
  }
  
  /**
   * Returns a list of template files containing macros to be used when merging.
   * @return 
   */
  public List<String> getMacroTemplates() {
    return new ArrayList();
  }
  
  public Object getProperty(String key) {
    return this.context.get(key);
  }
  
  public void setProperty(String key, Object value) {
    this.context.put(key, value);
  }
  
  public boolean isLoggingEnabled() {
    return this.loggingEnabled;
  }
  
  public void setLoggingEnabled(boolean loggingEnabled) {
    this.loggingEnabled = loggingEnabled;
  }
  
  
  // Protected
  
  /**
   * Override this to perform any necessary operations before the merge is performed.
   * Example: an inheriting class has a property which is a list. The class maintains
   * the list as an instance property, and assigns it to the VelocityContext
   * immediately before merging.
   */
  protected void performPreMerge() {}
  
  /**
   * Checks to ensure that a property has a non-null value.
   * @param key
   * @return 
   */
  protected boolean hasProperty(String key) {
    return this.getProperty(key) != null;
  }
  
  protected Properties getVelocityProperties() {
    final Properties props = new Properties();
    if (!isLoggingEnabled()) {
      props.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
    }
    return props;
  }
  
  protected void initVelocity() {
    // TODO: this would be nice if the properties could be set before merge, rather than during instantiation.
    this.engine = new VelocityEngine(getVelocityProperties());
    engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
    engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
    engine.init();
  }
  
  
  // Internal
  
  public static final String MAVEN_BASE_TARGET_DIR = "target/generated-sources/mapi";
  
  /**
   * We actually do NOT want windows file separators on windows machines, as they
   * may be interpreted as escape chars. - nwinant, 11/11/2013
   * http://stackoverflow.com/questions/1664073/classloader-getresource-doesnt-work-from-jar-file
   */
  // protected static final String FS = System.getProperty("file.separator");
  protected static final String FS = "/";
  
  protected VelocityEngine engine;
  protected final VelocityContext context;
  protected final String baseTargetDir;
  private boolean loggingEnabled = false;
}
