package com.cyc.library.model;

import com.cyc.library.mapi.ContextMap;
import com.cyc.library.mapi.SimpleContextMap;
import com.cyc.kb.exception.KBApiException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class ProjectObj extends ModelObj {

  private String workspace;
  private String dirName;
  // Use "name" filed in ModelObj
  //private String project;
  public static final String MAVEN_JAVA_DEFAULT_DIR = "src/main/java/";
  private List<PackageObj> packages = new ArrayList<PackageObj>();

  public ProjectObj(String workspaceName, String packageName, String javaDir) {
    this.setName(packageName);
    this.setWorkspace(workspaceName);
    if (javaDir == null) {
      javaDir = MAVEN_JAVA_DEFAULT_DIR;
    }
    this.setDirName(this.getWorkspace() + this.getName() + javaDir);
  }

  public ProjectObj(String workspaceName, String packageName) {
    this(workspaceName, packageName, MAVEN_JAVA_DEFAULT_DIR);
  }

  public String getWorkspace() {
    return workspace;
  }

  public void setWorkspace(String workspace) {
    if (!workspace.endsWith("/")) {
      workspace = workspace + "/";
    };
    this.workspace = workspace;
  }

  public void setName(String name) {
    if (!name.endsWith("/")) {
      name = name + "/";
    };
    super.setName(name);
  }

  public List<PackageObj> getPackages() {
    return packages;
  }

  public void setPackages(List<PackageObj> packages) {
    this.packages = packages;
  }

  public void setPackages(PackageObj aPackage) {
    this.packages.add(aPackage);
  }

  public String getDirName() {
    return dirName;
  }

  public void setDirName(String dirName) {
    this.dirName = dirName;
  }

  public List<ClassObj> getAllClasses() {
    final List<ClassObj> classes = new ArrayList<ClassObj>();
    for (PackageObj pkg : this.getPackages()) {
      classes.addAll(pkg.getClasses());
    }
    return classes;
  }

  public ContextMap getDefaultContextMap() throws KBApiException {
    return SimpleContextMap.getDefaultContextMap();
  }

  public void generateProject() throws Exception {
    for (PackageObj p : this.getPackages()) {
      System.out.println("Package dirs: " + this.getDirName() + p.getDirName());
      for (InterfaceObj i : p.getInterfaces()) {

        String iFaceDir = this.getDirName() + p.getDirName() + i.getDirName();
        String iFaceFile = iFaceDir + i.getName() + ".java";
        System.out.println("Interface Full Path: " + iFaceDir);
        System.out.println("Interface File Name: " + iFaceFile);

        File ifDir = new File(iFaceDir);
        if (!ifDir.exists() && !ifDir.mkdirs()) {
          throw new Exception("Could not create project. " + iFaceDir + " does not exist and couldn't be created.");
        }

        File ifFile = new File(iFaceFile);
        if (!ifFile.exists() && !ifFile.createNewFile()) {
          throw new Exception("Could not create project. " + iFaceFile + " does not exist and couldn't be created.");
        }

        FileUtils.writeStringToFile(ifFile, i.toString());

        System.out.println("Interface:\n\n" + i);
      }
      for (ClassObj c : p.getClasses()) {
        String cDirName = this.getDirName() + p.getDirName() + c.getDirName();
        String cFileName = cDirName + c.getName() + ".java";
        System.out.println("Class Full Path: " + cDirName);
        System.out.println("Class File Name: " + cFileName);

        File cDir = new File(cDirName);
        if (!cDir.exists() && !cDir.mkdirs()) {
          throw new Exception("Could not create project. " + cDirName + " does not exist and couldn't be created.");
        }

        File cFile = new File(cFileName);
        if (!cFile.exists() && !cFile.createNewFile()) {
          throw new Exception("Could not create project. " + cFileName + " does not exist and couldn't be created.");
        }

        FileUtils.writeStringToFile(cFile, c.toString());
        System.out.println("Class:\n\n" + c);
      }
    }
  }
  /*
   String workspace = "/fastscratch/vijay/stsworkspace";
   if (!workspace.endsWith("/")) {workspace = workspace + "/";};
	
   String project = "autogenUM"; 
   if (!project.endsWith("/")) {project = project + "/";};
	
   String javadefaultdir = "src/main/java";
   if (!javadefaultdir.endsWith("/")) {javadefaultdir = javadefaultdir + "/";};
	
   String packageName = "com.cyc.library.autogenUM";
	
   String packageDir = packageName.replaceAll("\\.", "/");
   if (!packageDir.endsWith("/")) {packageDir = packageDir + "/";};
	
   String interfacesDir = workspace + project + javadefaultdir + packageDir;
   String classDir = interfacesDir + "cyc/";

   System.out.println("Interface dir: " + interfacesDir);
   System.out.println("Class dir: " + classDir);
   */
}
