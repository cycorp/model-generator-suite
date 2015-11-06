package com.cyc.library.mapi.xml;

import com.cyc.library.mapi.ContextMap;
import com.cyc.library.model.ClassObj;
import com.cyc.library.model.CriteriaMethodObj;
import com.cyc.library.model.InterfaceObj;
import com.cyc.library.model.MethodObj;
import com.cyc.library.model.MethodObj.FuncType;
import com.cyc.library.model.PackageObj;
import com.cyc.library.model.ProjectObj;
import com.cyc.library.mapi.SimpleContextMap;
import com.cyc.kb.KBCollection;
import com.cyc.kb.Context;
import com.cyc.kb.client.*;
import com.cyc.kb.exception.KBApiException;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import com.cyc.xsd.cycmodeldescription.CycModelDescription;
import com.cyc.xsd.cycmodeldescription.Extends;
import com.cyc.xsd.cycmodeldescription.Interface;
import com.cyc.xsd.cycmodeldescription.Method;
import com.cyc.xsd.cycmodeldescription.Project;
import org.xml.sax.SAXException;

/**
 * Load a ProjectObj from an XML file which conforms to CycModelDescription.xsd
 *
 * @author nwinant
 */
public class XMLProjectLoader {

  public XMLProjectLoader(boolean shouldValidate, String workspace, String javaDir) throws JAXBException, SAXException {
    this.workspace = workspace;
    this.javaDir = javaDir;
    final JAXBContext context = JAXBContext.newInstance(CycModelDescription.class);
    this.unmarshaller = context.createUnmarshaller();
    if (shouldValidate) {
      final InputStream in = this.getClass().getResourceAsStream(SCHEMA_LOCATION);
      final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      unmarshaller.setSchema(schemaFactory.newSchema(new StreamSource(in)));
    }
  }

  public XMLProjectLoader(String workspace, String javaDir) throws JAXBException, SAXException {
    this(VALIDATION_DEFAULT, workspace, javaDir);
  }

  public XMLProjectLoader(String workspace) throws JAXBException, SAXException {
    this(workspace, null);
  }

  public XMLProjectLoader(boolean shouldValidate) throws JAXBException, SAXException {
    this(shouldValidate, null, null);
  }

  public XMLProjectLoader() throws JAXBException, SAXException {
    this(VALIDATION_DEFAULT, null, null);
  }

  // Public
  public ProjectObj loadProject(File file) throws KBApiException, Exception {
    return createProjectObj(loadXML(file));
  }

  public ProjectObj loadProject(String filename) throws KBApiException, Exception {
    return createProjectObj(loadXML(filename));
  }

  // Protected
  protected CycModelDescription loadXML(File file) throws JAXBException {
    return (CycModelDescription) unmarshaller.unmarshal(file);
  }

  protected CycModelDescription loadXML(String filename) throws JAXBException {
    return loadXML(new File(filename));
  }

  protected ProjectObj createProjectObj(CycModelDescription description) throws KBApiException, Exception {
    final ProjectObj project = new ProjectObj(
            getWorkspace(description.getProject()),
            description.getProject().getName(),
            getJavaDir(description.getProject()));
    final PackageObj pkg = new PackageObj(description.getProject().getPackage());
    project.setPackages(pkg);
    for (Interface iDesc : description.getInterface()) {
      final InterfaceObj iObj = createInterfaceObj(iDesc, pkg);
      final ClassObj cObj = createClassObj(iDesc, iObj);
      pkg.setInterfaces(iObj);
      pkg.setClasses(cObj);
    }
    return project;
  }

  protected FuncType getFuncType(String functionType) {
    if ("set".equals(functionType)) {
      return FuncType.SET;
    } else if ("get".equals(functionType)) {
      return FuncType.GET;
    } else if ("getTypeList".equals(functionType)) {
      return FuncType.TYPE_GET_LIST;
    } else if ("factory".equals(functionType)){
      return FuncType.FACTORY;
    } else if ("sent".equals(functionType)){
      return FuncType.SENT;
    }
    return null;
  }

  protected MethodObj createMethodObj(Method method) throws Exception {
    final FuncType funcType = getFuncType(method.getFunctionType());
    if (FuncType.TYPE_GET_LIST.equals(funcType)) {
      return new CriteriaMethodObj(method.getPredicate(), KBCollectionImpl.get(method.getCollection()), funcType);
    }
    if (method.getConfigArgPos() == null) {
      return new MethodObj(method.getPredicate(), KBCollectionImpl.get(method.getCollection()), funcType);
    } else {
      return new MethodObj(method.getPredicate(), KBCollectionImpl.get(method.getCollection()), funcType, method.getConfigArgPos());
    }
  }

  protected InterfaceObj createInterfaceObj(Interface iface, PackageObj pkg) throws Exception {
    InterfaceObj io1 = null;
    List<String> extendsCols = new ArrayList<String>();
    for (Extends e : iface.getExtends()) {
      extendsCols.add(e.getCollection());
    }
    io1 = new InterfaceObj(iface.getCollection(), iface.getRelationType(), extendsCols);

    io1.setVisibility("public");
    io1.setPackageName(pkg.getName());
    for (Method method : iface.getMethod()) {
      io1.setMethods(createMethodObj(method));
      // @todo check if we have a copy-constructor or clone for jaxb objects
      if (method.getFunctionType().equals("set")){
        Method m2 = new Method();
        m2.setCollection(method.getCollection());
        m2.setConfigArgPos(method.getConfigArgPos());
        m2.setPredicate(method.getPredicate());
        m2.setFunctionType("sent");
        io1.setMethods(createMethodObj(m2));
      }
    }
    return io1;
  }

  protected ClassObj createClassObj(Interface iface, InterfaceObj iObj) throws Exception {
    final ContextMap defaults = SimpleContextMap.getDefaultContextMap();
    final Context collCtx = (iface.getContext() != null)
            ? ContextImpl.get(iface.getContext())
            : defaults.getCollectionContext();
    final Context predCtx = (iface.getPredicateContext() != null)
            ? ContextImpl.get(iface.getPredicateContext())
            : defaults.getRelationContext();
    return new ClassObj(iObj, new SimpleContextMap(collCtx, predCtx));
  }

  protected PackageObj createPackageObj(Project project) {
    return new PackageObj(project.getPackage());
  }

  protected String getWorkspace(Project project) throws Exception {
    if (this.workspace != null) {
      return this.workspace;
    } else if (project.getWorkspace() != null) {
      return project.getWorkspace();
    }
    throw new Exception("Workspace is not specified in either CycModelDescription xml or in " + this.getClass().getSimpleName());
  }

  protected String getJavaDir(Project project) throws Exception {
    if (this.javaDir != null) {
      return this.javaDir;
    } else if (project.getJavaDir() != null) {
      return project.getJavaDir();
    }
    return null;
  }
  // Internal
  public static final boolean VALIDATION_DEFAULT = true;
  public static final String SCHEMA_LOCATION = "/xml/xsd/CycModelDescription.xsd";
  public final javax.xml.bind.Unmarshaller unmarshaller;
  private String workspace;
  private String javaDir;
}