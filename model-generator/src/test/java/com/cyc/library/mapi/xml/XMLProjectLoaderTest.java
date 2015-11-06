package com.cyc.library.mapi.xml;

import com.cyc.library.model.ProjectObj;
import java.io.IOException;
import javax.xml.bind.UnmarshalException;
import junit.framework.TestCase;
import org.junit.*;
import com.cyc.xsd.cycmodeldescription.*;

public class XMLProjectLoaderTest extends TestCase {

  @Before
  public void setUp() throws IOException {}
  
  @After
  public void tearDown() throws Exception {}
  
  
  // Tests
  
  @Test
  public void testloadXML() throws Exception {
    final XMLProjectLoader loader = new XMLProjectLoader(WORKSPACE);
    final CycModelDescription description = loader.loadXML(BASE_EXAMPLES_DIR + "/CycModelDescriptionExample1.xml");
    assertNotNull(description);
    
    assertNotNull(description.getProject());
    final Project project = description.getProject();
    assertEquals("TestAppThingamabob", project.getName());
    assertEquals("com.cyc.api.test", project.getPackage());
    assertEquals(null, project.getWorkspace());
    
    assertNotNull(description.getInterface());
    assertEquals(1, description.getInterface().size());
    final Interface iface = description.getInterface().get(0);
    assertEquals("Gizmo", iface.getCollection());
    assertNotNull(iface.getMethod());
    assertEquals(2, iface.getMethod().size());
    
    final Method method0 = iface.getMethod().get(0);
    assertEquals("gizmoObservedToBeOperationalOnDate", method0.getPredicate());
    
    final Method method1 = iface.getMethod().get(1);
    assertEquals("gizmoObservedToBeBrokenOnDate", method1.getPredicate());
  }
  
  @Test
  public void testLoadInvalidXML() throws Exception {
    boolean exceptionThrown = false;
    try {
      // This should throw an exception when it fails to validate...
      final XMLProjectLoader loader = new XMLProjectLoader();
      final CycModelDescription description = loader.loadXML(BASE_EXAMPLES_DIR + "/CycModelDescriptionExample2.xml");
    } catch (UnmarshalException e) {
      exceptionThrown = true;
      System.out.println("Good news! Caught this exception, as expected: " + e.toString());
    } finally {
      assertTrue(exceptionThrown);
    }
  }
  
  @Test
  public void testLoadProject() throws Exception {
    final XMLProjectLoader loader = new XMLProjectLoader(WORKSPACE);
    final ProjectObj proj = loader.loadProject(BASE_EXAMPLES_DIR + "/CycModelDescriptionExample1.xml");
    assertNotNull(proj);
    assertEquals("TestAppThingamabob/", proj.getName());
    assertEquals(1, proj.getPackages().size());
    assertEquals("com.cyc.api.test", proj.getPackages().get(0).getName());
    assertEquals(WORKSPACE + "/", proj.getWorkspace());
    assertEquals(WORKSPACE + "/TestAppThingamabob/src/main/java/", proj.getDirName());
  }
  
  /*
  @Test
  public void testCreateProject() throws Exception {
    final XMLProjectLoader loader = new XMLProjectLoader(WORKSPACE);
    final ProjectObj proj = loader.loadProject(BASE_EXAMPLES_DIR + "/CycModelDescriptionExample1.xml");
    proj.generateProject();
    assertTrue(true);
  }
  */
  
  
  // Internal
  
  public static final String BASE_EXAMPLES_DIR = "src/main/resources/xml/samples";
  public static final String WORKSPACE = "/host/arcturus/nwinant/src/head/cycorp/cyc/java/webapp";
}