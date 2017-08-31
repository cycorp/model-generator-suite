package com.cyc.model.objects;

import com.cyc.kb.Context;
import com.cyc.kb.FirstOrderCollection;
import com.cyc.kb.KbCollection;
import com.cyc.kb.KbCollectionFactory;
import com.cyc.kb.KbFunction;
import com.cyc.kb.KbIndividual;
import com.cyc.kb.KbObject;
import com.cyc.kb.KbTerm;
import com.cyc.kb.SecondOrderCollection;
import com.cyc.kb.exception.KbException;
import com.cyc.model.CycModelObject;
import com.cyc.model.exception.ModelException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class InterfaceObj extends ModelObj {

  private String projectName;
  private String packageName;
  private List<String> extendsInterfaces = new ArrayList<String>();
  private List<MethodObj> methods = new ArrayList<MethodObj>();
  private Set<String> imports = new HashSet<String>();
  // Vijay: The intention of dirName was to separate the Interfaces based on the 
  // relation type it implements. For example: #$genls relation based Interfaces
  // might reside in "a.package.type", where as #$isa based Interfaces can be in "a.package"
  // Saket: This can be captured in the packageName field itself.
  private String dirName;
  // Saket: This should represent variable collection order some how. This is required
  // for generating an interface for #$Thing
  private int collectionOrder;
  // this is used to give a meaningful name for the instances based on relationType.
  // For a genlPred relation, the user could choose "specializationPred" as instanceName.
  private String instanceName;
  // Derived filed. This is derived from coreType and relationType.
  private String instanceType;
  private String coreType;

  private String relationType;
  private static final Logger LOG = Logger.getLogger(InterfaceObj.class.getName());
  
  @Deprecated
  public InterfaceObj() {
  }

  public InterfaceObj(String cycName) throws KbException, ModelGeneratorException {
    this(cycName, "isa");
  }
  public InterfaceObj(String cycName, String relationType) throws KbException, ModelGeneratorException {
    this(cycName, relationType, new ArrayList<String>());
  }
  
  public InterfaceObj(String cycName, String relationType, List<String> extendsCollection) throws KbException, ModelGeneratorException {
    if (relationType == null){
      relationType = "isa";
    }
    if (extendsCollection == null) {
      extendsCollection = new ArrayList<String>();
    }
    extendsInterfaces = extendsCollection;
    
    LOG.fine("This inferface extends: ");
    for (String col : extendsCollection){
      LOG.fine(col);
      System.out.println("Extends: " + col);
    }
    
    this.setCycName(cycName);
    String relTypeModifier = "";
    if (relationType.equals("quotedIsa")){
      relTypeModifier = "QuotedInstance";
    } else if (relationType.equals("genls")) {
      //TODO: Enable generating Class with Suffixes for genls later, when SMT
      // Development is stable.
      //relTypeModifier = "Spec";
    }
    this.setName(this.getName()+relTypeModifier);
    if (relationType.equals("isa")) {
      this.setDirName("iface/");
    } else {
      this.setDirName("type/iface");
    }
    this.setRelationType(relationType);
    System.out.println("Relation type: " + this.getRelationType());
    
    KbCollection c = KbCollectionFactory.get(cycName);  
    // It is proabably more efficient to check type three times than getting all
    // ISAs of cycName and checking if FOC, SOC, is in the set. Getting all ISAs
    // would mean getting a list of collections and creating objects for them.
    if (c.isInstanceOf(KbCollectionFactory.get("FirstOrderCollection"))) {
      this.setCollectionOrder(1);
      if (this.getRelationType().equals("isa")){
        if (KbCollectionFactory.get("Microtheory").isGeneralizationOf(c)){
          this.setInstanceType(Context.class.getSimpleName());
          this.setCoreType(FirstOrderCollection.class.getSimpleName());
          this.setInstanceName("context");
        } else if (KbCollectionFactory.get("Function-Denotational").isGeneralizationOf(c)){
          this.setInstanceType(KbFunction.class.getSimpleName());
          this.setCoreType(FirstOrderCollection.class.getSimpleName());
          this.setInstanceName("function");
        } else {
          this.setInstanceType(KbIndividual.class.getSimpleName());
          this.setCoreType(FirstOrderCollection.class.getSimpleName());
          this.setInstanceName("individual");
        }
      
      } else if (this.getRelationType().equals("genls")) {
        this.setInstanceType(FirstOrderCollection.class.getSimpleName());
        this.setCoreType(FirstOrderCollection.class.getSimpleName());
        // Instance name can't be derived from Instance Type always.
        // Also, we might want to represent more complicate set/collections
        // and enforce them in the constructor.
        this.setInstanceName("specialization");
      } else if (this.getRelationType().equals("quotedIsa")) {
        this.setInstanceType(KbObject.class.getSimpleName());
        this.setCoreType(FirstOrderCollection.class.getSimpleName());
        this.setInstanceName("quotedTerm");        
      }
    } else if (c.isInstanceOf(KbCollectionFactory.get("SecondOrderCollection"))) {
      this.setCollectionOrder(2);
      if (this.getRelationType().equals("isa")){
        this.setInstanceType(FirstOrderCollection.class.getSimpleName());
        this.setCoreType(SecondOrderCollection.class.getSimpleName());
        this.setInstanceName("individual");
      } else if (this.getRelationType().equals("genls")) {
        this.setInstanceType(SecondOrderCollection.class.getSimpleName());
        this.setCoreType(SecondOrderCollection.class.getSimpleName());
        this.setInstanceName("specialization");
      }
    } else if (c.isInstanceOf(KbCollectionFactory.get("ThirdOrderCollection"))){
      this.setCollectionOrder(3);
      this.setInstanceType(SecondOrderCollection.class.getSimpleName());
      this.setCoreType("ThirdOrderCollection");
      this.setInstanceName("individual");
    } else if(c.isInstanceOf(KbCollectionFactory.get("VariedOrderCollection"))) {
      this.setCollectionOrder(99);
      this.setInstanceType(KbTerm.class.getSimpleName());
      this.setCoreType(KbCollection.class.getSimpleName());
      this.setInstanceName("term");
    } else {
      throw new ModelGeneratorException("Could not determine the collection order of " + cycName);
    }
    imports.add(List.class.getCanonicalName());
    imports.add(Set.class.getCanonicalName());
    imports.add(Date.class.getCanonicalName());
    imports.add(KbObject.class.getPackage().getName()+".*");
    imports.add(ModelException.class.getCanonicalName());
    imports.add(CycModelObject.class.getCanonicalName());
  }
  /*
  @Deprecated
  public InterfaceObj(String prjName, String pkgName, String ifaceName) {
    this.setProjectName(prjName);
    this.setPackageName(pkgName);
    this.setName(ifaceName);

    String workspace = XmlProjectLoaderTest.WORKSPACE;
    if (!workspace.endsWith("/")) {
      workspace = workspace + "/";
    };

    String projectDir = this.getProjectName();
    if (!projectDir.endsWith("/")) {
      projectDir = projectDir + "/";
    };

    String javadefaultdir = "src/main/java/";

    String packageDir = this.getPackageName().replaceAll("\\.", "/");
    if (!packageDir.endsWith("/")) {
      packageDir = packageDir + "/";
    };

    dirName = workspace + projectDir + javadefaultdir + packageDir;
    
    // Should we not be adding these here?
    imports.add("java.util.List");
    imports.add(Context.class.getCanonicalName());
    imports.add(KbIndividual.class.getCanonicalName());
  }
  */

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getDirName() {
    return dirName;
  }

  public void setDirName(String dirName) {
    this.dirName = dirName;
  }

  public Set<String> getImports() {
    return imports;
  }

  public void setImports(Set<String> imports) {
    this.imports = imports;
  }

  public void setImport(String anImport) {
    this.imports.add(anImport);
  }

  public List<String> getExtendsInterfaces() {
    return extendsInterfaces;
  }

  public void setExtendsInterfaces(List<String> extendsInterfaces) {
    this.extendsInterfaces = extendsInterfaces;
  }
  
  // temporary method to return the right interface for Apache Velocity.
  // TODO: BUG:
  // CRITICAL WARNING!!!!!!!!!!!!!!!!
  // This will not clean up CycName to the right Java name, so any non-conforming Cyc Name
  // will have issues!!!!!
  public String getExtendsInterface() {
    if (this.getExtendsInterfaces().isEmpty()){
      // return "Empty";
      return this.getInstanceType();
    } else {
      String tName = this.getExtendsInterfaces().get(0).replaceAll("\\W+", "");
      return tName;
    }
  }

  public List<MethodObj> getMethods() {
    return methods;
  }

  public void setMethods(List<MethodObj> methods) {
    for (MethodObj mo : methods) {
      this.setMethods(mo);
    }
  }

  public void setMethods(MethodObj method) {
    this.methods.add(method);

    for (ArgumentObj arg : method.getArguments()) {
      if (arg.isSObject()) {
        // TODO: Find the right way to identify SObjects and put the correct include statement
        // All of the KbObject should probably be imported statically
        // this.setImport(arg.getType());
      } else {
        this.setImport(this.packageName + ".cycImpl." + arg.getType() + "Impl");
        this.setImport(this.packageName + ".iface." + arg.getType());
      }
    }

    ArgumentObj retVal = method.getReturnValue();
    if (retVal != null && !retVal.getType().equals("List<List<Object>>")) {
      if (retVal.isSObject()) {
        // TODO: Find the right way to identify SObjects and put the correct include statement
        // All of the KbObject should probably be imported statically
        // this.setImport(retVal.getType());
      } else {        
        this.setImport(this.packageName + ".cycImpl." + retVal.getType() + "Impl");
        this.setImport(this.packageName + ".iface." + retVal.getType());
      }
    }
  }

  public String getPackageName() {
    return packageName;
  }
  
  public String getFullPackageName() {
    return getPackageName() + ".iface";
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public int getCollectionOrder() {
    return collectionOrder;
  }

  public void setCollectionOrder(int collectionOrder) {
    this.collectionOrder = collectionOrder;
  }

  public String getInstanceName() {
    return instanceName;
  }

  public void setInstanceName(String instanceName) {
    this.instanceName = instanceName;
  }

  public String getInstanceType() {
    return instanceType;
  }

  public void setInstanceType(String instanceType) {
    this.instanceType = instanceType;
  }

  public String getCoreType() {
    return coreType;
  }

  public void setCoreType(String coreType) {
    this.coreType = coreType;
  }

  public String getRelationType() {
    return relationType;
  }

  public void setRelationType(String relationType) {
    this.relationType = relationType;
  }

  
  @Override
  @Deprecated
  public String toString() {
    StringBuilder result = new StringBuilder()
            .append("/**\n"
            + " * This code was autogenerated by ModelGenerator program. The ModelGenerator\n"
            + " * creates Java Interfaces and Classes by introspecting the Cyc Knowledge Base.\n"
            + " * It is strongly recommended not to make any changes to this file. \n"
            + " * This will allow regeneration of code when the Cyc KB model changes with fewer\n"
            + " * downstream changes. Please extend the generated Interface to add \n"
            + " * functionality.\n"
            + " * \n"
            + " * The interface corresponds to " + this.getCycName() + " in the Cyc KB. \n"
            + " * <p>\n"
            + " * \n"
            + " * @author ModelGenerator\n"
            + " * @author Vijay Raj\n"
            + " * @version \"%I%, %G%\"\n"
            + " * @since 0.90\n"
            + " */\n\n");
    result.append("package ")
            .append(this.getPackageName())
            .append((this.getDirName() != null) ? this.getDirName().replaceFirst("\\/", "") : null)
            .append(";\n\n\n");
    for (String i : imports) {
      result.append("import ").append(i).append(";\n");
    }
    result.append("\n\npublic interface " + this.getName() + " {\n\n")
            .append("    public " + KbIndividual.class.getSimpleName() + " getInstance();\n\n")
            .append("    public List<" + this.getName() + "> allSibling" + this.getName() + "s (String ctxStr) throws Exception;\n\n");
    for (MethodObj mo : this.getMethods()) {
      result.append("    " + mo.toString() + "\n\n");
    }
    result.append("}\n");
    return result.toString();
  }

  @Override
  public int hashCode() {
    int hash = super.hashCode();
    hash = 17 * hash + (this.packageName != null ? this.packageName.hashCode() : 0);
    hash = 17 * hash + (this.relationType != null ? this.relationType.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    if (! super.equals(obj)) {
      return false;
    }
    final InterfaceObj other = (InterfaceObj) obj;
    if ((this.packageName == null) ? (other.packageName != null) : !this.packageName.equals(other.packageName)) {
      return false;
    }
    if ((this.relationType == null) ? (other.relationType != null) : !this.relationType.equals(other.relationType)) {
      return false;
    }
    return true;
  }
  
  
}
