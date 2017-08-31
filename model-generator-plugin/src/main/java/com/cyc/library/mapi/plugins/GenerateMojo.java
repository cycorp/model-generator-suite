package com.cyc.library.mapi.plugins;

import com.cyc.model.generator.ModelGenerator;
import com.cyc.model.generator.WebGenerator;
import com.cyc.model.xml.XmlProjectLoader;
import com.cyc.model.objects.ProjectObj;
import com.cyc.kb.exception.KbException;
import com.cyc.session.CycServerAddress;
import com.cyc.session.CycSessionManager;
import com.cyc.session.exception.SessionException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

/**
 * @mojo model
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
      setup();
      if (isGenerationNecessary()) {
        generateProject();
      } else {
        getLog().info("Skipping model generation; " + fullSrcPath + " exists and model.forceRegenerate=" + forceRegenerate);
      }
    } catch (Exception ex) {
      this.getLog().error(ex);
      throw new MojoExecutionException("Could not load " + fullCmdPath, ex);
    }
  }
  
  
  // Protected
  
  protected void printParameters() {
    getLog().info("mojo.project         : " + project);
    /*
    getLog().info("mojo...dir           : " + project.getModel().getBuild().getDirectory());
    getLog().info("mojo...srcDir        : " + project.getModel().getBuild().getSourceDirectory());
    getLog().info("mojo...finalName     : " + project.getModel().getBuild().getFinalName());
    List resources = project.getModel().getBuild().getResources();
    for (Object o : resources) {
      getLog().info("  resource: " + o);
    }
    */
    getLog().info("mojo...finalName     : " + project.getModel().getBuild().getFinalName());
    getLog().info("model.forceRegenerate: " + forceRegenerate);
    getLog().info("model.validateCMD    : " + validateCMD);
    getLog().info("model.workspacePath  : " + workspacePath);
    getLog().info("model.cmdPath        : " + cmdPath);
    getLog().info("model.srcPath        : " + srcPath);
    getLog().info("model.generateWS     : " + generateWS);
    getLog().info("model.generateUI     : " + generateUI);
    getLog().info("Cyc server           : " + cyc);
  }
  
  protected void setup() throws IOException, SessionException {
    if (workspacePath == null) {
      // this.workspacePath = new java.io.File(".").getCanonicalPath();
      File workspaceDir = new File(project.getModel().getBuild().getDirectory() + FS + "..");
      this.workspacePath = workspaceDir.getCanonicalPath();
    }
    cyc = CycSessionManager.getCurrentSession().getServerInfo().getCycServer();
    printParameters();
    
    if (srcPath.trim().startsWith(File.pathSeparator)) {
      this.fullSrcPath = srcPath + FS;
    } else {
      this.fullSrcPath = workspacePath + FS + srcPath + FS;
    }
    getLog().info("Full source path: " + fullSrcPath);
    
    if (cmdPath.trim().startsWith(File.pathSeparator)) {
      this.fullCmdPath = cmdPath;
    } else {
      this.fullCmdPath = workspacePath + FS + cmdPath;
    }
    getLog().info("Full CycModelDescription path: " + fullCmdPath);
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

  protected void generateProject() throws JAXBException, KbException, Exception {
    XmlProjectLoader loader = new XmlProjectLoader(validateCMD, workspacePath, fullSrcPath + FS);
    makeJavaPaths();
    getLog().info("Generating MAPI classes in " + fullSrcPath);
    final ProjectObj projectObj = loader.loadProject(fullCmdPath);
    projectObj.setDirName(fullSrcPath);
    generateModel(projectObj);
    generateWebapp(projectObj);
    getLog().info("... MAPI generation complete! Results in " + fullSrcPath);
  }
  
  protected void generateModel(ProjectObj projectObj) throws IOException {  
    getLog().info("Building model classes...");
    final ModelGenerator generator =  new ModelGenerator();
    System.out.println("PROJ DIR: " + projectObj.getDirName());
    System.out.println("PROJ WORK: " + projectObj.getWorkspace());
    generator.generate(projectObj);
  }
  
  protected void generateWebapp(ProjectObj projectObj) throws IOException {
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
      generator.generate(projectObj);
    }
  }
  
  
  // Internal
  
  protected static final String FS = System.getProperty("file.separator");
  
  /**
   * @parameter property="model.forceRegenerate" default-value="false"
   */
  protected boolean forceRegenerate;
 
  /**
   * @parameter property="model.validateCMD" default-value="true"
   */
  protected boolean validateCMD;
  
  /**
   * @parameter property="workspacePath" 
   */
  protected String workspacePath;
  
  /**
   * @parameter property="model.cmdPath" default-value="src/main/resources/CycModelDescription.xml"
   */
  protected String cmdPath;
  
  /**
   * @parameter property="model.srcPath" default-value="target/generated-sources/mapi"
   */
  protected String srcPath;
    
  /**
   * @parameter property="model.generateWS" default-value="false"
   */
  protected boolean generateWS;
  
  /**
   * @parameter property="model.generateUI" default-value="false"
   */
  protected boolean generateUI;
  
  /**
   * @parameter property="project"
   * @readonly
   */
  protected MavenProject project;
  
  protected String fullSrcPath;
  protected String fullCmdPath;
  protected CycServerAddress cyc;
}
