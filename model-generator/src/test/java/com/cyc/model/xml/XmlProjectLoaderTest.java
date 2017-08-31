package com.cyc.model.xml;

import com.cyc.model.objects.ProjectObj;
import java.io.IOException;
import javax.xml.bind.UnmarshalException;
import junit.framework.TestCase;
import org.junit.*;
import com.cyc.xsd.cycmodeldescription.*;
import java.util.List;

public class XmlProjectLoaderTest extends TestCase {

  @Before
  public void setUp() throws IOException {
  }

  @After
  public void tearDown() throws Exception {
  }

  // Tests
  @Test
  public void testloadXML() throws Exception {
    final XmlProjectLoader loader = new XmlProjectLoader(WORKSPACE);
    final CycModelDescription description = loader.loadXML(CMD_VALID);
    assertNotNull(description);

    assertNotNull(description.getProject());
    final Project project = description.getProject();
    assertEquals("ModelTestExample", project.getName());
    assertEquals("com.cyc.model.test", project.getPackage());
    assertEquals(null, project.getWorkspace());

    assertNotNull(description.getInterface());
    assertEquals(1, description.getInterface().size());
    final Interface iface = description.getInterface().get(0);
    assertEquals("HumanCyclist", iface.getCollection());
    assertNotNull(iface.getMethod());
    final List<Method> methods = iface.getMethod();
    assertEquals(4, methods.size());
    assertEquals("cyclistPrimaryProject", methods.get(0).getPredicate());
    assertEquals("cyclistPrimaryProject", methods.get(1).getPredicate());
    assertEquals("cyclistPrimaryProject", methods.get(2).getPredicate());
    assertEquals("groupsReviewer", methods.get(3).getPredicate());
  }

  @Test
  public void testLoadInvalidXML() throws Exception {
    boolean exceptionThrown = false;
    try {
      // This should throw an exception when it fails to validate...
      final XmlProjectLoader loader = new XmlProjectLoader();
      final CycModelDescription description = loader.loadXML(CMD_INVALID);
    } catch (UnmarshalException e) {
      exceptionThrown = true;
      System.out.println("Good news! Caught this exception, as expected: " + e.toString());
    } finally {
      assertTrue(exceptionThrown);
    }
  }

  @Test
  public void testLoadProject() throws Exception {
    final XmlProjectLoader loader = new XmlProjectLoader(WORKSPACE);
    final ProjectObj proj = loader.loadProject(CMD_VALID);
    assertNotNull(proj);
    assertEquals("ModelTestExample/", proj.getName());
    assertEquals(1, proj.getPackages().size());
    assertEquals("com.cyc.model.test", proj.getPackages().get(0).getName());
    assertEquals(WORKSPACE + "/", proj.getWorkspace());
    assertEquals(WORKSPACE + "/ModelTestExample/src/main/java/", proj.getDirName());
  }

  /*
   @Test
   public void testCreateProject() throws Exception {
   final XMLProjectLoader loader = new XMLProjectLoader(WORKSPACE);
   final ProjectObj proj = loader.loadProject(CMD_VALID);
   proj.generateProject();
   assertTrue(true);
   }
   */
  
  // Internal
    
  public static final String WORKSPACE = System.getProperty("java.io.tmpdir") + "/cyc-model-generator";
  public static final String BASE_EXAMPLES_DIR = "src/main/resources/xml/samples";
  public static final String CMD_VALID = BASE_EXAMPLES_DIR + "/CycModelDescriptionExample-valid.xml";
  public static final String CMD_INVALID = BASE_EXAMPLES_DIR + "/CycModelDescriptionExample-missing-package-attr.xml";
}
