package com.cyc.library.mapi.templates.web;

import com.cyc.library.mapi.templates.BaseTemplateTest;
import com.cyc.library.mapi.xml.XMLProjectLoader;
import com.cyc.library.mapi.xml.XMLProjectLoaderTest;
import com.cyc.library.model.ProjectObj;
import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nwinant
 */
public class WebXmlTemplateTest extends BaseTemplateTest {
  
  @Test
  public void testGetTargetFile() throws Exception {
    final XMLProjectLoader loader = new XMLProjectLoader(XMLProjectLoaderTest.WORKSPACE);
    final ProjectObj project = loader.loadProject(XMLProjectLoaderTest.BASE_EXAMPLES_DIR + "/CycModelDescriptionExample1.xml");
    final WebXmlTemplate template = new WebXmlTemplate(project);
    System.out.println(template.getTargetFile());
    assertEquals(new File("src/main/webapp/WEB-INF/web.xml"), template.getTargetFile());
  }
  
  @Test
  public void testGenerateString() throws Exception {
    final XMLProjectLoader loader = new XMLProjectLoader(XMLProjectLoaderTest.WORKSPACE);
    final ProjectObj project = loader.loadProject(XMLProjectLoaderTest.BASE_EXAMPLES_DIR + "/CycModelDescriptionExample1.xml");
    final WebXmlTemplate template = new WebXmlTemplate(project);
    assertEqualsFileContents(getTestFile("generate-string.txt"), template.generateString());
  }
}