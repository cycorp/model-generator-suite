package com.cyc.library.mapi.plugins;

import com.cyc.base.CycConnectionException;
import com.cyc.library.mapi.generator.ModelGenerator;
import com.cyc.library.mapi.generator.WebGenerator;
import com.cyc.library.mapi.xml.XMLProjectLoader;
import com.cyc.library.model.ProjectObj;
import com.cyc.kb.exception.KBApiException;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

/**
 * @mojo mapi
 * @phase generate-sources
 * @goal generate
 * @requiresProject false
 * 
 * @author nwinant
 */
public class GenerateMojo extends AbstractMojo {
  
  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    try {
      printParameters();
      setup();
      if (isGenerationNecessary()) {
        generateProject();
      } else {
        getLog().info("Skipping MAPI generation; " + fullSrcPath + " exists and mapi.forceRegenerate=" + forceRegenerate);
      }
    } catch (Exception ex) {
      this.getLog().error(ex);
      throw new MojoExecutionException("Could not load " + fullCmdPath, ex);
    }
  }
  
  
  // Protected
  
  protected void printParameters() {
    getLog().info("mojo.project        : " + project);
    /*
    getLog().info("mojo...dir          : " + project.getModel().getBuild().getDirectory());
    getLog().info("mojo...srcDir       : " + project.getModel().getBuild().getSourceDirectory());
    getLog().info("mojo...finalName    : " + project.getModel().getBuild().getFinalName());
    List resources = project.getModel().getBuild().getResources();
    for (Object o : resources) {
      getLog().info("  resource: " + o);
    }
    */
    getLog().info("mojo...finalName    : " + project.getModel().getBuild().getFinalName());
    getLog().info("mojo.forceRegenerate: " + forceRegenerate);
    getLog().info("mojo.validateCMD    : " + validateCMD);
    getLog().info("mojo.cmdPath        : " + cmdPath);
    getLog().info("mojo.srcPath        : " + srcPath);
    getLog().info("mojo.cyc            : " + cyc);
    getLog().info("mojo.generateWS     : " + generateWS);
    getLog().info("mojo.generateUI     : " + generateUI);
  }
  
  protected void setup() throws UnknownHostException, IOException, CycConnectionException {
    // this.workspacePath = new java.io.File(".").getCanonicalPath();
    File workspaceDir = new File(project.getModel().getBuild().getDirectory() + FS + "..");
    this.workspacePath = workspaceDir.getCanonicalPath();
    getLog().info("Workspace directory: " + workspacePath);
    this.fullSrcPath = workspacePath + FS + srcPath + FS;
    getLog().info("Full source path: " + fullSrcPath);
    this.fullCmdPath = workspacePath + FS + cmdPath;
    
  }

  protected boolean isGenerationNecessary() {
    return forceRegenerate || !new File(fullSrcPath).exists();
  }

  protected void makeJavaPaths() throws IOException, MojoExecutionException {
    final File path = new File(fullSrcPath);
    if (!path.exists()) {
      getLog().info("Creating " + fullSrcPath);
      path.mkdirs();
    }
  }

  protected void generateProject() throws JAXBException, KBApiException, Exception {
    XMLProjectLoader loader = new XMLProjectLoader(validateCMD, workspacePath, fullSrcPath + FS);
    makeJavaPaths();
    getLog().info("Generating MAPI classes in " + fullSrcPath);
    final ProjectObj project = loader.loadProject(fullCmdPath);
    project.setDirName(fullSrcPath);
    generateModel(project);
    generateWebapp(project);
    getLog().info("... MAPI generation complete! Results in " + fullSrcPath);
  }
  
  protected void generateModel(ProjectObj project) throws IOException {  
    getLog().info("Building model classes...");
    final ModelGenerator generator =  new ModelGenerator();
    generator.generate(project);
  }
  
  protected void generateWebapp(ProjectObj project) throws IOException {
    if (generateWS || generateUI) {
      getLog().info("Building web services...");
      final List<String> directives = new ArrayList<String>();
      if (generateWS) {
        directives.add(WebGenerator.GENERATE_WS_DIRECTIVE);
      }
      if (generateUI) {
        directives.add(WebGenerator.GENERATE_UI_DIRECTIVE);
      }
      final WebGenerator generator = new WebGenerator(directives);
      generator.generate(project);
    }
  }
  
  
  // Internal
  
  protected static final String FS = System.getProperty("file.separator");
  
  /**
   * @parameter expression="${mapi.forceRegenerate}" default-value="false"
   */
  protected boolean forceRegenerate;
 
  /**
   * @parameter expression="${mapi.validateCMD}" default-value="true"
   */
  protected boolean validateCMD;
  
  /**
   * @parameter expression="${mapi.cmdPath}" default-value="src/main/resources/CycModelDescription.xml"
   */
  protected String cmdPath;
  
  /**
   * @parameter expression="${mapi.srcPath}" default-value="target/generated-sources/mapi"
   */
  protected String srcPath;
  
  /**
   * @parameter expression="${mapi.cyc}" default-value="localhost:3600"
   */
  protected String cyc;
  
  /**
   * @parameter expression="${mapi.generateWS}" default-value="false"
   */
  protected boolean generateWS;
  
  /**
   * @parameter expression="${mapi.generateUI}" default-value="false"
   */
  protected boolean generateUI;
  
  /**
   * @parameter expression="${project}"
   * @readonly
   */
  protected MavenProject project;

  protected String workspacePath;
  protected String fullSrcPath;
  protected String fullCmdPath;
}
