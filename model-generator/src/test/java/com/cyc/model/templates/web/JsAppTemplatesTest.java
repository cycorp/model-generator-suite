package com.cyc.model.templates.web;

import com.cyc.model.objects.ProjectObj;
import com.cyc.model.templates.BaseTemplateTest;
import com.cyc.model.xml.XmlProjectLoader;
import com.cyc.model.xml.XmlProjectLoaderTest;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nwinant
 */
public class JsAppTemplatesTest extends BaseTemplateTest {
  
  @Before
  @Override
  public void setUp() throws Exception {
    super.setUp();
    final XmlProjectLoader loader = new XmlProjectLoader(XmlProjectLoaderTest.WORKSPACE);
    final ProjectObj project = loader.loadProject(XmlProjectLoaderTest.CMD_VALID);
    this.templates = new JsAppTemplates(project, "NAMESPACE", "appDir");
  }
  
  @Test
  public void testPrintSummary() {
    templates.printSummary();
    assertTrue(true); // TODO: could probably be a little more sophisticated...
  }
  
  @Test
  public void testGenerateString() throws Exception {
    final List<String> results = templates.generateString();
    assertEquals(2, results.size());
    assertEqualsFileContents(getTestFile("result-list"), results);
  }
  
  
  // Internal
  
  private JsAppTemplates templates;
}
